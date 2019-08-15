<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>接入类型数据字典操作页面</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet"
	href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
<link rel="stylesheet"
	href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/addClass.css"
	media="all" />
<style type="text/css">
.layui-form-label {
	width: 90px;
}

.layui-input-block {
	margin-left: 120px;
}
</style>
</head>

<body style="background-color: white;">
	<div class="layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>${province.province}-${city.city}-${area.area }</legend>
				</fieldset>
				<form class="layui-form" method="post">
					<input type="hidden" name="id" value="${residential.id }" />
					<input type="hidden" name="areacode" value="${residential.areacode }" />
					<input type="hidden" name="pid" value="0" />

					<div class="layui-form-item">
						<label class="layui-form-label">小区名称:</label>
						<div class="layui-input-block">
							<input type="text" name="name" id="name"
								value="${residential.name }" lay-verify="title"
								autocomplete="off" placeholder="请输入小区名称" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">小区地址</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入小区地址" name="address" id="address"
								style="resize:none" class="layui-textarea" >${residential.address }</textarea>
						</div>
					</div>
					
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">描述</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入内容(最多100个字符)" name="describe" id="describe"
								style="resize:none" class="layui-textarea">${residential.describe }</textarea>
						</div>
					</div>

					<div class="layui-form-item">
						<div class="layui-input-block">
							<button type="button" class="layui-btn" id="demo1"
								lay-filter="demo1">提交</button>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
	src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">

	$(function() {
		$("#code").focus();
    });

    function isCheckName(data){
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/residential/isCheckName.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
    }
	//点击登录按钮触发登录验证方法
	$("#demo1").click(function() {
	
		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
		
		var name = $("#name").val();

		if (name == ""|| name == null) {
			layer.msg('请输小区名称！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#name").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		var data = $("form").serialize();
        var num = isCheckName(data);
        if(num != 0 && num != -1){
            layer.msg('该小区已存在,请重新输入！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#name").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        var address = $("#address").val();
        if (address == ""|| address == null) {
            layer.msg('请输小区地址！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#address").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }

		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/residential/save.action", //路径 
			data : data, //数据，这里使用的是Json格式进行传输 
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
				console.log(result);
				var status = result.status;
				var msg = result.msg;
				layer.msg(msg, {
					icon : 1,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function() {
					close();
				});
			}
		});
		return false;
	});
	
	function close(){
		var index = window.parent.layer.getFrameIndex(window.name); //获取窗口索引
		window.parent.layer.close(index);	
		window.parent.raloadts();
	}
</script>
</html>
