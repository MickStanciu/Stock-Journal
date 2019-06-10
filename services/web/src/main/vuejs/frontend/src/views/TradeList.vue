<template>
    <div>
        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-2">Date</div>
            <div class="col-md-5">Action</div>
            <div class="col">Fees</div>
            <div class="col">Total</div>
        </div>

        <template v-for="(item, idx) in items">
            <div class="row pb-1 pt-1" v-bind:class="rowClass(item, idx)" v-bind:key="item.transactionId">
                <div class="col-md-2">{{ dateTz(item) }}</div>
                <div class="col-md-5">{{ encodeAction(item) }}</div>
                <div class="col">{{ printCurrencyFormat(item.brokerFees) }}</div>
                <div class="col">{{ printCurrencyFormat(calculateLineItemTotal(item)) }}</div>
            </div>
        </template>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-5">&nbsp;</div>
            <div class="col">&nbsp;</div>
            <div class="col">{{ printCurrencyFormat(calculateLineItemsTotal(items)) }}</div>
        </div>

        <div class="row pt-3">
            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewStockTradeClicked">Add new stock</button>
            </div>

            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewOptionTradeClicked">Add new option</button>
            </div>
        </div>

        <br/>

        <transition name="fade">
            <add-stock-trade v-if="isAddStockModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
        </transition>

        <transition name="fade">
            <add-option-trade v-if="isAddOptionModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
        </transition>

        <transition name="fade">
            <add-error v-if="isAddErrorEnabled"/>
        </transition>

    </div>
</template>

<script>
    import service from '../service';
    import dateTimeUtil from '../utils/time'
    import AddStockTrade from "../components/tradelist/AddStockTrade";
    import AddOptionTrade from "../components/tradelist/AddOptionTrade";
    import AddError from "../components/tradelist/AddError";
    import OptionApiModel from "../models/OptionApiModel";
    import ShareApiModel from "../models/ShareApiModel";

    export default {
        name: "TradeList",
        components: {AddOptionTrade, AddError, AddStockTrade},

        data: function () {
            return {
                items : [],
                timeZone : 'Australia/Sydney',
                currency : 'USD',
                symbol : this.$route.params.symbol
            }
        },

        computed: {
            isAddStockModalEnabled() {
                return this.$store.state.isAddStockModalEnabled;
            },
            isAddOptionModalEnabled() {
                return this.$store.state.isAddOptionModalEnabled;
            },
            isAddErrorEnabled() {
                return this.$store.state.isAddErrorEnabled;
            },
        },

        methods: {
            addNewStockTradeClicked: function() {
                this.$store.dispatch('showAddStockModal');
            },
            addNewOptionTradeClicked: function() {
                this.$store.dispatch('showAddOptionModal');
            },
            rowClass: function (item, idx) {
                let className = 'table-cell-odd';
                if (idx%2 === 0) {
                    className = 'table-cell-even';
                }

                if (item.isSynthetic === true) {
                    className += ' table-cell-synthetic';
                }

                return className;
            },

            expiryDateTz: function(item) {
                return dateTimeUtil.convertExpiryDateForDisplay(item.expiryDate);
            },

            dateTz: function(item) {
                return dateTimeUtil.convertForDisplay(item.date);
            },

            encodeAction: function (item) {
                let encoded = 'BOUGHT ';
                if ('SELL' === item.action) {
                    encoded = 'SOLD '
                }

                if (item.type === 'OPTION') {
                    //SOLD 3 LKQ May17'19 30 PUT @ 1
                    encoded += item.contracts + ' ' + item.stockSymbol + ' ' + item.actionType + ' ' + this.expiryDateTz(item)
                        + ' ' + item.strikePrice + ' @ ' + item.premium;
                } else if (item.type === 'STOCK') {
                    //BOUGHT 100 SWKS @ 87.17
                    encoded += item.quantity + ' ' + item.symbol + ' @ ' + item.price;
                }
                return encoded;
            },

            calculateLineItemTotal: function (item) {
                if (item.type === 'OPTION') {
                    let transactionValue = item.contracts * 100 * item.premium - item.brokerFees;
                    return parseFloat((transactionValue).toFixed(10));
                } else if (item.type === 'STOCK') {
                    let price = item.price;
                    if (item.action === 'BUY') {
                        price = price * -1;
                    }
                    return parseFloat((item.quantity * price - item.brokerFees).toFixed(10));
                }
                return 0;
            },

            calculateLineItemsTotal: function (items) {
                let _this = this;
                let total = 0.00;
                items.forEach(function (item) {
                    total += _this.calculateLineItemTotal(item);
                });
                return total;
            },

            printCurrencyFormat: function (value) {
                let params = {
                    style: 'currency',
                    currency: 'USD',
                    minimumFractionDigits: 2
                };
                return new Intl.NumberFormat('en-US', params).format(value);
            },

            loadData: function () {
                service
                    .getTradesPerSymbol(this.$route.params.symbol)
                    .then(data => {
                        let self = this;
                        let localItems = [];
                        data.optionList.forEach(function (item) {
                            let model = new OptionApiModel(item.stockSymbol);
                            model.stockPrice = item.stockPrice;
                            model.strikePrice = item.strikePrice;
                            model.contracts = item.contracts;
                            model.premium = item.premium;
                            model.action = item.action;
                            model.actionType = item.actionType;
                            model.brokerFees = item.brokerFees;
                            model.date = item.date;
                            model.expiryDate = item.expiryDate;
                            model.transactionId = item.transactionId;

                            localItems.push(model);
                        });

                        data.shareList.forEach(function (item) {
                            let model = new ShareApiModel(item.symbol);
                            model.price = item.price;
                            model.quantity = item.quantity;
                            model.action = item.action;
                            model.actionType = item.actionType;
                            model.brokerFees = item.brokerFees;
                            model.date = item.date;
                            model.transactionId = item.transactionId;

                            localItems.push(model);
                        });

                        data.syntheticShareList.forEach(function (item) {

                            let model = new ShareApiModel(item.symbol);
                            model.price = item.price;
                            model.quantity = item.quantity;
                            model.action = item.action;
                            model.actionType = item.actionType;
                            model.brokerFees = item.brokerFees;
                            model.date = item.date;
                            model.transactionId = 0;
                            model.isSynthetic = true;

                            localItems.push(model);
                        });

                        self.items = localItems.sort(function (a, b) {
                            return dateTimeUtil.sortDates(a, b);
                        });
                    });
            }
        },
        created() {
            this.loadData();
        },
        mounted() {
            this.$store.subscribe( (mutation, state) => {
                if (mutation.type === 'hideModalWithRefresh') {
                    this.loadData();
                }
            })
        }
    }

</script>

<style scoped>
    .table-header {
        background-color: #005cbf;
        color: white;
        vertical-align: center;
        border: #005cbf 1px solid;
    }

    .table-footer {
        background-color: #9fcdff;
        color: #005cbf;
        font-weight: bold;
        vertical-align: center;
        border: #9fcdff 1px solid;
    }

    .table-cell-odd {
        background-color: whitesmoke;
    }

    .table-cell-even {
        background-color: white;
    }

    .table-cell-synthetic {
        background-color: antiquewhite;
        font-style: italic;
    }

    .fade-enter-active {
        transition: opacity .5s;
    }

    .fade-leave-active {
        transition: opacity .5s;
    }
    .fade-enter, .fade-leave-to {
        opacity: 0;
    }

</style>