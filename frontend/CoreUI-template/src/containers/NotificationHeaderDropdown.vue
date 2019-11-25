<template>
  <AppHeaderDropdown right no-caret>
    <template slot="header" v-on:click="update">
      <i class="icon-bell" v-on:click="update"></i>
        <b-badge pill variant="danger" id="numberOfNotifications" v-on:click="update"></b-badge>
    </template>\
    <template slot="dropdown" v-on:click="update">
        <p id="ddown"></p>
    </template>
  </AppHeaderDropdown>
</template>

<script>
import { HeaderDropdown as AppHeaderDropdown } from "@coreui/vue";
export default {
  name: "NotificationHeaderDropdown",
  components: {
    AppHeaderDropdown
  },
  methods: {
        notify: function() {
            var token = localStorage.getItem("token");
            var http = new XMLHttpRequest();
            http.open("GET", "http://100.26.202.213:8080/notification/self_notifications", true);
            http.setRequestHeader('Authorization', token);
            http.onreadystatechange = function () {
                if (this.readyState === 4) {
                    if ((this.status == 200) && (this.status < 300)) {
                        var json_object = JSON.parse(this.responseText);
                        var result = "";
                        for(var i = 0; i<json_object.length;i++){
                          result = "\n" + json_object[i].followerName + " " + json_object[i].followerSurname + " followed you." + result;
                        }
                        document.getElementById("ddown").innerHTML = result;
                        
                        if (localStorage.getItem("notification") != json_object.length){
                          var old = localStorage.getItem("notification")
                          localStorage.setItem("notification", json_object.length);
                          if (old){
                            document.getElementById("numberOfNotifications").innerHTML = json_object.length - old; 
                          }else{
                            document.getElementById("numberOfNotifications").innerHTML = json_object.length; 
                          }
                        } 
                        
                    }
                }
            };
            http.send();
            return '';
        },
        update: function() {
             document.getElementById("numberOfNotifications").innerHTML = ""; 
        },
  },
  mounted(){
    this.notify()
  },
};
</script>
