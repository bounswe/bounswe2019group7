
new Vue({
  el: '#vue-app',
  data: {
    initialtext : 'Enter a number',
    result: "Check console!"
  },
  methods: {
    isOdd: function(event) {
      var input_value = this.$refs.input.value
      var http = new XMLHttpRequest();
      http.open("GET", "http://100.26.202.213:8080/api/example", true);
      http.setRequestHeader('number', input_value);
      http.onreadystatechange = function () {
        if (this.readyState === 4) {
          if ((this.status == 200) && (this.status < 300)) {
            var json_object = JSON.parse(this.responseText);
            if (json_object.isOdd){
              console.log("ODD");
            }else{
              console.log("EVEN");
            }
          }
      }};
      http.send();
      return '';
    },
  }

});
