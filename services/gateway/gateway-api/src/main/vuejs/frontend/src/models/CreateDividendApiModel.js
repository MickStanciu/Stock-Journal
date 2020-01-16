class CreateDividendApiModel {

    date = undefined;
    symbol = undefined;
    dividend = 0;
    quantity = 0;

    constructor(symbol) {
        this.symbol = symbol;
    }
}

export default CreateDividendApiModel;