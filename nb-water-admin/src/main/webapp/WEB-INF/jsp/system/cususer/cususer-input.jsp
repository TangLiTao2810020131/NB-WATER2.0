<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css" media="all"/>
    <style type="text/css">
    .layui-form-label{width:90px;}
    .layui-input-block{ margin-left:120px;}
    </style>
</head>
<body>
<div class="layui-body layui-bg-white">
	<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>客户信息</legend>
				</fieldset>
				<form class="layui-form"  method="post">
				<input type="hidden" name="cid"  value="${cid }" />
				<input type="hidden" name="id" id="id" value="${customerUser.id }" />

					<div class="layui-form-item">
						<label class="layui-form-label">姓名:</label>
						<div class="layui-input-block" id="div-realname">
							<input type="text" name="realname" id="realname" value="${customerUser.realname }"
								   lay-verify="realname" autocomplete="off" placeholder="请输入姓名"
								   class="layui-input">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">账号:</label>
						<div class="layui-input-block" id="div-username">
							<input type="text" name="username" id="username" value="${customerUser.username }"
								lay-verify="username" autocomplete="off" placeholder="请输入账号"
								class="layui-input">
						</div>
					</div>
					
					
					<div class="layui-form-item" id="pass">
						<label class="layui-form-label">密码:</label>
						<div class="layui-input-block">
							<input type="password" name="password" id="password" value="${customerUser.password }"
								lay-verify="password" autocomplete="off" placeholder="请输入密码"
								class="layui-input">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">手机号:</label>
						<div class="layui-input-block">
							<input type="text" name="tel" value="${customerUser.tel }"
								lay-verify="tel" autocomplete="off" placeholder="请输入手机号"
								class="layui-input">
						</div>
					</div>
					
					
  					
  					<div class="layui-form-item">
						<label class="layui-form-label">状态:</label>
						<div class="layui-input-block">
							<input type="checkbox"
								<c:if test="${customerUser.isopen==1}">checked="checked"</c:if>
								name="isopen" value="1" lay-skin="switch"
								lay-filter="switchTest" lay-text="ON|OFF">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">类型:</label>
						 <div class="layui-input-block">
							<input type="radio" name="type" value="1" <c:if test="${customerUser.type==1}">checked="checked"</c:if> title="创建者">  
							<input type="radio" name="type" value="2" <c:if test="${customerUser.type==2}">checked="checked"</c:if> title="管理员"> 
							<input type="radio" name="type" value="3" <c:if test="${customerUser.type==3}">checked="checked"</c:if> title="职员"> 
						</div>
					</div>
					
					
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit="" lay-filter="demo1" id="demo1">提交</button>
						</div>
					</div>
					
					</form>
			</div>
		</div>
</div>

<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>
layui.use('element', function(){
    var element = layui.element;
});

layui.use(['form', 'layedit', 'laydate'], function(){
	 var form = layui.form

    var id=$("#id").val();
    if (id!="" && id!=null) {
        $("#div-username").html("<label class=\"layui-form-mid\">${customerUser.username }</label>");
        $("#pass").attr("style","display:none");

        form.verify({
			realname: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (value.length==0) {
                    return '姓名不能为空'
                }
               /* if (!new RegExp("^[\u4e00-\u9fa5]{2,7}$").test(value)) {
                    return '请输入正确的姓名格式';
                }*/

            },
            tel:[/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/, '手机号必须是正确的格式！']
        });

    }
    else {
        form.verify({
            realname: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (value.length==0) {
                    return '姓名不能为空'
                }
               /* if (!new RegExp("^[\u4e00-\u9fa5]{2,7}$").test(value)) {
                    return '请输入正确的姓名格式';
                }*/

            },
            username: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (value.length==0) {
                    return '账号不能为空'
                }
                if (!new RegExp("^[a-zA-Z0-9_]{4,16}$").test(value)) {
                    return '请输入正确的格式（长度为4到16的字母，数字和下划线）';
                }
                if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                    return '账号首尾不能出现下划线\'_\'';
                }
                if (/^\d+\d+\d$/.test(value)) {
                    return '账号不能全为数字';
                }
            }, password: [/^[a-zA-Z0-9]{6,12}$/, '密码必须6到12位数字或者字母'],
            tel: [/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/, '手机号必须是正确的格式！']
        });
    }
	 //判断id是否为空 为空就是新增 判断用户的账号名的唯一性 不为空 就是编辑用户
	/* var id=$("#id").val();
	if (id=="" || id==null) {*/
    //检查用户的唯一性
    function isCheckCustomerUser(data){
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/cusUserController/isCheckCustomerUser.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
    }

    function isCheckCusUserType(data){
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/cusUserController/isCheckCusUserType.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
    }

    $("#demo1").click(function(){
        var data = $("form").serialize();
        var num=isCheckCustomerUser(data);
        if(num != 0 && num != -1) {
            layer.msg('用户名已存在,请重新输入！', {
                icon: 7,
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function () {
                $("#username").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        var num1=isCheckCusUserType(data);
        if(num1 != 0) {
            layer.msg('已有创建者用户，请重新选择账户类型！', {
                icon: 7,
                time: 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function () {
                $("#username").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
    });


     //监听提交
     form.on('submit(demo1)', function(data){
    	 var data = $("form").serialize();
    	 $.ajax({
		        type : "POST", //提交方式
		        url : "${ctx}/cusUserController/save.action"+"?mark=customer",//路径
		        data : data,//数据，这里使用的是Json格式进行传输
		        dataType:"json",
		        async:false,
		        success : function(result) {//返回数据根据结果进行相应的处理
		        	//alert(result);
                    layer.msg(result,{
                        icon:1,
                        time:2000
                    },function(){
                        layer.close(layer.index);
                        window.parent.location.reload();
                    })
		        },
		        error : function(result){
		        	//alert(result);
                    layer.msg(result,{
                        icon:2,
                        time:2000
                    },function(){
                        layer.close(layer.index);
                        window.parent.location.reload();
                    })
		        }
		       });
    	 return false;
     });
})
</script>
</body>
</html>