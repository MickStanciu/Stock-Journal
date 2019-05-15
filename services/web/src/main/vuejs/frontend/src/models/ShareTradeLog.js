class ShareTradeLog {

    type = 'SHARE';
    isSynthetic = false;
    id = undefined;
    symbol = undefined;
    price = undefined;
    quantity = undefined;
    action = undefined;
    actionType = undefined;
    brokerFee = undefined;
    date = undefined;
    static get Builder() {
        class Builder {
            withId(id) {
                this.id = id;
                return this;
            }

            withSymbol(symbol) {
                this.symbol = symbol;
                return this;
            }

            withPrice(price) {
                this.price = price;
                return this;
            }

            withQuantity(quantity) {
                this.quantity = quantity;
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

            withSyntheticFlag(flag) {
                this.isSynthetic = flag;
                return this;
            }

            build() {
                let future = new ShareTradeLog();
                future.id = this.id;
                future.pairId = this.pairId;
                future.symbol = this.symbol;
                future.price = this.price;
                future.quantity = this.quantity;
                future.action = this.action;
                future.actionType = this.actionType;
                future.brokerFee = this.brokerFee;
                future.date = this.date;
                future.isSynthetic = this.isSynthetic;

                return future;
            }
        }
        return Builder;
    }
}

export default ShareTradeLog;