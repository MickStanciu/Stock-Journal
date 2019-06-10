class ShareApiModel {

    type = 'STOCK';
    transactionId = undefined;
    accountId = undefined;
    date = undefined;
    symbol = undefined;
    price = undefined;
    quantity = undefined;
    action = undefined;
    actionType = undefined;
    brokerFees = undefined;
    mark = null;
    isSynthetic = false;


    constructor(symbol) {
        this.symbol = symbol;
        this.accountId = 'd79ec11a-2011-4423-ba01-3af8de0a3e14';
    }
}

export default ShareApiModel;