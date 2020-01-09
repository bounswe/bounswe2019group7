<template>
  <div class="Homepage">
    <b-row class="Currencies">
      <b-col v-for="[key, val] in map_currencyRates" sm="5" lg="2">
        <b-card  bg-variant="dark">
          <b-card-body align="center"class="pb-0">
            <h6  class="mb-0">{{key}}</h6>
            <p>{{val}}</p>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <b-row>
    <b-col class="Events" sm="6" lg="7">
    <div class="table">
      <h1 align="center">Economic Events</h1>
    <table>
    <thead>
      <tr>
        <th>Time</th>
        <th>Significance</th>
        <th>Event</th>
      </tr>
    </thead>
    <tbody>
    <tr v-for="event in get_rows()">
        <td>{{ event.stringDate}}</td>
        <td>{{ event.score+"/5"}}</td>
        <td>{{ event.title }}</td>
    </tr>
  </tbody>
</table>
<nav>
  <b-pagination  align="center" :total-rows="this.events.length" v-model="currentPage" :per-page="elementsPerPage">
  </b-pagination>
</nav>
</div>
</b-col>
<b-col sm="11" lg="5">
    <div class="Articles">
      <h1 align="center">Suggested Articles </h1>
  <b
    style="justify-content: right;"
    id="articleCard"
    v-for="item in this.articles"
    v-bind:key="item"
  >
      <b-card>
        <div slot="header">
          <router-link
            :to="{
              path: './article/' + item.uuid
            }"
            >{{ item.title }}</router-link
          >
          <p hidden>{{ item.uuid }}</p>
          <div class="card-header-actions">
            <b-badge variant="success"
              >{{ item.authorName }} {{ item.authorSurname }}</b-badge>
          </div>
        </div>
        {{ item.content }}
        <p style="color:grey; text-align: end; font-size: smaller;">
          {{ item.stringDate }}
        </p>
      </b-card>
  </b>
</div>
</b-col>
</b-row>
</div>
  </template>
<script>
import router from "../router";
export default {
  name: "Events",
  data() {
    return {
      isDisabled: false,
      form: {
        email: "",
        password: ""
      },
      currentPage: 1,
      elementsPerPage: 7,
      users: [],
      events: [],
      articles: [],
      currencies: ["USD/TRY", "EUR/TRY", "EUR/USD", "GBP/TRY", "USD/JPY", "EUR/GBP"],
      map_currencyRates: new Map()
    };
  },
  methods: {
    "get_rows": function get_rows() {
      var start = (this.currentPage - 1) * this.elementsPerPage;
      var end = start + this.elementsPerPage;
      return this.events.slice(start, end);
    },
  },
  async created() {
    for (var i in this.currencies) {
      var sourceCurrencyType = this.currencies[i].substring(0, 3);
      var targetCurrencyType = this.currencies[i].substring(4, 7);
      var params = "amount=0" + "&lastDays=1" + "&sourceCurrencyType=" + sourceCurrencyType + "&targetCurrencyType=" + targetCurrencyType;
      await this.$http.get("http://18.184.25.234:8090/currency/take-rates-last-days?" + params)
        .then((response) => {
          this.map_currencyRates.set(this.currencies[i], response.data.currencyConverterResources[0].rate.toFixed(4));
        });
    }
    this.$http.get('/main_page/get_feed')
      .then((response) => {
        if (response.status == 200) {
          this.events = response.data.suggestedEvents;
          this.users = response.data.suggestedUsers;
          this.articles = response.data.suggestedArticles;
        }
      });
  }
};
</script>
<style>
table {
  font-family: 'Arial';
  width: 600px;
  border-collapse: collapse;
  border: 3px solid #44475C;
  margin-left: auto;
  margin-right: auto;

}

table th {
  text-align: center;
  background: #30343D;
  color: #FFF;
  padding: 8px;
  min-width: 30px;
}

table td {
  text-align: center;
  padding: 8px;
  border-right: 2px solid #44475C;
}
</style>
