<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加模板权限</title>
    <%@ include file="/common/header.jsp"%>
    <script id="addActionTmpl" type="text/html">
                             名称<input type="text" name="name"/>&nbsp;&nbsp;<br>
                             描述<input type="text" name="description"/>&nbsp;&nbsp;
        <input type="submit" class="btn btn-primary" value="确定"/>&nbsp;&nbsp;
        <input type="button" value="取消" class="btn"/>
    </script>
    <script type="text/javascript">
        $(function(){
            //添加模块权限或者修改意象派、修改标题
            var url = '';
            if($('input[name="id"]').val()){//如果id有值
                url = '${ctx}/resource/update';
                $(document).attr("title", "修改模块权限");
                $("#title").text("修改模块权限");
             }else{
                url = '${ctx}/resource/create';
            }
			
            var actions = [];//存储模块权限功能
            var obj = {name:'', description:''};
            <c:forEach items="${resource.actions}" var="action"  varStatus="status"> 
               obj.name = '${action.name}';
               obj.description = '${action.description}';     
               var obj2 = {name:obj.name, description:obj.description};
                actions.push(obj2);        
            </c:forEach>
            
            //客户端验证
            $("#resource-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    var actionPrams = '';
                    for(var i=0; i<actions.length; i++){
                        actionPrams += '&actions['+ i +'].name='+actions[i].name + '&actions['+ i +'].description='+actions[i].description;                
                    }
                    $.post(url, $("#resource-form").serialize() + actionPrams, function(data){
                        if(data.success){
                            location.href = '${ctx}/resource/list';
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 50, lettersonly:true}
                }
            });
            //提交resource表单
            $("#add-action-form a").click(function(){
                $("#resource-form").submit();
            });

            //资源动作
            var getConfig = function(formId, errorId){
                   return {
                   errorPlacement: function(error, element){
                        common.showError(errorId, error.html());
                    },
                    submitHandler: function(){
                        var form = $("#add-action-form").serializeArray();   
                        for(var j=0; j<actions.length; j++){
                        	if(actions[j].name == form[0].value){
                        		common.showError("#error","功能已经存在");
                        		return false;
                        	}
                        }                  

                        $("#actions").append('<span>'+ form[0].value + '<i class="icon-remove"></i>&nbsp;&nbsp;</span>');
                        obj.name = form[0].value;
                        obj.description = form[1].value;
                        var obj2 = {name:obj.name, description:obj.description};
                        actions.push(obj2);                        
                        hideForm();

                        return false;
                    },
                    rules:{
                        name: {required: true, maxlength: 50, lettersonlyAndStar: true}
                   }
                };
            };

            //响应icon-remove动作
            $("#actions").on('click', '.icon-remove', function(){
                 var i = -1;
                 for(var j=0; j<actions.length; j++){
                    if(actions[j].name.trim() == $(this).parent().text().trim()){
						i = j;
						break;
                   }
                }   
                if(i>-1){
                    actions.remove(i);//从actions中删除数据
                    $(this).parent().remove();
                }
            });
 
            //显示form
            $("#action-div").on('click', "button", function(){
                $("#action-div").html(parseTemplate($("#addActionTmpl").html(), {}));
                //客户端验证
                $("#add-action-form").validate(getConfig('#add-action-form', '#error'));
            });
            var hideForm = function (){
                $("#action-div").html('<button class="btn btn-small"><i class="icon-plus"></i>添加动作</button>');
            };
            //取消
            $("#action-div").on('click', "input[type='button']", function(){
                hideForm();
            });
        });
    </script>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/user/resource-sidebar.jsp"%>
        <div class="span10 content">
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li><a href="${ctx}/resource/list">模块权限管理</a> <span class="divider">/</span></li>
                    <li id="title">添加模块权限</li>
                </ul>
            </div>

            <div id="error"></div>
            <form class="form-horizontal" action="${ctx}/resource/create" id="resource-form" autocomplete="off">
                <input type="hidden" name="id" value="${resource.id}">
                <div class="control-group">
                    <label class="control-label" for="resourceName"><span class="font-red">*</span>名称</label>
                    <div class="controls">
                        <input type="text" id="resourceName" name="name" value="${resource.name}"/><span class="help-inline">仅限英文</span>
                    </div>
                </div>
                <div class="control-group clearfix">
                    <label class="control-label" for="description">描述</label>
                    <div class="controls">
                        <textarea type="text" id="description" name="description">${resource.description}</textarea>
                    </div>
                </div>
            </form>
            <form class="form-horizontal" id="add-action-form">
                <div class="control-group">
                    <label class="control-label" for="resourceName">功能</label>
                    <div class="controls" id="actions">
                        <c:forEach items="${resource.actions}" var="action"><span>${action.name}<i class="icon-remove"></i>&nbsp;&nbsp;</span></c:forEach>
                    </div>
                    <div class="controls" id="action-div">
                        <button class="btn btn-small"><i class="icon-plus"></i>添加</button>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <a href="javascript:void(0)" class="btn btn-primary">提交</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>