class CreateShareApiModel {

    transactionId = undefined;
    date = undefined;
    symbol = undefined;
    price = 0;
    quantity = 0;
    action = undefined;
    brokerFees = 0;

    constructor(symbol) {
        this.symbol = symbol;
    }
}

export default CreateShareApiModel;