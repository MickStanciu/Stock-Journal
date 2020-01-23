class DividendApiModel {

    type = 'DIVIDEND';
    transactionId = undefined;
    accountId = undefined;
    date = undefined;
    symbol = undefined;
    dividend = 0;
    quantity = 0;

    groupSelected = false;
    legClosed = false;

    constructor(symbol) {
        this.symbol = symbol;
    }
}

export default DividendApiModel;