<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>图形列表</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            $("#addDS form").validate({
                errorLabelContainer: $('#info2'),
                submitHandler: function(form){
                    $.post('${ctx}/template/addGraph', $(form).serialize(), function(data){
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
                    $.post('${ctx}/graph/addGraphPoint', $(form).serialize(), function(data){
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
                $('input[name="graph.id"]').val(id);//设置添加图形点的graph.id

                $.get('${ctx}/graph/listGraphPoints', {id: id}, function(data){
                    if(data.success){
                        var template =
                                '<h4>图形点列表</h4><div><a href="#addDP" class="btn" data-toggle="modal">添加</a></div><table class="table table-bordered">' +
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
                $.post('${ctx}/graph/delete', {id: id}, function(data){
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
                    <li>图形列表</li>
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
                            <th>长度</th>
                            <th>宽度</th>
                            <th>操作</th>
                        </tr></thead>
                        <tbody>
                        <c:forEach items="${template.graphs}" var="graph">
                            <tr data-action="${graph.id}">
                                <td>${graph.name}</td>
                                <td>${graph.length}</td>
                                <td>${graph.width}</td>
                                <td>
                                    <a class="btn" href="${ctx}/graph/update?id=${graph.id}">修改</a>
                                    <button class="btn" data-action="delete=${graph.id}">删除</button>
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
        <h3>添加图形</h3>
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
                <label class="control-label">长度：</label>
                <div class="controls"><input type="text" name="length" value="300"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">宽度：</label>
                <div class="controls"><input type="text" name="width" value="500"/></div>
            </div>
            <div id="snmp" class="control-group">
                <label class="control-label">单位：</label>
                <div class="controls"><input type="text" name="unit" /></div>
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
        <h3>添加图形点</h3>
    </div>
    <div id="info3"></div>
    <form class="form-horizontal" autocomplete="off">
        <div class="modal-body">
            <input type="hidden" name="graph.id"/>
            <div class="control-group">
                <label class="control-label">数据源：</label>
                <div class="controls">
                    <select name="dataPointId">
                    <c:forEach items="${template.dataSources}" var="dataSource">
                        <c:forEach items="${dataSource.dataPoints}" var="dataPoint">
                            <option value="${dataPoint.id}">${dataPoint.name}</option>
                        </c:forEach>
                    </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">名称：</label>
                <div class="controls"><input type="text" name="name"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">类型：</label>
                <div class="controls"><select name="type">
                    <option value="line">线</option>
                    <option value="area">区域</option>
                </select></div>
            </div>
            <div class="control-group">
                <label class="control-label">Stacked：</label>
                <div class="controls"><input type="checkbox" name="stacked" value="true"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">线宽：</label>
                <div class="controls"><input type="text" name="lineWidth" value="1"/></div>
            </div>
            <div class="control-group">
                <label class="control-label">颜色：</label>
                <div class="controls"><input type="text" name="color"/></div>
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