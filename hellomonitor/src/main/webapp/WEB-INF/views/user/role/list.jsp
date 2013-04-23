<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
    <%@ include file="/common/header.jsp"%>
    <style type="text/css">
        <cp:hasMultiPermission name="role:update || role:delete">
        .hideOperation{display: table-cell;}
        </cp:hasMultiPermission>
    </style>
    <script type="text/javascript">
        $(function(){
            //删除角色
            $("button[data-action^='delete']").click(function(){
                if(confirm("确认删除吗？")){
                    var id = $(this).attr("data-action").split("-")[1];
                    common.ajaxOperation('${ctx}/role/delete', {id: id}, "#error");
                }
            });

            //模块权限及模块权限功能tree
            var setting = {
                async: {
                    enable: true,
                    url:"${ctx}/resource/listTree"
                },
                callback:{
                    beforeAsync: function(treeId, treeNode){
                        if(treeNode){
                            $.fn.zTree.getZTreeObj("tree").setting.async.otherParam = {"resourceId": treeNode.value};
                        }
                        return true;
                    },
                    beforeClick: function(treeId, treeNode){//禁止直接点击模块权限
                        return treeNode.getParentNode() != null;
                    }
                }
            };
            $.fn.zTree.init($("#tree"), setting);


            //显示树
            var roleId = '';
            $("button[data-action^='role-']").click(function(){
                roleId = $(this).attr("data-action").split('-')[1];
                //获取已有的权限
                $.get('${ctx}/role/listPermissions',{id: roleId}, function(data){
                    if(data.success){
                        $("#owned").html('');
                        for(var i=0; i<data.data.length; i++){
                            $("#owned").append('<li>'+ data.data[i].name +'</li>');
                        }
                        $("#permission").modal('toggle');
                    }else{
                        common.showError("#error", data.data);
                    }
                });
            });
            //确定角色拥有的权限
            $("input[type='button']").click(function(){
                var lis = $("#owned").children("li");
                var permission = '';
                for(var i=0; i<lis.length; i++){
                    permission += ','+lis[i].innerHTML;
                }
                $.post('${ctx}/role/updatePermission', {permission: permission.substring(1), id: roleId}, function(data){
                    if(data.success){
                        location.reload();
                    }else{
                        common.showError("#error", data.data);
                    }
                });
                return false;
            });

            //添加模块权限
            $("#add-resource").click(function(){
                var selectedNodes = $.fn.zTree.getZTreeObj("tree").getSelectedNodes();
                if(selectedNodes.length == 0){
                    common.showError("#error2","请选择要添加的模块权限(功能)");
                }else{
                    for(var i=0; i<selectedNodes.length; i++){
                        var parent = selectedNodes[i].getParentNode();
                        var lis = $("#owned").children("li");
                        var toInsert = '<li>'+ parent.name+':'+selectedNodes[i].name +'</li>';
                        //如果已经存在，则不用插入
                        var insert = true;
                        for(var i=0; i<lis.length; i++){
                            if(lis[i].outerHTML == toInsert){
                                insert = false;
                                break;
                            }
                        }
                        if(insert){
                            $("#owned").append(toInsert);
                        }
                    }
                }
            });
            //处理点击效果
            $("#owned").on('click', 'li', function(){
                $('#owned > li').removeClass('current');
                $(this).addClass('current');
            });
            //移除已有模块权限
            $("#remove-resource").click(function(){
                var selected = $('#owned').children('.current');
                if(selected.length == 0){
                    common.showError("#error2", "请选择要删除的模块权限(功能)");
                }else{
                    $(selected[0]).remove();
                }
            });
            //选择所有，批量删除
            $("input[name='selectAll']").click(function(){
                if($(this).attr("checked")){
                    $("input[name='roleId']").each(function(){
                        $(this).parent('span').addClass("checked");
                    });
                }else{
                    $("input[name='roleId']").each(function(){
                        $(this).parent('span').removeClass("checked");
                    });
                }
            });
            $("#batch-delete").click(function(){
                var roleIds = $("span[class='checked']").children('input[name="roleId"]');
                var strIds = '';
                roleIds.each(function(){
                    strIds += ','+$(this).val();
                });
                if(strIds.length < 1){
                    common.showError("#error", "请选择要删除的项");
                }else{
                    if(confirm("确认删除吗？")){
                        $.post('${ctx}/role/batchDelete', {ids: strIds.substring(1)}, function(data){
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
        <%@ include file="/common/user/role-sidebar.jsp"%>
        <div class="span10 content">
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>角色列表</li>
                </ul>
            </div>
            <div id="error"></div>
            <div>
                <shiro:hasPermission name="role:create">
                    <a href="${ctx}/role/create" class="btn btn-primary"><i class="icon-plus icon-white"></i>添加</a>&nbsp;&nbsp;
                </shiro:hasPermission>
                <shiro:hasPermission name="role:delete">
                    <button class="btn btn-danger" id="batch-delete">批量删除</button></div>
                </shiro:hasPermission>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead><tr><th><input type="checkbox" name="selectAll"/></th><th>角色</th><th>描述</th><th class="hideOperation width230">操作</th></tr></thead>
                <tbody>
                <c:forEach items="${roles.content}" var="role">
                    <tr>
                        <td><input type="checkbox" name="roleId" value="${role.id}"/></td>
                        <td>${role.name}</td>
                        <td>${role.description}</td>
                        <td class="hideOperation">
                            <shiro:hasPermission name="role:update">
                                <a href="${ctx}/role/update?id=${role.id}" class="btn"><i class="icon-edit"></i>修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="role:delete">
                                <button class="btn" data-action="delete-${role.id}"><i class="icon-remove"></i>删除</button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="role:update">
                                <button class="btn" data-action="role-${role.id}"><i class="icon-asterisk"></i>权限分配</button>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <tags:pagination page="${roles}" paginationSize="5"/>
        </div>
    </div>
</div>

<div class="modal hide fade" id="permission">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>权限分配</h3>
    </div>
    <div class="modal-body">
        <div id="error2"></div>
        <div class="row-fluid">
            <div class="span5 well" style="width:260px;">所有模块权限：
                <ul id="tree" class="ztree"></ul>
            </div>
            <div class="span2" style="width:36px;">
                <a href="#" class="btn" id="add-resource"><i class="icon-arrow-right"></i></a><br/><br/>
                <a href="#" class="btn" id="remove-resource"><i class="icon-arrow-left"></i></a>
            </div>
            <div class="span5 well" style="width:260px;">已有模块权限：
                <ul class="ztree" id="owned"></ul>
            </div>
        </div>
    </div>
    <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal">取消</a>
            <input type="button" class="btn btn-primary" value="确定">
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>