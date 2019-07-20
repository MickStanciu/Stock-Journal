<template>
    <div id="trade-list">
        <share-data v-bind:datamodel="shareData" v-if="shareDataLoaded"/>

        <div class="row pt-2 mt-3">
            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewStockTradeClicked">Add new stock</button>
            </div>

            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewOptionTradeClicked">Add new option</button>
            </div>
        </div>

        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-1">#</div>
            <div class="col-md-2">Date</div>
            <div class="col-md-5">Action</div>
            <div class="col-md-1">Fees</div>
            <div class="col-md-2 text-right">Total</div>
            <div class="col-md-1">Action</div>
        </div>

        <template v-for="(item, idx) in items">
            <div class="row pb-1 pt-1" v-bind:class="rowClass(item, idx)" v-bind:key="item.transactionId">
                <div class="col-md-1 action-element" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']" v-on:click="rowClick(item)">{{ idx + 1 }}</div>
                <div class="col-md-2" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ dateTz(item) }}</div>
                <div class="col-md-5" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ encodeAction(item) }}</div>
                <div class="col-md-1" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ printCurrencyFormat(item.brokerFees) }}</div>
                <div class="col-md-2 text-right" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ printCurrencyFormat(calculateLineItemTotal(item)) }}</div>
                <div class="col-md-1">
                    <font-awesome-icon icon="trash-alt" class="action-icon" v-if="!item.isSynthetic" v-on:click="deleteRecordClicked(item.transactionId)"></font-awesome-icon>
                </div>
            </div>
        </template>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-1">&nbsp;</div>
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-5">&nbsp;</div>
            <div class="col-1">&nbsp;</div>
            <div class="col text-right">{{ printCurrencyFormat(getTotal) }}</div>
        </div>


        <transition name="fade">
            <add-stock-trade v-if="isAddStockModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
        </transition>

        <transition name="fade">
            <add-option-trade v-if="isAddOptionModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
        </transition>

        <transition name="fade">
            <add-error v-if="isAddErrorEnabled"/>
        </transition>

        <transition name="fade">
            <delete-stock-trade v-if="isDeleteStockModalEnabled" v-bind:post="{model: selectedModel}"/>
        </transition>

        <transition name="fade">
            <delete-option-trade v-if="isDeleteOptionModalEnabled" v-bind:option_model="selectedModel"/>
        </transition>

    </div>
</template>

