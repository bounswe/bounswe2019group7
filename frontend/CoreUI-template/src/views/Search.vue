<template>


<div class="Search Bar">
  <div class="form-group" id="bar-div">
    <label for="input" class="sr-only"></label>
    <input type="text" class="form-control" id="bar" placeholder="Search">
    
  </div>
  <b-button type="submit" variant="primary" v-on:click="search">Search</b-button>
  <div class="Articles" id="articles">
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

  <div class="table" id="events">
      <h3 align="left">Events</h3>
      <table>
        <thead>
          <tr>
            <th>Time</th>
            <th>Significance</th>
            <th>Event</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="event in this.events">
              <td>{{ event.stringDate}}</td>
              <td>{{ event.score+"/5"}}</td>
              <td>{{ event.title }}</td>
          </tr>
        </tbody>
      </table>
  </div>

  <div class="table" id="users">
      <h3 align="left">Users</h3>
      <li v-for="user in users">
          {{ user.name + " " + user.surname }}
      </li>
  </div>

</div>
        

  
</template>

  
<script>
import router from "../router";

    export default {
      name: "Search",
      data: function() {
        return {
          selected: [],
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
          errors: [],
          events: [],
          users: [],
          currentPage: 1,
          elementsPerPage: 7
        };
      },

      methods: {
        search: function(event) {
            var that = this;
            var textToSearch = document.getElementById("bar").value;
            this.$http.get('/search/basic/' + textToSearch)
              .then((response) => {
                if (response.status == 200) {
                  var articles = document.getElementById("articles");
                  var events = document.getElementById("events");
                  var users = document.getElementById("users");

                  articles.style.display = "";
                  events.style.display = "";
                  users.style.display = "";

                  this.articles = response.data.articleResources;
                  this.events = response.data.eventResources;
                  this.users = response.data.minimalUserResources;
                  
                  this.$forceUpdate();
                }
              });
            http.send();
            return '';

            
        },
      },
      mounted(){
          var articles = document.getElementById("articles");
          var events = document.getElementById("events");
          var users = document.getElementById("users");

          articles.style.display = "none";
          events.style.display = "none";
          users.style.display = "none";
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
  
