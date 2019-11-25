<script>
import { Line } from 'vue-chartjs'
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips'
import { hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities'

export default {
  components: {
    hexToRgba,
    CustomTooltips
  },
  extends: Line,
  mounted () {
            var amount = 1;
            var last_days=7;
            var from ="USD";
            var to = "TRY";

            var params = "amount=" + amount + "&lastDays=" + last_days+ "&sourceCurrencyType=" + from + "&targetCurrencyType="+ to;
            var http = new XMLHttpRequest();
            http.open("GET", "http://100.26.202.213:8080/currency/take-rates-last-days"+"?"+params, true);
            http.setRequestHeader('sourceCurrencyType', from);
            http.setRequestHeader('targetCurrencyType', to);
            http.setRequestHeader('amount', amount);
            http.setRequestHeader('lastDays', last_days);
            var that = this;
            http.onreadystatechange = function () {
                if (this.readyState === 4) {
                    if ((this.status == 200) && (this.status < 300)) {
                        var json_object = JSON.parse(this.responseText);
                        var labels = [];
                        var dataset = []
                        for(var i = 0; i< json_object.currencyConverterResources.length; i++){
                          dataset.push(json_object.currencyConverterResources[i].amount)
                          labels.push(json_object.currencyConverterResources[i].date)
                          that.renderChart({
                            labels: labels,
                            datasets: [
                                {
                                label: from + " - " + to,
                                backgroundColor: '#f87979',
                                data: dataset
                                }
                            ]
                            }, {responsive: true, maintainAspectRatio: false})
                            }     
                     }
                }
            };
            http.send();
            return '';

  }
}
</script>
