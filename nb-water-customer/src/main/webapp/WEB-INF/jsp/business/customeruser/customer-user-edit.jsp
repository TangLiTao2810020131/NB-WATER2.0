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
				<legend>帐号信息</legend>
			</fieldset>
			<form class="layui-form" method="post">
				<input type="hidden" name="id" value="${customerUser.id }" />


				<div class="layui-form-item">
					<label class="layui-form-label">账号:</label>
					<div class="layui-input-block">
						<input type="text" name="username" style="cursor:not-allowed;"
							   readonly="readonly" value="${customerUser.username }"
							   lay-verify="username" autocomplete="off" placeholder="请输入账号"
							   class="layui-input">
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">姓名:</label>
					<div class="layui-input-block">
						<input type="text" name="realname" style="cursor:not-allowed;"
							   value="${customerUser.realname }"
							   lay-verify="username" autocomplete="off" placeholder="请输入姓名"
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

				<div class="layui-form-item" style="width: 150px;">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="demo1"
								id="demo1">提交</button>
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
        form.verify({
            tel:[/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/, '手机号必须是正确的格式！']
        });


        //监听提交
        form.on('submit(demo1)', function(data){
            var data = $("form").serialize();
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/customerUser/edit.action",//路径
                data : data,//数据，这里使用的是Json格式进行传输
                dataType:"json",
                async:false,
                success : function(result) {//返回数据根据结果进行相应的处理
                    var status = result.status;
                    var msg = result.msg;
                    layer.msg(msg, {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        layer.close(layer.index);
                        window.parent.location.reload();
                    });
                },
                error : function(result){
                    layer.closeAll();
                    window.location.reload();
                }
            });
            return false;
        });
    })
</script>
</body>
</html>