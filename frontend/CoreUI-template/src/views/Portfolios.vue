<template>
  <div class="animated fadeIn">
      <b-card id="upperCard">
        <b-card-body>
          <b-row style="justify-content:center">
            <b-col sm="5" lg="5">
              <b-form @submit="createPortfolio">
                <h3 align="center">Create a new Portfolio</h3>
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
      <h3 align="center">My Portfolios</h3>
      <b
        class="row"
        style="justify-content: center;"
        id="portfolioCard"
        v-for="portfolio in portfolios":key="portfolio.id"
      >
        <b-col sm="6" md="9">
          <b-card>
            <div slot="header">


              {{ portfolio.name }}
              <div class="card-header-actions">
                <b-button v-on:click="deletePortfolio(portfolio.id)"  variant="danger">Delete</b-button>
              </div>
            </div>
            <div style="margin-bottom:20px;">
              <h6>Select a base Trading Equipment:</h6>
              <select v-bind:id="portfolio.name" class="form-control">
                  <option value="price1">USD</option>
                  <option value="price2">TRY</option>
                  <option value="price3">EUR</option>
                  <option value="price4">GBP</option>
                  <option value="price5">JPY</option>
                  <option value="price6">CNY</option>
                  <option value="price6">BTC</option>
                  <option value="price6">ETH</option>
                  <option value="price6">XRP</option>
                  <option value="price6">LTC</option>
                  <option value="price6">XMR</option>
              </select>
              <h6 style="margin-top:5px">Select a target Trading Equipment:</h6>
              <select v-bind:id="portfolio.id" class="form-control" >
                  <option value="price1">USD</option>
                  <option value="price2">TRY</option>
                  <option value="price3">EUR</option>
                  <option value="price4">GBP</option>
                  <option value="price5">JPY</option>
                  <option value="price6">CNY</option>
                  <option value="price6">BTC</option>
                  <option value="price6">ETH</option>
                  <option value="price6">XRP</option>
                  <option value="price6">LTC</option>
                  <option value="price6">XMR</option>
              </select>
              <b-button v-on:click="addTradingEquipment(portfolio.id,portfolio.name)"variant="primary" class="pull-right" style="margin-top:5px">Add to Portfolio</b-button>
            </div>
            <b-row class="Currencies">
              <b-col v-for="item in portfolio.currencyPairs" sm="6" lg="3">
                <b-card  bg-variant="dark">
                  <b-card-body align="center"class="pb-0">
                    <h5 class="mb-0">{{item.targetType}}/{{item.baseType}}</h5>
                    <h6>{{item.rate.toFixed(4)}}</h6>
                    <b-button v-on:click="deleteTradingEquipment(item.targetType,item.baseType,portfolio.id)"variant="danger" class="pb-0">Delete</b-button>
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
      var base_currencies=(document.getElementById(portfolioName));
      var baseCurrency = base_currencies.options[base_currencies.selectedIndex].text;
      var target_currencies=(document.getElementById(portfolioID));
      var targetCurrency = target_currencies.options[target_currencies.selectedIndex].text;
      this.$http
      .post('/portfolio/add_currency_pair_to_portfolio',
      {},
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          FirstCurrencyType: targetCurrency,
          SecondCurrencyType:baseCurrency
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

    },
    "deleteTradingEquipment": function deleteTradingEquipment(targetType,baseType,portfolioID) {
      this.$http
      .delete('/portfolio/remove_currency_pair_from_portfolio',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          FirstCurrencyType: baseType,
          SecondCurrencyType:targetType
        },
        params: {
          portfolioID : portfolioID,
        }
      }
      )
      .then(response => {

        if(response.status == 200){
          location.reload();
        }
        else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })

    },
    "deletePortfolio": function deletePortfolio(portfolioID) {
      this.$http
      .delete('/portfolio/delete',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        params: {
          portfolioId : portfolioID,
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          location.reload();
        }
        else{
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
