<template>
    <div>
        <h2>Trade Log</h2>

        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-2">Date</div>
            <div class="col-md-4">Action</div>
            <div class="col">Fees</div>
            <div class="col">Total</div>
        </div>

        <template v-for="(item, idx) in items">
            <div class="row pb-2 pt-2" v-bind:class="rowClass(idx)" v-bind:key="item.id">
                <div class="col-md-2">{{ dateTz(item) }}</div>
                <div class="col-md-4">{{ encodeAction(item) }}</div>
                <div class="col">{{ item.brokerFee }}</div>
                <div class="col">{{ calculateLineItemTotal(item).toFixed(4) }}</div>
            </div>
        </template>


        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-4">&nbsp;</div>
            <div class="col">&nbsp;</div>
            <div class="col">{{ calculateLineItemsTotal(items).toFixed(4) }}</div>
        </div>

        <div class="row pt-3">
            <button type="button" class="btn btn-primary">Add new trade</button>
        </div>
    </div>
</template>

<script>
    import * as moment from "moment/moment";
    import * as moment_tz from "moment-timezone";
    import TradeLog from "../models/TradeLog";
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
            rowClass: function (idx) {
                return {
                    'table-cell-odd' : idx%2 === 1,
                    'table-cell-even' : idx%2 === 0
                }
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
                //SOLD 3 LKQ May17'19 30 PUT @ 1
                let encoded = 'BOUGHT ';
                if ('SELL' === item.action) {
                    encoded = 'SOLD '
                }

                encoded += item.contracts + ' ' + item.symbol + ' ' + item.actionType + ' ' + this.expiryDateTz(item) + ' @ '
                    + item.premium;
                return encoded;
            },

            calculateLineItemTotal: function (item) {
                let total = item.contracts * 100;

                if ('SELL' === item.action) {
                    total *= item.premium;
                } else {
                    total *= item.premium * (-1);
                }
                total = total - item.brokerFee;
                this.grandTotal += total;
                return total;
            },

            calculateLineItemsTotal: function (items) {
                let _this = this;
                let total = 0.00;
                items.forEach(function (item) {
                    total += _this.calculateLineItemTotal(item);
                });
                return total;
            }
        },
        created() {
            service
                .getTradesPerSymbol(this.$route.params.symbol)
                .then(data => {
                    let self = this;
                    data.forEach(function (item) {
                        console.log(item);

                        self.items.push(
                            new TradeLog.Builder()
                                .withId(item.transactionId)
                                .withPairId(null)
                                .withSymbol(item.stockSymbol)
                                .withStockPrice(item.stockPrice)
                                .withStrikePrice(item.strikePrice)
                                .withContracts(item.contracts)
                                .withPremium(item.premium)
                                .withAction(item.actionGW)
                                .withActionType(item.actionTypeGW)
                                .withBrokerFee(item.brokerFees)
                                .withDate(item.date)
                                .withExpiryDate(item.expiryDate)
                                .build())
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
</style>