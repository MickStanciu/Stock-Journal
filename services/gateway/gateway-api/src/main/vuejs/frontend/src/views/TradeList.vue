<template>
    <div id="trade-list">
        <share-data v-bind:datamodel="shareData" v-if="shareDataLoaded"/>

        <div class="row pt-2 mt-3">
            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewStockTradeClicked">
                    <font-awesome-icon icon="plus-circle"/>&nbsp;Stock
                </button>
            </div>

            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewOptionTradeClicked">
                    <font-awesome-icon icon="plus-circle"/>&nbsp;Option
                </button>
            </div>

            <div class="col">
                <button type="button" class="btn btn-primary" v-on:click="addNewDividendTradeClicked">
                    <font-awesome-icon icon="plus-circle"/>&nbsp;Dividend
                </button>
            </div>
        </div>

        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-2">Date</div>
            <div class="col-md-5">Action</div>
            <div class="col-md-1">Fees</div>
            <div class="col-md-2 text-right">Total</div>
            <div class="col-md-2">Action</div>
        </div>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-5">&nbsp;</div>
            <div class="col-md-1">&nbsp;</div>
            <div class="col-md-2 text-right">{{ printCurrencyFormat(getTotal) }}</div>
            <div class="col-md-2">&nbsp;</div>
        </div>

        <template v-for="(item, idx) in items">
            <div class="row pb-1 pt-1" v-bind:class="rowClass(item, idx)" v-bind:key="item.transactionId">
                <div class="col-md-2" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ dateTz(item) }}</div>
                <div class="col-md-5" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']" v-html="encodeAction(item)"></div>
                <div class="col-md-1" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ printCurrencyFormat(item.brokerFees) }}</div>
                <div class="col-md-2 text-right" v-bind:class="[item.groupSelected ? 'row-selected' : 'row-not-selected']">{{ printCurrencyFormat(calculateLineItemTotal(item)) }}</div>
                <div class="col-md-2">
                    <template  v-if="!item.isSynthetic">
                        <template v-if="item.legClosed">
                            <font-awesome-icon icon="unlock" class="action-icon" v-on:click="legClosedClicked(item)" v-bind:class="[item.legClosed ? 'item-not-active' : 'item-active']"></font-awesome-icon>
                        </template>
                        <template v-else>
                            <font-awesome-icon icon="lock-open" class="action-icon" v-on:click="legClosedClicked(item)" v-bind:class="[item.legClosed ? 'item-not-active' : 'item-active']"></font-awesome-icon>
                        </template>
                        <font-awesome-icon icon="calculator" class="action-icon" v-on:click="groupSelectClicked(item)" v-bind:class="[item.groupSelected ? 'item-active' : 'item-not-active']"></font-awesome-icon>
                        <font-awesome-icon icon="edit" class="action-icon" v-on:click="editRecordClicked(item)"></font-awesome-icon>
                        <font-awesome-icon icon="trash-alt" class="action-icon" v-on:click="deleteRecordClicked(item)"></font-awesome-icon>
                    </template>

                    <template v-else>
                        <font-awesome-icon icon="dollar-sign" class="action-icon" v-on:click="syntheticPriceClicked()"></font-awesome-icon>
                    </template>

                </div>
            </div>
        </template>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-5">&nbsp;</div>
            <div class="col-md-1">&nbsp;</div>
            <div class="col-md-2 text-right">{{ printCurrencyFormat(getTotal) }}</div>
            <div class="col-md-2">&nbsp;</div>
        </div>

        <h5 class="pt-3">Statistics</h5>
        <statistics v-bind:datamodel="statisticsModel" />

        <transition name="fade">
            <add-stock-trade v-if="this.$store.state.stock.isAddStockModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
            <edit-stock-trade v-if="this.$store.state.stock.isEditStockModelEnabled" v-bind:stock_model="selectedModel"/>
            <delete-stock-trade v-if="this.$store.state.stock.isDeleteStockModalEnabled" v-bind:post="{model: selectedModel}"/>
            <synthetic-price v-if="this.$store.state.isSyntheticModalEnabled" v-bind:post="{model: selectedModels}"/>

            <add-option-trade v-if="this.$store.state.option.isAddOptionModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
            <edit-option-trade v-if="this.$store.state.option.isEditOptionModelEnabled" v-bind:stock_model="selectedModel"/>
            <delete-option-trade v-if="this.$store.state.option.isDeleteOptionModalEnabled" v-bind:post="{model: selectedModel}"/>

            <add-dividend-trade v-if="this.$store.state.dividend.isAddDividendModalEnabled" v-bind:post="{symbol: symbol.toUpperCase()}"/>
            <delete-dividend-trade v-if="this.$store.state.dividend.isDeleteDividendModalEnabled" v-bind:post="{model: selectedModel}"/>

            <add-error v-if="isAddErrorEnabled"/>
        </transition>
    </div>
