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
<form class="layui-form"  method="post">
<ul id="demo" class="ztree"></ul>

<div class="layui-form-item">
						<div class="layui-input-block">
							<a class="layui-btn" lay-submit="" lay-filter="demo1">提交</a>
						</div>
					</div>
</form>
</div>
</div>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script>
var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};


	var zNodes = ${tree};

	/*
	layui.use('element', function(){
	    var element = layui.element;
	});

	layui.use('tree', function(){
		  //layui.tree(options);

		  layui.tree({
			  elem: '#demo' //传入元素选择器
			  ,nodes: ${tree}
			});
		});*/

	$(document).ready(function(){
		$.fn.zTree.init($("#demo"), setting, zNodes);
	});
layui.use('element', function(){
    var element = layui.element;
});
layui.use('layer', function(){
    var layer = layui.layer;




});

		
		layui.use(['form', 'layedit', 'laydate'], function(){
			 var form = layui.form 
		     //监听提交
		     form.on('submit(demo1)', function(data){
		    	// var data = $("form").serialize();
		    	
		    	var zTreeOjb = $.fn.zTree.getZTreeObj("demo");
		    	var checkedNodes = zTreeOjb.getCheckedNodes();
		    	//alert(checkedNodes[0].id);
		 		var ids = "";
		 		for(var i=0 ; i< checkedNodes.length;i++){
		 			ids += checkedNodes[i].id;
		 			if(i != checkedNodes.length-1) ids+=",";
		 		}
		 		if(ids == ""){
		 			layer.msg("请分配至少一个资源!", {
		 				icon : 5,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
		 			});
		 			return ;
		 		}
		    	 $.ajax({ 
				        type : "POST", //提交方式 
				        url : '${ctx}/authority/saveResource.action?aid=${aid}',//路径 
				        data :"ids="+ids,//数据，这里使用的是Json格式进行传输 
				        dataType:"json",
				        async:false,
				        success : function(result) {//返回数据根据结果进行相应的处理 
                            layer.msg("操作成功", {
                                icon : 1,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                layer.close(layer.index);
                                window.parent.location.reload();
                            });

				        },
				        error : function(result){
                            layer.msg("操作失败,请选择管理员!", {
                                icon : 5,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                layer.close(layer.index);
                                window.parent.location.reload();
                            });
				        }

				       });
		     });
		})
</script>
</body>
</html>