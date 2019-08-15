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
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>
</head>
<body class="layui-layout-body" >
<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">

        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <ul id="demoTree1" class="dtree" data-id="0"></ul>
            </div>
        </div>

        <div class="layui-body">
            <div class="layadmin-tabsbody-item">
                <iframe name="iframe_content" id="iframe_content" frameborder="0" width="100%" height="100%"></iframe>
            </div>
        </div>

    </div>
</div>

</body>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script>

    layui.config({
        base: '${ctx}/resources/layui_ext/dtree/' //配置 layui 第三方扩展组件存放的基础目录
    }).extend({
        dtree: 'dtree' //定义该组件模块名
    }).use(['element','layer', 'dtree'], function(){
        var layer = layui.layer,
            dtree = layui.dtree,
            $ = layui.$;


        dtree.render({
            elem: "#demoTree1",  //绑定元素
            url: "${ctx}/remote/treeData.action", //异步接口
            initLevel: 1,  // 指定初始展开节点级别
            cache: false,  // 当取消节点缓存时，则每次加载子节点都会往服务器发送请求
            async: false,
            useIframe: true,  //启用iframe
            iframe: {
                iframeElem: "#iframe_content",  // iframe的ID
                iframeUrl: "${ctx}/ownerstatistic/list.action", // iframe路由到的地址
                //iframeLoad: "all", // 表示点击任意节点加载iframe
                iframeDefaultRequest: {nodeId: "areaId",parentId:"parentId"} // 这里就将nodeId这个参数名称改为了areaId这个名称
                //,iframeRequest: {username: "zhangsan"} // 这里就自定义了需要传递的参数
            }
        });

        //单击节点 监听事件
        dtree.on("node('demoTree1')" ,function(param){
            layer.msg(JSON.stringify(param));
        });
    });



</script>

</html>

