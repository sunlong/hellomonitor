<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改个人信息</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            //客户端验证
            $("#user-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    $.post('${ctx}/user/updatePersonal', $("#user-form").serialize(), function(data){
                        if(data.success){
                            common.showInfo('#error', '修改成功');
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 50},
                    email:{required: true, maxlength: 50, email: true},
                    nickname: {required: true, maxlength: 50},
                    phone: {required: true, maxlength: 11, digits: true}
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
                    <li>修改个人信息</li>
                </ul>
            </div>

            <div id="error"></div>
            <form class="form-horizontal" id="user-form" autocomplete="off">
                <input type="hidden" name="id" value="${user.id}">
                <div class="control-group">
                    <label class="control-label">用户组</label>
                    <div class="controls">
                        <span class="input-large uneditable-input">${user.userGroup.name}</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">角色</label>
                    <div class="controls">
                        <span class="input-large uneditable-input">${user.role.name}</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="username"><span class="font-red">*</span>用户名</label>
                    <div class="controls">
                        <input type="text" id="username" name="username" value="${user.username}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="nickname"><span class="font-red">*</span>昵称</label>
                    <div class="controls">
                        <input type="text" id="nickname" name="nickname" value="${user.nickname}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="email"><span class="font-red">*</span>邮箱</label>
                    <div class="controls">
                        <input type="text" id="email" name="email" value="${user.email}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="phone"><span class="font-red">*</span>手机</label>
                    <div class="controls">
                        <input type="text" id="phone" name="phone" value="${user.phone}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">创建时间</label>
                    <div class="controls">
                        <span class="input-large uneditable-input"><fmt:formatDate value="${user.createdDate}" type="both"/></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">最后登录时间</label>
                    <div class="controls">
                        <span class="input-large uneditable-input"><fmt:formatDate value="${user.lastLoginDate}" type="both"/></span>
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