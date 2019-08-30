import * as moment from 'moment/moment';
import * as moment_tz from 'moment-timezone';

//TODO: cannot stay like this!
const timeZone = 'Australia/Sydney';


const dateTimeUtil = {

    /*
        GENERATE DATES
     */
    //Returns date 'DD-MMM-YYYY'
    dateNowFormatted : function() {
        const dateFormat = 'DD-MMM-YYYY';
        return moment().tz(timeZone).format(dateFormat);
    },

    //Returns expiry date 'MMM DD'
    expDateNowFormatted : function() {
        const dateFormat = 'MMM DD';
        return moment().tz(timeZone).format(dateFormat);
    },

    //Returns NOW
    dateNowUnformatted : function() {
        return moment().tz(timeZone);
    },

    /*
        CONVERTS FROM OFFSET ZULU TO ...
     */

    //Converts OffsetZulu example: 2018-12-25T10:00:00Z to 'DD-MM-YYYY'
    convertFromOffsetZuluToDisplay: function(item) {
        const outputFormat = 'DD-MMM-YYYY';
        if (typeof item === 'undefined') {
            return moment().tz(timeZone).format(outputFormat);
        }
        return moment(item).tz(timeZone).format(outputFormat);
    },

    //Converts Date to DD MMM YYYY
    convertForDisplay: function(item) {
        const dateFormat = 'DD MMM YYYY';
        return moment(item).tz(timeZone).format(dateFormat);
    },

    //converts 2018-10-17 21:00:00.000000 +11:00 => 2018-10-17T10:00:00Z into ...Nov17'18
    convertExpiryDateForDisplay: function(item) {
        return moment(item).tz(timeZone).format('MMMDD\'YY');
    },

    /*
        CONVERTS FROM ... TO OFFSET ZULU
    */

    //Converts 'DD-MM-YYYY to OffsetZulu example: 2018-12-25T10:00:00Z
    //Assuming the input is in Australian time!!!
    convertToOffsetDateTime: function(text) {
        const dateFormat = 'DD-MMM-YYYY';
        const offsetFormat = 'YYYY-MM-DDTHH:mm:ssZ';
        const nowUtc = moment.utc();

        return moment(text, dateFormat)
            .tz(timeZone)
            .utc()
            .hour(nowUtc.get('hour'))
            .minute(nowUtc.get('minute'))
            .second(nowUtc.get('second'))
            .format(offsetFormat)
    },

    //Converts 'MMM DD to OffsetZulu example: 2018-12-25T10:00:00Z
    convertExpToOffsetDateTime: function(text) {
        const dateFormat = 'MMM DD';
        return moment(text, dateFormat).utc().format('YYYY-MM-DDTHH:mm:ssZ');
    },

    /*
        VALIDATIONS
     */

    checkIfDateIsCorrect: function(text) {
        const dateFormat = 'DD-MMM-YYYY';
        const obj = moment(text, dateFormat, true);
        return obj !== null && obj !== 'undefined' && obj.isValid() === true;
    },

    checkIfExpDateIsCorrect: function(text) {
        const dateFormat = 'MMM DD';
        const obj = moment(text, dateFormat, true);
        return obj !== null && obj !== 'undefined' && obj.isValid() === true;
    },

    //sort dates
    sortDates: function (a, b) {
        const dateA = moment(a.date).tz(timeZone);
        const dateB = moment(b.date).tz(timeZone);
        return dateA - dateB;
    }
};

export default dateTimeUtil;

