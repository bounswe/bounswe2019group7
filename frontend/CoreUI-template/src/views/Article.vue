<template>
  <div class="animated fadeIn">
    <b class="row" style="justify-content: center;" id="articleCard">
      <b-col sm="6" md="10">
        <b-card>
          <div slot="header">
            {{ item.title }}
            <div class="card-header-actions">
              <star-rating
                :star-size="20"
                active-color="#20a8d8"
                v-model="rating"
                @rating-selected="setRating"
              ></star-rating>
            </div>
          </div>
          <div class="row">
            <p>Title:</p>
            <p style="color:grey">{{ item.title }}</p>
          </div>
          <div class="row">
            Author:
            <p style="color:grey">{{ item.authorName }} {{ item.authorSurname }}</p>
          </div>
          <div class="row">
            Author Email:
            <p style="color:grey">{{ item.authorEmail }}</p>
          </div>
          <div class="row">
            Publish Date:
            <p style="color:grey">{{ item.stringDate }}</p>
          </div>
          <div class="row">
            Score:
            <p style="color:grey">{{ item.score }}</p>
          </div>
          <div class="row">
            Abstract:
            <p style="color:grey">{{ item.contentAbstract }}</p>
          </div>
          <div class="row">
            Content:
            <p style="color:grey">{{ item.content }}</p>
          </div>
        </b-card>
      </b-col>
    </b>
  </div>
</template>
<script>
import router from "../router";
import StarRating from "vue-star-rating";

export default {
  components: {
    StarRating
  },
  mounted() {
    this.id = this.$route.params.id;
    this.$http
      .get(
        "http://100.26.202.213:8080/article/article?id=" + this.$route.params.id
      )
      .then(
        response => {
          console.log(response);
          this.item = response.data;
          this.rating = this.item.score;
        },
        error => {
          console.log("Error");
        }
      );
  },
  data() {
    return {
      item: [],
      rating: 4.0
    };
  },
  methods: {
    setRating() {
      this.$http
        .post(
          "/article/give_point",
          {},
          {
            headers: {
              Authorization: localStorage.getItem("token")
            },
            params: {
              articleID: this.item.uuid,
              score: this.rating
            }
          }
        )
        .then(
          response => {
            location.reload();
          },
          error => {
            console.log("eerror");
          }
        );
    }
  }
};
</script>
