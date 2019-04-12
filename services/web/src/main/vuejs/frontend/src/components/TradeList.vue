<template>
    <div>
        <span>HELLO {{ msg }}</span>
        <ul>
            <li v-for="item in items">
                {{item.date}} {{ item.symbol }} - {{ item.stockPrice }} - {{ item.strikePrice }} - {{ item.contracts }} - {{ item.premium }}
            </li>
        </ul>

    </div>
</template>

<script>

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
        data() {
            return {
                msg : "WORLD",
                items : []
            }
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

</style>