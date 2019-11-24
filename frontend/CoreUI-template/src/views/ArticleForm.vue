<template>
  <div class="animated fadeIn">
    <b-row style="justify-content:center">
      <b-card :no-body="true" id="upperCard">
        <b-card-body class="p-5 clearfix">
          <b-row>
            <b-col sm="12">
              <b-form @submit="checkForm">
                <h1>Create an Article</h1>
                <p v-if="errors.length" class="text-danger">
                  <b>Please correct the following error(s):</b>
                  <ul>
                    <li v-for="(error,index) in errors" :key="index">{{ error }}</li>
                  </ul>
                </p>
                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="cui-tags icons font-xl d-block"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Title"
                    autocomplete="title"
                    v-model="form.title"
                  />
                </b-input-group>
                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="cui-align-center icons font-2xl d-block mt-4"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-textarea class="form-control" v-model="form.content" :rows="9" placeholder="Content.."></b-form-textarea>
                </b-input-group>
                <b-button :disabled="isDisabled" type="submit" variant="primary" class="pull-right">Save</b-button>
              </b-form>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>
    </b-row>
  </div>
</template>

<script>
export default {
  name: "Forms",
  data: function() {
    return {
      errors: [],
      isDisabled: false,
      form: {
        authorEmail: "",
        content: "",
        title: ""
        }
    };
  },
  methods: {
    checkForm(e) {
      
      e.preventDefault();
      this.errors = [];

      if (!this.form.title) {
        this.errors.push("Title required.");
      }
      if (!this.form.content) {
        this.errors.push("Content required.");
      }
      if (this.errors.length) {
        return false;
      }

      this.isDisabled = true;

      this.$http
      .post('/article/create',
        {
          authorEmail: localStorage.getItem("email"),
          content: this.form.content,
          title: this.form.title
        }, {
          headers: {
            Authorization: localStorage.getItem("token")
          }
        }
      )
      .then(response => {
        if(response.status == 200){
          this.$router.push("/profilePage");
        }else{
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      },
      error => {
        this.errors = [`An error occurred: ${error.message}`];
        this.isDisabled = false;
        })

    }
  }
};
</script>

<style>
.card {
    min-width: 100%
}
</style>
