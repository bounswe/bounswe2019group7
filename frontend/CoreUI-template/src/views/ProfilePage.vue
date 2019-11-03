<template>
  <div class="animated fadeIn">
    <b-row>
      <b-card :no-body="true" id="upperCard">
        <b-card-body class="p-5 clearfix">
          <b-row>
            <b-col sm="4">
              <img src="img/avatars/user.png" class="img-avatar" alt="admin@bootstrapmaster.com" />
              <div class="h2 text-primary mb-0 mt-2">Name Surname</div>
              <div class="text-muted text-uppercase font-weight-bold font-xs">(Required information)</div>
            </b-col>
            <b-col sm="4">
              <b-card>
                <div class="h1 text-muted text-right mb-0">
                  <i class="icon-pie-chart"></i>
                </div>
                <div class="h4 mb-0">28%</div>
                <small class="text-muted text-uppercase font-weight-bold">Prediction Success Rate</small>
                <b-progress height="{}" class="progress-xs mt-3 mb-0" :value="25" />
              </b-card>
              <b-button size="lg" variant="primary" block>Portfolios</b-button>
              <b-button size="lg" variant="primary" block>Create an Alarm</b-button>
            </b-col>
            <b-col sm="4">
              <b-button size="lg" variant="primary" block>Follow</b-button>
              <br />
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-eye"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">89k</div>
                    <div class="text-uppercase text-muted small">Followers</div>
                  </div>
                  <div>
                    <div class="text-value">459</div>
                    <div class="text-uppercase text-muted small">Following</div>
                  </div>
                </div>
              </div>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>
    </b-row>
    <b-row>
      <b-card :no-body="true" id="lowerCard">
        <b-card-body class="p-5 clearfix">
          <b-tabs>
            <b-tab title="Articles" active>
              <b-col lg="12">
                <c-table :table-data="items" :fields="fields" caption="Article Tables">
                  <template v-slot:cell(details)="data">
                    <!-- `data.value` is the value after formatted by the Formatter -->
                    <a
                      :href="`#${data.value.replace(/[^a-z]+/i,'-').toLowerCase()}`"
                    >{{ data.value }}</a>
                  </template>
                </c-table>
              </b-col>
            </b-tab>
          </b-tabs>
        </b-card-body>
      </b-card>
    </b-row>
  </div>
</template>

<script>
import SocialBoxChartExample from "./dashboard/SocialBoxChartExample";
import { shuffleArray } from "@/shared/utils";
import cTable from "./base/Table.vue";

const someData = () =>
  shuffleArray([
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      description: "",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      description: "",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    },
    {
      title: "Samppa Nori",
      publishDate: "2012/01/01",
      details: "Member"
    }
  ]);

export default {
  name: "tables",
  data() {
    return {
      selected: [], // Must be an array reference!
      show: true,
      items: someData,
      itemsArray: someData(),
      fields: [
        { key: "title", label: "Title", sortable: true },
        { key: "description" },
        { key: "publishDate", label: "Publish Date" },
        { key: "details", label: "See Details", formatter: "fullName" }
      ]
    };
  },
  components: {
    SocialBoxChartExample,
    cTable
  },
  methods: {
    click() {
      // do nothing
    }
  }
};
</script>

<style scoped>
.img-avatar {
  width: 50%;
}
#upperCard {
  width: 100%;
}
#lowerCard {
  width: 100%;
}
#followersCard {
  margin-top: 2%;
  height: 73%;
}
</style>
