<template>
  <AppHeaderDropdown right no-caret>
    <template slot="header">
      <img src="img/avatars/user.png" class="img-avatar" />
    </template>\
    <template slot="dropdown">
      <b-dropdown-header tag="div" class="text-center">
        <strong>Settings</strong>
      </b-dropdown-header>
      <b-dropdown-item :to="'/profilePage'">
        <i class="fa fa-user" /> Profile
      </b-dropdown-item>
      <b-dropdown-item :to="'/userSettings'">
        <i class="fa fa-wrench" /> Settings
      </b-dropdown-item>
      <b-dropdown-item :to="'/tradingAccount'" v-if="seen">
        <i class="fa fa-usd" /> Trading Account
      </b-dropdown-item>
      <b-dropdown-item>
        <i class="fa fa-usd" /> Payments
      </b-dropdown-item>
      <b-dropdown-divider />
      <b-dropdown-item v-on:click="logout">
        <i class="fa fa-lock" /> Logout
      </b-dropdown-item>
    </template>
  </AppHeaderDropdown>
</template>

<script>
import { HeaderDropdown as AppHeaderDropdown } from "@coreui/vue";
export default {
  name: "DefaultHeaderDropdownAccnt",
  components: {
    AppHeaderDropdown
  },
  data() {
    return {
      seen: false,
      item: []
    };
  },
  methods: {
    logout: function() {
      //TODO delete the cookie
      localStorage.removeItem("token");
      localStorage.removeItem("notification");
      localStorage.removeItem("email");
      localStorage.removeItem("password");
      document.location.href = ""; //mainpage -- login
    }
  },
  mounted() {
    this.$http
      .get("http://100.26.202.213:8080/user_profile/self_profile", {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      })
      .then(
        response => {
          this.item = response.data;
          if (this.item.role == "BASIC_USER") {
            this.seen = false;
          } else {
            this.seen = true;
          }
        },
        error => {
          console.log("eerror");
        }
      );
  }
};
</script>
