<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加用户</title>
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
            //添加用户或者修改用户、修改标题
            var url = '';
            if($('input[name="id"]').val()){//如果id有值
                url = '${ctx}/user/update';
                $(document).attr("title", "修改用户");
                $("#title").text("修改用户");
            }else{
                url = '${ctx}/user/create';
            }

            //客户端验证
            $("#user-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    var userGroup = '&userGroup.id=';
                    var nodes = $.fn.zTree.getZTreeObj("tree").getSelectedNodes();
                    if(nodes.length == 0){//如果没有选择，就相当于没有改变用户组
                        <c:choose>
                            <c:when test="${user.userGroup.id != null}">
                                userGroup += ${user.userGroup.id};
                            </c:when>
                            <c:otherwise>
                                common.showError("#error", "请选择一个用户组");
                                return false;
                            </c:otherwise>
                        </c:choose>
                    }else{
                        userGroup += nodes[0].value;
                    }
                    
                    var roleName = ("&role.name="+$("#role option:selected").text()); //获取名称
                    
                    $.post(url, $("#user-form").serialize() + userGroup+roleName, function(data){
                        if(data.success){
                            location.href = '${ctx}/user/list';
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    username: {required: true, maxlength: 50, lettersonly:true},
                    nickname: {required: true, maxlength: 50},
                    phone: {required: true, maxlength: 11, digits: true},
                    password: {required: true, maxlength: 40},
                    email:{required: true, maxlength: 50, email: true}
                }
            });

            //用户组
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
                    onAsyncSuccess: function(event, treeId, treeNode, msg){//修改用户时选中此用户所属的用户组
                        var userGroupId = 0;
                        if($('input[name="id"]').val()){//修改用户
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
                    <li><a href="${ctx}/user/list">用户管理</a> <span class="divider">/</span></li>
                    <li id="title">添加用户</li>
                </ul>
            </div>

            <div id="error"></div>
            <form class="form-horizontal" id="user-form" autocomplete="off">
                <input type="hidden" name="id" value="${user.id}">
                <div class="control-group">
                    <label class="control-label"><span class="font-red">*</span>用户组</label>
                    <div class="controls">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="role"><span class="font-red">*</span>角色</label>
                    <div class="controls">
                        <select id="role" name="role.id">
                            <c:forEach items="${roles}" var="role">
                                <c:choose>
                                    <c:when test="${user.role.id == role.id}">
                                        <option value="${role.id}" selected="selected">${role.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${role.id}">${role.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="username"><span class="font-red">*</span>用户名</label>
                    <div class="controls">
                        <input type="text" id="username" name="username" value="${user.username}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="nickname"><span class="font-red">*</span>昵称</label>
                    <div class="controls">
                        <input type="text" id="nickname" name="nickname" value="${user.nickname}"/>
                    </div>
                </div>
                <c:if test="${user.id==null}">
                    <div class="control-group">
                        <label class="control-label" for="password"><span class="font-red">*</span>密码</label>
                        <div class="controls">
                            <input type="password" id="password" name="password" value="${user.password}"/>
                        </div>
                    </div>
                </c:if>
                <div class="control-group">
                    <label class="control-label" for="email"><span class="font-red">*</span>邮箱</label>
                    <div class="controls">
                        <input type="text" id="email" name="email" value="${user.email}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="phone"><span class="font-red">*</span>手机</label>
                    <div class="controls">
                        <input type="text" id="phone" name="phone" value="${user.phone}"/>
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