/**
 * User: sunlong Date: 13-2-2 Time: 上午10:37
 */
var common = {};

/**
 * 异步操作
 * 
 * @param url
 */
common.ajaxOperation = function(url, data, errorId) {
    data = data || {};
	$.post(url, data, function(data) {
		if (data.success) {
            setTimeout(function(){
                location.reload();
            }, 1000);
		} else {
            if(errorId){
                common.showError(errorId, data.data);
            }else{
                alert(data.data);
            }
		}
	});
};

/**
 * 同步操作
 * 
 * @param url
 */
common.ajaxOperationWithNoAsync = function(url) {
	$.ajax({
		url : url,
		data : {},
		async : true,
		success : function(data) {
			if (data.success) {
				location.reload();
			} else {
				alert(data.data);
			}
		}
	});
};

common.queryAsyncJobResult = function(url, errorDiv, jobId, success, failed){
    var invoke = function(){
        if(failed){
            failed();
        }
    };
    var queryInterval;
    var query = function(){
        $.get(url, {id: jobId}, function(data) {
            if (data.success) {
                if(data.data.jobstatus == 1){//完成
                    clearInterval(queryInterval);
                    success(data);
                }else if(data.data.jobstatus == 2){//出错
                    common.showError(errorDiv, data.data.jobresult.errortext);
                    clearInterval(queryInterval);
                    invoke();
                }
            } else {
                common.showError(errorDiv, data.data);
                clearInterval(queryInterval);
                invoke();
            }
        });
    };
    queryInterval = setInterval(query, 1000);
};

/**
 * 显示错误消息
 * 
 * @param errorDiv
 * @param errorMsg
 */
common.showError = function(errorDiv, errorMsg) {
	$(errorDiv).html('<div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">&times;</button>' + errorMsg + '</div>').show();
};

common.showInfo = function(errorDiv, errorMsg) {
    $(errorDiv).html('<div class="alert alert-success"><button type="button" class="close" data-dismiss="alert">&times;</button>' + errorMsg + '</div>').show();
};

/**
 * 禁止输入空格
 */
common.keydownFalse = function() {
	$('document').on('keydown', 'input', function(event) {
		if (event.keyCode == 32) {
			return false;
		}
	});
};

/**
 * 数组添加移除元素功能
 * 
 * @param from
 * @param to
 * @return {*}
 */
Array.prototype.remove = function(from, to) {
	var rest = this.slice((to || from) + 1 || this.length);
	this.length = from < 0 ? this.length + from : from;
	return this.push.apply(this, rest);
};

$(function(){
    $("a.dropdown-toggle").mouseenter(function(){
        $(this).trigger('click');
    });

    //修正bootstrap modal 垂直不居中情况，水平居中使用css
    $('body').on('show', '.modal', function(){
        $(this).css({'margin-top':($(window).height()-$(this).height())/2,'top':'0'});
    });

    //设置validation默认样式
    $.validator.setDefaults({errorClass: 'alert alert-error'});

    $('th[data-sort]').click(function(){
        var params = $(this).attr('data-sort').split(',');
        var sortDir = 'ASC';
        if(params[3].trim() == 'ASC'){
            sortDir = 'DESC';
        }
        location.href = params[0].trim() + '?sortName=' + params[2].trim() + '&sortDir=' + sortDir + "&" + params[1].trim();
    });
});



/**
 * 随机串
 * @param len
 * @return {string}
 */
common.ramdomString = function(len){
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
};

common.formatDate = function(date){
    var theDate = new Date(date);
    return theDate.getFullYear() + '-' + (theDate.getMonth()+1) + '-' + theDate.getDate() + ' ' +
        theDate.getHours() + ':' + theDate.getMinutes() + ':' + theDate.getSeconds();
}

common.getEvnetSeverity = function(str){
    var result = "";
    if(str=='5'){
        result = "严重";
    }else if(str=='4'){
        result = "错误";
    }else if(str=='3'){
        result = "警告";
    }else if(str=='2'){
        result = "信息";
    }else if(str=='1'){
        result = "调试";
    }else if(str=='0'){
        result = "清除";
    }else{

    }
    return result;
};

common.getEvnetState = function(str){
    var result = "";
    if(str=='New'){
        result = "新告警";
    }else if(str=='Acknowledged'){
        result = "已确认";
    }else if(str=='Closed'){
        result = "已关闭";
    }else if(str=='Cleared'){
        result = "已清除";
    }else if(str=='aged'){
        result = "已过期";
    }else if(str=='Suppressed'){
        result = "已忽略";
    }else{

    }
    return result;
};