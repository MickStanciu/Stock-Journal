class CreateOptionApiModel {

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

    constructor(symbol) {
        this.stockSymbol = symbol;
    }
}

export default CreateOptionApiModel;