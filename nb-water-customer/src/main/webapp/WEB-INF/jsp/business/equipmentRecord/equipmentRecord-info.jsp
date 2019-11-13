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
<title>水表设备信息详情页面</title>
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
<link rel="stylesheet" href="${ctx}/resources/css/addClass.css" />
<style type="text/css">
.layui-form-label {
	width: 95px;
}

.layui-input-block {
	margin-left: 120px;
}

.layui-form-mid{
	word-wrap:break-word;
	width:400px;
}
</style>
</head>
<body>
	<div class="layui-body layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>安装设备记录详情</legend>
				</fieldset>

				<div class="layui-form-item">
					<label class="layui-form-label">关联业主号:</label>
					<label class="layui-form-mid">${watermeter.useraccount }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">关联业主名:</label>
					<label class="layui-form-mid">${watermeter.username }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">水表IMEI号:</label>
					<label class="layui-form-mid">${watermeter.watermetercode }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">水表型号:</label> <label
						class="layui-form-mid">${watermeter.watermeter }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">水表读数:</label> <label
						class="layui-form-mid">${watermeter.basenum }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">安装人员:</label> <label
						class="layui-form-mid">${watermeter.optionuser }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">安装地址:</label> <label
						class="layui-form-mid">${watermeter.address }</label>
				</div>	
			
				<div class="layui-form-item">
					<label class="layui-form-label">安装时间:</label> <label
						class="layui-form-mid">${watermeter.installdate }</label>
				</div>				


			</div>
		</div>
	</div>
</body>
</html>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
	src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>