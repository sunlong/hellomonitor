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

common.formatDate = function(date){
    var theDate = new Date(date);
    return theDate.getFullYear() + '-' + (theDate.getMonth()+1) + '-' + theDate.getDate() + ' ' +
        theDate.getHours() + ':' + theDate.getMinutes() + ':' + theDate.getSeconds();
};

/**
 * 数组添加移除元素功能
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
    $.validator.setDefaults({errorClass: 'alert alert-error', errorLabelContainer: $("#info")});

    //搜索排序
    $('th[data-sort]').click(function(){
        var params = $(this).attr('data-sort').split(',');
        var sortDir = 'ASC';
        if(params[3].trim() == 'ASC'){
            sortDir = 'DESC';
        }
        location.href = params[0].trim() + '?sortName=' + params[2].trim() + '&sortDir=' + sortDir + "&" + params[1].trim();
    });
});