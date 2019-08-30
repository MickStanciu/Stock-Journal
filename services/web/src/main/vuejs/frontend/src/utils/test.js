var m = [];
m[0] = ["moment()", moment()];
m[1] = ["moment(moment())", moment(moment())];
m[2] = ["moment().format()", moment().format()];
m[3] = ["moment().toString()", moment().toString()];
m[4] = ["moment('2013-11-16') - moment('2013-11-15')", moment('2013-11-16') - moment('2013-11-15')];
m[5] = ["moment().endOf('day')", moment().endOf('day')];
var now = moment('2013-11-22'); //same as finish
var start = moment('2013-11-10');
var finish = moment('2013-11-22');
var event = moment('2013-11-16').endOf('day');
m[6] = ["event.diff(start)", event.diff(start)];
m[7] = ["event.diff(finish)", event.diff(finish)];
m[8] = ["moment().format('YYYY-MM-DD')", moment().format('YYYY-MM-DD')];
m[9] = ["moment().format('YYYY')", moment().format('YYYY')];
m[10] = ["moment().format('MM-DD')", moment().format('MM-DD')];
m[11] = ["start.diff(finish)", start.diff(finish)];
m[12] = ["finish.diff(start)", finish.diff(start)];
m[13] = ["moment(start).add('years',1).calendar()", moment(start).add('years',1).calendar()];
m[14] = ["now.diff(start) > 0 && now.diff(finish) < 0", now.diff(start) > 0 && now.diff(finish) < 0];
m[15] = ["moment(moment())", moment(moment())];
m[16] = ["moment('Tue Dec 23 12:02:08 EST 2014').format()",moment('Tue Dec 23 12:02:08 EST 2014').format()];
m[17] = ["moment().format('MMMM Do [at] h:mm:ss a')", moment().format('MMMM Do [at] h:mm a')];
m[18] = ["moment('2015/04/02 12:00').subtract('hours',9).format('YYYY/MM/DD hh:mm')", moment('2015/04/02 12:00').subtract('hours',9).format('YYYY/MM/DD hh:mm')];
m[19] = ["moment('1/1/0001').format()",moment('1/1/0001').format()];
m[20] = ["moment('0001-01-01').format()",moment('0001-01-01').format()];
m[21] = ["moment().format('MMMM D, YYYY')",moment().format('MMMM D, YYYY')];
m[22] = ["event.diff(start)/1000/60",event.diff(start)/1000/60];
m[23] = ["now.isBetween(start, finish, '(]'))",now.isBetween(start, finish, 'days','(]')];


var units = null || 'milliseconds' || 'seconds' || 'minutes' || 'hours' || 'days' || 'months' || 'years';

var x = moment("01/01/2015", "MM/DD/YYYY");
var a = moment("01/01/2015", "MM/DD/YYYY");
var b = moment("01/01/2016", "MM/DD/YYYY");

m[24] = ['x.isBetween(a,b)',x.isBetween(a,b)];
m[25] = ['x.isBetween(a,b,units)',x.isBetween(a,b,units)];
m[26] = ['x.isBetween(a,b,units,[])',x.isBetween(a,b,units,[])]
m[27] = ['x.isBetween(a,b,[])',x.isBetween(a,b,[])];
m[28] = ['!x.isBefore(a) && !x.isAfter(b)', !x.isBefore(a) && !x.isAfter(b)];

m[29] = ['', moment().add(4, 'weeks')];
m[30] = ['', moment().add(28, 'days')];

var iat = moment(parseInt('' + 1515021797 + '000'));
var exp = moment(parseInt('' + 1515025397 + '000'));

m[31] = ['idtoken.iat', iat];
m[32] = ['idtoken.exp', exp ];
m[33] = ['idtoken.diff', iat.diff(exp)];

for(var i = 0; i < m.length; i++) {
    $('#output > tbody:last').append('<tr><td>' + m[i][0] + '</td><td>' + m[i][1] + '</td></tr>');
}

