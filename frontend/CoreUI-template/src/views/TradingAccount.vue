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
                    <b-row>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="success"
                          @click="openModal('CNY')"
                          class="mr-1"
                        >Buy</b-button>
                        <div class="text-uppercase text-muted small">Click to Buy Chinese Yuan</div>
                      </b-col>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="danger"
                          @click="openSellModal('CNY')"
                          class="mr-1"
                        >Sell</b-button>
                        <div class="text-uppercase text-muted small">Click to Sell Chinese Yuan</div>
                      </b-col>
                    </b-row>
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
                    <b-row>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="success"
                          @click="openModal('EUR')"
                          class="mr-1"
                        >Buy</b-button>
                        <div class="text-uppercase text-muted small">Click to Buy Euro</div>
                      </b-col>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="danger"
                          @click="openSellModal('EUR')"
                          class="mr-1"
                        >Sell</b-button>
                        <div class="text-uppercase text-muted small">Click to Sell Euro</div>
                      </b-col>
                    </b-row>
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
                    <b-row>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="success"
                          @click="openModal('GBP')"
                          class="mr-1"
                        >Buy</b-button>
                        <div class="text-uppercase text-muted small">Click to Buy Pound</div>
                      </b-col>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="danger"
                          @click="openSellModal('GBP')"
                          class="mr-1"
                        >Sell</b-button>
                        <div class="text-uppercase text-muted small">Click to Sell Pound</div>
                      </b-col>
                    </b-row>
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
                    <b-row>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="success"
                          @click="openModal('JPY')"
                          class="mr-1"
                        >Buy</b-button>
                        <div class="text-uppercase text-muted small">Click to Buy Japanese Yen</div>
                      </b-col>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="danger"
                          @click="openSellModal('JPY')"
                          class="mr-1"
                        >Sell</b-button>
                        <div class="text-uppercase text-muted small">Click to Sell Japanese Yen</div>
                      </b-col>
                    </b-row>
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
                    <b-row>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="success"
                          @click="openModal('TRY')"
                          class="mr-1"
                        >Buy</b-button>
                        <div class="text-uppercase text-muted small">Click to Buy Turkish Lira</div>
                      </b-col>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="danger"
                          @click="openSellModal('TRY')"
                          class="mr-1"
                        >Sell</b-button>
                        <div class="text-uppercase text-muted small">Click to Sell Turkish Lira</div>
                      </b-col>
                    </b-row>
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
                    <b-row>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="success"
                          @click="openModal('USD')"
                          class="mr-1"
                        >Buy</b-button>
                        <div class="text-uppercase text-muted small">Click to Buy Dollar</div>
                      </b-col>
                      <b-col sm="6">
                        <b-button
                          type="button"
                          variant="danger"
                          @click="openSellModal('USD')"
                          class="mr-1"
                        >Sell</b-button>
                        <div class="text-uppercase text-muted small">Click to Sell Dollar</div>
                      </b-col>
                    </b-row>
                  </div>
                </div>
              </div>
            </b>
          </b-row>
        </b-card-body>
      </b-card>
    </b-row>

    <!-- Modal Buy-->
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
            v-model="form.creditCardNumber"
          />
        </b-input-group>
        <b-row>
          <b-col sm="4">
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
          </b-col>
          <b-col sm="4">
            <b-input-group class="mb-4">
              <b-input-group-prepend>
                <b-input-group-text style="height:34px">
                  <p class="mb-2 mt-2">Month</p>
                </b-input-group-text>
              </b-input-group-prepend>
              <b-form-select
                id="month"
                :plain="true"
                :options="['01','02','03','04','05','06','07','08','09','10','11','12']"
                v-model="expiredMonth"
              ></b-form-select>
            </b-input-group>
          </b-col>
          <b-col sm="4">
            <b-input-group class="mb-4">
              <b-input-group-prepend>
                <b-input-group-text style="height:34px">
                  <p class="mb-2 mt-2">Year</p>
                </b-input-group-text>
              </b-input-group-prepend>
              <b-form-select
                id="year"
                :plain="true"
                :options="['19','20','21','22','23','24','25','26','27','28','29','30','31']"
                v-model="expiredYear"
              ></b-form-select>
            </b-input-group>
          </b-col>
        </b-row>

        <b-input-group class="mb-4">
          <b-input-group-prepend>
            <b-input-group-text style="height:69%">
              <i class="icon-key"></i>
              <p class="ml-2 mb-2 mt-2">Amount</p>
            </b-input-group-text>
          </b-input-group-prepend>
          <b-form-input
            type="number"
            class="form-control"
            placeholder="Amount"
            autocomplete="amount"
            v-model.number="form.amount"
          />
        </b-input-group>
        <div class="text-uppercase text-muted small">Total: {{formCurrencySymbol}} {{form.amount}}</div>
        <b-button
          class="btn-block"
          size="lg"
          variant="primary"
          v-on:click="buyTransaction()"
        >MAKE TRANSACTION</b-button>
      </b-form>
    </b-modal>

    <!-- Modal Sell-->
    <b-modal
      title="Sell"
      variant="danger"
      header-bg-variant="danger"
      content-class="border-danger"
      v-model="dangerModal"
      @ok="dangerModal = false"
    >
      <b-form>
        <b-input-group class="mb-4">
          <b-input-group-prepend>
            <b-input-group-text style="height:69%">
              <i class="icon-key"></i>
              <p class="ml-2 mb-2 mt-2">Amount</p>
            </b-input-group-text>
          </b-input-group-prepend>
          <b-form-input
            type="number"
            class="form-control"
            placeholder="Amount"
            autocomplete="amount"
            v-model.number="formSell.amount"
          />
        </b-input-group>
        <div
          class="text-uppercase text-muted small"
        >Total: {{formSellCurrencySymbol}} {{formSell.amount}}</div>
        <b-button
          class="btn-block"
          size="lg"
          variant="danger"
          v-on:click="sellTransaction()"
        >MAKE TRANSACTION</b-button>
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
      formSell: {
        amount: 0,
        soldTypeCurrency: ""
      },
      primaryModal: false,
      cnySymbol: "元",
      eurSymbol: "€",
      poundSymbol: "£",
      jpySymbol: "円",
      turkishLiraSymbol: "₺",
      dollarSymbol: "$",
      formCurrencySymbol: "",
      expiredYear: "",
      expiredMonth: "",
      dangerModal: false,
      formSellCurrencySymbol: ""
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
    openModal(currencyType) {
      this.primaryModal = true;
      this.$nextTick(function() {
        if (currencyType == "CNY") {
          this.formCurrencySymbol = this.cnySymbol;
        } else if (currencyType == "EUR") {
          this.formCurrencySymbol = this.eurSymbol;
        } else if (currencyType == "GBP") {
          this.formCurrencySymbol = this.poundSymbol;
        } else if (currencyType == "JPY") {
          this.formCurrencySymbol = this.jpySymbol;
        } else if (currencyType == "TRY") {
          this.formCurrencySymbol = this.turkishLiraSymbol;
        } else if (currencyType == "USD") {
          this.formCurrencySymbol = this.dollarSymbol;
        }
        this.form.boughtTypeCurrency = currencyType;
      });
      this.$nextTick();
    },
    openSellModal(currencyType) {
      this.dangerModal = true;
      this.$nextTick(function() {
        if (currencyType == "CNY") {
          this.formSellCurrencySymbol = this.cnySymbol;
        } else if (currencyType == "EUR") {
          this.formSellCurrencySymbol = this.eurSymbol;
        } else if (currencyType == "GBP") {
          this.formSellCurrencySymbol = this.poundSymbol;
        } else if (currencyType == "JPY") {
          this.formSellCurrencySymbol = this.jpySymbol;
        } else if (currencyType == "TRY") {
          this.formSellCurrencySymbol = this.turkishLiraSymbol;
        } else if (currencyType == "USD") {
          this.formSellCurrencySymbol = this.dollarSymbol;
        }
        this.formSell.soldTypeCurrency = currencyType;
      });
    },
    buyTransaction() {
      this.form.expiredDate = this.expiredMonth + this.expiredYear;
      this.$http
        .post(
          "/buy_transaction",
          {
            amount: this.form.amount,
            boughtTypeCurrency: this.form.boughtTypeCurrency,
            creditCardNumber: this.form.creditCardNumber,
            cvv: this.form.cvv,
            expiredDate: this.form.expiredDate
          },
          {
            headers: {
              Authorization: localStorage.getItem("token")
            }
          }
        )
        .then(response => {
          if (response.status == 200) {
            this.primaryModal = false;
            const boughtTypeLastAmount = response.boughtTypeLastAmount;
            this.$http
              .get("/get_trading_account", {
                headers: {
                  Authorization: localStorage.getItem("token")
                }
              })
              .then(response => {
                this.item = response.data;
              });
            this.form.amount = 0;
          }
        });
    },
    sellTransaction() {
      this.$http
        .post(
          "/sell_transaction",
          {
            amount: this.formSell.amount,
            soldTypeCurrency: this.formSell.soldTypeCurrency
          },
          {
            headers: {
              Authorization: localStorage.getItem("token")
            }
          }
        )
        .then(response => {
          if (response.status == 200) {
            this.dangerModal = false;
            this.$http
              .get("/get_trading_account", {
                headers: {
                  Authorization: localStorage.getItem("token")
                }
              })
              .then(response => {
                this.item = response.data;
              });
            this.formSell.amount = 0;
          }
        });
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
          this.form.creditCardNumber = response.data.iban;
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