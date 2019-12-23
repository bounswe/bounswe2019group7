<template>
  <div class="animated fadeIn">
      <h3 align="center">Predictions</h3>
      <table>
      <thead>
        <tr>
          <th>Prediction For</th>
          <th>Prediction Time</th>
          <th>Lower Bound</th>
          <th>Upper Bound</th>
          <th>Target Date</th>
          <th>Status</th>
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
  },

  mounted() {
    if(this.$route.params.email != null){
    this.$http
      .get('/prediction/get_user_predictions', {
        headers: {
          Authorization: localStorage.getItem("token"),
          otherUserEmail :this.$route.params.email
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
