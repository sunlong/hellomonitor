<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>模块权限管理</title>
    <%@ include file="/common/header.jsp"%>
    <style type="text/css">
        <cp:hasMultiPermission name="resource:update || resource:delete">
        .hideOperation{display: table-cell;}
        </cp:hasMultiPermission>
    </style>
    <script type="text/javascript">
        $(function(){
            //删除
            $("button[data-action^='delete']").click(function(){
                if(confirm("确认删除吗？")){
                    var id = $(this).attr("data-action").split("-")[1];
                    common.ajaxOperation('${ctx}/resource/delete', {id: id}, "#error");
                }
            });

            //action table
            var resourceId = '';
            $("button[data-action^='action-']").click(function(){
                resourceId = $(this).attr("data-action").split('-')[1];
                var template =
                        '<div id="add-action-div"><button class="btn btn-primary" id="add-action-btn">添加</button></div>' +
                        '<table class="table">' +
                            '<tr><th>名称</th><th>描述</th><th>操作</th></tr>' +
                        '<# for(var i=0; i<data.length; i++){ #>' +
                            '<tr><td><#=data[i].name#></td><td><#=data[i].description#></td><td><button class="btn" data-action="deleteAction-<#=data[i].id#>">删除</a></td></tr>' +
                        '<# } #>' +
                        '</table>';

                $.get('${ctx}/resource/listActions', {id: resourceId}, function(data){
                    if(data.success){
                        $("#action-table").html(parseTemplate(template, {data: data.data}));
                        $("#action").modal('toggle');
                    }else{
                        common.showError("#error", data.data);
                    }
                });
            });

            var getConfig = function(formId, errorId){
                return {
                    errorLabelContainer: $(errorId),
                    submitHandler: function(){
                        $.post('${ctx}/resource/addAction', $(formId).serialize(), function(data){
                            if(data.success){
                                var template = '<tr><td><#= data.name #></td><td><#= data.description #></td><td><button class="btn" data-action="deleteAction-<#= data.id #>">删除</button></td></tr>';
                                $("#action-table > table").append(parseTemplate(template, {data: data.data}));
                            }else{
                                common.showError(errorId, data.data);
                            }
                        });
                        return false;
                    },
                    rules:{
                        name: {required: true, maxlength: 50, lettersonlyAndStar: true}
                    }
                };
            };

            //显示添加表单
            $("#action-table").on('click', '#add-action-btn', function(){
                var value = $(this).attr('class')[1];
                var template =
                        '<div id="error2"></div>' +
                        '<form class="form-horizontal" id="add-action-form">' +
                            '<input type="hidden" name="resource.id" value="<#=data#>"/>' +
                            '名称：<input type="text" name="name" class="input-medium"/>&nbsp;&nbsp;&nbsp;' +
                            '描述：<input type="text" name="description" class="input-medium"/>&nbsp;&nbsp;&nbsp;' +
                            '<input type="submit" value="添加" class="btn btn-primary"/>&nbsp;&nbsp;' +
                            '<input type="button" value="取消" class="btn"/>' +
                        '</form>';
                $("#add-action-div").html(parseTemplate(template, {data: resourceId}));
                //客户端验证
                $("#add-action-form").validate(getConfig('#add-action-form', '#error2'));
            });
            //取消显示表单，重新显示btn
            $("#action-table").on('click', "input[type='button']", function(){
                $("#add-action-div").html('<button class="btn btn-primary" id="add-action-btn">添加</button>');
            });

            //删除action
            $("#action-table").on('click', "button[data-action^='deleteAction-']", function(){
                var _this = $(this);
                var actionId = _this.attr('data-action').split('-')[1];
                if(confirm("确认删除吗？")){
                    $.post('${ctx}/resource/deleteAction', {actionId: actionId}, function(data){
                        if(data.success){
                            _this.parent().parent().remove();
                        }else{
                            common.showError("#error2", data.data);
                        }
                    });
                }
            });

            //选择所有，批量删除
            $("input[name='selectAll']").click(function(){
                if($(this).attr("checked")){
                    $("input[name='resourceId']").each(function(){
                        $(this).parent('span').addClass("checked");
                    });
                }else{
                    $("input[name='resourceId']").each(function(){
                        $(this).parent('span').removeClass("checked");
                    });
                }
            });
            $("#batch-delete").click(function(){
                var roleIds = $("span[class='checked']").children('input[name="resourceId"]');
                var strIds = '';
                roleIds.each(function(){
                    strIds += ','+$(this).val();
                });
                if(strIds.length < 1){
                    common.showError("#error", "请选择要删除的项");
                }else{
                    if(confirm("确认删除吗？")){
                        $.post('${ctx}/resource/batchDelete', {ids: strIds.substring(1)}, function(data){
                            if(data.success){
                                location.reload();
                            }else{
                                common.showError("#error", data.data);
                            }
                        });
                    }
                }
            });
        });
    </script>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/user/resource-sidebar.jsp"%>
        <div class="span10 content">
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>模块权限列表</li>
                </ul>
            </div>
            <div id="error"></div>
            <div>
                <shiro:hasPermission name="resource:create">
                <a href="${ctx}/resource/create" class="btn btn-primary"><i class="icon-plus icon-white"></i>添加</a>&nbsp;&nbsp;
                </shiro:hasPermission>
                <shiro:hasPermission name="resource:delete">
               		 <button class="btn btn-danger" id="batch-delete">批量删除</button>
                </shiro:hasPermission>     
            <div class="pull-right">
             <form action="${ctx}/resource/list" method="get">
                                                                   名称<input type="text" name="name" class="input-medium">&nbsp;&nbsp;
                        <button type="submit" class="btn btn-primary"><i class="icon-search icon-white"></i>查询</button>
             </form>
            </div>        
           </div>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead><tr><th><input type="checkbox" name="selectAll"/></th><th>名称</th><th>描述</th><th class="hideOperation width230">操作</th></tr></thead>
                <tbody>
                <c:forEach items="${resources.content}" var="resource">
                    <tr>
                        <td><input type="checkbox" name="resourceId" value="${resource.id}"/></td>
                        <td>${resource.name}</td>
                        <td>${resource.description}</td>
                        <td class="hideOperation">
                            <shiro:hasPermission name="resource:update">
                                <a href="${ctx}/resource/update?id=${resource.id}" class="btn"><i class="icon-edit"></i>修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="resource:delete">
                                <button class="btn" data-action="delete-${resource.id}"><i class="icon-remove"></i>删除</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="resource:update">
                                <button class="btn" data-action="action-${resource.id}"><i class="icon-asterisk"></i>功能管理</button>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <tags:pagination page="${resources}" paginationSize="5"/>
        </div>
    </div>
</div>

<div class="modal hide fade" id="action">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>功能管理</h3>
    </div>
    <div class="modal-body">
        <div id="action-table"></div>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">关闭</a>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>