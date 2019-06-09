class OptionApiModel {

    transactionId = undefined;
    accountId = undefined;
    date = undefined;
    symbol = undefined;
    action = undefined;
    actionType = 'OPTION';
    brokerFees = undefined;
    mark = null;


    constructor(symbol) {
        this.symbol = symbol;
        this.accountId = 'd79ec11a-2011-4423-ba01-3af8de0a3e14';
    }
}

export default OptionApiModel;