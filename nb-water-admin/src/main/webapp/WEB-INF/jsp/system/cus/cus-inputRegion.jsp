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
					<legend>区域选择</legend>
				</fieldset>
				<form class="layui-form" method="post">
					<input type="hidden" name="cusid" id="cusid" value="${cusid}" />
					
					<div class="layui-form-item"
						style="margin-top: 20px;margin-bottom: 30px;">
						<label class="layui-form-label">省份:</label>
						<div class="layui-input-block"
							style="margin-top: 20px;margin-bottom: 30px;">
							<select id="province" name="province" lay-filter="pselect">
								<option value="">---请选择省份---</option>
								<c:forEach items="${pList}" var="p">
									<option value="${p.provinceid}">${p.province}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="layui-form-item"
						style="margin-top: 20px;margin-bottom: 30px;">
						<label class="layui-form-label">地市:</label>
						<div class="layui-input-block"
							style="margin-top: 20px;margin-bottom: 30px;">
							<select id="city" name="city" lay-filter="cselect">
								<option value="">---请选择地市---</option>
							</select>
						</div>
					</div>

					<div class="layui-form-item"
						style="margin-top: 20px;margin-bottom: 30px;">
						<label class="layui-form-label">区县:</label>
						<div class="layui-input-block"
							style="margin-top: 20px;margin-bottom: 30px;">
							<select id="area" name="area" lay-filter="aselect">
								<option value="">---请选择区(县)---</option>
							</select>
						</div>
					</div>

					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit="" lay-filter="demo1"
								id="demo1">提交</button>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
</html>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>
	layui.use('element', function() {
		var element = layui.element;
	});

	layui.use([ 'layer', 'form' ], function() {
		var layer = layui.layer,
			form = layui.form;
		form.on('select(pselect)', function(data) {
			var pid = (data.value);
			if ("all" == pid) {
				$("#area").html("");
				var qxoption = $("<option>").val("all").text("全部");
				$("#area").append(qxoption);
				form.render('select');

				return;
			}
			$.ajax({
				type : 'POST',
				url : '${ctx}/city/getCity.action',
				data : {
					father : pid
				},
				dataType : 'json',
				success : function(data) {
					$("#area").html("");
					var qxoption = $("<option>").val("").text("---请选择区(县)---");
					$("#area").append(qxoption);
					var qxoptionall = $("<option>").val("all").text("全部");
					$("#area").append(qxoptionall);
					form.render('select');
					$("#city").html("");
					var soption = $("<option>").val("").text("---请选择地市---");
					$("#city").append(soption);
					form.render('select');
					$.each(data, function(key, val) {
						var option1 = $("<option>").val(val.cityid).text(val.city);
						$("#city").append(option1);
						form.render('select');
					});

				}
			});
		});

		form.on('select(cselect)', function(data) {
			var cid = (data.value);
			if ("all" == cid) {
				$("#area").html("");
				var qxoption = $("<option>").val("all").text("全部");
				$("#area").append(qxoption);
				form.render('select');
				return;
			}
			$.ajax({
				type : 'POST',
				url : '${ctx}/area/getArea.action',
				data : {
					father : cid
				},
				dataType : 'json',
				success : function(data) {
					$("#area").html("");
					var qxoption = $("<option>").val("").text("---请选择区(县)---");
					$("#area").append(qxoption);
					var qxoptionall = $("<option>").val("all").text("全部");
					$("#area").append(qxoptionall);
					form.render('select');
					$.each(data, function(key, val) {
						var option1 = $("<option>").val(val.id).text(val.area);
						$("#area").append(option1);
						form.render('select');
					});
				}
			});
		});


		form.on('submit(demo1)', function(data) {

			var data = $("form").serialize();
			$.ajax({
				type : "POST", //提交方式
				url : "${ctx}/cusController/saveInRegion.action", //路径
				data : data, //数据，这里使用的是Json格式进行传输
				dataType : "json",
				async : false,
				success : function(result) { //返回数据根据结果进行相应的处理
					layer.msg("操作成功", {
						icon : 1,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					}, function() {
						layer.close(layer.index);
						window.parent.location.reload();
					});
				},
				error : function(result) {
					layer.msg("操作失败!", {
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
	});
</script>