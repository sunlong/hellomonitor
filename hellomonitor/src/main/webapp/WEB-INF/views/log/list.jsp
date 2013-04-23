<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>系统日志</title>
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
                    <li>系统日志</li>
                </ul>
            </div>

            <div id="error"></div>
            <div class="row-fluid">
                <div>
                    <form  action="${ctx}/log/list" method="post" class="form-horizontal">
                        <div class="pull-right">
       						IP地址：<input type="text" name="params['ip']" class="input-medium" value="${params.ip}">&nbsp;
                        	 用户名：<input type="text" name="params['username']" class="input-medium" value="${params.username}">&nbsp;
                        	 关键字：<input type="text" name="params['message']" class="input-medium" value="${params.createdDate}">&nbsp;
                            <button type="submit" class="btn btn-primary"><i class="icon-search icon-white"></i>查询</button>
                        </div>
                    </form>
                </div>
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <thead><tr>
                        <th data-sort="${ctx}/log/list,${searchParams},username,${sortBean.sortDir}">用户名</th>
                        <th data-sort="${ctx}/log/list,${searchParams},ip,${sortBean.sortDir}">IP地址</th>
                        <th data-sort="${ctx}/log/list,${searchParams},createdDate,${sortBean.sortDir}">创建时间</th>
                        <th>操作日志详情</th>
                    </tr></thead>
                    <tbody>
                    <c:forEach items="${logs.content}" var="log">
                        <tr>
                            <td>${log.username}</td>
                            <td>${log.ip}</td>
                            <td><fmt:formatDate value="${log.createdDate}" type="both"/></td>
                            <td>${log.message}</td>                            
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <tags:pagination page="${logs}" paginationSize="5"/>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>