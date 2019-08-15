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
				
					
					
					<div class="layui-form-item">
						<label class="layui-form-label">账号:</label>
						<label class="layui-form-mid">${customerUser.username }</label>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">姓名:</label>
						<label class="layui-form-mid">${customerUser.realname }</label>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">手机号:</label>
						<label class="layui-form-mid">${customerUser.tel }</label>
					</div>

  					<div class="layui-form-item">
						<label class="layui-form-label">状态:</label>
						<label class="layui-form-mid">
							<c:if test="${customerUser.isopen==1}">开通</c:if>
							<c:if test="${customerUser.isopen==0 || customerUser.isopen==null }">关闭</c:if>
						</label>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">类型:</label>
						<label class="layui-form-mid">
							<c:if test="${customerUser.type==1}">创建者</c:if>
							<c:if test="${customerUser.type==2}">管理员</c:if>
							<c:if test="${customerUser.type==3}">职员</c:if>
						</label>
					</div>
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
	// var form = layui.form
    
})
</script>
</body>
</html>