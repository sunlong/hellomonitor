<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改密码</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            //客户端验证
            $("#user-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    $.post('${ctx}/user/updatePassword', $("#user-form").serialize(), function(data){
                        if(data.success){
                            location.href = '${ctx}/user/list';
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    password: {required: true, maxlength: 32},
                    newPassword:{required: true, maxlength: 32},
                    rePassword:{required: true, maxlength: 32, equalTo:"#newPassword"}
                },
                messages: {
                    rePassword: {equalTo:'两次密码不一致'}
                }
            });
        });
    </script>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/user/user-center-sidebar.jsp"%>
        <div class="span10 content">
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>修改密码</li>
                </ul>
            </div>

            <div id="error"></div>
            <form class="form-horizontal" id="user-form" autocomplete="off">
                <input type="hidden" name="id" value="${user.id}" />
                <div class="control-group">
                    <label class="control-label" for="password"><span class="font-red">*</span>旧密码</label>
                    <div class="controls">
                        <input type="password" id="password" name="password"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="newPassword"><span class="font-red">*</span>新密码</label>
                    <div class="controls">
                        <input type="password" id="newPassword" name="newPassword"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="rePassword"><span class="font-red">*</span>重复新密码</label>
                    <div class="controls">
                        <input type="password" id="rePassword" name="rePassword"/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input class="btn btn-primary" type="submit" value="提交" />
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>