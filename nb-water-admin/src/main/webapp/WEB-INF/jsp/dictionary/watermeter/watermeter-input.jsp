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

<body>
	<div class="layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>水表型号信息</legend>
				</fieldset>
				<form class="layui-form" method="post">
					<input type="hidden" name="id" value="${watermeter.id }" />


					<div class="layui-form-item">
						<label class="layui-form-label">水表型号:</label>
						<div class="layui-input-block">
							<input type="text" name="type" id="type"
								value="${watermeter.type }" lay-verify="title"
								autocomplete="off" placeholder="请输入水表型号" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">水表口径:</label>
						<div class="layui-input-block">
							<input type="text" name="caliber" id="caliber"
								value="${watermeter.caliber }" lay-verify="title"
								autocomplete="off" placeholder="请输入水表口径" class="layui-input">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label" >有磁无磁:</label>
						<div class="layui-input-block">
							<input name="magnetism" lay-filter="rselect" type="radio" value="1" title="有" <c:if test="${watermeter.magnetism == 1 }">checked="checked"</c:if> />
							<input name="magnetism" lay-filter="rselect" type="radio" value="0" title="无" <c:if test="${watermeter.magnetism == 0 }">checked="checked"</c:if> />	
						</div>
					</div>
					
					<div class="layui-form-item" id = "controldiv" style="display: none;" >
						<label class="layui-form-label">有无阀控:</label>
						<div class="layui-input-block">
							<input name="control" type="radio" value="1" title="有" <c:if test="${watermeter.control == 1 }">checked="checked"</c:if> />
							<input name="control" type="radio" value="0" title="无" <c:if test="${watermeter.control == 0 }">checked="checked"</c:if> />	
						</div>
					</div>
<%-- 					<div class="layui-form-item">
						<label class="layui-form-label">水表倍率:</label>
						<div class="layui-input-block">
							<input type="text" name="ratio" id="ratio"
								value="${watermeter.ratio }" lay-verify="title"
								autocomplete="off" placeholder="请输入水表倍率" class="layui-input">
						</div>
					</div> --%>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">描述</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入内容" name="describe" id="describe"
								style="resize:none" class="layui-textarea">${watermeter.describe }</textarea>
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
		var magnetism = '${watermeter.magnetism}';
        if(magnetism == 1){
            $("#controldiv").show();
            $("input[name='control'][value='0']").attr("checked",true);
        }
        if(magnetism == 0){
            $("#controldiv").hide();
            $("input[name='control'][value='0']").attr("checked",true);
        }
    }); 
    

    layui.use([ 'layer', 'form' ], function() {
     var layer = layui.layer,form = layui.form;
		
		form.on('radio(rselect)', function(data){
			if(data.value == 1){
				$("#controldiv").show();
			}
			if(data.value == 0){
				$("#controldiv").hide();
                $("input[name='control'][value='0']").attr("checked",true);
			}
		});      
    });
	//点击登录按钮触发登录验证方法
	$("#demo1").click(function() {
	
		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
		
		var type = $("#type").val();
		var caliber = $("#caliber").val();
		if (type == ""|| type == null) {
			layer.msg('请输入水表类型！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#type").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		if (caliber == ""|| caliber == null) {
			layer.msg('请输入水表口径！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#caliber").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		var data = $("form").serialize();
		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/watermeter/save.action", //路径 
			data : data, //数据，这里使用的是Json格式进行传输 
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
                var status = result.status;
                var msg = result.msg;
                layer.msg(msg, {
                    icon : 1,
                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function() {
                    close();
                });
			},
			error : function(result) {
				layer.msg("操作失败,请选择管理员!", {
					icon : 5,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function() {
					layer.close(layer.index);
					window.parent.location.reload();
				});
			}
		});
		return false;
	});

    function close(){
        var index = window.parent.layer.getFrameIndex(window.name); //获取窗口索引
        window.parent.layer.close(index);
        window.parent.raloadt();
    }
</script>
</html>
