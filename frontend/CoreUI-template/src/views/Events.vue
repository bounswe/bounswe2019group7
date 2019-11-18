<template>
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
      currentPage: 1,
      elementsPerPage: 7,
      events: []
    };
  },
  methods: {
    "get_rows": function get_rows() {
      var start = (this.currentPage - 1) * this.elementsPerPage;
      var end = start + this.elementsPerPage;
      return this.events.slice(start, end);
    },
  },
  created() {
    this.events = [];
    this.$http.get('/event/get_events')
      .then((response) => {
        if(response.status==200){
        this.events = response.data.instances;
      }
      });
  }
};
</script>
<style>
table {
  font-family: 'Arial';
  width: 750px;
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
.pagination {
  font-family: 'Arial', sans-serif;
  text-align: right;
  width: 750px;
  padding: 8px;
  margin-left: auto;
  margin-right: auto;
}

</style>
