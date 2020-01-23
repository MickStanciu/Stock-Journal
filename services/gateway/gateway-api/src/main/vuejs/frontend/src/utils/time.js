import {DateTime} from 'luxon';

const DATE_FULL_FORMAT = 'yyyy-LL-dd HH:mm';
const DATE_DMY = 'dd-LLL-yyyy';
const DATE_MD = 'LLL dd';

const dateTimeUtil = {


    //Returns DateTime from 2020-01-18T03:03Z. Assumes ZULU
    createDateTimeFromFullString : function(text) {
        let parsedVal = text.substring(0, 10) + ' ' + text.substring(11, 16);
        return DateTime.fromFormat(parsedVal, DATE_FULL_FORMAT, {'zone': 'UTC'});
    },

    //Returns expiry date 'MMM DD'
    createExpDateFormatted : function() {
        let dt = DateTime.local();
        console.debug("BEFORE >> " + dt.toFormat(DATE_MD) + " >> " + dt.zoneName);
        return dt.toFormat(DATE_MD);
    },

    //Converts
    convertFromOffsetZuluToExpDateDisplay: function(text) {
        let dt = this.createDateTimeFromFullString(text).toLocal();
        return dt.toFormat(DATE_MD);
    },

    //Returns NOW formatted: 2018-12-25T10:00:00Z to '25-Dec-2018'
    createTodayDateFormatted: function() {
        let dt = DateTime.local();

        console.debug("BEFORE >> " + dt.toFormat('dd-LLL-yyyy HH:mm a Z') + " >> " + dt.zoneName);
        return dt.toFormat(DATE_DMY);
    },

    //Converts Java Date from 2020-01-18T02:33Z to DD-MMM-YYYY
    convertFromOffsetZuluToDisplay: function(text) {
        // console.debug(parsedVal);
        let dt = this.createDateTimeFromFullString(text).toLocal();
        // console.debug(dt.toLocal().toString());
        return dt.toFormat(DATE_DMY);
    },

    //converts 2018-10-17 21:00:00.000000 +11:00 => 2018-10-17T10:00:00Z into ...Nov17'18
    convertExpiryDateForDisplay: function(text) {
        let dt = this.createDateTimeFromFullString(text).toLocal();
        return dt.toFormat(DATE_MD);
    },

    //Converts Local Time to java format OffsetDateTime
    //BEFORE >> 18-Jan-2020 13:33 PM +11 >> Australia/Sydney
    //AFTER  >> 18-Jan-2020 02:33 AM +0 >> UTC
    //GWAPI  >> 2020-01-18T02:33:00Z >> Java Format
    //Assuming the input is in Local time!!!
    convertDateToOffsetDateTime: function(text) {
        let now = DateTime.local();
        let dt = DateTime.fromFormat(text, DATE_DMY)
            .plus({
            'hours' : now.hour,
            'minutes' : now.minute
        }).toUTC();

        console.debug("AFTER  >> " + dt.toFormat('dd-LLL-yyyy HH:mm a Z') + " >> " + dt.zoneName);
        return dt.toFormat('yyyy-LL-dd') + 'T' + dt.toFormat('HH:mm:ss') + 'Z';
    },

    //Converts 'MMM DD to OffsetZulu example: 2018-12-25T10:00:00Z
    convertExpToOffsetDateTime: function(text) {
        let now = DateTime.local();
        let dt = DateTime.fromFormat(text, DATE_MD).toUTC();
        console.debug("AFTER  >> " + dt.toFormat('dd-LLL-yyyy HH:mm a Z') + " >> " + dt.zoneName);
        return dt.toFormat('yyyy-LL-dd') + 'T' + dt.toFormat('HH:mm:ss') + 'Z';
    },

    /*
        VALIDATIONS
     */

    checkIfDateIsCorrect: function(text) {
        let obj = DateTime.fromFormat(text, DATE_DMY);
        return obj !== null && obj.isValid === true;
    },

    checkIfExpDateIsCorrect: function(text) {
        let obj = DateTime.fromFormat(text, DATE_MD);
        return obj !== null && obj.isValid === true;
    },

    //sort dates. Compares 2 DateTime Objects
    sortDates: function (a, b) {
        const dateA = this.createDateTimeFromFullString(a);
        const dateB = this.createDateTimeFromFullString(b);
        return dateA > dateB;
    }
};

export default dateTimeUtil;

