/**
 * Created by TYY on 2017/7/3.
 */
function timeFormat(rowDate) {
    var date = new Date(rowDate);
    var year = date.getFullYear();      //年
    var month = date.getMonth()+1;      //月
    var day = date.getDate();
    var h = date.getHours();        //时
    var m = date.getMinutes();      //分
    var s = date.getSeconds();      //秒
    var str = year + '-' + month + '-' + day + ' ' + h + ':' + m + ':' + s;
    return str;
}