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
<title>上周周期操作页面</title>
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
					<legend>上报周期</legend>
				</fieldset>
				<form class="layui-form" method="post">

					<div class="layui-form-item">
						<label class="layui-form-label">上报周期:</label>
						<div class="layui-input-block">
							<input type="text" name="time" id="time" lay-verify="title"
								autocomplete="off" placeholder="请输入上报周期(0.5~168)小时"
								class="layui-input">
						</div>
					</div>

					<div class="layui-form-item">
						<div class="layui-input-block">
							<button type="button" class="layui-btn" id="demo1"
								lay-filter="demo1">发送</button>
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
	$(function() {});
	$("#demo1").click(function() {
		var time = $("#time").val();
		var ids = '${ids}';
		layer.confirm("请问是否确定发送设置上报周期命令!", {
			btn : [ "确定", "取消" ] //按钮
		}, function(index) {
			$.ajax({
				type : "POST", //提交方式
				url : '${ctx}/sysEquipment/reportCycle.action', //路径
				data : {
					"id" : ids,
					"time" : time,
                    "initNames":'${initNames}',
                    "doornums":'${doornums}'
				}, //数据，这里使用的是Json格式进行传输
				dataType : "json",
				async : false,
				success : function(result) { //返回数据根据结果进行相应的处理
					var status = result.status;
					var msg = result.msg;
					if (status == 1) {
						layer.msg(msg, {
							icon : 1,
							time : 2000 //2秒关闭（如果不配置，默认是3秒）
						}, function() {
							close();
						});
					} else if (status == 2) {
						layer.msg(msg, {
							icon : 2,
							time : 2000 //2秒关闭（如果不配置，默认是3秒）
						}, function() {
							close();
						});
					}
				}
			});
		});
	});

	function close() {
		var index = window.parent.layer.getFrameIndex(window.name); //获取窗口索引
		window.parent.layer.close(index);
		window.parent.raloadt();
	}
</script>
</html>
