<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>注册</title>
    <%@ include file="/common/header.jsp" %>
    <script type="text/javascript">
        $(function(){
            //客户端验证
            $("#user-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    $.post('${ctx}/user/register', $("#user-form").serialize(), function(data){
                        if(data.success){
                            location.href = '${ctx}/index.jsp';
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 50},
                    email:{required: true, maxlength: 50, email: true},
                    nickname:{required: true, maxlength: 50},
                    phone:{required: true, maxlength: 11, digits: true},
                    password:{required: true, maxlength: 32},
                    rePassword:{required: true, maxlength: 32, equalTo:"#password"}
                },
                messages: {
                    rePassword: {equalTo:'两次密码不一致'}
                }
            });
        });
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">

        <div class="row-fluid">
            <div class="span12 center login-header">
                <h2>欢迎使用集云一体化云平台</h2>
            </div>
        </div><!--/row-->

        <div class="row-fluid">
            <div class="well span5 center login-box">
                <div id="error"></div>
                <div class="alert alert-info">
                    用户注册
                </div>
                <form class="form-horizontal" id="user-form" autocomplete="off">
                    <fieldset>
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
                            <label class="control-label" for="password"><span class="font-red">*</span>密码</label>
                            <div class="controls">
                                <input type="password" id="password" name="password"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="rePassword"><span class="font-red">*</span>重复密码</label>
                            <div class="controls">
                                <input type="password" id="rePassword" name="rePassword"/>
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
                            <div class="controls">
                                <input class="btn btn-primary" type="submit" value="注册" />
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div><!--/row-->
    </div><!--/fluid-row-->
</div>
</body>
</html>