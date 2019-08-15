<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<title></title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet"
		  href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
	<link rel="stylesheet"
		  href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css"
		  type="text/css">
	<link rel="stylesheet" href="${ctx}/resources/css/addClass.css"
		  media="all" />
	<style type="text/css">
		.layui-form-label {
			width: 90px;
		}

		.layui-input-block {
			margin-left: 120px;
		}
	</style>
</head>
<body>
<div class="layui-body layui-bg-white">
	<div class="layui-fluid">
		<div class="layui-row  layui-col-space15">
			<fieldset class="layui-elem-field layui-field-title"
					  style="margin-top: 12px;">
				<legend>修改密码</legend>
			</fieldset>
			<input type="hidden" name="id" id="id" value="${id}" />


			<div class="layui-form-item">
				<label class="layui-form-label">原密码:</label>
				<div class="layui-input-block">
					<input type="password" name="password" id="password"
						   autocomplete="off" placeholder="请输入原密码" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">新密码:</label>
				<div class="layui-input-block">
					<input type="password" name="newpassword" id="newpassword"
						   autocomplete="off" placeholder="请输入新密码" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">重复新密码:</label>
				<div class="layui-input-block">
					<input type="password" name="cnewpassword" id="cnewpassword"
						   autocomplete="off" placeholder="请输入重复新密码" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item" style="width: 150px;">
				<div class="layui-input-block">
					<button class="layui-btn"  lay-filter="demo1"
							id="demo1">提交</button>
				</div>
			</div>
			<button class="layui-btn" lay-submit="" lay-filter="demo2" id="demo2" style="display: none;margin-left: 255px;margin-top: -100px;" >退出</button>
		</div>
	</div>
</div>
</div>

<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
		src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script>

    $(function() {
        var str = ('${str}');
        if(str == "updatepass"){
            $("#demo2").show();
        }
    });

    layui.use('element', function() {
        var element = layui.element;
    });

    function isCheckPassWord(password,id) {
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/customerUser/checkPassword.action", //路径
            data :{password:password,id:id}, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
    }

    $("#demo2").click(function(){
        layer.confirm("确定退出?!", {
            btn : [ "确定", "取消" ] //按钮
        }, function(index) {
            window.location.href = '${ctx}/login/logOut.action';
        });
    });


    //点击登录按钮触发登录验证方法
    $("#demo1").click(function() {

        $("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据

        var password = $("#password").val();
        var newpassword = $("#newpassword").val();
        var cnewpassword = $("#cnewpassword").val();


        if(password == ''){
            layer.msg('原密码不能为空！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        if(newpassword == ''){
            layer.msg('新密码不能为空！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        var re=/^[a-zA-Z0-9]{6,12}$/;
        if (!re.test(newpassword)){
            layer.msg('密码必须是6到12位的数字或字母！', {
                icon : 5,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;

        }
        if(cnewpassword == ''){
            layer.msg('重复新密码不能为空！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;
        }


        //var data = $("form").serialize();
        var pwd=$("#password").val();
        var id=$("#id").val();
        var num = isCheckPassWord(pwd,id);
        if(num == -1){
            layer.msg('原密码不正确！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;
        }

        if(password == newpassword){
            layer.msg('原密码与新密码不能一致！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        if(newpassword != cnewpassword){
            layer.msg('两次新密码不一致！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            },function(){
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        layer.confirm("修改密码需要重新登录，是否确定修改密码？", {
            btn : [ "确定", "取消" ] //按钮
        }, function(index) {
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/customerUser/savePassWord.action", //路径
                data : {id:id,cnewpassword:cnewpassword}, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    console.log(result);
                    var status = result.status;
                    var msg = result.msg;
                    layer.msg(msg, {
                        icon : 1,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        layer.close(layer.index);
                        window.parent.location.reload();
                        //window.location.href = "${ctx}/custormerLogin/logOut.action";
                    });
                }
            });
        });
    });


</script>
</body>
</html>