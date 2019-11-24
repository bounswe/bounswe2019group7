<template>
  <div class="app flex-row align-items-center">
    <div class="container">
      <b-row class="justify-content-center">
        <b-col md="6" sm="8">
          <b-card no-body class="mx-4">
            <b-card-body class="p-4">
              <b-form @submit="checkForm">
                <h1>Register</h1>
                <p v-if="errors.length" class="text-danger">
                  <b>Please correct the following error(s):</b>
                  <ul>
                    <li v-for="(error,index) in errors" :key="index">{{ error }}</li>
                  </ul>
                </p>
                <p class="text-muted">Create your account</p>
                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="icon-user"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Name"
                    autocomplete="name"
                    v-model="form.name"
                  />
                </b-input-group>

                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="icon-user"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Surname"
                    autocomplete="surname"
                    v-model="form.surname"
                  />
                </b-input-group>

                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>@</b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Email"
                    autocomplete="email"
                    v-model="form.email"
                  />
                </b-input-group>

                <b-input-group class="mb-3">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="icon-lock"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="password"
                    class="form-control"
                    placeholder="Password"
                    autocomplete="new-password"
                    v-model="form.password"
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
                    placeholder="Repeat password"
                    autocomplete="new-password"
                    v-model="form.repeatPassword"
                  />
                </b-input-group>

                <b-input-group class="mb-4">
                  <b-input-group-prepend>
                    <b-input-group-text>
                      <i class="icon-location-pin"></i>
                    </b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Location"
                    autocomplete="location"
                    v-model="form.location"
                  />
                </b-input-group>

                <b-form-group label="User Type:" label-for="userType" :label-cols="3">
                  <b-form-radio-group
                    id="userType"
                    v-model="form.userType"
                    :plain="true"
                    :options="[
                      {text: 'Trader ',value: '1'},
                      {text: 'Basic User ',value: '2'}
                    ]"
                    checked="1"
                    stacked
                    v-on:change="changeUserType"
                  ></b-form-radio-group>
                </b-form-group>
                <div v-if="seen">
                  <b-input-group class="mb-4" id="creditCardDiv">
                    <b-input-group-prepend>
                      <b-input-group-text>
                        <i class="icon-credit-card"></i>
                      </b-input-group-text>
                    </b-input-group-prepend>
                    <b-form-input
                      type="text"
                      class="form-control"
                      placeholder="IBAN"
                      autocomplete="iban"
                      v-model="form.iban"
                    />
                  </b-input-group>
                  <b-input-group class="mb-4" id="idNumberDiv">
                    <b-input-group-prepend>
                      <b-input-group-text>
                        <i class="fa fa-id-card-o fa-lg"></i>
                      </b-input-group-text>
                    </b-input-group-prepend>
                    <b-form-input
                      type="text"
                      class="form-control"
                      placeholder="Identity Number"
                      autocomplete="identityNumber"
                      v-model="form.identityNumber"
                    />
                  </b-input-group>
                </div>

                <b-button :disabled="isDisabled" type="submit" variant="success" block>Create Account</b-button>
              </b-form>
            </b-card-body>
            <b-card-footer
              class="p-4"
              style="align-self:center; padding:1.5rem 10% !important; background-color:unset"
            >
              <b-row>
                <b-button size="lg" variant="google-plus" class="mr-1 btn-brand">
                  <i class="fa fa-google"></i>
                  <span>Sign up with Google</span>
                </b-button>
              </b-row>
            </b-card-footer>
          </b-card>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data: function() {
    return {
      seen: true,
      errors: [],
      isDisabled: false,
      form: {
        name: "",
        surname: "",
        email: "",
        password: "",
        repeatPassword: "",
        location: "",
        iban: "",
        identityNumber: "",
        userType: 1
        }
    };
  },
  methods: {
    changeUserType() {
      this.seen = !this.seen;
    },
    checkForm(e) {
      
      e.preventDefault();
      this.errors = [];

      if (!this.form.name) {
        this.errors.push("Name required.");
      }
      if (!this.form.surname) {
        this.errors.push("Surname required.");
      }
      if (!this.form.email) {
        this.errors.push("Email required.");
      } else if (!this.validEmail(this.form.email)) {
        this.errors.push("Valid email required.");
      }
      //password kontrolü
      if (!this.form.password) {
        this.errors.push("Password required.");
      } else if (this.form.password != this.form.repeatPassword) {
        this.errors.push("Passwords do not match.");
      }

      if(this.form.userType==1){
        if (this.form.iban.length < 16 || this.form.iban.length > 18) {
          this.errors.push("IBAN must have 16-18 characters.");
        }

        if (this.form.identityNumber.length != 11) {
          this.errors.push("Identity number must have 11 characters.");
        }
      }

      if (this.errors.length) {
        return false;
      }

      this.isDisabled = true;

      if(this.form.userType==1){
        this.$http
          .post('registration/trader_register',
          {
            name: this.form.name,
            surname: this.form.surname,
            email: this.form.email,
            locationX: 0,
            locationY: 0,
            city: this.form.location,
            iban: this.form.iban,
            identityNo: this.form.identityNumber
          },
          {
            headers: {
              password: this.form.password
            }
          }
        )
        .then(response => {
          if(response.status == 200){
            this.$router.push("login");
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
      else {
        this.$http
      .post('registration/basic_register',
        {
          name: this.form.name,
          surname: this.form.surname,
          email: this.form.email,
          locationX: 0,
          locationY: 0,
          city: this.form.location
        },
        {
          headers: {
            password: this.form.password
          }
        }
      )
      .then(response => {
        if(response.status == 200){
          this.$router.push("login");
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

    },
    validEmail: function(email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    }
  }
};
</script>
