<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns:th="http://www.thymeleaf.org">
<head>
	<title>NB-LOT水表管理平台</title>
	<link href=”${ctx}/resources/image/etsIco.ico” rel=”SHORTCUT ICON” />
	<meta charset="utf-8" />
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="shortcut  icon" type="${ctx}/resources/image/x-icon"
		  href="${ctx}/resources/images/etsIco.ico" media="screen" />
	<link rel="stylesheet" href="${ctx}/resources/css/layui.css" media="all">
	<script language="JavaScript">
        if (window != top)
            top.location.href = location.href;
	</script>

	<style type="text/css">
		body, html {
			height: 100%;
			min-width: 1000px;
			color: #fffff;
			overflow: hidden;
		}

		.logo {
			position: absolute;
			height: 120px;
			width: 120px;
			top: 20px;
			left: 20px;
		}

		.logo img {
			width: 100%;
		}

		.content {
			position: absolute;
			width: 500px;
			top: 45%;
			left: 7%;
		}

		.content img {
			width: 100%;
		}

		.logininfo {
			position: absolute;
			right: 7%;
			top: 50%;
			transform: translateY(-50%);
			text-align: center;
			background: url(${ctx}/resources/images/login_infobg.png);
			background-size: 100% 100%;
			width: 400px;
			height: 400px;
		}

		.write {
			color: #FFFFFF;
		}

		.loginname {
			position: absolute;
			top: 410px;
			left: 100px;
		}
		/*验证码*/
		#vCode {
			display: inline-block;
			border: 1px solid #666;
			width: 100px;
			height: 38px;
			position: absolute;
			top: -2px;
			right: 20px;
		}

		#vCode img {
			display: inline-block;
			height: 100%;
			width: 100%;
			position: absolute;
			top: 0;
			left: 0;
		}

		.layui-form-item {
			padding: 0px 20px 0 0;
			margin-bottom: 10px;
		}

		.layui-form-item .layui-input-block {
			display: flex;
			border-bottom: 1px solid #fff;
		}

		.layui-input-block {
			margin-left: 95px;
			margin-right: 75px;
		}

		.layui-input {
			border: none;
			background: transparent;
			color: #fff;
		}

		.layui-input::-ms-input-placeholder {
			color: red;
			/*opacity:1;*/
			font-size: 14px;
		}

		#layui-form {
			width: 400px;
			height: 400px;
			background-size: 100% 100%;
			color: #FFFFFF;
		}

		.layui-btn {
			background-color: #fff;
			color: #4e6e92;
			height: 34px;
			width: 275px;
			margin-top: 10px;
		}

		.layui-btn:hover {
			opacity: 1;
			color: #4e6e92;
		}

		input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {
			color: #fff;
			opacity: 0.5;
		}

		input:-moz-placeholder, textarea:-moz-placeholder {
			color: #fff;
			opacity: 0.5;
		}

		input::-moz-placeholder, textarea::-moz-placeholder {
			color: #fff;
			opacity: 0.5;
		}

		input:-ms-input-placeholder, textarea:-ms-input-placeholder {
			color: #fff;
			opacity: 0.5;
		}

		.layui-tab-title {
			width: 230px;
			left: 85px;
			margin-top: 70px;
		}

		.layui-tab-title li {
			color: #83bfda;
			min-width: 85px;
		}

		.layui-tab-brief>.layui-tab-title .layui-this {
			color: #fff;
		}

		.layui-tab-brief>.layui-tab-title .layui-this:after, .layui-tab-brief>.layui-tab-more li.layui-this:after
		{
			border-bottom: 2px solid #fff;
		}
	</style>
