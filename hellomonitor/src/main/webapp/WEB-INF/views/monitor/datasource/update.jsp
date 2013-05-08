<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改数据源</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            //客户端验证
            var url = '${ctx}/dataSource/updateSnmpDataSource';
            <c:if test="${dataSourceName == 'WmiDataSource'}">
                url = '${ctx}/dataSource/updateWmiDataSource';
            </c:if>
            <c:if test="${dataSourceName == 'CommandDataSource'}">
                url = '${ctx}/dataSource/updateCmdDataSource';
            </c:if>

            $("form").validate({
                submitHandler: function(form){
                    $.post(url, $(form).serialize(), function(data){
                        if(data.success){
                            location.href = '${ctx}/template/listDataSources?id=${dataSource.template.id}';
                        }else{
                            common.showError("#info", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 32},
                    <c:if test="${dataSourceName == 'SnmpDataSource'}">
                    oid: {required: true},
                    </c:if>
                    <c:if test="${dataSourceName == 'CommandDataSource'}">
                    command: {required: true},
                    </c:if>
                    collectionInterval: {required: true, number: true}
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
        <div class="span10 content">
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li><a href="${ctx}/template/listDataSources?id=${dataSource.template.id}">数据源列表</a> <span class="divider">/</span></li>
                    <li id="title">修改数据源</li>
                </ul>
            </div>

            <div id="info"></div>
            <form class="form-horizontal" autocomplete="off">
                <div class="modal-body">
                    <input type="hidden" name="id" value="${dataSource.id}"/>
                    <div class="control-group">
                        <label class="control-label">名称：</label>
                        <div class="controls"><input type="text" name="name" value="${dataSource.name}"/></div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">收集间隔(秒)：</label>
                        <div class="controls"><input type="text" name="collectionInterval" value="${dataSource.collectionInterval}"/></div>
                    </div>
                    <c:if test="${dataSourceName == 'SnmpDataSource'}">
                        <div id="snmp" class="control-group">
                            <label class="control-label">OID：</label>
                            <div class="controls"><input type="text" name="oid" value="${dataSource.oid}"/></div>
                        </div>
                    </c:if>
                    <c:if test="${dataSourceName == 'CommandDataSource'}">
                        <div id="command" class="control-group">
                            <label class="control-label">命令：</label>
                            <div class="controls"><input type="text" name="command" value="${dataSource.command}"/></div>
                        </div>
                    </c:if>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn" data-dismiss="modal">取消</a>
                    <input type="submit" class="btn btn-primary" value="提交">
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>