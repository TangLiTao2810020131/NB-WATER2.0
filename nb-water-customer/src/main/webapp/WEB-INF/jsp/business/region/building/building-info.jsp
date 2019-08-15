<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>小区详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css" media="all"/>
        <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/dtree.css "/>
    <link rel="stylesheet" href="${ctx}/resources/layui_ext/dtree/font/iconfont.css"/>    
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
					<legend>${residential.province }-${residential.city}-${residential.area }-${residential.name}</legend>
				</fieldset>
				<div id="nav1">
				    <!-- 顶部切换卡 -->
				    <!--ul的id要和lay-filter一致-->
				    <div class="layui-tab" lay-filter="main_tab1">
				        <ul id="main_tab1" class="layui-tab-title">
				            <li lay-id="0" class="layui-this">楼层信息</li>
				            <li lay-id="1">门牌号</li>
				        </ul>	
				        <div class="layui-tab-content">
				            <div class="layui-tab-item layui-show">
							<div class="layui-form-item">
								<label class="layui-form-label">栋:</label>
								<label class="layui-form-mid">
									${build.building }
								</label>
							</div>					
							<div class="layui-form-item">
								<label class="layui-form-label">描述:</label>
								<label class="layui-form-mid">
									${build.describe }
								</label>
							</div>
						</div>
						<div class="layui-tab-item">
							 <ul id="demoTree1" class="dtree" data-id="0"></ul>
						</div></div>
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
	 var form = layui.form 
});

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
            url: "${ctx}/building/treeData.action?buildingid=${build.id}", //异步接口
            initLevel: 1,  // 指定初始展开节点级别
            cache: false,  // 当取消节点缓存时，则每次加载子节点都会往服务器发送请求
            async: false
        });

        //单击节点 监听事件
/*         dtree.on("node('demoTree1')" ,function(param){
            layer.msg(JSON.stringify(param));
        }); */
    });
</script>
</body>
</html>