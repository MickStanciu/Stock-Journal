const moneyUtil = {
    printCurrencyFormat: function (value) {
        if (typeof value === 'undefined') {
            value = 0;
        }
        let params = {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 2
        };
        return new Intl.NumberFormat('en-US', params).format(value);
    }
};

export default moneyUtil;