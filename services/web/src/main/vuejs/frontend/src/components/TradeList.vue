<template>
    <div>
        <h2>Trade Log</h2>
        <SymbolSearch class="pt-2"/>

        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-2">Date</div>
            <div class="col-md-4">Action</div>
            <div class="col">Fees</div>
            <div class="col">Total</div>
        </div>

        <div class="row pb-2 pt-2" v-for="item in items">
            <div class="col-md-2">{{ dateTz(item) }}</div>
            <div class="col-md-4">{{ encodeAction(item) }}</div>
            <div class="col">{{ item.brokerFee }}</div>
            <div class="col">{{ calculateLineItemTotal(item).toFixed(4) }}</div>
        </div>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-4">&nbsp;</div>
            <div class="col">&nbsp;</div>
            <div class="col">{{ calculateLineItemsTotal(items).toFixed(4) }}</div>
        </div>
    </div>
</template>

<script>
    import * as moment from "moment";
    import * as momenttz from "moment-timezone";
    import SymbolSearch from './tradelist/SymbolSearch';

    class TradeLog {
        static get Builder() {
            class Builder {
                withId(id) {
                    this.id = id;
                    return this;
                }

                withPairId(pairId) {
                    this.pairId = pairId;
                    return this;
                }

                withSymbol(symbol) {
                    this.symbol = symbol;
                    return this;
                }

                withStockPrice(stockPrice) {
                    this.stockPrice = stockPrice;
                    return this;
                }

                withStrikePrice(strikePrice) {
                    this.strikePrice = strikePrice;
                    return this;
                }

                withContracts(contracts) {
                    this.contracts = contracts;
                    return this;
                }

                withPremium(premium) {
                    this.premium = premium;
                    return this;
                }

                withAction(action) {
                    this.action = action;
                    return this;
                }

                withActionType(actionType) {
                    this.actionType = actionType;
                    return this;
                }

                withBrokerFee(brokerFee) {
                    this.brokerFee = brokerFee;
                    return this;
                }

                withDate(date) {
                    //todo: fix the damn date format
                    this.date = date;
                    return this;
                }

                withExpiryDate(expiryDate) {
                    //todo: fix the damn date format
                    this.expiryDate = expiryDate;
                    return this;
                }

                build() {
                    let future = new TradeLog();
                    future.id = this.id;
                    future.pairId = this.pairId;
                    future.symbol = this.symbol;
                    future.stockPrice = this.stockPrice;
                    future.strikePrice = this.strikePrice;
                    future.contracts = this.contracts;
                    future.premium = this.premium;
                    future.action = this.action;
                    future.actionType = this.actionType;
                    future.brokerFee = this.brokerFee;
                    future.date = this.date;
                    future.expiryDate = this.expiryDate;

                    return future;
                }
            }
            return Builder;
        }
    }

    export default {
        name: "TradeList",
        components: {SymbolSearch},
        data: function () {
            return {
                items : [],
                timeZone : 'Australia/Sydney',
                currency : 'USD'
            }
        },
        methods: {
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
                return total;
            },

            calculateLineItemsTotal: function (items) {
                let _this = this;
                let total = 0.00;
                items.forEach(function (item) {
                    total += _this.calculateLineItemTotal(item);
                });
                return total;
            },

        },
        created() {
            this.items = [
                new TradeLog.Builder()
                    .withId('90ef0965-0eba-4854-9ab5-12ef21be6464')
                    .withPairId(null)
                    .withSymbol('MAT')
                    .withStockPrice(14.83)
                    .withStrikePrice(14)
                    .withContracts(1)
                    .withPremium(0.55)
                    .withAction('SELL')
                    .withActionType('PUT')
                    .withBrokerFee(0)
                    .withDate('2018-10-17T10:00:00Z')
                    .withExpiryDate('2018-11-15T13:00:00Z')
                    .build(),

                new TradeLog.Builder()
                    .withId('b8480fe6-f4ce-4375-b508-feedbf9d3977')
                    .withPairId('90ef0965-0eba-4854-9ab5-12ef21be6464')
                    .withSymbol('MAT')
                    .withStockPrice(null)
                    .withStrikePrice(14)
                    .withContracts(1)
                    .withPremium(0.3)
                    .withAction('BUY')
                    .withActionType('PUT')
                    .withBrokerFee(0)
                    .withDate('2018-11-03T10:00:00Z')
                    .withExpiryDate('2018-11-15T13:00:00Z')
                    .build(),
            ];
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

    }

    .table-cell-even {

    }
</style>