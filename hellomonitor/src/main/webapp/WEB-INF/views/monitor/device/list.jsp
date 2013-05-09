<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>设备列表</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            $("button[data-action^='delete=']").click(function(){
                var id = $(this).attr('data-action').split('=')[1];
                $.post('${ctx}/device/delete', {id: id}, function(data){
                    if(data.success){
                        location.reload();
                    }else{
                        common.showError('#info', data.data);
                    }
                });
            });
        });
    </script>
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
                    <li>设备列表</li>
                </ul>
            </div>

            <div id="info"></div>
            <div class="row-fluid">
                <div>
                    <div class="pull-left"><a href="${ctx}/device/create" class="btn">添加</a></div>
                    <div class="pull-right">
                        <form  action="${ctx}/device/list" method="post" class="form-horizontal">
                            IP地址：<input type="text" name="params['ip']" class="input-medium" value="${params.ip}">&nbsp;
                            <button type="submit" class="btn btn-primary"><i class="icon-search icon-white"></i>查询</button>
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-condensed">
                    <thead><tr>
                        <th>设备名称</th>
                        <th>设备分类</th>
                        <th data-sort="${ctx}/device/list,${searchParams},ip,${sortBean.sortDir}">IP</th>
                        <th>操作</th>
                    </tr></thead>
                    <tbody>
                    <c:forEach items="${devices.content}" var="device">
                        <tr>
                            <td>${device.name}</td>
                            <td>${device.deviceClass.name}</td>
                            <td>${device.deviceProperty.ip}</td>
                            <td><a href="${ctx}/device/update?id=${device.id}" class="btn">修改</a> <button class="btn" data-action="delete=${device.id}">删除</button></td>
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