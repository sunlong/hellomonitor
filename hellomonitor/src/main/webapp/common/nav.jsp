<%@ page pageEncoding="utf-8" %>
<div class="navbar navbar-fixed-top" id="NavMenu">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</a>
            <a class="brand" href="${ctx}/"> <img alt="Logo" src="${ctx}/static/images/logo.png" /> <span>HelloMonitor</span></a>

			<div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-user"></i><span class="hidden-phone">系统管理</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/userGroup/list">用户组管理</a></li>
                    <li><a href="${ctx}/user/list">用户管理</a></li>
                    <li><a href="${ctx}/role/list">角色管理</a></li>
                    <li><a href="${ctx}/resource/list">资源管理</a></li>
                    <li><a href="${ctx}/log/list">系统日志</a></li>
                </ul>
			</div>

            <div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-eye-open"></i><span class="hidden-phone">监控管理</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/deviceClass/list">设备分类管理</a></li>
                    <li><a href="${ctx}/device/list">设备管理</a></li>
                    <li><a href="${ctx}/event/list">告警管理</a></li>
                    <li><a href="${ctx}/template/list">模板管理</a></li>
                </ul>
            </div>

            <div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-th"></i><span class="hidden-phone">统计报表</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/capacity/list">资源统计</a></li>
                </ul>
            </div>

            <!-- user dropdown starts -->
            <div class="btn-group pull-right">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-Current"></i><span class="hidden-phone"><shiro:principal property="nickname"/></span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/user/updatePersonal">修改个人信息</a></li>
                    <li><a href="${ctx}/user/updatePassword">修改个人密码</a></li>
                    <li><a href="${ctx}/user/logout">退出</a></li>
                </ul>
            </div>
            <!-- user dropdown ends -->
		</div>
	</div>
</div>