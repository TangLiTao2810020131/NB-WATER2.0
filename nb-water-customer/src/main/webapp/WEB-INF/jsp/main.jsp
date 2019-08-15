<%@ page language="java" import="java.util.*,java.lang.String" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
session.invalidate();


%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>${useros }</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/login.css" media="all">
</head>
<body layadmin-themealias="default" scroll="no">

<div class="layadmin-user-login layadmin-user-display-show" id="">
<h2>this is main.jsp</h2>

</div>

</body>
</body>
</html>