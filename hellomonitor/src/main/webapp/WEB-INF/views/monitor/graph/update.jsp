<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改图形</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            $("form").validate({
                submitHandler: function(form){
                    $.post('${ctx}/graph/update', $(form).serialize(), function(data){
                        if(data.success){
                            location.href = '${ctx}/template/listGraphs?id=${graph.template.id}';
                        }else{
                            common.showError("#info", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 32}
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
                    <li><a href="${ctx}/template/listGraphs?id=${graph.template.id}">图形列表</a> <span class="divider">/</span></li>
                    <li id="title">修改图形</li>
                </ul>
            </div>

            <div id="info"></div>
            <form class="form-horizontal" autocomplete="off">
                <div class="modal-body">
                    <input type="hidden" name="id" value="${graph.id}"/>
                    <div class="control-group">
                        <label class="control-label">名称：</label>
                        <div class="controls"><input type="text" name="name" value="${graph.name}"/></div>
                    </div>
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