<script>
    import service from '../service';
    import dateTimeUtil from '../utils/time'
    import AddStockTrade from "../components/tradelist/AddStockTrade";
    import DeleteStockTrade from "../components/tradelist/DeleteStockTrade";
    import DeleteOptionTrade from "../components/tradelist/DeleteOptionTrade";
    import AddOptionTrade from "../components/tradelist/AddOptionTrade";
    import AddError from "../components/tradelist/AddError";
    import OptionApiModel from "../models/OptionApiModel";
    import ShareApiModel from "../models/ShareApiModel";
    import DividendApiModel from "../models/DividendApiModel";
    import ShareData from "../components/tradelist/ShareData";

    export default {
        name: "TradeList",
        components: {ShareData, AddOptionTrade, AddError, AddStockTrade, DeleteStockTrade, DeleteOptionTrade},

        data: function () {
            return {
                items: [],
                shareData: undefined,
                shareDataLoaded: false,
                timeZone: 'Australia/Sydney',
                currency: 'USD',
                symbol: this.$route.params.symbol,
                selectedModel : undefined,
            }
        },

        computed: {
            isAddStockModalEnabled() {
                return this.$store.state.isAddStockModalEnabled;
            },
            isDeleteStockModalEnabled() {
                return this.$store.state.isDeleteStockModalEnabled;
            },
            isAddOptionModalEnabled() {
                return this.$store.state.isAddOptionModalEnabled;
            },
            isDeleteOptionModalEnabled() {
                return this.$store.state.isDeleteOptionModalEnabled;
            },
            isAddErrorEnabled() {
                return this.$store.state.isAddErrorEnabled;
            },
            getTotal: function () {
                let _this = this;
                let total = 0.00;
                this.items.forEach(function (item) {
                    if (item.groupSelected === true) {
                        total += _this.calculateLineItemTotal(item);
                    }
                });
                return total;
            }
        },

        methods: {
            addNewStockTradeClicked: function() {
                this.$store.dispatch('showAddStockModal');
            },

            deleteRecordClicked: function(id) {
                this.selectedModel = this.getModelById(id);

                if (typeof this.selectedModel !== 'undefined') {
                    if ("SHARE" === this.selectedModel.type) {
                        this.$store.dispatch('showDeleteStockModal');
                    } else if ("OPTION" === this.selectedModel.type) {
                        this.$store.dispatch('showDeleteOptionModal');
                    }
                }
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

            rowClick : function (item) {
                item.groupSelected = !item.groupSelected;
            },

            getModelById: function (id) {
                for(let i = 0; i < this.items.length; i++) {
                    let item = this.items[i];
                    if (item.transactionId === id) {
                        return item;
                    }
                }
                return undefined;
            },

            expiryDateTz: function(item) {
                return dateTimeUtil.convertExpiryDateForDisplay(item.expiryDate);
            },

            dateTz: function(item) {
                return dateTimeUtil.convertForDisplay(item.date);
            },

            encodeAction: function (item) {
                let encoded = '';

                if ('SHARE' === item.type  || 'OPTION' === item.type) {
                    if ('SELL' === item.action) {
                        encoded = 'SOLD'
                    } else {
                        encoded = 'BOUGHT'
                    }
                } else if ('DIVIDEND' === item.type) {
                    encoded = 'RECEIVED DIVIDEND';
                } else if ('SYNTHETIC_SHARE' === item.type) {
                    encoded = "WILL";
                    if ('SELL' === item.action) {
                        encoded += ' SELL'
                    } else {
                        encoded += ' BUY'
                    }
                }

                let params = {
                    style: 'decimal',
                    minimumFractionDigits: 2
                };


                if (item.type === 'OPTION') {
                    //SOLD 3 LKQ May17'19 30 PUT @ 1
                    encoded += ' ' + item.contracts + ' ' + item.stockSymbol + ' ' + item.optionType + ' ' + this.expiryDateTz(item)
                        + ' ' + item.strikePrice + ' @ ' + Intl.NumberFormat('en-US', params).format(item.premium);
                } else if (item.type === 'SHARE' || item.type === 'SYNTHETIC_SHARE') {
                    //BOUGHT 100 SWKS @ 87.17
                    encoded += ' ' + item.quantity + ' ' + item.symbol + ' @ ' + Intl.NumberFormat('en-US', params).format(item.price);
                } else if (item.type === 'DIVIDEND') {
                    encoded += ' ' + item.symbol + ' @ ' + Intl.NumberFormat('en-US', params).format(item.dividend);
                }
                return encoded;
            },

            calculateLineItemTotal: function (item) {
                if (item.type === 'OPTION') {
                    let price = item.premium;
                    if (item.action === 'BUY') {
                        price = price * -1;
                    }
                    let transactionValue = item.contracts * 100 * price - item.brokerFees;
                    return parseFloat((transactionValue).toFixed(10));
                } else if (item.type === 'SHARE' || item.type === 'SYNTHETIC_SHARE') {
                    let price = item.price;
                    if (item.action === 'BUY') {
                        price = price * -1;
                    }
                    return parseFloat((item.quantity * price - item.brokerFees).toFixed(10));
                } else if (item.type === 'DIVIDEND') {
                    return parseFloat((item.dividend * 100).toFixed(10));
                }
                return 0;
            },

            printCurrencyFormat: function (value) {
                if (typeof value === 'undefined') {
                    value = 0;
                }
                let params = {
                    style: 'currency',
                    currency: 'USD',
                    minimumFractionDigits: 2
                };
                return new Intl.NumberFormat('en-US', params).format(value);
            },

            loadData: function () {
                console.log("RELOADING");
                service
                    .getTradesPerSymbol(this.$route.params.symbol)
                    .then(data => {
                        // console.log("DATA RELOADED");
                        // console.log(data);
                        let self = this;
                        let localItems = [];
                        data.optionList.forEach(function (item) {
                            let model = new OptionApiModel(item.stockSymbol);
                            model.stockPrice = item.stockPrice;
                            model.strikePrice = item.strikePrice;
                            model.contracts = item.contracts;
                            model.premium = item.premium;
                            model.action = item.action;
                            model.optionType = item.optionType;
                            model.brokerFees = item.brokerFees;
                            model.date = item.date;
                            model.expiryDate = item.expiryDate;
                            model.transactionId = item.transactionId;
                            model.groupSelected = item.groupSelected;
                            model.legClosed = item.legClosed;

                            localItems.push(model);
                        });

                        data.shareList.forEach(function (item) {
                            let model = new ShareApiModel(item.symbol);
                            model.price = item.price;
                            model.quantity = item.quantity;
                            model.action = item.action;
                            model.brokerFees = item.brokerFees;
                            model.date = item.date;
                            model.transactionId = item.transactionId;
                            model.type = item.type;
                            model.groupSelected = item.groupSelected;
                            model.legClosed = item.legClosed;

                            if (model.type === 'SYNTHETIC_SHARE') {
                                model.isSynthetic = true;
                            }

                            localItems.push(model);
                        });

                        data.dividendList.forEach(function (item) {
                            let model = new DividendApiModel(item.symbol);
                            model.date = item.date;
                            model.transactionId = item.transactionId;
                            model.dividend = item.dividend;
                            model.groupSelected = item.groupSelected;
                            model.legClosed = item.legClosed;

                            localItems.push(model);
                        });

                        self.items = localItems.sort(function (a, b) {
                            return dateTimeUtil.sortDates(a, b);
                        }).reverse();
                    });

            }
        },

        created() {
            service
                .getShareData(this.$route.params.symbol)
                .then(data => {
                    let self = this;
                    self.shareData = data;
                    self.shareDataLoaded = true;
                });

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

    #trade-list {
        font-size: 0.75rem;
    }

    .table-header {
        background-color: #005cbf;
        color: white;
        vertical-align: center;
        border: #005cbf 1px solid;
        font-weight: bold;
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

    .table-cell-odd:hover {
        background-color: #abdde5;
    }

    .table-cell-even {
        background-color: white;
    }

    .table-cell-even:hover {
        background-color: #abdde5;
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

    .action-icon {
        margin-left: 2px;
    }

    .action-icon:hover {
        cursor: pointer;
        color:red;
    }

    .action-element:hover {
        cursor: pointer;
        font-weight: bold;
    }


    .row-selected {
        color: black;
    }

    .row-not-selected {
        color: gray;
    }

</style>