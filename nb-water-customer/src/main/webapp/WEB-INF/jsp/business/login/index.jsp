<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>NB远传水表后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet"
          href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">

</head>
<body class="layui-layout-body">
<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-left" id="lashen">


                <li class="layui-nav-item layadmin-flexible" lay-unselect="">
                    <a href="javascript:;" class="kit-side-fold" layadmin-event="flexible"  title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right"></i>
                    </a>
                </li>

                <li class="layui-nav-item" lay-unselect="">
                    <a href="javascript:;" layadmin-event="refresh" title="刷新" onclick="document.getElementById('main').contentWindow.location.reload(true)">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
            </ul>

            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item">
                    <a layadmin-event="FullScreen" id="FullScreen" style="cursor:pointer;">
                        <i class="layui-icon layui-icon-screen-full"></i>
                    </a>
                </li>
                <li class="layui-nav-item">
                    <a style="cursor: pointer;">帮助文档</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" onclick="manual();">用户使用手册</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a style="cursor: pointer;">客服电话</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;">0551-5555678</a>
                        </dd>
                        <dd>
                            <a href="javascript:;">0551-5555678</a>
                        </dd>
                        <dd>
                            <a href="javascript:;">0551-5555678</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a style="cursor: pointer;">${userSession.REALNAME}</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" onclick="editCustormerUser()">修改信息</a>
                        </dd>
                        <dd>
                            <a href="javascript:;" onclick="editPassword()">修改密码</a>
                        </dd>
                        <dd>
                <li class="layui-nav-item">
                    <a href="${ctx}/custormerLogin/logOut.action">退出</a>
                </li>
                </dd>
                </dl>
                </li>
            </ul>

        </div>

        <!-- 侧边菜单 -->
        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div class="layui-logo" >
                    <a onclick="javascript:window.location.reload()" class="btn" lay-tips="客户管理" lay-direction="1">
                        <i class="layui-icon layui-icon-website" style="display: none;"></i>
                        <span>${userSession.CUSTOMERNAME }</span>
                    </a>
                </div>

                <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
                    <li data-name="home" class="layui-nav-item layui-nav-itemed">

                        <dl class="layui-nav-child">
                            <shiro:hasAnyRoles  name="创建者">
                            <dd data-name="console" class="">
                                <a class="btn"  href="${ctx}/residential/tree.action"  target= "main">
                                    <i class="layui-icon layui-icon-util"></i>
                                    <span>小区维护</span>
                                </a>
                            </dd>
                            </shiro:hasAnyRoles >
                            <dd data-name="console" class="">

								<shiro:hasAnyRoles  name="创建者,管理员,职员">
	                                <a class="btn" href="javascript:;" lay-tips="用户中心" lay-direction="3">
	                                    <i class="layui-icon layui-icon-user"></i>
	                                    <span>用户中心</span>
	                                    <span class="layui-nav-more"></span></a>
	                                <dl class="layui-nav-child">
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/owner/tree.action"  target= "main">用户管理</a>
	                                    </dd>
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/ownerRecord/list.action"  target= "main">用户记录</a>
	                                    </dd>
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/transferrecord/list.action"  target= "main">过户记录</a>
	                                    </dd>
	                                </dl>
                                </shiro:hasAnyRoles>
                            </dd>
                            <shiro:hasAnyRoles  name="创建者,管理员,职员">
                           	 	<dd data-name="console" class="">
	                                <a class="btn" href="javascript:;" lay-tips="设备管理" lay-direction="3">
	                                    <i class="layui-icon layui-icon-senior"></i>
	                                    <span>设备管理</span>
	                                    <span class="layui-nav-more"></span></a>
	                                <dl class="layui-nav-child">
	                                    <dd data-name="console1" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/equipment/tree.action"  target= "main">水表管理</a>
	                                    </dd>
	                                    <dd data-name="console1" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/equipmentRecord/list.action"  target= "main">水表记录</a>
	                                    </dd>
	                                    <dd data-name="console1" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/waterchange/list.action"  target= "main">换表记录</a>
	                                    </dd>
	                                    <dd data-name="console1" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/remote/tree.action"  target= "main">远程控制</a>
	                                    </dd>
	                                    <dd data-name="console1" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/commandsendlog/list.action"  target= "main">命令记录</a>
	                                    </dd>
	                                    <dd data-name="console1" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/readlog/list.action"  target= "main">上报记录</a>
	                                    </dd>
	                                    <shiro:hasAnyRoles  name="创建者">
		                                    <dd data-name="console" class="">
		                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/delaytime/info.action"  target= "main">延迟设置</a>
		                                    </dd>
	                                    </shiro:hasAnyRoles>
	                                </dl>
                           		 </dd>
                            </shiro:hasAnyRoles>
                            <shiro:hasAnyRoles  name="创建者,管理员,职员">
                            	<dd data-name="console" class="">
	                                <a class="btn" href="javascript:;" lay-tips="抄表管理" lay-direction="3">
	                                    <i class="layui-icon layui-icon-list"></i>
	                                    <span>抄表管理</span>
	                                    <span class="layui-nav-more"></span></a>
	                                <dl class="layui-nav-child">
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/meterread/tree.action"  target= "main">最新抄表</a>
	                                    </dd>
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/meterreadlog/tree.action"  target= "main">抄表记录</a>
	                                    </dd>
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/balance/tree.action"  target= "main">抄表结算</a>
	                                    </dd>
	                                </dl>
                            	</dd>
                           </shiro:hasAnyRoles>  	
                           
                           <shiro:hasAnyRoles  name="创建者,管理员,职员">
                            	<dd data-name="console" class="">
	                                <a class="btn" href="javascript:;" lay-tips="用户中心" lay-direction="3">
	                                    <i class="layui-icon layui-icon-log"></i>
	                                    <span>定时任务</span>
	                                    <span class="layui-nav-more"></span></a>
	                                <dl class="layui-nav-child">
	                                	<shiro:hasAnyRoles  name="创建者">
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/timertask/info.action"  target= "main">任务配置</a>
	                                    </dd>
	                                     </shiro:hasAnyRoles>
		                                    <dd data-name="console" class="">
		                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/schedulejoblog/list.action"  target= "main">执行记录</a>
		                                    </dd>
	                                </dl>
                            	</dd>
                            </shiro:hasAnyRoles>

                            	<dd data-name="console" class="">
	                                <a class="btn" href="javascript:;" lay-tips="用户中心" lay-direction="3">
	                                    <i class="layui-icon layui-icon-table"></i>
	                                    <span>统计分析</span>
	                                    <span class="layui-nav-more"></span></a>
	                                <dl class="layui-nav-child">
	                                    <dd data-name="console" class="">
	                                        <a lay-href="模板页面/菜单管理.html" href="${ctx}/ownerstatistic/tree.action"  target= "main">用户用量统计</a>
	                                    </dd>
		                                <dd data-name="console" class="">
		                                    <a lay-href="模板页面/菜单管理.html" href="${ctx}/residentialstatistic/tree.action"  target= "main">小区用量统计</a>
		                                </dd>
		                                <dd data-name="console" class="">
		                                    <a lay-href="模板页面/菜单管理.html" href="${ctx}/districtstatistic/list.action"  target= "main">区域用量统计</a>
		                                </dd>
	                                </dl>
                            </dd>    

                            <shiro:hasAnyRoles  name="创建者,管理员">
	                            <dd data-name="console" class="">
	                                <a class="btn" href="${ctx}/customerUser/list.action"  target= "main">
	                                    <i class="layui-icon layui-icon-friends"></i><span>职员管理</span></a>
	                            </dd>
                            </shiro:hasAnyRoles>
                            <%--  <shiro:hasAnyRoles  name="创建者">
                            <dd data-name="console" class="">
                                <a class="btn" href="${ctx}/price/list.action"  target= "main">
                                    <i class="layui-icon layui-icon-dollar"></i><span>费用设置</span></a>
                            </dd>
                            </shiro:hasAnyRoles>
                             <shiro:hasAnyRoles  name="创建者">
                            <dd data-name="console" class="">
                                <a class="btn" href="${ctx}/condition/info.action"  target= "main">
                                    <i class="layui-icon layui-icon-rmb"></i><span>限制金额</span></a>
                            </dd>
                            </shiro:hasAnyRoles> --%>
                             <shiro:hasAnyRoles  name="创建者,管理员,职员">
	                            <dd data-name="console" class="">
	                                <a class="btn" href="${ctx}/alarm/list.action"  target= "main">
	                                    <i class="layui-icon layui-icon-tips"></i><span>设备告警</span></a>
	                            </dd>
                            </shiro:hasAnyRoles>
                            <shiro:hasAnyRoles  name="创建者,管理员,职员">
                            <dd data-name="console" class="">
                                <a class="btn" href="${ctx}/loginLog/list.action"  target= "main">
                                    <i class="layui-icon layui-icon-friends"></i><span>登录日志</span></a>
                            </dd>
                            </shiro:hasAnyRoles>
                            <shiro:hasAnyRoles  name="创建者,管理员,职员">
                            <dd data-name="console" class="">
                                <a class="btn" href="${ctx}/logCustomerOpr/list.action"  target= "main">
                                    <i class="layui-icon layui-icon-file-b"></i><span>操作日志</span></a>
                            </dd>
                            </shiro:hasAnyRoles>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>



        <!-- 主体内容 -->
        <div class="layui-body">
            <div class="layadmin-tabsbody-item">
                <iframe src="" frameborder="0" class="layadmin-iframe" name="main" id="main"></iframe>
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
<script
        src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script>
    //JavaScript代码区域
    layui.use('element', function() {
        var element = layui.element;

    });

    $(function() {
        var str = ('${str}');
        var id = ('${userSession.ID}');
        if(str == "updatepass"){
            layui.use('layer', function () {
                var index = layer.open({
                    title:'新用户登录请修改密码',
                    type: 2,
                    area: ['700px', '375px'],
                    closeBtn:0,
                    fixed: true, //是否固定
                    content: '${ctx}/customerUser/editeditPassword.action?id='+id+'&str='+str
                });
            });
        }
    });

    layui.use('layer', function() {
        var layer=layui.layer;
        window.editCustormerUser = function() {
            var id = ('${userSession.ID}');
            layer.open({
                type : 2,
                area : [ '600px', '400px' ],
                fixed : false, //不固定
                maxmin : true,
                content : '${ctx}/customerUser/editCustomerUserInfo.action?id=' + id
            });
        }
        window.editPassword = function() {
            var id = ('${userSession.ID}');
            
            var index = layer.open({
                type : 2,
                area : [ '600px', '400px' ],
                fixed : false, //不固定
                maxmin : true,
                content : '${ctx}/customerUser/editeditPassword.action?id=' + id
            });
        }        
    });

    //点击全屏
    function entryFullScreen() {
        var docE = document.documentElement;
        if (docE.requestFullScreen) {
            docE.requestFullScreen();
        } else if (docE.mozRequestFullScreen) {
            docE.mozRequestFullScreen();
        } else if (docE.webkitRequestFullScreen) {
            docE.webkitRequestFullScreen();
        }
    }

    //点击退出全屏
    function exitFullScreen() {
        var docE = document;
        if (docE.exitFullscreen) {
            docE.exitFullscreen();
        } else if (docE.mozCancelFullScreen) {
            docE.mozCancelFullScreen();
        } else if (docE.webkitCancelFullScreen) {
            docE.webkitCancelFullScreen();
        }
    }

    //调用事件
    $(document).ready(function() {
        var fScreen = localStorage.getItem('fullscreen_info');
        if (fScreen && fScreen != 'false') {
            var fScreenIndex = layer.alert('按ESC退出全屏', {
                    title : '进入全屏提示信息',
                    skin : 'layui-layer-lan',
                    closeBtn : 0,
                    anim : 4,
                    offset : '100px'
                }, function() {
                    entryFullScreen();
                    $('#FullScreen').html('<i class="layui-icon layui-icon-screen-full"></i>');
                    layer.close(fScreenIndex);
                }
            )
        }
    });

    // 全屏切换
    $('#FullScreen').bind('click', function() {
        var fullscreenElement = document.fullscreenElement ||
            document.mozFullScreenElement ||
            document.webkitFullscreenElement;
        if (fullscreenElement == null) {
            entryFullScreen();
            $(this).html('<i class="layui-icon layui-icon-screen-restore"></i>');
        } else {
            exitFullScreen();
            $(this).html('<i class="layui-icon layui-icon-screen-full"></i>');
        }
    });


    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });

    var isShow = true;  //定义一个标志位
    $('.kit-side-fold').click(function(){
        //选择出所有的span，并判断是不是hidden
        $('.layui-nav-item span').each(function(){
            if($(this).is(':hidden')){
                $(this).show();
            }else{
                $(this).hide();
            }
        });
        //判断isshow的状态
        if(isShow){
            $('.layui-side.layui-side-menu').width(60); //设置宽度
            $('#lashen').css('margin-left', '-160px');  //修改图标的位置
            $('.kit-side-fold i').attr('class', 'layui-icon layui-icon-spread-left');
            //将footer和body的宽度修改
            $('.layui-body').css('left', 60+'px');
            $('.layui-footer').css('left', 60+'px');
            $('.layui-logo').css('width', 60+'px');
            $('.layui-icon.layui-icon-website').show();
            //将二级导航栏隐藏
            $('dd span').each(function(){
                $(this).hide();
            });
            //修改标志位
            isShow =false;
        }else{
            $('.layui-side.layui-side-menu').width(220);
            $('#lashen').css('margin-left', '0px');
            $('.kit-side-fold i').attr('class', 'layui-icon layui-icon-shrink-right');
            $('.layui-body').css('left', 220+'px');
            $('.layui-footer').css('left', 220+'px');
            $('.layui-logo').css('width', 220+'px');
            $('.layui-icon.layui-icon-website').hide();
            $('dd span').each(function(){
                $(this).show();
            });
            isShow = true;
        }
    });
    $('.btn').click(function(){
        if(!isShow){
            $('.layui-side.layui-side-menu').width(220);
            $('#lashen').css('margin-left', '0px');
            $('.kit-side-fold i').attr('class', 'layui-icon layui-icon-shrink-right');
            $('.layui-body').css('left', 220+'px');
            $('.layui-footer').css('left', 220+'px');
            $('.layui-logo').css('width', 220+'px');
            $('dd span').each(function(){
                $(this).show();
            });
            $('.btn span').show();
            isShow =true;
        }
    });
    //用户使用手册
    function manual()
    {
        layer.open({
            type : 2,
            title:['用户使用手册','font-size:14px'],
            area : [ '800px', '600px' ],
            fixed : false, //不固定
            maxmin:true,
            content : '${ctx}/fileManage/manual.action'
        });
    }
</script>

</body>
</html>