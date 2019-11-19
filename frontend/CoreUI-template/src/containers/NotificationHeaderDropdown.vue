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
            console.log("notify"); 
            document.getElementById("ddown").innerHTML = "No Notifications."; 
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
