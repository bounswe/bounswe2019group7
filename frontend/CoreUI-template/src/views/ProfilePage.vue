<template>
  <div class="animated fadeIn">
    <b-row>
      <b-card :no-body="true" id="upperCard">
        <b-card-body class="p-5 clearfix">
          <b-row>
            <b-col sm="4">
              <div class="h2 text-primary mb-0 mt-2">{{ item.role }}</div>
              <img
                src="img/avatars/user.png"
                class="img-avatar"
                alt="admin@bootstrapmaster.com"
              />
              <div class="h2 text-primary mb-0 mt-2">
                {{ item.name }} {{ item.surname }}
              </div>
              <div class="text-muted font-weight-bold font-xs">
                Email: {{ item.email }}
              </div>
              <div class="text-muted font-weight-bold font-xs">
                Country: {{ item.city }} / {{ item.country }}
              </div>
            </b-col>
            <b-col sm="4">
              <b-card>
                <div class="h1 text-muted text-right mb-0">
                  <i class="icon-pie-chart"></i>
                </div>
                <div class="h4 mb-0">28%</div>
                <small class="text-muted text-uppercase font-weight-bold"
                  >Prediction Success Rate</small
                >
                <b-progress
                  height="{}"
                  class="progress-xs mt-3 mb-0"
                  :value="25"
                />
              </b-card>
              <router-link
                :to="{
                  path: '/portfolios'
                }"
              >
                <b-button
                  size="lg"
                  variant="primary"
                  block
                  >{{portfolio}}</b-button
                >
              </router-link>

              <router-link
                :to="{
                  path: '/otherPredictions/'+ item.email
                }"
              >
                <b-button
                  size="lg"
                  v-if="seen"
                  variant="primary"
                  style="margin-top:5px"
                  block
                  >Predictions</b-button
                >
              </router-link>

            </b-col>
            <b-col sm="4">
              <b-button v-on:click="followUser(item.email)" v-if="seen" size="md" variant="primary" block
                >Follow</b-button
              >
              <b-button v-on:click="unfollowUser(item.email)" v-if="seen" size="md" variant="danger" block
                >Unfollow</b-button>
              <br />
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-eye"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example
                      :data="[65, 59, 84, 84, 51, 55, 40]"
                    />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">{{ item.followerCount }}</div>
                    <div class="d-flex flex-column text-md-center">
                      <div>
                        <b-btn v-on:click="getFollowers(item.email)"  id="popoverButton-sync" variant="primary" >Followers</b-btn>
                      </div>
                      <div class="p-2">
                        <b-popover placement="bottom" show.sync="show" target="popoverButton-sync" title="Followers">
                          <strong v-for="item in this.usersFollowers">{{item.name}} {{item.surname}} <br></br></strong>
                        </b-popover>
                      </div>
                    </div>
                  </div>
                  <div>
                    <div class="text-value">{{ item.followingCount }}</div>
                    <div class="d-flex flex-column text-md-center">
                      <div>
                        <b-btn v-on:click="getFollowings(item.email)"  id="popoverButton-sync2" variant="primary" >Followings</b-btn>
                      </div>
                      <div class="p-2">
                        <b-popover placement="bottom" show.sync="getFollowings" target="popoverButton-sync2" title="Followings">
                          <strong v-for="item in this.usersFollowings">{{item.name}} {{item.surname}} <br></br></strong>
                        </b-popover>
                      </div>
                    </div>
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
          <router-link
            :to="{
              path: './articleForm'
            }"
          >
            <b-button
              size="lg"
              variant="primary"
              style="margin-bottom:1%;"
              v-if="articleButton"
              >Create an Article</b-button
            >
          </router-link>
          <b-button
            size="lg"
            variant="primary"
            style="margin-bottom:1%; margin-left:1%"
            v-if="seenTop"
            v-on:click="showTopArticles"
            >See Top Articles</b-button
          >
          <b-button
            size="lg"
            variant="primary"
            style="margin-bottom:1%;  margin-left:1%"
            v-if="seenAll"
            v-on:click="showAllArticles"
            >See All Articles</b-button
          >
          <b-tabs>
            <b-tab title="Articles" active>
              <b-col lg="12">
                <c-table
                  :table-data="items"
                  :fields="fields"
                  caption="Article Tables"
                >
                  <template v-slot:cell(details)="data">
                    <!-- `data.value` is the value after formatted by the Formatter -->
                    <a
                      :href="
                        `#${data.value.replace(/[^a-z]+/i, '-').toLowerCase()}`
                      "
                      >{{ data.value }}</a
                    >
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