</template>

<script>
    import service from '../service';
    import dateTimeUtil from '../utils/time'
    import moneyUtil from "../utils/money";
    import AddStockTrade from "../components/tradelist/stock/AddStockTrade";
    import DeleteStockTrade from "../components/tradelist/stock/DeleteStockTrade";
    import EditStockTrade from "../components/tradelist/stock/EditStockTrade";
    import AddOptionTrade from "../components/tradelist/option/AddOptionTrade";
    import EditOptionTrade from "../components/tradelist/option/EditOptionTrade";
    import DeleteOptionTrade from "../components/tradelist/option/DeleteOptionTrade";
    import AddDividendTrade from "../components/tradelist/dividend/AddDividendTrade";
    import DeleteDividendTrade from "../components/tradelist/dividend/DeleteDividendTrade";
    import AddError from "../components/tradelist/AddError";
    import ShareApiModel from "../models/ShareApiModel";
    import ShareData from "../components/tradelist/ShareData";
    import SettingsApiModel from "../models/SettingsApiModel";
    import SyntheticPrice from "../components/tradelist/SyntheticPrice";
    import Statistics from "../components/tradelist/Statistics";
    import statisticsModel from "../models/StatisticsModel";

    export default {
        name: "TradeList",
        components: {
            EditOptionTrade,
            EditStockTrade,
            Statistics,
            SyntheticPrice,
            DeleteDividendTrade, AddDividendTrade,
            ShareData, AddOptionTrade, AddError, AddStockTrade, DeleteStockTrade, DeleteOptionTrade
        },

        data: function () {
            return {
                items: [],
                itemsLoaded: false,
                shareData: undefined,
                shareDataLoaded: false,
                currency: 'USD',
                symbol: this.$route.params.symbol,
                selectedModel : undefined,
                selectedModels : [],
                statisticsModel : statisticsModel
            }
        },

        computed: {
            isAddErrorEnabled() {
                return this.$store.state.isAddErrorEnabled;
            },
            getTotal: function () {
                return this.statisticsModel.selected.realisedPremium;
            }
        },

        watch: {
            itemsLoaded: {
                handler: function (newVal, oldVal) {
                    console.log("ITEMS LOADED");
                    if (newVal === true) {
                        this.loadStats(this);
                    }
                },
                deep: true
            }
        },

        methods: {
            addNewStockTradeClicked: function() {
                this.$store.dispatch('stock/showAddStockModal');
            },
            addNewOptionTradeClicked: function() {
                this.$store.dispatch('option/showAddOptionModal');
            },
            addNewDividendTradeClicked: function() {
                this.$store.dispatch('dividend/showAddDividendModal');
            },

            deleteRecordClicked: function(item) {
                this.selectedModel = item;

                if (typeof this.selectedModel !== 'undefined') {
                    if ("SHARE" === this.selectedModel.type) {
                        this.$store.dispatch('stock/showDeleteStockModal');
                    } else if ("OPTION" === this.selectedModel.type) {
                        this.$store.dispatch('option/showDeleteOptionModal');
                    } else if ("DIVIDEND" === this.selectedModel.type) {
                        this.$store.dispatch('dividend/showDeleteDividendModal');
                    } else {
                        console.error("Clicked on unknown type: " + this.selectedModel.type);
                    }
                }
            },

            editRecordClicked: function(item) {
                this.selectedModel = item;

                if ("SHARE" === this.selectedModel.type) {
                    this.$store.dispatch('stock/showEditStockModal');
                } else if ("OPTION" === this.selectedModel.type) {
                    this.$store.dispatch('option/showEditOptionModal');
                } else if ("DIVIDEND" === this.selectedModel.type) {
                    this.$store.dispatch('dividend/showEditDividendModal');
                } else {
                    console.error("Clicked on unknown type: " + this.selectedModel.type);
                }
            },

            legClosedClicked: function(item) {
                item.legClosed = !item.legClosed;

                let settingModel = new SettingsApiModel();
                settingModel.transactionId = item.transactionId;
                settingModel.groupSelected = item.groupSelected;
                settingModel.legClosed = item.legClosed;
                settingModel.preferredPrice = 0;

                service.saveSetting(settingModel);
                this.loadStats(this);
            },

            groupSelectClicked : function (item) {
                item.groupSelected = !item.groupSelected;

                let settingModel = new SettingsApiModel();
                settingModel.transactionId = item.transactionId;
                settingModel.groupSelected = item.groupSelected;
                settingModel.legClosed = item.legClosed;
                settingModel.preferredPrice = 0;

                service.saveSetting(settingModel);
                this.loadStats(this);
            },

            syntheticPriceClicked: function () {
                //will scan all items and update the price into them!
                this.selectedModels = this.getAllActiveShares();
                this.$store.dispatch('showSyntheticShareModal');
            },

            rowClass: function (item, idx) {
                if (item.isSynthetic === true) {
                    return 'table-cell-synthetic';
                }

                if (item.legClosed === true) {
                    return 'table-cell-closed';
                } else {
                    let className = 'table-cell-odd';
                    if (idx%2 === 0) {
                        className = 'table-cell-even';
                    }
                    return className;
                }
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

            getAllActiveShares: function() {
                let activeShares = [];
                for(let i = 0; i < this.items.length; i++) {
                    if (this.items[i].type === 'SHARE' && this.items[i].groupSelected === true && this.items[i].legClosed === false) {
                        activeShares.push(this.items[i]);
                    }
                }
                return activeShares;
            },

            expiryDateTz: function(item) {
                return dateTimeUtil.convertExpiryDateForDisplay(item.expiryDate);
            },

            dateTz: function(item) {
                return dateTimeUtil.convertFromOffsetZuluToDisplay(item.date);
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
                    encoded = 'RECEIVED ' + item.quantity + ' DIVIDEND';
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
                } else if (item.type === 'SHARE') {
                    //BOUGHT 100 SWKS @ 87.17
                    encoded += ' ' + item.quantity + ' ' + item.symbol + ' @ ' + Intl.NumberFormat('en-US', params).format(item.price);
                } else if (item.type === 'SYNTHETIC_SHARE') {
                    if (item.preferredPrice !== null && item.preferredPrice !== 0.00) {
                        encoded += ' ' + item.quantity + ' ' + item.symbol + ' @ ' + '<s style="background-color: #ed969e">' + Intl.NumberFormat('en-US', params).format(item.price) + '</s>' + ' '
                            + Intl.NumberFormat('en-US', params).format(item.preferredPrice);
                    } else {
                        encoded += ' ' + item.quantity + ' ' + item.symbol + ' @ ' + Intl.NumberFormat('en-US', params).format(item.price);

                    }
                } else if (item.type === 'DIVIDEND') {
                    encoded += ' ' + item.symbol + ' @ ' + Intl.NumberFormat('en-US', params).format(item.dividend);
                }
                return encoded;
            },

            calculateLineItemTotal: function (item) {
                if (item.type === 'OPTION') {
                    return this.calculateOptionLineItemPL(item);
                } else if (item.type === 'SHARE') {
                   return this.calculateShareLineItemPL(item);
                } else if (item.type === 'SYNTHETIC_SHARE') {
                    return this.calculateSyntheticShareLineItemPL(item, true);
                } else if (item.type === 'DIVIDEND') {
                    return this.calculateDividendLineItemPL(item);
                }
                console.error("UNKNOWN TYPE: " + item.type);
                return 0;
            },

            calculateShareLineItemPL: function (item) {
                if (item.type !== 'SHARE') {
                    console.error("Item type is not SHARE");
                    return 0;
                }

                let price = item.price;
                if (item.action === 'BUY') {
                    price = price * -1;
                }
                return parseFloat((item.quantity * price - item.brokerFees).toFixed(10));
            },

            calculateSyntheticShareLineItemPL: function (item, useAverage) {
                if (item.type !== 'SYNTHETIC_SHARE') {
                    console.error("Item type is not SYNTHETIC_SHARE");
                    return 0;
                }

                let price = item.price;
                if (useAverage === true && item.preferredPrice !== null && item.preferredPrice !== 0.00) {
                    price = item.preferredPrice;
                }
                if (item.action === 'BUY') {
                    price = price * -1;
                }
                return parseFloat((item.quantity * price - item.brokerFees).toFixed(10));
            },

            calculateOptionLineItemPL: function (item) {
                if (item.type !== 'OPTION') {
                    console.error("Item type is not OPTION");
                    return 0;
                }

                let income = item.premium * item.contracts * 100;
                    if (item.action === 'BUY') {
                        income = income * -1;
                    }
                return parseFloat((income - item.brokerFees).toFixed(10));
            },

            calculateDividendLineItemPL: function (item) {
                return parseFloat((item.quantity * item.dividend).toFixed(10));
            },

            printCurrencyFormat: moneyUtil.printCurrencyFormat,

            loadStats: function(context) {
                let totalRealisedPremium = 0;
                let selectedRealisedPremium = 0;
                let sharesNumber = 0;
                let shareAveragePrice = 0;

                this.items.forEach(function (item) {
                    if (item.type === 'SYNTHETIC_SHARE') {
                        totalRealisedPremium += parseFloat(context.calculateSyntheticShareLineItemPL(item, false));
                    } else {
                        totalRealisedPremium += parseFloat(context.calculateLineItemTotal(item));
                    }

                    if (item.groupSelected === true) {
                        if (item.type === 'SYNTHETIC_SHARE') {
                            selectedRealisedPremium += parseFloat(context.calculateSyntheticShareLineItemPL(item, false));
                        } else {
                            selectedRealisedPremium += parseFloat(context.calculateLineItemTotal(item));
                        }
                    }

                    if (item.type === 'SHARE') {
                        if (item.action === 'BUY') {
                            sharesNumber += item.quantity;
                        } else {
                            sharesNumber -= item.quantity;
                        }
                    }

                    if (item.type === 'SYNTHETIC_SHARE') {
                        shareAveragePrice = item.price;
                    }
                });

                this.statisticsModel.totals.realisedPremium = totalRealisedPremium;
                this.statisticsModel.selected.realisedPremium = selectedRealisedPremium;
                this.statisticsModel.sharesNumber = sharesNumber;
                this.statisticsModel.shareAveragePrice = shareAveragePrice;
                if (sharesNumber !== 0) {
                    this.statisticsModel.breakEvenPrice = (sharesNumber * shareAveragePrice - selectedRealisedPremium) / sharesNumber;
                }
            },

            loadData: function () {
                console.log("RELOADING");
                service
                    .getTradesPerSymbol(this.$route.params.symbol)
                    .then(data => {
                        // console.log("DATA RELOADED");
                        let self = this;
                        self.itemsLoaded = false;
                        let localItems = [];
                        data.optionList.forEach(function (item) {
                        //     let model = new OptionApiModel(item.stockSymbol);
                        //     model.stockPrice = item.stockPrice;
                        //     model.strikePrice = item.strikePrice;
                        //     model.contracts = item.contracts;
                        //     model.premium = item.premium;
                        //     model.action = item.action;
                        //     model.optionType = item.optionType;
                        //     model.brokerFees = item.brokerFees;
                        //     model.date = item.date;
                        //     model.expiryDate = item.expiryDate;
                        //     model.transactionId = item.transactionId;
                        //     model.groupSelected = item.groupSelected;
                        //     model.legClosed = item.legClosed;
                        //
                        //     localItems.push(model);
                        });

                        data.shareList.forEach(function (item) {
                            let model = new ShareApiModel(item.symbol);
                            model.price = item.price;
                            model.preferredPrice = item.preferredPrice;
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
                            // let model = new DividendApiModel(item.symbol);
                            // model.date = item.date;
                            // model.transactionId = item.transactionId;
                            // model.dividend = item.dividend;
                            // model.quantity = item.quantity;
                            // model.groupSelected = item.groupSelected;
                            // model.legClosed = item.legClosed;
                            //
                            // localItems.push(model);
                        });

                        localItems = localItems.sort(function (a, b) {
                            return dateTimeUtil.sortDates(a, b);
                        }).reverse();

                        self.items = localItems;
                        self.itemsLoaded = true;
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
                if (mutation.type === 'refreshData') {
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

    .table-cell-synthetic:hover {
        background-color: #abdde5;
    }

    .table-cell-closed {
        background-color: lightgray;
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
        margin-right: 2px;
        width: 1rem;
    }

    .action-icon:hover {
        cursor: pointer;
    }

    .row-selected {
        color: black;
    }

    .row-not-selected {
        color: gray;
    }

    .item-active {
        color: green;
    }

    .item-not-active {
        color: red;
    }

</style>
