class UpdateShareApiModel {

    type = 'SHARE';
    transactionId = undefined;
    date = undefined;
    symbol = undefined;
    price = 0;
    preferredPrice = 0;
    quantity = 0;
    action = undefined;
    brokerFees = 0;
    groupSelected = false;
    legClosed = false;

    constructor(symbol) {
        this.symbol = symbol;
    }
}

export default UpdateShareApiModel;