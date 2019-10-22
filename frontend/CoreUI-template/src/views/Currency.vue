<template>
    <div class="wrapper" id="wrapper">
      <div class="animated fadeIn">
        <b-row >
          <b-col cols="12" md="6">
            <b-card
              header-tag="header"
              footer-tag="footer">
              <div slot="header">
                <i class="fa fa-align-justify"></i><strong> Currency</strong>
              </div>
              <div> 
                <label>From:</label>
                <select id="from" class="form-control">
                    <option value="price1">EUR</option>
                    <option value="price2">GBP</option>
                    <option value="price3">USD</option>
                    <option value="price4">TRY</option>
                </select>
              </div>
              <div id="empty-div"> </div>
              <div> 
                <label>To:</label>
                <select id="to" class="form-control">
                    <option value="price5">EUR</option>
                    <option value="price6">GBP</option>
                    <option value="price7">USD</option>
                    <option value="price8">TRY</option>
                </select>
              </div>
              <div id="empty-div"> </div>
              <div>
                <label>Amount:</label>
                <input type="text" class="form-control" id="amount">
              </div>
              <div id="empty-div"> </div>
              <div id="convert-div">
                    <b-button type="submit" variant="primary" class="px-4" v-on:click="convert">Convert</b-button>
              </div>
              <div id="empty-div"> </div>
              <div id="result">
              </div>
              
            </b-card>
          </b-col>
        </b-row>
      </div>
    </div>
  </template>
  
<script>
    export default {
      name: "Currency",
      data: function() {
        return {
          errors: [],
          isDisabled: false,
          form: {
            email: "",
            password: ""
            }
        };
      },
      methods: {
        convert: function(event) {
            var input_value = document.getElementById("amount").value; 
            var e = document.getElementById("from");
            var from = e.options[e.selectedIndex].text;
            var e2 = document.getElementById("to");
            var to = e2.options[e2.selectedIndex].text;
            var params = "inputCurrencyType=" + from + "&outputCurrencyType=" + to + "&amount=" + input_value;
            var http = new XMLHttpRequest();
            http.open("GET", "http://100.26.202.213:8080/currency/convert"+"?"+params, true);
            http.setRequestHeader('inputCurrencyType', from);
            http.setRequestHeader('outputCurrencyType', to);
            http.setRequestHeader('amount', input_value);
            http.onreadystatechange = function () {
                if (this.readyState === 4) {
                    if ((this.status == 200) && (this.status < 300)) {
                        var json_object = JSON.parse(this.responseText);
                        var result = input_value + " " + from + " is " + parseFloat(json_object.amount).toFixed(2) + " " + to;
                        document.getElementById("result").innerHTML = result;
                    }
                }
            };
            http.send();
            return '';
        },
      }
    };
</script>

<style>
    #wrapper{
      display: block;
      margin-top: 10%;;
      margin-left: 25%;
      margin-right: 25%;
      width: 100%;
    }
    #empty-div{
      display: block;
      margin-bottom: 0.5cm;
    }
    #convert-div{
        text-align:center;
    }
    #result{
        text-align: center;
        font-size: x-large;
    }
</style>
  
