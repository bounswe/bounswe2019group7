<template>
  <div class="animated fadeIn">
      <b-card id="upperCard">
        <b-card-body>
          <b-row style="justify-content:center">
            <b-col sm="5" lg="5">
              <b-form @submit="createPortfolio">
                <h2>Create a new Portfolio</h2>
                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="cui-tags icons font-xl d-block"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Portfolio Name"
                    autocomplete="title"
                    v-model="form.portfolioName"
                  />
                </b-input-group>
                <b-button :disabled="isDisabled" type="submit" variant="primary" class="pull-right">Create</b-button>
              </b-form>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>
      <h1 align="center">My Portfolios</h1>
      <b
        class="row"
        style="justify-content: center;"
        id="articleCard"
        v-for="portfolio in portfolios":key="portfolio.id"
      >
        <b-col sm="6" md="9">
          <b-card>
            <div slot="header">
              <h1>{{ portfolio.name }}</h1>
              <div class="card-header-actions">
                <b-badge  v-for="currency in portfolio.currencyTypes" variant="success">{{ currency}}</b-badge>
              </div>
            </div>
            <div style="margin-bottom:20px;">
              <h5>Add a new Trading Equipment:</h5>
              <select v-bind:id="portfolio.name" class="form-control">
                  <option value="price1">USD</option>
                  <option value="price2">TRY</option>
                  <option value="price3">EUR</option>
                  <option value="price4">GBP</option>
                  <option value="price5">JPY</option>
                  <option value="price6">CNY</option>
              </select>
              <b-button v-on:click="addTradingEquipment(portfolio.id,portfolio.name)"variant="primary" class="pull-right">Add to Portfolio</b-button>
            </div>
            <b-row class="Currencies">
              <b-col v-for="item in portfolio.currencyPairs" sm="6" lg="3">
                <b-card  bg-variant="dark">
                  <b-card-body align="center"class="pb-0">
                    <h5 class="mb-0">{{item.targetType}}/{{item.baseType}}</h5>
                    <h6>{{item.rate.toFixed(4)}}</h6>
                  </b-card-body>
                </b-card>
              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b>
  </div>
</template>
<script>
export default {
  name:"Events",
  data() {
    return {
      isDisabled: false,
      form: {
        email: "",
        password: ""
      },
      portfolios: []
    };
  },
  methods: {
    "createPortfolio": function createPortfolio() {
      this.$http
      .post('/portfolio/create',
      {},
      {
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        params: {
          portfolioName: this.form.portfolioName,
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          this.$router.go("/portfolios");
        }else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })
      this.forceRerender();

    },
    "addTradingEquipment": function addTradingEquipment(portfolioID,portfolioName) {
      var currencies=(document.getElementById(portfolioName));
      var baseCurrency = currencies.options[currencies.selectedIndex].text;
      this.$http
      .post('/portfolio/add_currency_to_portfolio',
      {},
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          BaseCurrencyType: baseCurrency,
        },
        params: {
          portfolioID : portfolioID,
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          this.$router.go("/portfolios");
        }else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })

    }

  },
  mounted() {
    this.events = [];
    this.$http
      .get("http://100.26.202.213:8080/portfolio/get_self_portfolios", {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      })
      .then(
        response => {
          if(response.status==200){
          this.portfolios = response.data.portfolios;
        }
        },
      );
  }
};
</script>
<style>
</style>
