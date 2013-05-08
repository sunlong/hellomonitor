<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>数据源列表</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            var url = '${ctx}/template/addSnmpDataSource';
            $("#data-source-type").change(function(){
                var type = $(this).val();
                switch(type){
                    case 'SNMP':
                        $('#snmp').show();
                        $('#command').hide();
                        break;
                    case 'WMI':
                        url = '${ctx}/template/addWmiDataSource';
                        break;
                    case 'COMMAND':
                        $('#command').show();
                        $('#snmp').hide();
                        url = '${ctx}/template/addCmdDataSource';
                        break;
                }
            });
            $("#addDS form").validate({
                errorLabelContainer: $('#info2'),
                submitHandler: function(form){
                    $.post(url, $(form).serialize(), function(data){
                        if(data.success){
                            location.reload();
                        }else{
                            common.showError('#info2', data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 32}
                }
            });

            $("#addDP form").validate({
                errorLabelContainer: $('#info3'),
                submitHandler: function(form){
                    $.post('${ctx}/dataSource/addDataPoint', $(form).serialize(), function(data){
                        if(data.success){
                            location.reload();
                        }else{
                            common.showError('#info3', data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 32}
                }
            });

            $('tr[data-action]').click(function(){
                var id=$(this).attr('data-action');
                $('input[name="dataSource.id"]').val(id);//设置添加数制点的dataSource.id

                $.get('${ctx}/dataSource/listDataPoints', {id: id}, function(data){
                    if(data.success){
                        var template =
                                '<h4>数据点列表</h4><div><a href="#addDP" class="btn" data-toggle="modal">添加</a></div><table class="table table-bordered">' +
                                '<# for(var i=0; i<data.length; i++){ #>' +
                                '<tr><td><#= data[i].name #></td><td><button class="btn" data-action="add-dp=<#= data[i].id #>">修改</button> <button class="btn" data-action="delete-dp=<#= data[i].id #>">删除</button></td></tr>' +
                                '<# } #>' +
                                '</table>';
                        $("#datapoint").html(parseTemplate(template, {data: data.data}));
                        $('button[data-action^="add-dp="]').click(function(){

                        });
                        $('button[data-action^="delete-dp="]').click(function(){

                        });
                    }else{
                        common.showError('#info', data.data);
                    }
                });
            });

            $('button[data-action^="delete="]').click(function(){
                var id=$(this).attr('data-action').split('=')[1];
                $.post('${ctx}/dataSource/delete', {id: id}, function(data){
                    if(data.success){
                        location.reload();
                    }else{
                        common.showError('#info', data.data);
                    }
                });
                return false;
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
                    <li>数据源列表</li>
                </ul>
            </div>

            <div id="info"></div>
            <div class="row-fluid">
                <div><a href="#addDS" class="btn" data-toggle="modal">添加</a></div>
            </div>
            <div class="row-fluid">
                <div class="span6">
                    <table class="table table-striped table-bordered">
                        <thead><tr>
                            <th>名称</th>
                            <th>操作</th>
                        </tr></thead>
                        <tbody>
                        <c:forEach items="${template.dataSources}" var="dataSource">
                            <tr data-action="${dataSource.id}">
                                <td>${dataSource.name}</td>
                                <td>
                                    <a class="btn" href="${ctx}/dataSource/update?id=${dataSource.id}">修改</a>
                                    <button class="btn" data-action="delete=${dataSource.id}">删除</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span6" id="datapoint"></div>
            </div>
        </div>
    </div>
</div>

<div class="modal hide fade" id="addDS">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加数据源</h3>
    </div>
    <div id="info2"></div>
    <form class="form-horizontal" autocomplete="off">
        <div class="modal-body">
            <input type="hidden" name="template.id" value="${template.id}"/>
            <div class="control-group">
                <label class="control-label">名称：</label>
                <div class="controls"><input type="text" name="name"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">收集间隔(秒)：</label>
                <div class="controls"><input type="text" name="collectionInterval" value="300"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">类型：</label>
                <div class="controls"><select id="data-source-type">
                    <option value="SNMP">SNMP</option>
                    <option value="WMI">WMI</option>
                    <option value="COMMAND">COMMAND</option>
                </select></div>
            </div>
            <div id="snmp" class="control-group">
                <label class="control-label">OID：</label>
                <div class="controls"><input type="text" name="oid" /></div>
            </div>
            <div id="command" class="control-group hide">
                <label class="control-label">命令：</label>
                <div class="controls"><input type="text" name="command" /></div>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal">取消</a>
            <input type="submit" class="btn btn-primary" value="提交">
        </div>
    </form>
</div>

<div class="modal hide fade" id="addDP">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加数据点</h3>
    </div>
    <div id="info3"></div>
    <form class="form-horizontal" autocomplete="off">
        <div class="modal-body">
            <input type="hidden" name="dataSource.id"/>
            <div class="control-group">
                <label class="control-label">名称：</label>
                <div class="controls"><input type="text" name="name"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">类型：</label>
                <div class="controls"><select name="type">
                    <option value="COUNTER">COUNTER</option>
                    <option value="DERIVE">DERIVE</option>
                    <option value="ABSOLUTE">ABSOLUTE</option>
                    <option value="GAUGE">GAUGE</option>
                </select></div>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal">取消</a>
            <input type="submit" class="btn btn-primary" value="提交">
        </div>
    </form>
</div>

<%@ include file="/common/footer.jsp"%>
</body>
</html>