<template>
  <div class="animated fadeIn">
    <b-row>
      <b-card :no-body="true" id="upperCard" v-if="upperCardSeen">
        <b-card-body class="p-5 clearfix">
          <b-row style="justify-content:center">
            <div class="h2 text-primary mb-4 mt-2">You do not have any trading account yet!</div>
            <b-button
              class="btn-block"
              size="lg"
              variant="primary"
              style="margin-bottom:1%; margin-left:1%"
              v-on:click="createTradingAccount"
            >Click Here to Create a Trading Account</b-button>
          </b-row>
        </b-card-body>
      </b-card>
    </b-row>
    <b-row>
      <b-card :no-body="true" id="lowerCard" v-if="lowerCardSeen">
        <b-card-body class="p-5 clearfix">
          <div class="h2 text-primary mb-4 mt-2">YOUR TRADING ACCOUNT</div>
          <b-row>
            <b class="col-lg-6">
              <b-input-group class="mb-4" id="creditCardDiv">
                <b-input-group-prepend>
                  <b-input-group-text style="height:69%">
                    <i class="icon-credit-card"></i>
                    <p class="ml-2 mb-2 mt-2">CREDIT CARD NUMBER</p>
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
            </b>
            <b class="col-lg-6">
              <b-input-group class="mb-4" id="creditCardDiv">
                <b-input-group-prepend>
                  <b-input-group-text style="height:69%">
                    <i class="icon-key"></i>
                    <p class="ml-2 mb-2 mt-2">CVV</p>
                  </b-input-group-text>
                </b-input-group-prepend>
                <b-form-input
                  type="text"
                  class="form-control"
                  placeholder="CVV"
                  autocomplete="iban"
                />
              </b-input-group>
            </b>
          </b-row>
          <b-row>
            <b class="col-sm-6 col-lg-4">
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-cny fa-lg"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">元 {{ item.cnyAmount }}</div>
                    <div class="text-uppercase text-muted small">Chinese Yuan</div>
                  </div>
                  <div>
                    <b-button type="button" variant="primary" @click="esra()" class="mr-1">Buy</b-button>
                    <div class="text-uppercase text-muted small">Click to Buy Chinese Yuan</div>
                  </div>
                </div>
              </div>
            </b>
            <b class="col-sm-6 col-lg-4">
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-euro fa-lg"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">€ {{ item.eurAmount }}</div>
                    <div class="text-uppercase text-muted small">Euro</div>
                  </div>
                  <div>
                    <div class="text-value"></div>
                    <div class="text-uppercase text-muted small">Following</div>
                  </div>
                </div>
              </div>
            </b>
            <b class="col-sm-6 col-lg-4">
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-gbp fa-lg"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">£ {{ item.gbpAmount }}</div>
                    <div class="text-uppercase text-muted small">Pound</div>
                  </div>
                  <div>
                    <div class="text-value"></div>
                    <div class="text-uppercase text-muted small">Following</div>
                  </div>
                </div>
              </div>
            </b>
          </b-row>
          <b-row>
            <b class="col-sm-6 col-lg-4">
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-jpy fa-lg"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">円 {{ item.jpyAmount }}</div>
                    <div class="text-uppercase text-muted small">Japanese Yen</div>
                  </div>
                  <div>
                    <div class="text-value"></div>
                    <div class="text-uppercase text-muted small">Following</div>
                  </div>
                </div>
              </div>
            </b>
            <b class="col-sm-6 col-lg-4">
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-turkish-lira fa-lg"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">₺ {{ item.tryAmount }}</div>
                    <div class="text-uppercase text-muted small">Turkish Lira</div>
                  </div>
                  <div>
                    <div class="text-value"></div>
                    <div class="text-uppercase text-muted small">Following</div>
                  </div>
                </div>
              </div>
            </b>
            <b class="col-sm-6 col-lg-4">
              <div class="brand-card" id="followersCard">
                <div class="brand-card-header bg-twitter">
                  <i class="fa fa-usd fa-lg"></i>
                  <div class="chart-wrapper">
                    <social-box-chart-example :data="[65, 59, 84, 84, 51, 55, 40]" />
                  </div>
                </div>
                <div class="brand-card-body">
                  <div>
                    <div class="text-value">$ {{ item.usdAmount }}</div>
                    <div class="text-uppercase text-muted small">Dollar</div>
                  </div>
                  <div>
                    <div class="text-value"></div>
                    <div class="text-uppercase text-muted small">Following</div>
                  </div>
                </div>
              </div>
            </b>
          </b-row>
        </b-card-body>
      </b-card>
    </b-row>

    <!-- Modal Component -->
    <b-modal
      title="Buy"
      variant="primary"
      header-bg-variant="primary"
      content-class="border-primary"
      v-model="primaryModal"
      @ok="primaryModal = false"
    >
      <b-form>
        <b-input-group class="mb-4">
          <b-input-group-prepend>
            <b-input-group-text style="height:69%">
              <i class="icon-credit-card"></i>
              <p class="ml-2 mb-2 mt-2">CREDIT CARD NUMBER</p>
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
        <b-input-group class="mb-4">
          <b-input-group-prepend>
            <b-input-group-text style="height:69%">
              <i class="icon-key"></i>
              <p class="ml-2 mb-2 mt-2">CVV</p>
            </b-input-group-text>
          </b-input-group-prepend>
          <b-form-input
            type="text"
            class="form-control"
            placeholder="CVV"
            autocomplete="cvv"
            v-model="form.cvv"
          />
        </b-input-group>
        <b-input-group class="mb-4">
          <b-input-group-prepend>
            <b-input-group-text style="height:69%">
              <i class="icon-key"></i>
              <p class="ml-2 mb-2 mt-2">Amount</p>
            </b-input-group-text>
          </b-input-group-prepend>
          <b-form-input
            type="text"
            class="form-control"
            placeholder="Amount"
            autocomplete="amount"
            v-model="form.amount"
          />
        </b-input-group>
        <div class="text-uppercase text-muted small">Total: 元 {{form.amount}}</div>
      </b-form>
    </b-modal>
  </div>
