<template>
  <div class="animated fadeIn">
    <div class="row" style="margin-bottom:1%">
      <b-button
        size="lg"
        variant="primary"
        block
        v-if="seenTop"
        v-on:click="showTopArticles"
        >See Top Articles</b-button
      >
      <b-button
        size="lg"
        variant="primary"
        block
        v-if="seenAll"
        v-on:click="showAllArticles"
        >See All Articles</b-button
      >
    </div>
    <b
      class="row"
      style="justify-content: center;"
      id="articleCard"
      v-for="item in items"
      v-bind:key="item"
    >
      <b-col sm="6" md="10">
        <b-card>
          <div slot="header">
            <router-link
              :to="{
                path: './article/' + item.uuid
              }"
              >{{ item.title }}</router-link
            >

            <p hidden>{{ item.uuid }}</p>
            <div class="card-header-actions">
              <b-badge variant="success"
                >{{ item.authorName }} {{ item.authorSurname }}</b-badge
              >
            </div>
          </div>
          {{ item.contentAbstract }}
          <p style="color:grey; text-align: end; font-size: smaller;">
            {{ item.stringDate }}
          </p>
        </b-card>
      </b-col>
    </b>
  </div>
</template>
<script>
import router from "../router";
export default {
  mounted() {
    this.$http.get("http://100.26.202.213:8080/article/articles").then(
      response => {
        this.items = response.data.articles;
      },
      error => {
        console.log("Error");
      }
    );
  },
  data() {
    return {
      items: [],
      seenTop: true,
      seenAll: false
    };
  },
  methods: {
    showTopArticles() {
      this.$http.get("/article/top_articles").then(
        response => {
          this.seenTop = false;
          this.seenAll = true;
          this.items = response.data.articles;
        },
        error => {
          console.log("Error");
        }
      );
    },
    showAllArticles() {
      this.$http.get("/article/articles").then(
        response => {
          this.seenTop = true;
          this.seenAll = false;
          this.items = response.data.articles;
        },
        error => {
          console.log("Error");
        }
      );
    }
  }
};
</script>

<style scoped>
#upperCard {
  width: 81%;
}
</style>
