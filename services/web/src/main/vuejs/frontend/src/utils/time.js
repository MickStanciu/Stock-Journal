import * as moment from 'moment/moment';
import * as moment_tz from 'moment-timezone';

//TODO: cannot stay like this!
const dateTimeUtil = {

    getTimeZone: function () {
        return 'Australia/Sydney';
    },

    /*
        GENERATE DATES
     */
    //Returns date 'DD-MMM-YYYY'
    dateNowFormatted : function() {
        const dateFormat = 'DD-MMM-YYYY';
        return moment().tz(this.getTimeZone()).format(dateFormat);
    },

    //Returns expiry date 'MMM DD'
    expDateNowFormatted : function() {
        const dateFormat = 'MMM DD';
        return moment().tz(this.getTimeZone()).format(dateFormat);
    },

    //Returns NOW
    dateNowUnformatted : function() {
        return moment().tz(this.getTimeZone());
    },

    /*
        CONVERTS FROM OFFSET ZULU TO ...
     */

    //Converts OffsetZulu example: 2018-12-25T10:00:00Z to 'DD-MM-YYYY'
    convertFromOffsetZuluToDisplay: function(item) {
        const outputFormat = 'DD-MMM-YYYY';
        if (typeof item === 'undefined') {
            return moment().add(moment().utcOffset(), 'm').format(outputFormat);
        }
        console.warn("convertFromOffsetZuluToDisplay USING TZ");
        return moment(item).tz(this.getTimeZone()).format(outputFormat);
    },

    //Converts Date to DD MMM YYYY
    convertForDisplay: function(item) {
        const dateFormat = 'DD MMM YYYY';
        //return moment(item).tz(this.getTimeZone()).format(dateFormat);

        return moment(item)
            .add(moment().utcOffset(), 'm')
            .format(dateFormat);
    },

    //converts 2018-10-17 21:00:00.000000 +11:00 => 2018-10-17T10:00:00Z into ...Nov17'18
    convertExpiryDateForDisplay: function(item) {
        return moment(item).tz(this.getTimeZone()).format('MMMDD\'YY');
    },

    /*
        CONVERTS FROM ... TO OFFSET ZULU
    */

    //Converts 'DD-MM-YYYY to OffsetZulu example: 2018-12-25T10:00:00Z
    //Assuming the input is in Local time!!!
    convertToOffsetDateTime: function(text) {
        const dateFormat = 'DD-MMM-YYYY';
        const offsetFormat = 'YYYY-MM-DDTHH:mm:ssZ';
        const nowUtc = moment.utc();

        return moment(text, dateFormat)
            .hour(nowUtc.get('hour'))
            .minute(nowUtc.get('minute'))
            .second(nowUtc.get('second'))
            .subtract(moment().utcOffset(), 'm')
            .format(offsetFormat);
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
        const dateA = moment(a.date).tz(this.getTimeZone());
        const dateB = moment(b.date).tz(this.getTimeZone());
        return dateA - dateB;
    }
};

export default dateTimeUtil;

