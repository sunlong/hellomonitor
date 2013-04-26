<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>模板列表</title>
    <%@ include file="/common/header.jsp"%>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/user/log-sidebar.jsp"%>
        <div class="span10 content"><!-- content starts -->
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>模板列表</li>
                </ul>
            </div>

            <div class="row-fluid">
                <div>
                    <div class="pull-left"><a href="${ctx}/template/create" class="btn">添加</a></div>
                    <div class="pull-right">
                        <form  action="${ctx}/device/list" method="post" class="form-horizontal">
                            名称：<input type="text" name="params['ip']" class="input-medium" value="${params.name}">&nbsp;
                            <button type="submit" class="btn btn-primary"><i class="icon-search icon-white"></i>查询</button>
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-condensed">
                    <thead><tr>
                        <th>名称</th>
                        <th>设备分类</th>
                        <th>操作</th>
                    </tr></thead>
                    <tbody>
                    <c:forEach items="${devices.content}" var="device">
                        <tr>
                            <td>${device.name}</td>
                            <td>${device.deviceClass.name}</td>
                            <td><a href="${ctx}/datasource/list" class="btn">数据源管理</a> <a href="${ctx}/graph/list" class="btn">图形管理</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <tags:pagination page="${devices}" paginationSize="5"/>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>