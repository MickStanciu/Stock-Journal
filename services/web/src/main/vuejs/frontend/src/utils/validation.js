import dateTimeUtil from "./time";

const validation = {
    isNumber(test) {
        return !isNaN(test);
    },

    isPositiveInteger(test) {
        if (isNaN(test)) {
            return false;
        }
        let value = parseFloat(test);
        return Number.isInteger(value) && value > 0;
    },

    isDate(test) {
        return dateTimeUtil.checkIfDateIsCorrect(test);
    }
};

export default validation;