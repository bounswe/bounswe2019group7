<template>
  <AppHeader fixed>
    <SidebarToggler class="d-lg-none" display="md" mobile />
    <b-link class="navbar-brand" to="#">
      <img
        class="navbar-brand-full"
        src="img/brand/eyeTradeLogo.d0e77fad.png"
        width="89"
        height="25"
        alt="CoreUI Logo"
      />
      <img
        class="navbar-brand-minimized"
        src="img/brand/eyeTradeLogo.d0e77fad.png"
        width="30"
        height="30"
        alt="CoreUI Logo"
      />
    </b-link>
    <SidebarToggler class="d-md-down-none" display="lg" :defaultOpen="true" />
    <b-navbar-nav class="d-md-down-none">
      <b-nav-item class="px-3" to="/dashboard"></b-nav-item>
    </b-navbar-nav>
    <b-navbar-nav class="ml-auto">

      <NotificationHeaderDropdown />
      <DefaultHeaderDropdownAccnt />
      <b-nav-item class="d-md-down-none">
        <!-- Empty space. -->
      </b-nav-item>
    </b-navbar-nav>

    <!--<AsideToggler class="d-lg-none" mobile />-->
  </AppHeader>
</template>
<script>
import { Header as AppHeader, SidebarToggler, AsideToggler } from "@coreui/vue";
import DefaultHeaderDropdownAccnt from "./DefaultHeaderDropdownAccnt";
import NotificationHeaderDropdown from "./NotificationHeaderDropdown";

export default {
  name: "DefaultHeader",
  components: {
    AsideToggler,
    AppHeader,
    DefaultHeaderDropdownAccnt,
    NotificationHeaderDropdown,
    SidebarToggler
  },
  methods: {
        notify: function() {
            //TODO get notifications from endpoint
            console.log("Get notifications")
        },
      }
};
</script>
