<template>

  

<div class="Articles">
  <div class="form-group" id="bar-div">
    <label for="input" class="sr-only"></label>

    <input type="text" class="form-control" id="bar" placeholder="Search">

</div>

<b-button type="submit" variant="primary" v-on:click="search">Search</b-button>

      <h3 align="left">Articles </h3>
  <b
    style="justify-content: right;"
    id="articleCard"
    v-for="item in this.articles"
    v-bind:key="item"
  >
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
              >{{ item.authorName }} {{ item.authorSurname }}</b-badge>
          </div>
        </div>
        {{ item.content }}
        <p style="color:grey; text-align: end; font-size: smaller;">
          {{ item.stringDate }}
        </p>
      </b-card>
  </b>
</div>
        

  
</template>

  
<script>
import router from "../router";

    export default {
      name: "Search",
      data: function() {
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
          isDisabled: false,
          errors: []
        };
      },

      methods: {
        search: function(event) {
            var that = this;
            console.log(that)
            var textToSearch = document.getElementById("bar").value;
            this.$http.get('/search/basic/' + textToSearch)
              .then((response) => {
                if (response.status == 200) {
                  this.articles = response.data.articleResources;
                  console.log(response.data.articleResources);
                  this.$forceUpdate();
                }
              });
            http.send();
            return '';

            
        },
      }
    };
</script>

<style>
    #bar-div{
      display: inline-block;
      margin-top: 0%;
      margin-left: 0%;
    }

    #bar{
      display: inline-block;
      
    }
</style>
  