const someData = () => shuffleArray([{}]);

export default {
  name: "tables",
  data() {
    return {
      selected: [], // Must be an array reference!
      show: true,
      items: someData,
      itemsArray: someData(),
      fields: [
        { key: "title", label: "Title", sortable: true, formatter: "uuid" },
        { key: "contentAbstract", label: "Abstract" },
        { key: "stringDate", label: "Publish Date" }
      ],
      item: [],
      seen: true,
      portfolio: "Portfolio",
      articleButton: false,
      articles: [],
      seenTop: true,
      seenAll: false,
      usersFollowers:[],
      usersFollowings:[],
      show: false

    };
  },
  components: {
    SocialBoxChartExample,
    cTable
  },
  methods: {
    showTopArticles() {
      this.seenAll = true;
      this.seenTop = false;
      if (this.portfolio == "My Portfolio") {
        this.$http
          .get("http://100.26.202.213:8080/article/self_top_articles", {
            headers: {
              Authorization: localStorage.getItem("token")
            }
          })
          .then(
            response => {
              this.items = response.data.articles;
            },
            error => {
              console.log("error");
            }
          );
      } else {
        this.$http
          .get(
            "http://100.26.202.213:8080/article/user_top_articles?userEmail=" +
              this.$route.params.email
          )
          .then(
            response => {
              this.items = response.data.articles;
            },
            error => {
              console.log("error");
            }
          );
      }
    },
    showAllArticles() {
      this.seenAll = false;
      this.seenTop = true;
      if (this.portfolio == "My Portfolio") {
        this.$http
          .get("http://100.26.202.213:8080/article/self_articles", {
            headers: {
              Authorization: localStorage.getItem("token")
            }
          })
          .then(
            response => {
              this.items = response.data.articles;
            },
            error => {
              console.log("error");
            }
          );
      } else {
        this.$http
          .get(
            "http://100.26.202.213:8080/article/user_articles?userEmail=" +
              this.$route.params.email
          )
          .then(
            response => {
              this.items = response.data.articles;
            },
            error => {
              console.log("error");
            }
          );
      }
    },
    followUser(email) {
      this.$http
      .post('/user_following/follow',
      {},
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          followingUserEmail :email
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
    },
    unfollowUser(email) {
      this.$http
      .delete('/user_following/unfollow',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          followingUserEmail :email
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
    },
    getFollowers(mail){
      this.$http
      .get('/user_following/get_followers',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          otherUserEmail: mail
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          this.usersFollowers=response.data;
        }else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })
    },
    getFollowings(mail){
      this.$http
      .get('/user_following/get_followings',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
          otherUserEmail: mail
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          this.usersFollowings=response.data;
        }else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })
    }

  },
   mounted() {
    if (this.$route.params.email == null) {
      this.seen = !this.seen;
      this.portfolio = "My Portfolio";
      this.articleButton = true;
      this.$http
        .get("http://100.26.202.213:8080/user_profile/self_profile", {
          headers: {
            Authorization: localStorage.getItem("token")
          }
        })
        .then(
          response => {
            this.item = response.data;

          },
          error => {
            console.log("eerror");
          }
        );
      this.$http
        .get("http://100.26.202.213:8080/article/self_articles", {
          headers: {
            Authorization: localStorage.getItem("token")
          }
        })
        .then(
          response => {
            this.items = response.data.articles;
          },
          error => {
            console.log("error");
          }
        );
    } else {
      this.$http
        .get("http://100.26.202.213:8080/user_profile/other_profile", {
          headers: {
            Authorization: localStorage.getItem("token"),
            email: this.$route.params.email
          }
        })
        .then(
          response => {
            this.item = response.data;
          },
          error => {
            console.log("eerror");
          }
        );
      this.$http
        .get(
          "http://100.26.202.213:8080/article/user_articles?userEmail=" +
            this.$route.params.email
        )
        .then(
          response => {
            this.items = response.data.articles;
          },
          error => {
            console.log("error");
          }
        );
    }
    let user = JSON.parse(localStorage.getItem("user"));
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
