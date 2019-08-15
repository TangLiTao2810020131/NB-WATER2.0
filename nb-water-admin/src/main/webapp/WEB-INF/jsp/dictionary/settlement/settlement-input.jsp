<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>结算方式数据字典操作页面</title>
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
	<div class="layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>结算方式信息</legend>
				</fieldset>
				<form class="layui-form" method="post">
					<input type="hidden" name="id" value="${settlement.id }" />

					<div class="layui-form-item">
						<label class="layui-form-label">结算方式类型:</label>
						<div class="layui-input-block">
							<input type="text" name="settlementmethod" id="settlementmethod"
								value="${settlement.settlementmethod }" lay-verify="title"
								autocomplete="off" placeholder="请输入结算方式类型" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">结算方式规则:</label>
						<div class="layui-input-block">
							<input type="text" name="settlementrules" id="settlementrules"
								value="${settlement.settlementrules }" lay-verify="title"
								autocomplete="off" placeholder="请输入结算方式规则" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">描述</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入内容" name="describe" id="describe"
								style="resize:none" class="layui-textarea">${settlement.describe }</textarea>
						</div>
					</div>

					<div class="layui-form-item">
						<div class="layui-input-block">
							<button type="button" class="layui-btn" id="demo1"
								lay-filter="demo1">提交</button>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
	src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	$(function() {

    });  
	//点击登录按钮触发登录验证方法
	$("#demo1").click(function() {
	
		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
		

		var settlementmethod = $("#settlementmethod").val();
		var settlementrules = $("#settlementrules").val();

		if (settlementmethod == ""|| settlementmethod == null) {
			layer.msg('请输入结算方式类型！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#settlementmethod").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		if (settlementrules == ""|| settlementrules == null) {
			layer.msg('请输入结算方式规则！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#settlementrules").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		var data = $("form").serialize();
		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/settlement/save.action", //路径 
			data : data, //数据，这里使用的是Json格式进行传输 
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
                if (result==0) {
                    layer.msg("新增成功", {
                        icon : 1,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        layer.close(layer.index);
                        window.parent.location.reload();
                    });
                }else if(result==-1) {
                    layer.msg("编辑成功", {
                        icon : 1,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        layer.close(layer.index);
                        window.parent.location.reload();
                    });
                }else{
                    layer.msg("新增失败，可能结算方式类型重复！", {
                        icon : 5,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        layer.close(layer.index);
                        //window.parent.location.reload();
                    });
                }
			},
			error : function(result) {
				layer.msg("操作失败,请选择管理员!", {
					icon : 5,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function() {
					layer.close(layer.index);
					window.parent.location.reload();
				});
			}
		});
		return false;
	});
</script>
</html>
