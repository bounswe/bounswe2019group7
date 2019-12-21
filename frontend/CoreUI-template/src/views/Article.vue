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
            <router-link
              :to="{
                path:'/profilePage/' + item.authorEmail
              }"
              >{{ item.authorName }} {{ item.authorSurname }}<br></br></router-link
            >
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
      <b-col sm="6" md="10">
        <b-card>
          <div slot="header">
            <h3>Comments</h3>
          </div>
          <b-card v-for="comment in comments">
            <div slot="header">
              <router-link
                :to="{
                  path: '/profilePage/' + comment.minimalUserResource.email
                }"
                v-if="!ableToDeleteComment(comment.minimalUserResource.id)"
                >{{comment.minimalUserResource.name}} {{comment.minimalUserResource.surname}}</router-link
              >
              <router-link
                :to="{
                  path: '/profilePage/'
                }"
                v-if="ableToDeleteComment(comment.minimalUserResource.id)"
                >{{comment.minimalUserResource.name}} {{comment.minimalUserResource.surname}}</router-link
              >
             <div class="card-header-actions">
               <b-badge  variant="success">{{comment.createdDate}}</b-badge>
                 <b-button v-on:click="deleteComment(comment.id)"  v-if="ableToDeleteComment(comment.minimalUserResource.id)" variant="danger" style="margin-left:5px">Delete</b-button>
             </div>
            </div>
            <p style="color:grey">{{comment.content}}</p>
          </b-card>
        </b-card>
      </b-col>
      <b-col sm="6" md="10">
        <b-card>
          <div slot="header">
            <h3>Post a new Comment</h3>
          </div>
        <b-form @submit="postComment">
          <b-input-group class="mb-3">
            <b-input-group-prepend>
              <b-input-group-text>
                <i class="icons font-2xl d-block mt-5 cui-pencil"></i>
              </b-input-group-text>
            </b-input-group-prepend>
            <b-form-textarea class="form-control" v-model="form.contentComment" :rows="5" placeholder="Write a comment..."></b-form-textarea>
          </b-input-group>
          <b-button :disabled="isDisabled" type="submit" variant="primary" class="pull-right">Comment</b-button>
        </b-form>
      </b-card>
      </b-col>
    </b>
  </div>
</template>
<script>
import router from "../router";
import StarRating from "vue-star-rating";

export default {
  data() {
    return {
      item: [],
      rating: 4.0,
      isDisabled: false,
      form: {
        contentComment:""
      },
      comments:[],
      user:[]

    };
  },
  components: {
    StarRating
  },
   async mounted() {

    this.id = this.$route.params.id;

    await this.$http
      .get(
        "http://100.26.202.213:8080/article/article?id=" + this.$route.params.id
      )
      .then(
        response => {
          this.item = response.data;
          this.rating = this.item.score;
        },
        error => {
          console.log("Error");
        }
      );
       this.$http
      .get('/comment_controller/get_comments_of_article/?articleOrEventId='+this.item.uuid,
      {
        headers: {
          Authorization: localStorage.getItem("token"),
        }
      }
      )
      .then(response => {
          if(response.status == 200){
            this.comments=response.data;
          }else{
            this.errors = [`An error occurred.`];
            this.isDisabled = false;
          }
        });

         this.$http
          .get("http://100.26.202.213:8080/user_profile/self_profile", {
            headers: {
              Authorization: localStorage.getItem("token")
            }
          })
          .then(
            response => {
              this.user=response.data;
            });
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
    },
    postComment() {
      this.$http
      .post('/comment_controller/post_comment',
      {
        articleEventId:this.item.uuid,
        commentType: "Article",
        content: this.form.contentComment,
      },
      {
        headers: {
          Authorization: localStorage.getItem("token"),
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
    "deleteComment": function deleteComment(commentID) {
      this.$http
      .delete('/comment_controller/delete_comment',
      {
        headers: {
          Authorization: localStorage.getItem("token"),
        },
        params: {
          articleOrEventId  : commentID,
        }
      }
      )
      .then(response => {
        if(response.status == 200){
          location.reload();
        }
        else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      })

    },
    "ableToDeleteComment": function teableToDeleteCommentst(ownerID){
      return (ownerID==this.user.id);

    }
  }
};
</script>
<style>



</style>
