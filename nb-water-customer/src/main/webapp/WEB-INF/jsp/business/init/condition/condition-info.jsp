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
    .layui-form-label{width:100px;}
    .layui-input-block{ margin-left:130px;}
    </style>
</head>
<body>
<div class="layui-body layui-bg-white">
	<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>限制金额设置</legend>
				</fieldset>
				<form class="layui-form"  method="post" style="width:350px; margin:0 auto;">
				<input type="hidden" name="aid" value="${accountOpener.id }" />
				<input type="hidden" name="oid" value="${overdueFine.id }" />
				<input type="hidden" name="pid" value="${priceMax.id }" />
					
					
					
					<div class="layui-form-item">
						<label class="layui-form-label">最大购买金额 :</label>
						<div class="layui-input-block">
							<input type="text" name="maxprice" value="${priceMax.maxprice }"
								lay-verify="title" autocomplete="off" placeholder="0.00"
								class="layui-input">
						</div>
					</div>
					
					
					<div class="layui-form-item">
						<label class="layui-form-label">开户费:</label>
						<div class="layui-input-block">
							<input type="text" name="accountOpenerPrice" value="${accountOpener.price }"
								lay-verify="title" autocomplete="off" placeholder="0.00"
								class="layui-input">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">滞纳金:</label>
						<div class="layui-input-block">
							<input type="text" name="overdueFilePrice" value="${overdueFine.price }"
								lay-verify="title" autocomplete="off" placeholder="0.00"
								class="layui-input">
						</div>
					</div>
					
					
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
						</div>
					</div>
					
					</form>
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

layui.use(['form', 'layedit', 'laydate','layer'], function(){
	 var form = layui.form,
		 layer = layui.layer;
     //监听提交
     form.on('submit(demo1)', function(data){
    	 var data = $("form").serialize();
    	 $.ajax({ 
		        type : "POST", //提交方式 
		        url : "${ctx}/condition/save.action",//路径 
		        data : data,//数据，这里使用的是Json格式进行传输 
		        dataType:"json",
		        async:false,
		        success : function(result) {//返回数据根据结果进行相应的处理 
		        	//alert(result);
					layer.msg(result,{
					    time:2000,
						icon:1
					},function (){
                        location.reload();
					});

		        },
		        error : function(result){
		        	//alert(result);
                    layer.msg(result,{
                        time:2000,
                        icon:2
                    },function(){
                        location.reload();
					});

		        }
		       });
    	 return false;
     });
})
</script>
</body>
</html>