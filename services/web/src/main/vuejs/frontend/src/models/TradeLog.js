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
                this.date = date;
                return this;
            }

            withExpiryDate(expiryDate) {
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

export default TradeLog;