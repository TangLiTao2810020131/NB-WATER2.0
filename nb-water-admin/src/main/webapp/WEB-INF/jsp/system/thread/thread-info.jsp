<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
</head>
<body>
<div class="layui-body layui-bg-white">
    <div class="layui-fluid">
        <div class="layui-row  layui-col-space15">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 12px;">
                <legend>线程池运行状态</legend>
            </fieldset>
            <div class="layui-form-item">
                <label class="layui-form-label">排队线程:</label>
                <label class="layui-form-mid">${queue }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">活动线程:</label>
                <label class="layui-form-mid">${active }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">执行完成:</label>
                <label class="layui-form-mid">${completed }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">总线程数:</label>
                <label class="layui-form-mid">${task }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">max线程数:</label>
                <label class="layui-form-mid">${max }</label>
            </div>

        </div>
    </div>
</div>

</body>
</html>