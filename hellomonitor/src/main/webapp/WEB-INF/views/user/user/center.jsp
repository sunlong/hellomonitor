<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>查看个人信息</title>
    <%@ include file="/common/header.jsp"%>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/user/user-center-sidebar.jsp"%>
        <div class="span10 content"><!-- content starts -->
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>查看个人信息</li>
                </ul>
            </div>

            <div class="row-fluid">
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <tbody>
                        <tr>
                            <td>用户名</td>
                            <td>${user.username}</td>
                            </tr>
                        <tr>
                            <td>电子邮箱</td>
                            <td>${user.email}</td>
                        </tr>
                        <tr>
                            <td>角色</td>
                            <td>${user.role.name}</td>
                        </tr>
                        <tr>
                            <td>用户组</td>
                            <td>${user.userGroup.name}</td>
                        </tr>
                        <tr>
                            <td>创建时间</td>
                            <td><fmt:formatDate value="${user.createdDate}" type="both"/></td>
                        </tr>
                        <tr>
                            <td>最后登录时间</td>
                            <td><fmt:formatDate value="${user.lastLoginDate}" type="both"/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>