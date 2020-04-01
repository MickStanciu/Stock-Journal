import dateTimeUtil from "./time";

const validation = {
    isNumber(test) {
        return !isNaN(test) && (test + '').trim() !== '';
    },

    isPositiveNumber(test) {
        if (isNaN(test) || (test + '').trim() === '') {
            return false;
        }

        let value = parseFloat(test);
        return value > 0;
    },

    isPositiveOrZeroNumber(test) {
        if (isNaN(test) || (test + '').trim() === '') {
            return false;
        }

        let value = parseFloat(test);
        return value >= 0;
    },

    isPositiveInteger(test) {
        if (isNaN(test) || (test + '').trim() === '') {
            return false;
        }
        let value = parseFloat(test);
        return Number.isInteger(value) && value > 0;
    },

    isDate(test) {
        return dateTimeUtil.checkIfDateIsCorrect(test);
    },

    isExpDate(test) {
        return dateTimeUtil.checkIfExpDateIsCorrect(test);
    },

    isUserName(test) {
        return test.trim().length >= 5
    },

    isPassword(test) {
        return test.trim().length >= 5
    }
};

export default validation;