</template>

<script>
import SocialBoxChartExample from "./dashboard/SocialBoxChartExample";
import { shuffleArray } from "@/shared/utils";

const someData = () => shuffleArray([{}]);

export default {
  name: "modals",
  data() {
    return {
      upperCardSeen: true,
      lowerCardSeen: false,
      item: [],
      form: {
        amount: 0,
        boughtTypeCurrency: "",
        creditCardNumber: "",
        cvv: "",
        expiredDate: ""
      },
      primaryModal: false,
      currentSymbol: "",
      currentTransaction: ""
    };
  },
  components: {
    SocialBoxChartExample
  },
  methods: {
    createTradingAccount() {
      this.$http
        .get("/create_trading_account", {
          headers: {
            Authorization: localStorage.getItem("token")
          }
        })
        .then(
          response => {
            this.item = response.data;
            this.upperCardSeen = true;
            this.lowerCardSeen = false;
          },
          error => {
            this.upperCardSeen = false;
            this.lowerCardSeen = true;
          }
        );
    },
    esra() {
      this.primaryModal = true;
      this.$nextTick(function() {
        this.form.boughtTypeCurrency = "CYN";
        this.currentSymbol = "tl";
        this.currentTransaction = "Chinese Yuan";
      });
      this.$nextTick();
    }
  },
  mounted() {
    this.$http
      .get("/get_trading_account", {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      })
      .then(
        response => {
          this.item = response.data;
          this.upperCardSeen = false;
          this.lowerCardSeen = true;
        },
        error => {
          this.upperCardSeen = true;
          this.lowerCardSeen = false;
        }
      );

    this.$http
      .get("http://100.26.202.213:8080/user_profile/self_profile", {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      })
      .then(
        response => {
          this.form.iban = response.data.iban;
        },
        error => {
          console.log("eerror");
        }
      );
  }
};
</script>

<style scoped>
.img-avatar {
  width: 50%;
}
#upperCard {
  width: 100%;
}
#lowerCard {
  width: 100%;
}
#followersCard {
  margin-top: 2%;
  height: 73%;
}
</style>
