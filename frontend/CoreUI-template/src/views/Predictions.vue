<template>
  <div class="animated fadeIn">
      <b-card id="upperCard">
        <b-card-body>
          <b-row style="justify-content:center">
            <b-col sm="5" lg="5">
              <b-form @submit="makePrediction">
                <h3 align="center">Make a new Prediction</h3>
                <div style="margin-bottom:20px;">
                  <h6>Select a base Trading Equipment:</h6>
                  <select  id="base" class="form-control">
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
                  <select  id="target" class="form-control" >
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
                </div>
                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="icon-arrow-left-circle"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="double"
                    class="form-control"
                    placeholder="Lower Bound For Prediction"
                    autocomplete="title"
                    v-model="form.lowerBoundOfPredictedRate"
                  />
                </b-input-group>
                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="icon-arrow-right-circle"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="double"
                    class="form-control"
                    placeholder="Upper Bound For Prediction"
                    autocomplete="title"
                    v-model="form.upperBoundOfPredictedRate"
                  />
                </b-input-group>

                <b-form-group
                  label="Targetted Day" label-for="date"
                  :label-cols="3"
                  >
                  <b-form-input type="date" id="date"></b-form-input>
                </b-form-group>
                <b-button :disabled="isDisabled" type="submit" variant="primary" class="pull-right">Make a Prediction</b-button>
              </b-form>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>
      </b>
      <h3 align="center">My Predictions</h3>
      <table>
      <thead>
        <tr>
          <th>Prediction For</th>
          <th>Prediction Time</th>
          <th>Lower Bound</th>
          <th>Upper Bound</th>
          <th>Target Date</th>
          <th>Status</th>
          <th>Edit</th>
        </tr>
      </thead>
      <tbody>
      <tr v-for="item in predictions.futurePredictions">
          <td>{{item.dividendCurrencyType}}/{{item.divisorCurrencyType}}</td>
          <td>{{item.predictionDate}}</td>
          <td>{{item.lowerBoundOfPredictedRate}}</td>
          <td>{{item.upperBoundOfPredictedRate}}</td>
          <td>{{item.targetDayForPrediction}}</td>
          <td>
          <b-badge variant="primary"
            >{{item.predictionStatus}}</b-badge
          >
        </td>
          <b-button v-bind:value="item.id" v-on:click="deletePrediction($event)"  variant="danger" style="margin-left:5px">Delete</b-button>

      </tr>
      <tr v-for="item in predictions.successfulPredictions">
          <td>{{item.dividendCurrencyType}}/{{item.divisorCurrencyType}}</td>
          <td>{{item.predictionDate}}</td>
          <td>{{item.lowerBoundOfPredictedRate}}</td>
          <td>{{item.upperBoundOfPredictedRate}}</td>
          <td>{{item.targetDayForPrediction}}</td>
          <td>
          <b-badge variant="success"
            >{{item.predictionStatus}}</b-badge
          >
        </td>
      </tr>
      <tr v-for="item in predictions.failedPredictions">
          <td>{{item.dividendCurrencyType}}/{{item.divisorCurrencyType}}</td>
          <td>{{item.predictionDate}}</td>
          <td>{{item.lowerBoundOfPredictedRate}}</td>
          <td>{{item.upperBoundOfPredictedRate}}</td>
          <td>{{item.targetDayForPrediction}}</td>
          <td>
          <b-badge variant="danger"
            >{{item.predictionStatus}}</b-badge
          >
        </td>
      </tr>
    </tbody>
  </table>
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
      predictions: []
    };
  },
  methods: {
    "makePrediction": function makePrediction() {
      var base_currencies=(document.getElementById("base"));
      var baseCurrency = base_currencies.options[base_currencies.selectedIndex].text;
      var target_currencies=(document.getElementById("target"));
      var targetCurrency = target_currencies.options[target_currencies.selectedIndex].text;
      var date=document.getElementById("date").value.toString();

      this.$http
      .post('/prediction/create',
      {
        dividendCurrencyType:baseCurrency,
        divisorCurrencyType: targetCurrency,
        lowerBoundOfPredictedRate: this.form.lowerBoundOfPredictedRate ,
        targetDayForPrediction:date,
        upperBoundOfPredictedRate: this.form.upperBoundOfPredictedRate
      },
      {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          location.reload();
        }else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })
      this.forceRerender();

    },
    "deletePrediction": function deletePrediction(e) {
      var predictionID=e.target.value;
      this.$http
      .delete('/prediction/delete',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
            predictionId : predictionID
        },
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
      .get('/prediction/get_self_predictions', {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      })
      .then(
        response => {
          if(response.status==200){

          this.predictions = response.data;
        }
        },
      );
  }
};
</script>
<style>
table {
  font-family: 'Arial';
  width: 700px;
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
