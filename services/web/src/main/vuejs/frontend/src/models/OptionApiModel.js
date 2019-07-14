class OptionApiModel {

    type = 'OPTION';
    transactionId = undefined;
    accountId = undefined;
    date = undefined;
    stockSymbol = undefined;
    stockPrice = undefined;
    strikePrice = undefined;
    expiryDate = undefined;
    impliedVolatility = null;
    contracts = undefined;
    premium = undefined;
    action = undefined;
    optionType = undefined;
    brokerFees = undefined;
    mark = null;
    isSynthetic = false;

    isIncludedInTotalCalculation = true;

    constructor(symbol) {
        this.stockSymbol = symbol;
        this.accountId = 'd79ec11a-2011-4423-ba01-3af8de0a3e14';
    }
}

export default OptionApiModel;