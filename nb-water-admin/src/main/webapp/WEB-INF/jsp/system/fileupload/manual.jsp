<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String baseTomcatPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();    
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>NB远传水表后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet"
          href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <style>
        .media{
            margin:auto;
        }
    </style>
</head>
<body>
    <div class="panel panel-primary">
        <div class="panel-heading" align="center">
            <h2></h2>
        </div>
        <div class="panel-body">
            <a class="media" href="<%=baseTomcatPath %>/${url}"></a>
        </div>
    </div>
</body>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/manual/js/jquery.media.js"></script>
<script>
    $(function() {
        $('a.media').media({width:700, height:530});
    });
</script>
</html>