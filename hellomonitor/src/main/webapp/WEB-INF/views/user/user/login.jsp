<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>欢迎您使用HelloMonitor--请登录</title>
    <link href='${ctx}/static/css/login.css' rel='stylesheet'>
    <%@ include file="/common/header.jsp" %>
  </head>
  <body id="loginMian">
  <div class="container-fluid">
      <div class="row-fluid">

          <div class="row-fluid">
              <div class="span12 center login-header">
                  <h2>欢迎使用HelloMonitor</h2>
              </div><!--/span-->
          </div><!--/row-->

          <div class="row-fluid">
              <div class="well span5 center login-box" id="loginMain">
                  <c:if test="${username != null}">
                      <div class="alert alert-error">
                          <button class="close" data-dismiss="alert">×</button>用户名或者密码不正确.
                      </div>
                  </c:if>
                  <div class="alert alert-info">
                      请登录
                  </div>
                  <form class="form-horizontal" action="${ctx}/user/login" method="post">
                      <fieldset>
                          <div class="input-prepend" title="用户名" data-rel="tooltip">
                              <span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="username" id="username" type="text" value="${username}"/>
                          </div>
                          <div class="clearfix"></div>

                          <div class="input-prepend" title="密码" data-rel="tooltip">
                              <span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10" name="password" id="password" type="password" />
                          </div>
                          <div class="clearfix"></div>

                          <div class="input-prepend">
                              <label class="remember" for="remember"><input type="checkbox" id="remember" name="rememberMe"/>记住我</label><a class="Register" href="${ctx}/user/register">注册新用户</a>
                          </div>
                          <div class="clearfix"></div>

                          <p class="center span5">
                              <button type="submit" class="btn btn-primary">登录</button>
                          </p>
                      </fieldset>
                  </form>
              </div><!--/span-->
          </div><!--/row-->
      </div><!--/fluid-row-->
  </div>
  <div class="LoginFooter"><p>©2013 E书家 保留所有权利</p></div>
  </body>
</html>