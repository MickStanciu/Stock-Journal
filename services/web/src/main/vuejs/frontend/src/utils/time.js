import * as moment from 'moment/moment';
import * as moment_tz from 'moment-timezone';

//TODO: cannot stay like this!
const timeZone = 'Australia/Sydney';


const dateTimeUtil = {

    //Returns date 'DD-MMM-YYYY'
    dateNowFormatted() {
        const dateFormat = 'DD-MMM-YYYY';
        return moment().tz(timeZone).format(dateFormat);
    },

    //Returns expiry date 'MMM DD'
    expDateNowFormatted() {
        const dateFormat = 'MMM DD';
        return moment().tz(timeZone).format(dateFormat);
    },

    //Converts Date to DD MMM YYYY
    convertForDisplay: function(item) {
        const dateFormat = 'DD MMM YYYY';
        return moment(item).tz(timeZone).format(dateFormat);
    },

    //Converts 'DD-MM-YYYY to OffsetZulu example: 2018-12-25T10:00:00Z
    convertToOffsetDateTime(text) {
        const dateFormat = 'DD-MMM-YYYY';
        return moment(text, dateFormat).utc().format('YYYY-MM-DDTHH:mm:ssZ');
    },

    checkIfDateIsCorrect(text) {
        const dateFormat = 'DD-MMM-YYYY';
        const obj = moment(text, dateFormat, true);
        return obj !== null && obj !== 'undefined' && obj.isValid() === true;
    },

    checkIfExpDateIsCorrect(text) {
        const dateFormat = 'MMM DD';
        const obj = moment(text, dateFormat, true);
        return obj !== null && obj !== 'undefined' && obj.isValid() === true;
    },

    //converts 2018-10-17 21:00:00.000000 +11:00 => 2018-10-17T10:00:00Z into ...Nov17'18
    convertExpiryDateForDisplay: function(item) {
        return moment(item.expiryDate).tz(timeZone).format('MMMDD\'YY');
    },

    //sort dates
    sortDates : function (a, b) {
        const dateA = moment(a.date).tz(timeZone);
        const dateB = moment(b.date).tz(timeZone);
        return dateA - dateB;
    }
};

export default dateTimeUtil;

