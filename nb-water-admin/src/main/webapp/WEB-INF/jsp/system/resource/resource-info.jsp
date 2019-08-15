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
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
        <style type="text/css">
    .layui-form-label{width:90px;}
    .layui-input-block{ margin-left:120px;}
    </style>
</head>
<body>
<div class="layui-body layui-bg-white">
	<div class="layui-fluid">
		<div class="layui-row  layui-col-space15">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 12px;">
            	<legend>资源信息</legend>
            </fieldset>
            <div class="layui-form-item">
            	<label class="layui-form-label">上级资源名称:</label>
                <label class="layui-form-mid">${pname }</label>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">资源名称:</label>
                <label class="layui-form-mid">${resource.resourcename }</label>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">资源URL:</label>
                <label class="layui-form-mid">${resource.resourceurl }</label>
            </div>
            <div class="layui-form-item">
            	<label class="layui-form-label">备注:</label>
                <label class="layui-form-mid">${resource.descr }</label>
            </div>
           
		</div>
	</div>
</div>

</body>
</html>