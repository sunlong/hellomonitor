<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>设备分类管理</title>
    <%@ include file="/common/header.jsp"%>
    <script id="addDeviceClassTmpl" type="text/html">
        <div id="error"></div>
        <form class="form-horizontal" id="add-root-form" autocomplete="off">
            <input type="text" name="name"/>&nbsp;&nbsp;
            <input type="submit" value="提交" class="btn btn-primary"/>&nbsp;&nbsp;
            <input type="button" value="取消" class="btn"/>
        </form>
    </script>
    <script type="text/javascript">
        $(function(){
            function beforeDrop(treeId, treeNodes, targetNode) {
                var result = false;
                if(targetNode){
                    $.ajax({
                        async : false,
                        type: 'POST',
                        dataType : "json",
                        url: '${ctx}/deviceClass/changeParent',
                        data: {id: treeNodes[0].value, parentId: targetNode.value},
                        success: function(data){
                            if(data.success){
                                result = true;
                            }else{
                                common.showError("#error3", data.data);
                            }
                        }
                    });
                }
                return result;
            }
            //删除
            function beforeRemove(treeId, treeNode) {
                var result = false;
                if(confirm("确认删除 " + treeNode.name + " 吗？")){
                    $.ajax({
                        async : false,
                        type: 'POST',
                        dataType : "json",
                        url: '${ctx}/deviceClass/delete',
                        data: {id: treeNode.value},
                        success: function(data){
                            if(data.success){
                                result = true;
                            }else{
                                common.showError("#error3", data.data);
                            }
                        }
                    });
                }
                return result;
            }
            //修改
            function beforeRename(treeId, treeNode, newName) {
                if (newName.length == 0) {
                    alert("名称不能为空.");
                    var zTree = $.fn.zTree.getZTreeObj("tree");
                    setTimeout(function(){zTree.editName(treeNode)}, 10);
                    return false;
                }

                var result = false;
                $.ajax({
                    async : false,
                    type: 'POST',
                    dataType : "json",
                    url: '${ctx}/deviceClass/update',
                    data: {'id': treeNode.value, 'name': newName},
                    success: function(data){
                        if(data.success){
                            result = true;
                        }else{
                            common.showError("#error3", data.data);
                            $.fn.zTree.getZTreeObj("tree").cancelEditName();
                        }
                    }
                });

                return result;
            }

            //树
            var showRenameBtn = false;
            var showRemoveBtn = false;
            var showAddBtn = false;
            <shiro:hasPermission name="deviceClass:update">
                showRenameBtn = true;
            </shiro:hasPermission>
            <shiro:hasPermission name="deviceClass:delete">
                showRemoveBtn = true;
            </shiro:hasPermission>
            <shiro:hasPermission name="deviceClass:create">
                showAddBtn = true;
            </shiro:hasPermission>

            var setting = {
                view: {
                    selectedMulti: false,
                    addHoverDom: function addHoverDom(treeId, treeNode) {
                        if(!showAddBtn) {
                            return;
                        }
                        var sObj = $("#" + treeNode.tId + "_span");
                        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.value).length>0){
                            return;
                        }
                        var addStr = "<span class='button add' id='addBtn_" + treeNode.value + "' title='添加子分类' onfocus='this.blur();'></span>";
                        sObj.after(addStr);
                        var btn = $("#addBtn_"+treeNode.value);
                        if (btn) {
                            btn.bind("click", function(){
                                $("input[name='parent.id']").val(treeNode.value);

                                $('#addUG').modal('toggle');
                                return false;
                            });
                        }
                    },
                    removeHoverDom: function removeHoverDom(treeId, treeNode) {
                        $("#addBtn_"+treeNode.value).unbind().remove();
                    }
                },
                edit: {
                    enable: true,
                    removeTitle: '删除此节点',
                    renameTitle: '重命名',
                    showRemoveBtn: showRemoveBtn,
                    showRenameBtn: showRenameBtn
                },
                async: {
                    enable: true,
                    url:"${ctx}/deviceClass/listSub"
                },
                callback:{
                    beforeAsync: function(treeId, treeNode){
                        if(treeNode){
                            $.fn.zTree.getZTreeObj("tree").setting.async.otherParam = {"parentId": treeNode.value};
                        }
                        return true;
                    },
                    beforeDrop: beforeDrop,
                    beforeRemove: beforeRemove,
                    beforeRename: beforeRename
                }
            };
            $.fn.zTree.init($("#tree"), setting);

            var getConfig = function(formId, errorId){
                return {
                    errorLabelContainer: $(errorId),
                    submitHandler: function(){
                        $.post('${ctx}/deviceClass/create', $(formId).serialize(), function(data){
                            if(data.success){
                                location.href = '${ctx}/deviceClass/list';
                            }else{
                                common.showError(errorId, data.data);
                            }
                        });
                        return false;
                    },
                    rules:{
                        name: {required: true, maxlength: 50}
                    }
                };
            };

            //添加根
            $("#add-root").on('click', "button", function(){
                $("#add-root").html(parseTemplate($("#addDeviceClassTmpl").html(), {}));
                //客户端验证
                $("#add-root-form").validate(getConfig('#add-root-form', '#error'));
            });
            $("#add-root").on('click', "input[type='button']", function(){
                $("#add-root").html('<button class="btn btn-primary">添加根分类</button>');
            });
            //~

            //客户端验证
            $("#add-form").validate(getConfig('#add-form', '#error2'));
            $('#addUG').on('hidden', function () {
                $("#error2").children(0).remove();
            });
        });
    </script>
</head>
<body>
<%@ include file="/common/nav.jsp"%>
<div class="container-fluid">
    <div class="row-fluid">
        <%@ include file="/common/monitor/deviceClass-sidebar.jsp"%>
        <div class="span10 content"><!-- content starts -->
            <div>
                <ul class="breadcrumb">
                    <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
                    <li>设备分类管理</li>
                </ul>
            </div>
            <div class="well well-large">
                <div id="error3"></div>
                <div id="add-root">
                    <shiro:hasPermission name="deviceClass:create">
                        <button class="btn btn-primary">添加根分类</button>
                    </shiro:hasPermission>
                </div>
                <div id="tree-div" class="listTree">
                    <ul id="tree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal hide fade" id="addUG">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加设备分类</h3>
    </div>
    <div id="error2"></div>
    <form class="form-horizontal" id="add-form" autocomplete="off">
        <div class="modal-body">
            <input type="hidden" name="parent.id"/>
            <input type="hidden" name="id"/>
            <input type="text" name="name"/>
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