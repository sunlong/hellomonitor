<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加模板</title>
    <%@ include file="/common/header.jsp"%>
    <script id="addActionTmpl" type="text/html">
        <form class="form-horizontal" id="add-action-form" autocomplete="off">
            <input type="text" name="name"/>&nbsp;&nbsp;
            <input type="submit" value="确定" class="btn btn-primary"/>&nbsp;&nbsp;
            <input type="button" value="取消" class="btn"/>
        </form>
    </script>
    <script type="text/javascript">
        $(function(){
            //添加模板或者修改模板、修改标题
            var url = '';
            if($('input[name="id"]').val()){//如果id有值
                url = '${ctx}/template/update';
                $(document).attr("title", "修改模板");
                $("#title").text("修改模板");
            }else{
                url = '${ctx}/template/create';
            }

            //客户端验证
            $("#template-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    var deviceClass = '&deviceClass.id=';
                    var nodes = $.fn.zTree.getZTreeObj("tree").getSelectedNodes();
                    if(nodes.length == 0){//如果没有选择，就相当于没有改变设备分类
                        <c:choose>
                            <c:when test="${template.deviceClass.id != null}">
                                deviceClass += ${template.deviceClass.id};
                            </c:when>
                            <c:otherwise>
                                common.showError("#error", "请选择一个设备分类");
                                return false;
                            </c:otherwise>
                        </c:choose>
                    }else{
                        deviceClass += nodes[0].value;
                    }
                    
                    $.post(url, $("#template-form").serialize() + deviceClass, function(data){
                        if(data.success){
                            location.href = '${ctx}/template/list';
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 32}
                }
            });

            //设备分类
            var setting = {
                view: {
                    selectedMulti: false
                },
                async: {
                    enable: true,
                    url:"${ctx}/userGroup/listSub"
                },
                callback:{
                    beforeAsync: function(treeId, treeNode){
                        if(treeNode){
                            $.fn.zTree.getZTreeObj("tree").setting.async.otherParam = {"parentUserGroupId": treeNode.value};
                        }
                        return true;
                    },
                    onAsyncSuccess: function(event, treeId, treeNode, msg){//修改模板时选中此模板所属的设备分类
                        var userGroupId = 0;
                        if($('input[name="id"]').val()){//修改模板
                            var msgObj = $.parseJSON(msg);
                            if(msgObj.length > 0){
                                userGroupId = '${user.userGroup.id}';
                                for(var i=0; i<msgObj.length; i++){
                                    if(msgObj[i].value == userGroupId){
                                        var treeObj = $.fn.zTree.getZTreeObj("tree");
                                        var node = treeObj.getNodeByParam("value", userGroupId, treeNode);
                                        $.fn.zTree.getZTreeObj("tree").selectNode(node);
                                    }
                                }
                            }
                        }
                    }
                }
            };
            $.fn.zTree.init($("#tree"), setting);
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
                    <li><a href="${ctx}/template/list">模板管理</a> <span class="divider">/</span></li>
                    <li id="title">添加模板</li>
                </ul>
            </div>

            <div id="error"></div>
            <form class="form-horizontal" id="template-form" autocomplete="off">
                <input type="hidden" name="id" value="${template.id}">
                <div class="control-group">
                    <label class="control-label"><span class="font-red">*</span>设备分类</label>
                    <div class="controls">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="name"><span class="font-red">*</span>名称</label>
                    <div class="controls">
                        <input type="text" id="name" name="name" value="${template.name}"/>
                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <input class="btn btn-primary" type="submit" value="提交" />
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>
</body>
</html>