</head>
<body id="body" onkeydown="keyLogin(event);">
<div
		style="background:url(${ctx}/resources/images/login_bg.jpg) no-repeat; height: 100%; background-size:100% 100%;">
	<div class="logo">
		<img src="${ctx}/resources/images/login_logo.png" />
	</div>
	<div class="content">
		<img src="${ctx}/resources/images/login_water.png" />
	</div>
	<!--登录信息-->
	<div class="logininfo">

		<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
			<p style="margin-top:78px;padding-left: 170px;color: white;">
				企业登录
			<hr style="width: 314px;margin-left:43px;" />
			</p>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<!--用户登录-->
					<form class="layui-form" action="user" method="post"
						  lay-filter="example" id="user-form">

						<div class="layui-form-item">
							<div class="layui-input-block">
								<i class="layui-icon layui-icon-user"
								   style="font-size: 28px; color: #fff; padding-top: 4px;"></i> <input
									type="text" name="customerName" id="customerName"
									autocomplete="off" placeholder="请输入企业名称" maxlength="17"
									class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<i class="layui-icon layui-icon-username"
								   style="font-size: 28px; color: #fff; padding-top: 4px;"></i> <input
									type="text" name="username" id="username" autocomplete="off"
									placeholder="请输入用户名" maxlength="17" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<i class="layui-icon layui-icon-password"
								   style="font-size: 28px; color: #fff; padding-top: 4px;"></i> <input
									type="password" name="password" id="password"
									placeholder="请输入密码" maxlength="16" autocomplete="off"
									class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block" style="border-bottom: none;">
								<button type="button" class="layui-btn" lay-submit=""
										lay-filter="user-btn" id="user-btn">登录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="loginname">
			<img src="${ctx}/resources/images/login_name.png" width="200">
		</div>
	</div>


</div>
</body>
</html>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script>

    function keyLogin(e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) { //回车键的键值为13
            $("#user-btn").click();
            $("#user-btn").unbind("click");
            $("#user-btn").attr('disabled', true); //设置提交禁用防止重复提交数据
        }
    }


    //点击登录按钮触发登录验证方法
    $("#user-btn").click(function() {
        logincus();
        return false;
    });

    //登录验证方法
    function logincus() {
        $("#user-btn").unbind("click");
        $("#user-btn").attr('disabled', true); //设置提交禁用防止重复提交数据
        var customerName=$.trim($("#customerName").val());
        var username = $.trim($("#username").val());
        var password = $("#password").val();
        if ($.trim(customerName) == "" || $.trim(customerName) == null) {
            layer.msg('请输入企业名称！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#user-btn").bind("click", logincus);
                $("#user-btn").attr('disabled', false);
                $('#customerName').focus();
            });
            return;
        }
        if ($.trim(username) == "" || $.trim(username) == null) {
            layer.msg('请输入用户名！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#user-btn").bind("click", logincus);
                $("#user-btn").attr('disabled', false);
                $('#username').focus();
            });
            return;
        }
        var regx = /^[a-zA-Z0-9_-]{4,16}$/;
        if (!regx.test($.trim(username) )) {
            layer.msg('请输入正确格式！', {
                icon : 2,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#user-btn").bind("click", logincus);
                $("#user-btn").attr('disabled', false);
                $("#username").val("")
                $('#username').focus();
            });
            return;
        }
        if ($.trim(password )== "" || $.trim(password ) == null) {
            layer.msg('请输入密码！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#user-btn").bind("click", logincus);
                $("#user-btn").attr('disabled', false);
                $('#password').focus();
            });
            return;
        }
        var regx = /^[a-zA-Z0-9_-]{4,16}$/;
        if (!regx.test($.trim(password ))) {
            layer.msg('请输入正确格式！', {
                icon : 2,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#user-btn").bind("click", logincus);
                $("#user-btn").attr('disabled', false);
                $("#password").val("")
                $('#password').focus();
            });
            return;
        }
        var data = {'username':username,'password':password,'customerName':customerName};
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/custormerLogin/loginCheck.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                var status = result.status;
                var msg = result.msg;
                console.log(msg);
                if (status == "0") {
                    layer.msg(msg, {
                        icon : 1,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        window.location.href = "${ctx}/custormerLogin/loginSuccess.action";
                    });
                }
                if (status == "1") {
                    layer.msg(msg, {
                        icon : 3,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        $("#user-btn").bind("click", logincus);
                        $("#user-btn").attr('disabled', false);
                    });
                }
                if (status == "2") {
                    layer.msg(msg, {
                        icon : 3,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        $("#user-btn").bind("click", logincus);
                        $("#user-btn").attr('disabled', false);
                    });
                }
            },
            error : function(result) {
                layer.msg("登录失败!", {
                    icon : 3
                });
                $("#user-btn").bind("click", logincus);
                $("#user-btn").attr('disabled', false);
            }
        });
    }
</script>
