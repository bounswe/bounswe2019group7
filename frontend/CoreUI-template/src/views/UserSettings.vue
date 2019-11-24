<template>
  <div class="animated fadeIn">
    <b-row style="justify-content:center">
      <b-card :no-body="true" id="upperCard">
        <b-card-body class="p-5 clearfix">
          <b-row>
            <b-col sm="4">
              <img src="img/avatars/user.png" class="img-avatar" alt="admin@bootstrapmaster.com" />
              <div class="h2 text-primary mb-0 mt-2">{{name}} {{surname}}</div>
              <div class="h4 text-primary mb-0 mt-4">{{privacy}}<c-switch class="mx-1 mt-1" color="primary" variant="pill" id="private" @change="privacyCheck()"/></div>
            </b-col>
            <b-col sm="8">
              <b-form @submit="checkForm">
                <h1>Personal Information</h1>
                <p v-if="errors.length" class="text-danger">
                  <b>Please correct the following error(s):</b>
                  <ul>
                    <li v-for="(error,index) in errors" :key="index">{{ error }}</li>
                  </ul>
                </p>
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
                    <b-input-group-text><i class="fa fa-phone fa-lg"></i></b-input-group-text>
                  </b-input-group-prepend>
                  <b-form-input
                    type="text"
                    class="form-control"
                    placeholder="Phone"
                    autocomplete="phone"
                    v-model="form.phone"
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
                    placeholder="Country"
                    autocomplete="country"
                    v-model="form.country"
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
                    placeholder="City"
                    autocomplete="city"
                    v-model="form.city"
                  />
                </b-input-group>
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
                      autocomplete="identityNo"
                      v-model="form.identityNo"
                    />
                  </b-input-group>
                </div>
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
import { Switch as cSwitch } from '@coreui/vue'
export default {
  name: 'switches',
  components: {
    cSwitch
  },
  data: function() {
    return {
      seen: true,
      errors: [],
      isDisabled: false,
      form: {
        city: "",
        country: "",
        email: "",
        iban: "",
        identityNo: "",
        name: "",
        phone: "",
        surname: "",
      },
      name: "",
      surname: "",
      isTrader: true,
      privacy: ""
    };
  },
  mounted() {
    this.$http
        .get("http://100.26.202.213:8080/user_profile/self_profile", {
          headers: {
            Authorization: localStorage.getItem("token")
          }
        })
        .then(
          response => {
            this.form.city = response.data.city;
            this.form.country = response.data.country;
            this.form.email = response.data.email;
            this.form.name = response.data.name;
            this.form.phone = response.data.phone;
            this.form.surname = response.data.surname;

            if(response.data.role == "BASIC_USER") {
              this.seen = false;
            }
            else {
              this.form.iban = response.data.iban;
              this.form.identityNo = response.data.identityNo;
            }

            if(response.data.privacyType == "PRIVATE_USER") {
              document.getElementById('private').checked = false;
              this.privacy = "Private Profile";
            }
            else {
              document.getElementById('private').checked = true;
              this.privacy = "Public Profile";
            }
            this.name = response.data.name;
            this.surname = response.data.surname;
          },
          error => {
            console.log("eerror");
          }
        );
  },
  methods: {
    checkForm(e) {
      
      e.preventDefault();

      if(this.seen == false) {
        this.$http
          .post('/user_profile/update_basic_profile',
            {
              city: this.form.city,
              country: this.form.country,
              email: this.form.email,
              name: this.form.name,
              phone: this.form.phone,
              surname: this.form.surname
            },
            {
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
          }
        )
      }
      else {
        this.$http
          .post('/user_profile/update_trader_profile',
            {
              city: this.form.city,
              country: this.form.country,
              email: this.form.email,
              iban: this.form.iban,
              identityNo: this.form.identityNo,
              name: this.form.name,
              phone: this.form.phone,
              surname: this.form.surname
            },
            {
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
          }
        )
      }
    },
    privacyCheck() {
      console.log("hel");
        if(document.getElementById('private').checked == true) {
          this.privacy = "Public Profile";
          console.log("trrr");
          this.$http
          .post("http://100.26.202.213:8080/user_profile/update_privacy", {},{
            headers: {
              Authorization: localStorage.getItem("token"),
              privacy: "PUBLIC_USER"
            }
          })
          .then(
            response => {
            },
            error => {
              console.log("eerror");
            }
          );
        }
        else {
          this.privacy = "Private Profile";
          this.$http
          .post("http://100.26.202.213:8080/user_profile/update_privacy", {}, {
            headers: {
              Authorization: "" +localStorage.getItem("token"),
              privacy: "PRIVATE_USER"
            }
          })
          .then(
            response => {
            },
            error => {
              console.log("error");
            }
          );
        }
    }
  }
};
</script>

<style>
.switch {
  padding-top:1%
}
</style>
