<%@ page pageEncoding="UTF-8" %>
<div class="span2 main-menu-span affix">
    <div class="well nav-collapse sidebar-nav">
        <div class="accordion" id="accordion2">
            <ul class="nav nav-tabs nav-stacked main-menu">
                <li class="nav-header hidden-tablet"><i class="icon-Arrow"></i>个人中心</li>
                <li><a class="ajax-link" href="${ctx}/user/updatePersonal"><i class="icon-eye-open"></i><span class="hidden-tablet">修改个人信息</span></a></li>
                <li><a class="ajax-link" href="${ctx}/user/updatePassword"><i class="icon-edit"></i><span class="hidden-tablet">修改密码</span></a></li>
            </ul>
        </div>
    </div>
</div>