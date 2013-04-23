<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <%@ include file="/common/header.jsp"%>
    <style type="text/css">
        <cp:hasMultiPermission name="user:update || user:delete">
        .hideOperation{display: table-cell;}
        </cp:hasMultiPermission>
    </style>
    <script type="text/javascript">
        $(function(){
            //删除用户
            $("button[data-action^='delete']").click(function(){
                if(confirm("确认删除吗？")){
                    var id = $(this).attr("data-action").split("-")[1];
                    common.ajaxOperation('${ctx}/user/delete', {id: id}, "#error");
                }
            });
        });
    </script>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/user/user-sidebar.jsp"%>
        <div class="span10 content"><!-- content starts -->
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>用户管理</li>
                </ul>
            </div>

            <div id="error"></div>
            <div class="row-fluid">
                <div>
                    <form class="form-horizontal" action="${ctx}/user/list" method="get">
                        <shiro:hasPermission name="user:create">
                            <a href="${ctx}/user/create" class="btn btn-primary"><i class="icon-plus icon-white"></i>添加</a>
                        </shiro:hasPermission>

                        <div class="pull-right">
                            用户名<input type="text" name="params['username']" class="input-medium" value="${params.username}">&nbsp;
                            邮箱<input type="text" name="params['email']" class="input-medium" value="${params.email}">&nbsp;
                            <button type="submit" class="btn btn-primary"><i class="icon-search icon-white"></i>查询</button>
                        </div>
                    </form>
                </div>
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <thead><tr><th data-sort="${ctx}/user/list,${searchParams},username,${sortBean.sortDir}">用户名</th><th>昵称</th><th>邮箱</th><th>手机</th><th>角色</th><th>用户组</th><th>创建时间</th><th>最后登录时间</th><th class="hideOperation width140">操作</th></tr></thead>
                    <tbody>
                    <c:forEach items="${users.content}" var="user">
                        <tr>
                            <td>${user.username}</td>
                            <td>${user.nickname}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.role.name}</td>
                            <td>${user.userGroup.name}</td>
                            <td><fmt:formatDate value="${user.createdDate}" type="both"/></td>
                            <td><fmt:formatDate value="${user.lastLoginDate}" type="both"/></td>
                            <td class="hideOperation">
                                <shiro:hasPermission name="user:update">
                                    <a href="${ctx}/user/update?id=${user.id}" class="btn"><i class="icon-edit"></i>修改</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="user:delete">
                                    <button class="btn" data-action="delete-${user.id}"><i class="icon-remove"></i>删除</button></td>
                                </shiro:hasPermission>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <tags:pagination page="${users}" paginationSize="5"/>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>