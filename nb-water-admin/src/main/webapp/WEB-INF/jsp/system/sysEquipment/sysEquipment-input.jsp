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
<title>水表设备操作页面</title>
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
	<div class="layui-fluid">
		<div class="layui-row  layui-col-space15">
			<fieldset class="layui-elem-field layui-field-title"
				style="margin-top: 12px;">
				<legend>水表信息</legend>
			</fieldset>
			<form class="layui-form" method="post">
				<input type="hidden" name="uuid" id="uuid" value="${equipment.uuid }" />
				<input type="hidden" name="batchid" id="batchid" value="${batchid}" />
				<input type="hidden" name="type" id="type" /> <input type="hidden"name="deviceid" value="${equipment.deviceid }" />
				<div class="layui-form-item" id="div-pca">

					<div class="layui-form-item">
						<label class="layui-form-label">水表ID号:</label>
						<div class="layui-input-block" id="div-three">
							<input type="id" name="id" id="id"
								value="${equipment.imei }"
								<c:if test="${equipment.id != null}"> style="cursor:not-allowed" disabled="disabled" </c:if>
								lay-verify="title" autocomplete="off" placeholder="请输入水表ID号"
								class="layui-input">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">水表IMEI号:</label>
						<div class="layui-input-block" id="div-three">
							<input type="text" name="imei" id="imei"
								value="${equipment.imei }"
								<c:if test="${equipment.imei != null}"> style="cursor:not-allowed" disabled="disabled" </c:if>
								lay-verify="title" autocomplete="off" placeholder="请输入水表IMEI号"
								class="layui-input">
						</div>
					</div>


					<div id="controlDiv" class="layui-form-item">
						<label class="layui-form-label">阀门状态:</label>
						<div class="layui-input-block">
							<input type="checkbox"
								<c:if test="${equipment.control==1}">checked="checked"</c:if>
								name="control" value="1" lay-skin="switch"
								lay-filter="switchTest" lay-text="ON|OFF">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">水表底数:</label>
						<div class="layui-input-block">
							<input type="text" name="basenum" id="basenum"
								value="${equipment.basenum }" lay-verify="title"
								autocomplete="off" placeholder="请输入水表底数" class="layui-input">
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
</body>
</html>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
	src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">



	var id = $("#id").val();
	if (id != "" && id != null) {
		$("#div-adress").attr("style", "display:none");
		$("#div-three").html("<label class=\"layui-form-mid\">${equipment.imei }</label>");
	}

	$(function() {
		$("#code").focus();
	});

	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var date = new Date();
		//时间控件
		laydate.render({
			elem : '#installdate',
			type : 'datetime',
			max : '' + date
		});
	})

	function isCheckIMEI(data) {
		var num;
		$.ajax({
			type : "POST", //提交方式
			url : "${ctx}/sysEquipment/isCheckIMEI.action", //路径
			data : data, //数据，这里使用的是Json格式进行传输
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
				num = result;
			}
		});
		return num;
	}

	//点击保存按钮触发登录验证方法
	$("#demo1").click(function() {

		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据

		var imei = $("#imei").val();
		if (imei == "" || imei == null) {
			layer.msg('请输入水表IMEI号！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#imei").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		var re = /^\d{15}$/;

		if (!re.test(imei)) {
			layer.msg('水表IMEI号是15位数字！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#imei").focus();
				$("#demo1").attr('disabled', false);
			});
			return;

		}

		var data = $("form").serialize();
		var num = isCheckIMEI(data);
		if (num != 0 && num != -1) {
			layer.msg('该设备IMEI号已注册,请输入其他IMEI号！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#imei").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}

		var basenum = $("#basenum").val();
		if (basenum == "" || basenum == null) {
			layer.msg('请填入水表底数！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#basenum").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		var re = /^[0-9]*$/;
		if (!re.test(basenum)) {
			layer.msg('水表底数是整数！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#basenum").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		if (basenum < 0 || basenum > 90000) {
			layer.msg('水表底数只能输入0到90000之间的整数！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#basenum").focus();
				$("#demo1").attr('disabled', false);
			});
			return;

		}


		$.ajax({
			type : "POST", //提交方式
			url : "${ctx}/sysEquipment/save.action", //路径
			data : data, //数据，这里使用的是Json格式进行传输
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
				console.log(result);
				var status = result.status;
				var msg = result.msg;
				if (status == 1) {
					layer.msg(msg, {
						icon : 1,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					}, function() {
						layer.close(layer.index);
						window.parent.location.reload();
					});
				}
				if (status == 3) {
					layer.msg(msg, {
						icon : 2,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					}, function() {
						layer.close(layer.index);
						window.parent.location.reload();
					});
				}
			},
			error : function(result) {
				layer.msg("操作失败,请选择管理员!", {
					icon : 1,
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
