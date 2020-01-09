class OptionApiModel {

    type = 'OPTION';
    transactionId = undefined;
    accountId = undefined;
    date = undefined;
    stockSymbol = undefined;
    stockPrice = 0;
    strikePrice = 0;
    expiryDate = undefined;
    impliedVolatility = 0;
    contracts = 0;
    premium = 0;
    action = undefined;
    optionType = undefined;
    brokerFees = 0;
    mark = null;
    isSynthetic = false;

    groupSelected = false;
    legClosed = false;

    constructor(symbol) {
        this.stockSymbol = symbol;
        this.accountId = 'd79ec11a-2011-4423-ba01-3af8de0a3e14';
    }
}

export default OptionApiModel;