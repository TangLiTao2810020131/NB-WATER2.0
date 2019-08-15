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
<title>水表设备操作页面</title>
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
	<div class="layui-body layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 12px;">
					<legend>定时配置</legend>
				</fieldset>
				<form class="layui-form" method="post" style="width:400px; margin:0 auto;">
					<input type="hidden" name="jobid" id="jobid" value="${job.jobid}" />

					<div class="layui-form-item">
						<label class="layui-form-label">任务名称:</label> 
						<label class="layui-form-mid">自动结算</label>
					</div>

					<div id="controlDiv" class="layui-form-item">
						<label class="layui-form-label">执行日期:</label>
						<div class="layui-input-block" style="width: 140px;">
							<select id="executordate" name="executordate">
								<option value="0">请选择执行日期</option>
								<c:forEach items="${list}" var="str">
									<option value="${str}" <c:if test="${job.executordate == str}">selected="selected" </c:if> >每月${str}号执行</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div id="controlDiv" class="layui-form-item">
						<label class="layui-form-label">执行状态:</label>
						<label class="layui-form-mid">
							<c:if test="${job.jobstatus == '1'}">激活中</c:if>
							<c:if test="${job.jobstatus == '0'}">休眠</c:if>
							<c:if test="${job.jobstatus == ''}">暂无</c:if>
						</label>
					</div>
					
					<div id="controlDiv" class="layui-form-item">
						<label class="layui-form-label">执行结果:</label>
						<label class="layui-form-mid">
							<c:if test="${empty joblog}">未执行</c:if>
							<c:if test="${not empty joblog}">
								<input type="hidden" name="issuccess" id="issuccess" value="${joblog.issuccess}" />
								<c:if test="${joblog.issuccess == 1}">成功</c:if>
								<c:if test="${joblog.issuccess == 0}">失败</c:if>
							</c:if>
						</label>
					</div>					

					<div id="controlDiv" class="layui-form-item">
						<label class="layui-form-label">任务:</label> 
						<label class="layui-form-mid"> 
							<c:if test="${job.jobstatus == '1'}"> <a class="layui-btn layui-btn-primary layui-btn-xs"  href="javascript:;" onclick="changeJobStatus('${job.jobid}','stop')">停止</a></c:if>
							<c:if test="${job.jobstatus == '0'}"> <a class="layui-btn layui-btn-primary layui-btn-xs" href="javascript:;" onclick="changeJobStatus('${job.jobid}','start')">开启</a></c:if>
							<c:if test="${job.jobstatus==''}">暂无</c:if>
						</label>
					</div>

					<div class="layui-form-item">
						<div class="layui-input-block">
							<button type="button" class="layui-btn" id="demo1"
								lay-filter="demo1">保存</button>
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


	//点击登录按钮触发登录验证方法
	$("#demo1").click(function() {

		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
		
		var jobStatus = "${job.jobstatus}";
		if(jobStatus == 1){
			layer.msg('定时任务已启动，请先停止当前任务！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#demo1").attr('disabled', false);
			});
			return;			
		}

		var issuccess = $("#issuccess").val();
		if(issuccess == 1){
			layer.msg('定时任务已执行完毕，请次月在重新设置执行时间！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#demo1").attr('disabled', false);
			});
			return;			
		}

		var executordate = $("#executordate").val();
		if (executordate == "0") {
			layer.msg('请选择执行时间！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#executordate").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		var data = $("form").serialize();
		$.ajax({
			type : "POST", //提交方式
			url : "${ctx}/timertask/save.action", //路径
			data : data, //数据，这里使用的是Json格式进行传输
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
				console.log(result);
				var status = result.status;
				var msg = result.msg;
                layer.alert(msg, {
                   	icon: 6
                   	},function(){
                   	window.location.href = "${ctx}/timertask/info.action";
                });				
			},
			error : function(result) {
				layer.msg("操作失败,请选择管理员!", {
					icon : 1,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function() {
					layer.close(layer.index);
					window.parent.location.reload();
				});
			}
		});
		return false;
	});
	function changeJobStatus(jobId, type) {
    	$.ajax({
				type : "POST",
				async : false,
				dataType : "JSON",
				cache : false,
				url : "${ctx}/timertask/changeJobStatus.action",
				data : {
					jobId : jobId,
					cmd : type
				},
				success : function(result) {
                    var status = result.status;
                    var msg = result.msg;
                    layer.alert(msg, {
                    	icon: 6
                    	},function(){
                    	window.location.href = "${ctx}/timertask/info.action";
                    });
				}//end-callback
			});//end-ajax
    }
</script>
</html>
