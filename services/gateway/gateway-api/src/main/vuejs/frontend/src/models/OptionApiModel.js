class OptionApiModel {

    type = 'OPTION';
    transactionId = undefined;
    date = undefined;
    stockSymbol = undefined;
    stockPrice = 0;
    strikePrice = 0;
    expiryDate = undefined;
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
    }
}

export default OptionApiModel;