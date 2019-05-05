<template>
    <div>
        <h2>Trade Log</h2>

        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-2">Date</div>
            <div class="col-md-5">Action</div>
            <div class="col">Fees</div>
            <div class="col">Total</div>
        </div>

        <template v-for="(item, idx) in items">
            <div class="row pb-1 pt-1" v-bind:class="rowClass(item, idx)" v-bind:key="item.id">
                <div class="col-md-2">{{ dateTz(item) }}</div>
                <div class="col-md-5">{{ encodeAction(item) }}</div>
                <div class="col">{{ printCurrencyFormat(item.brokerFee, false) }}</div>
                <div class="col">{{ printCurrencyFormat(calculateLineItemTotal(item), false) }}</div>
            </div>
        </template>


        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-5">&nbsp;</div>
            <div class="col">&nbsp;</div>
            <div class="col">{{ printCurrencyFormat(calculateLineItemsTotal(items), true) }}</div>
        </div>

        <div class="row pt-3">
            <button type="button" class="btn btn-primary">Add new trade</button>
        </div>
    </div>
</template>

<script>
    import * as moment from "moment/moment";
    import * as moment_tz from "moment-timezone";
    import OptionTradeLog from "../models/OptionTradeLog";
    import ShareTradeLog from "../models/ShareTradeLog";
    import service from '../service';

    export default {
        name: "TradeList",
        data: function () {
            return {
                items : [],
                timeZone : 'Australia/Sydney',
                currency : 'USD',
            }
        },
        methods: {
            rowClass: function (item, idx) {
                let className = 'table-cell-odd';
                if (idx%2 === 0) {
                    className = 'table-cell-odd';
                }

                if (item.isSynthetic === true) {
                    className += ' table-cell-synthetic';
                }

                return className;
            },

            expiryDateTz: function(item) {
                //converts 2018-10-17 21:00:00.000000 +11:00 => 2018-10-17T10:00:00Z into ...Nov17'18
                const date = moment(item.expiryDate).tz(this.timeZone);
                return date.format('MMMDD\'YY');
            },

            dateTz: function(item) {
                const date = moment(item.date).tz(this.timeZone);
                return date.format('DD MMM YYYY');
            },

            encodeAction: function (item) {
                let encoded = 'BOUGHT ';
                if ('SELL' === item.action) {
                    encoded = 'SOLD '
                }

                if (item.type === 'OPTION') {
                    //SOLD 3 LKQ May17'19 30 PUT @ 1
                    encoded += item.contracts + ' ' + item.symbol + ' ' + item.actionType + ' ' + this.expiryDateTz(item)
                        + ' ' + item.strikePrice + ' @ ' + item.premium;
                } else if (item.type === 'SHARE') {
                    //BOUGHT 100 SWKS @ 87.17
                    encoded += item.quantity + ' ' + item.symbol + ' @ ' + item.price;
                }
                return encoded;
            },

            calculateLineItemTotal: function (item) {
                if (item.type === 'OPTION') {
                    return item.contracts * 100 * item.premium - item.brokerFee;
                } else if (item.type === 'SHARE') {
                    let price = item.price;
                    if (item.action === 'BUY') {
                        price = price * -1;
                    }
                    return item.quantity * price - item.brokerFee;
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

            printCurrencyFormat: function (value, round) {
                let params = {
                    style: 'currency',
                    currency: 'USD',
                    minimumSignificantDigits: 1,
                    maximumSignificantDigits: 21
                };

                if (round === true) {
                    params.maximumSignificantDigits = 4;
                }

                return new Intl.NumberFormat('en-US', params).format(value);
            }
        },
        created() {
            service
                .getTradesPerSymbol(this.$route.params.symbol)
                .then(data => {
                    let self = this;
                    let localItems = [];
                    data.optionList.forEach(function (item) {
                        localItems.push(
                            new OptionTradeLog.Builder()
                                .withId(item.transactionId)
                                .withPairId(null)
                                .withSymbol(item.stockSymbol)
                                .withStockPrice(item.stockPrice)
                                .withStrikePrice(item.strikePrice)
                                .withContracts(item.contracts)
                                .withPremium(item.premium)
                                .withAction(item.action)
                                .withActionType(item.actionType)
                                .withBrokerFee(item.brokerFees)
                                .withDate(item.date)
                                .withExpiryDate(item.expiryDate)
                                .build())
                    });

                    data.shareList.forEach(function (item) {
                        localItems.push(
                            new ShareTradeLog.Builder()
                                .withId(item.transactionId)
                                .withSymbol(item.symbol)
                                .withPrice(item.price)
                                .withQuantity(item.quantity)
                                .withAction(item.action)
                                .withActionType(item.actionType)
                                .withBrokerFee(item.brokerFees)
                                .withDate(item.date)
                                .build())
                    });

                    data.syntheticShareList.forEach(function (item) {

                        localItems.push(
                            new ShareTradeLog.Builder()
                                .withId(item.transactionId)
                                .withSymbol(item.symbol)
                                .withPrice(item.price)
                                .withQuantity(item.quantity)
                                .withAction(item.action)
                                .withActionType(item.actionType)
                                .withBrokerFee(item.brokerFees)
                                .withDate(item.date)
                                .withSyntheticFlag(true)
                                .build())
                    });

                    //todo: sort
                    self.items = localItems.sort(function (a, b) {
                        const dateA = moment(a.date).tz(self.timeZone);
                        const dateB = moment(b.date).tz(self.timeZone);
                        return dateA - dateB;
                    });
                });
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

</style>