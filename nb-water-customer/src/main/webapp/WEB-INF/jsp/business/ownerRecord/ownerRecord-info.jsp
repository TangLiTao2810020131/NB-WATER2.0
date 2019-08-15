<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>业主查看页面</title>
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
					<legend>业主记录详情</legend>
				</fieldset>

				<div class="layui-form-item">
					<label class="layui-form-label">户号:</label>
					<label class="layui-form-mid">${record.useraccount }</label>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">户名:</label> <label
						class="layui-form-mid">${record.username }</label>
				</div>


				<div class="layui-form-item">
					<label class="layui-form-label">证件号码:</label> <label
						class="layui-form-mid">${record.idnum }</label>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">收费模式:</label> <label
						class="layui-form-mid">${record.operationmode}</label>
				</div>	

				<div class="layui-form-item">
					<label class="layui-form-label">单价:</label> <label
						class="layui-form-mid">${record.userunitprice}</label>
				</div>	
				
				<div class="layui-form-item">
					<label class="layui-form-label">住址:</label> <label
						class="layui-form-mid">${record.address }</label>
				</div>	
				
				
				<div class="layui-form-item">
					<label class="layui-form-label">水表IMEI号:</label> <label
						class="layui-form-mid">${record.watermetercode }</label>
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
