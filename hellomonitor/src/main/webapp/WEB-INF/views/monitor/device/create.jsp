<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加设备</title>
    <%@ include file="/common/header.jsp"%>
    <script type="text/javascript">
        $(function(){
            var url = '';
            if($('input[name="id"]').val()){//如果id有值
                url = '${ctx}/device/update';
                $(document).attr("title", "修改设备");
                $("#title").text("修改设备");
            }else{
                url = '${ctx}/device/create';
            }

            //客户端验证
            $("form").validate({
                submitHandler: function(){
                    $.post(url, $("form").serialize(), function(data){
                        if(data.success){
                            location.href = '${ctx}/device/list';
                        }else{
                            common.showError("#info", data.data);
                        }
                    });
                    return false;
                },
                rules:{
                    name: {required: true, maxlength: 32},
                    ip: {required: true, ipv4: true}
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
                    <li><a href="${ctx}/device/list">设备管理</a> <span class="divider">/</span></li>
                    <li id="title">添加设备</li>
                </ul>
            </div>

            <div id="info"></div>
            <form class="form-horizontal" autocomplete="off">
                <input type="hidden" name="id" value="${device.id}">
                <div class="control-group">
                    <label class="control-label" for="name"><span class="font-red">*</span>名称</label>
                    <div class="controls">
                        <input type="text" id="name" name="name" value="${device.name}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="ip"><span class="font-red">*</span>IP</label>
                    <div class="controls">
                        <input type="text" id="ip" name="ip" value="${device.ip}"/>
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