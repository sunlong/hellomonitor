<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加角色</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            //添加角色或者修改角色、修改标题
            var url = '';
            if($('input[name="id"]').val()){//如果id有值
                url = '${ctx}/role/update';
                $(document).attr("title", "修改角色");
                $("#title").text("修改角色");
            }else{
                url = '${ctx}/role/create';
            }

            //客户端验证
            $("#role-form").validate({
                errorLabelContainer: $("#error"),
                submitHandler: function(){
                    $.post(url, $("#role-form").serialize(), function(data){
                        if(data.success){
                            location.href = '${ctx}/role/list';
                        }else{
                            common.showError("#error", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 50},
                    description:{ maxlength:200 }
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
                    <li><a href="${ctx}/role/list">角色管理</a> <span class="divider">/</span></li>
                    <li id="title">添加角色</li>
                </ul>
            </div>

            <div id="error"></div>
            <form class="form-horizontal" action="${ctx}/role/create" id="role-form" autocomplete="off">
                <input type="hidden" name="id" value="${role.id}">
                <div class="control-group">
                    <label class="control-label" for="roleName"><span class="font-red">*</span>名称</label>
                    <div class="controls">
                        <input type="text" id="roleName" name="name" value="${role.name}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="description">描述</label>
                    <div class="controls">
                        <textarea id="description" name="description">${role.description}</textarea>
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