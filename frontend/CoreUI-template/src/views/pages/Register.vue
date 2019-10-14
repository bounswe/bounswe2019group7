<template>
  <div class="app flex-row align-items-center">
    <div class="container">
      <b-row class="justify-content-center">
        <b-col md="6" sm="8">
          <b-card no-body class="mx-4">
            <b-card-body class="p-4">
              <b-form>
                <h1>Register</h1>
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
                    placeholder="Username"
                    autocomplete="username"
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
                  />
                </b-input-group>

                <b-form-group label="User Type:" label-for="userType" :label-cols="3">
                  <b-form-radio-group
                    id="userType"
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
                      placeholder="Credit Card Number(0000-0000-0000-0000)"
                      autocomplete="creditCardNumber"
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
                    />
                  </b-input-group>
                </div>

                <b-button variant="success" block>Create Account</b-button>
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
      seen: true
    };
  },
  methods: {
    changeUserType() {
      console.log(this.seen);
      this.seen = !this.seen;
    }
  }
};
</script>
