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
					<legend>收费类型设置</legend>
				</fieldset>
				<form class="layui-form"  method="post">
				<input type="hidden" name="id" id="id" value="${price.id }" />
					
					
					
					<div class="layui-form-item">
						<label class="layui-form-label">类型名称:</label>
						<div class="layui-input-block">
							<input type="text" name="type" id="type" value="${price.type }"
								lay-verify="type" autocomplete="off" placeholder="类型名称"
								class="layui-input">
						</div>
					</div>
					
					
					<div class="layui-form-item">
						<label class="layui-form-label">价格:</label>
						<div class="layui-input-block">
							<input type="text" name="price" value="${price.price }"
								lay-verify="price" autocomplete="off" placeholder="0.00"
								class="layui-input">
						</div>
					</div>
					
					
					
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit="" lay-filter="demo1" id="demo1">提交</button>
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
	 var form = layui.form
         , layer = layui.layer
   //自定义正则的验证
    form.verify({

        type: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '类型名称不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '类型名称首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '类型名称不能全为数字';
            }
        },
        price:[/^([1-9]\d*|0)(\.\d{1,2})?$/,'只能输入数字']
    })


     //监听提交
     form.on('submit(demo1)', function(data){
         $("#demo1").attr('disabled',true); //设置提交禁用防止重复提交数据
         function isCheckType(data){
             var num;
             $.ajax({
                 type : "POST", //提交方式
                 url : "${ctx}/price/isCheckType.action", //路径
                 data : data, //数据，这里使用的是Json格式进行传输
                 dataType : "json",
                 async : false,
                 success : function(result) { //返回数据根据结果进行相应的处理
                     num = result;
                 }
             });
             return num;
         };
         var data = $("form").serialize();
         var num = isCheckType(data);
         if(num != 0 && num != -1){
             layer.msg('类型名称已存在,请重新输入！', {
                 icon : 7,
                 time : 2000 //2秒关闭（如果不配置，默认是3秒）
             }, function() {
                 $("#type").focus();
                 $("#demo1").attr('disabled', false);
             });
             return;
         }
    	 //var data = $("form").serialize();
    	 $.ajax({ 
		        type : "POST", //提交方式 
		        url : "${ctx}/price/save.action",//路径 
		        data : data,//数据，这里使用的是Json格式进行传输 
		        dataType:"json",
		        async:false,
		        success : function(result) {//返回数据根据结果进行相应的处理
		        	//alert(result);
                    layer.msg(result,{
                        icon:1,
						time:2000
					},function(){
                        layer.close(layer.index);
                        window.parent.location.reload();
					});

		        },
		        error : function(result){
		        	//alert(result);
                    layer.msg(result,{
                        icon:2,
                        time:2000
                    },function(){
                        layer.close(layer.index);
                        window.parent.location.reload();
					});
		        }
		       });
    	 return false;
     });
})
</script>
</body>
</html>