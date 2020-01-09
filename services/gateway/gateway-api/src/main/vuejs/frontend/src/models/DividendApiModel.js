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
        this.accountId = 'd79ec11a-2011-4423-ba01-3af8de0a3e14';
    }
}

export default DividendApiModel;