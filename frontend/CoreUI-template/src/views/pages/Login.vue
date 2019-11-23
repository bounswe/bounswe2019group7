<template>
  <div class="app flex-row align-items-center">
    <div class="container">
      <b-row class="justify-content-center">
        <b-col md="8">
          <b-card-group>
            <b-card no-body class="p-4">
              <b-card-body>
                <b-form @submit="checkForm"> 
                  <h1>Login</h1>
                  <p v-if="errors.length" class="text-danger">
                  <b>Please correct the following error(s):</b>
                  <ul>
                    <li v-for="(error,index) in errors" :key="index">{{ error }}</li>
                  </ul>
                </p>
                  <p class="text-muted">Sign In to your account</p>
                  <b-input-group class="mb-3">
                    <b-input-group-prepend>
                      <b-input-group-text>
                        <i class="icon-user"></i>
                      </b-input-group-text>
                    </b-input-group-prepend>
                    <b-form-input
                      type="text"
                      class="form-control"
                      placeholder="Username"
                      autocomplete="username email"
                      v-model="form.email"
                    />
                  </b-input-group>
                  <b-input-group class="mb-4">
                    <b-input-group-prepend>
                      <b-input-group-text>
                        <i class="icon-lock"></i>
                      </b-input-group-text>
                    </b-input-group-prepend>
                    <b-form-input
                      type="password"
                      class="form-control"
                      placeholder="Password"
                      autocomplete="current-password"
                      v-model="form.password"
                    />
                  </b-input-group>
                  <b-row>
                    <b-col cols="6">
                      <b-button :disabled="isDisabled" type="submit" variant="primary" class="px-4">Login</b-button>
                    </b-col>
                    <b-col cols="6" class="text-right">
                      <b-button variant="link" class="px-0">Forgot password?</b-button>
                    </b-col>
                  </b-row>
                </b-form>
              </b-card-body>
              <b-card-footer
                class="p-4"
                style="align-self:center; padding:1.5rem 10% !important; background-color:unset"
              >
                <b-row>
                  <b-button size="lg" variant="google-plus" class="mr-1 btn-brand">
                    <i class="fa fa-google"></i>
                    <span>Login with Google</span>
                  </b-button>
                </b-row>
              </b-card-footer>
            </b-card>
            <b-card no-body class="text-white bg-primary py-5 d-md-down-none" style="width:44%">
              <b-card-body class="text-center">
                <div>
                  <h2>Sign up</h2>
                  <p>Consider signing up to follow the latest news and articles, create your own portfolio and more!</p>
                  <b-button :to="'register'" variant="primary" class="active mt-3">Register Now!</b-button>
                </div>
                <div>
                  <b-button :to="'/dashboard'" class="active mt-3">Continue as a guest</b-button>
                </div>
              </b-card-body>
            </b-card>
          </b-card-group>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data: function() {
    return {
      errors: [],
      isDisabled: false,
      form: {
        email: "",
        password: ""
        }
    };
  },
  methods: {
    checkForm(e) {
      
      e.preventDefault();
      this.errors = [];

      if (!this.form.email) {
        this.errors.push("Email required.");
      } else if (!this.validEmail(this.form.email)) {
        this.errors.push("Valid email required.");
      }

      if (!this.form.password) {
        this.errors.push("Password required.");
      } 

      this.isDisabled = true;

      this.$http
      .post('login',
        {
          email: this.form.email,
          password: this.form.password
        }
      )
      .then(response => {
        if(response.status == 200){
          this.$router.push("/dashboard");
          document.getElementById('#tokenForUserID').val(response.token);
        } else {
          this.errors = [`An error occurred.`];
          this.isDisabled = false;
        }
      },
      error => {
        this.errors = [`An error occurred: ${error.message}`];
        this.isDisabled = false;
        })

    },
    validEmail: function(email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    }
  }
};
</script>
