
const validation = {
    isNumber(test) {
        return !isNaN(test);
    },

    isPositiveInteger(test) {
        if (isNaN(test)) {
            return false;
        }
        let value = parseInt(test, 10);
        return Number.isInteger(value) && value > 0;
    },

    isDate(test) {
        return false;
    }
};

export default validation;