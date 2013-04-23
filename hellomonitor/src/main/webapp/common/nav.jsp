<%@ page pageEncoding="utf-8" %>
<div class="navbar navbar-fixed-top" id="NavMenu">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</a>
            <a class="brand" href="${ctx}/"> <img alt="Logo" src="${ctx}/static/images/header/images/logo-img.png" /> <span>集云一体化云平台</span></a>

			<div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-user"></i><span class="hidden-phone">系统管理</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/user/updatePersonal">个人中心</a></li>
                    <li><a href="${ctx}/systemConfig/show">首页设置</a></li>
                    <li><a href="${ctx}/userGroup/list">用户组管理</a></li>
                    <li><a href="${ctx}/user/list">用户管理</a></li>
                    <li><a href="${ctx}/role/list">角色管理</a></li>
                    <li><a href="${ctx}/resource/list">模块权限设置</a></li>
                    <li><a href="${ctx}/configuration/list">系统参数设置</a></li>
                    <li><a href="${ctx}/log/list">系统日志</a></li>
                </ul>
			</div>

            <div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-globe"></i><span class="hidden-phone">虚拟化管理</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/zone/list">资源域管理</a></li>
                    <li><a href="${ctx}/pod/list">机架管理</a></li>
                    <li><a href="${ctx}/cluster/list">集群管理</a></li>
                    <li><a href="${ctx}/host/list">主机管理</a></li>
                    <li><a href="${ctx}/template/list">模板管理</a></li>
                    <li><a href="${ctx}/network/list">网络管理</a></li>
                    <li><a href="${ctx}/virtualmachine/list">虚拟机管理</a></li>
                    <li><a href="${ctx}/primaryStorage/list">存储管理</a></li>
                    <li><a href="${ctx}/serviceOffering/list">服务方案管理</a></li>
                </ul>
            </div>

            <div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-eye-open"></i><span class="hidden-phone">监控管理</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/device/list">设备管理</a></li>
                    <li><a href="${ctx}/event/list">告警管理</a></li>
                    <li><a href="${ctx}/devicetemplate/templateList">监控模板管理</a></li>
                    <li><a href="${ctx}/device/environment">环境监控</a></li>
                    <li><a href="${ctx}/setting/backup">备份管理</a></li>
                </ul>
            </div>

            <div class="btn-group pull-left">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-th-large"></i><span class="hidden-phone">资源流程管理</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${ctx}/serviceOfferingBusiness/list">计算资源管理</a></li>
                    <li><a href="${ctx}/diskOfferingBusiness/list">磁盘资源管理</a></li>
                    <li><a href="${ctx}/virtualMachineBusiness/list">虚拟机管理</a></li>
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
                    <li><a href="${ctx}/user/logout">退出</a></li>
                </ul>
            </div>
            <!-- user dropdown ends -->
		</div>
	</div>
</div>