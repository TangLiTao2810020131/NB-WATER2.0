<%@ page import="com.ets.system.user.entity.tb_user" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    tb_user user=(tb_user) session.getAttribute("userSession");
    request.setAttribute("user",user);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>NB远传水表后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
</head>
<body class="layui-layout-body" >
<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item" >
                    <a href="javascript:;">欢迎${user.username}登录</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" id="edit" onclick="edit()">编辑信息</a></dd>
                        <dd><a href="javascript:;" id="editpassword" onclick="editPassword()">修改密码</a></dd>
                        <dd><a href="${ctx}/login/logOut.action">安全退出</a></dd>
                    </dl>
                </li>
            </ul>
        </div>

        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div class="layui-logo" lay-href="home/console.html">
                    <span>NB-IOT远传水表后台</span>
                </div>
                <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
                    <shiro:hasPermission name="数据字典">
                        <li data-name="home" class="layui-nav-item">
                            <a href="javascript:;" lay-tips="数据字典" lay-direction="2">
                                <i class="layui-icon layui-icon-senior"></i>
                                <cite>数据字典</cite>
                                <span class="layui-nav-more"></span></a>
                            <dl class="layui-nav-child">

                                <shiro:hasPermission name="行政区域">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/province/list.action"  target= "main">行政区域</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="水表型号">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/watermeter/list.action"  target= "main">水表型号</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="付费模式">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/pay/list.action"  target= "main">付费模式</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="结算方式">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/settlement/list.action"  target= "main">结算方式</a>
                                    </dd>
                                </shiro:hasPermission>
                            </dl>
                        </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="系统管理">
                        <li data-name="home" class="layui-nav-item">
                            <a href="javascript:;" lay-tips="系统管理" lay-direction="2">
                                <i class="layui-icon layui-icon-component"></i>
                                <cite>系统管理</cite>
                                <span class="layui-nav-more"></span></a>
                            <dl class="layui-nav-child">

                                <shiro:hasPermission name="用户管理">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/user/list.action"  target= "main">用户管理</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="资源管理">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/resource/list.action"  target= "main">资源管理</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="权限管理">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/authority/list.action"  target= "main">权限管理</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="角色管理">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/role/list.action"  target= "main">角色管理</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="登录日志">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/logLogin/list.action" target= "main">登录日志</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="操作日志">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/logOpr/list.action?index=user" target= "main">操作日志</a>
                                    </dd>
                                </shiro:hasPermission>
                            </dl>
                        </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="客户管理">
                        <li data-name="home" class="layui-nav-item">
                            <a href="javascript:;" lay-tips="客户管理" lay-direction="2">
                                <i class="layui-icon layui-icon-friends"></i>
                                <cite>客户管理</cite>
                                <span class="layui-nav-more"></span></a>
                            <dl class="layui-nav-child">


                                <shiro:hasPermission name="客户列表">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/cusController/list.action"  target= "main">客户列表</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="命令记录">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/commandsendlog/clist.action"  target= "main">命令记录</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="数据上报记录">
                                    <dd data-name="console1" class="">
                                        <a  href="${ctx}/readlog/clist.action"  target= "main">数据上报记录</a>
                                    </dd>
                                
                                </shiro:hasPermission>
                                
                                <shiro:hasPermission name="任务监控">
                                 	<dd data-name="console" class="">
                                        <a  href="${ctx}/quartzlogcontroller/list.action"  target= "main" >任务监控</a>
                                  	</dd>
                                 </shiro:hasPermission>
                            </dl>
                        </li>
                    </shiro:hasPermission>
  
                   <shiro:hasPermission name="文件管理">
                        <li data-name="home" class="layui-nav-item">
                            <a href="javascript:;" lay-tips="文件管理" lay-direction="2">
                                <i class="layui-icon layui-icon-file-b"></i>
                                <cite>文件管理</cite>
                                <span class="layui-nav-more"></span></a>
                            <dl class="layui-nav-child">

                                <shiro:hasPermission name="文件上传">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/fileManage/fileUploadPage.action"  target= "main" >文件上传</a>
                                    </dd>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="文件上传记录">
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/fileManage/list.action"  target= "main" >文件上传记录</a>
                                    </dd>
                                </shiro:hasPermission>
                              
                            </dl>
                        </li>
                    </shiro:hasPermission>                  
                    <li data-name="home" class="layui-nav-item">
                            <a href="javascript:;" lay-tips="水表管理" lay-direction="2">
                                <i class="layui-icon layui-icon-file-b"></i>
                                <cite>水表管理</cite>
                                <span class="layui-nav-more"></span></a>
                            <dl class="layui-nav-child">

									<dd data-name="console" class="">
                                        <a  href="${ctx}/sysdelaytime/info.action"  target= "main" >命令延迟时间</a>
                                    </dd
                                     
                                     <dd data-name="console" class="">
                                        <a  href="${ctx}/batch/list.action"  target= "main" >批次管理</a>
                                    </dd>
                                    
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/sysEquipment/list.action"  target= "main" >水表检测</a>
                                    </dd>
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/sysEquipment/toSysEquipmentImport.action"  target= "main" >水表导入</a>
                                    </dd>
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/sysreadlog/list.action"  target= "main" >上报记录</a>
                                    </dd>
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/syscommandsendlog/list.action"  target= "main" >命令记录</a>
                                    </dd>
                                    <dd data-name="console" class="">
                                        <a  href="${ctx}/sysalarm/list.action"  target= "main" >设备告警</a>
                                    </dd>
                            </dl>
                        </li>
                    
                </ul>
            </div>
        </div>



        <!-- 主体内容 -->
        <div class="layui-body">
            <div class="layadmin-tabsbody-item">
                <iframe src="" frameborder="0" class="layadmin-iframe" name= "main" scroll="no" ></iframe>
            </div>
        </div>
        <div class="layui-footer">
            <!-- 底部固定区域 -->
            <p>版权所有 © 银通物联</p>
        </div>
    </div>
</div>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    $(function() {
        var str = ('${str}');
        var id="${user.id}";
        if(str == "updatepass"){
            layui.use('layer', function () {
               var index = layer.open({
                    title:'新用户登录修改密码',
                    type: 2,
                    area: ['700px', '375px'],
                    closeBtn:0,
                    fixed: true, //是否固定
                    content: '${ctx}/user/userEditPassword.action?id='+id+'&str='+str
                });
            });
        }
    });

    layui.use('layer', function () {
        var id="${user.id}";
        console.log(id);
        window.edit=function(){
            layer.open({
                type: 2,
                area: ['700px', '375px'],
                fixed: false, //不固定
                maxmin: true,
                content: '${ctx}/user/useredit.action?id='+id
            });
        },
            window.editPassword=function(){
                layer.open({
                    type: 2,
                    area: ['700px', '375px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/user/userEditPassword.action?id='+id
                });
            }

    });



</script>
</body>
</html>