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
<title>手动结算操作页面</title>
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
					<legend>手动结算</legend>
				</fieldset>
				<form class="layui-form" method="post">
					<input type="hidden" name="id" value="${meterread.meterreadid }" />
					<input type="hidden" name="watermeterid" value="${meterread.equipmentid }" />
					<input type="hidden" name="xqname" value="${xqname}" />
					<input type="hidden" name="watermetercode" value="${meterread.watermetercode}" />
					<input type="hidden" name="building" value="${building}" />
					<input type="hidden" name="doornum" value="${doornum}" />
<%-- 
					<div class="layui-form-item">
						<label class="layui-form-label">水表IMEI号:</label>
						<label class="layui-form-mid">${meterread.watermetercode }</label>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">最新抄表读数:</label>
						<label class="layui-form-mid">${meterread.value }</label>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">最新抄表日期:</label>
						<label class="layui-form-mid">${meterread.optiontime }</label>						
					</div> --%>

					<div class="layui-form-item">
						<label class="layui-form-label">结算月份:</label>
						<div class="layui-input-block">
							<input type="text" name="balancemonth" id="balancemonth" placeholder="---请选择结算月份---"   autocomplete="off" class="layui-input" readonly="readonly">
						</div>
					</div>

<%-- 					<div class="layui-form-item">
						<label class="layui-form-label">结算人员:</label>
						<div class="layui-input-block">
							<select name="optionuser" id="optionuser">
								<option value="">---请选择结算人员---</option>
								<c:forEach items="${userList}" var="user">
									<option value="${user.id}"  >${user.username}</option>
								</c:forEach>
							</select>
						</div>
					</div> --%>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">描述</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入内容" name="describe" id="describe"
								style="resize:none;height:270px;" class="layui-textarea"></textarea>
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
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#balancemonth',type: 'month'
            
        });
    });
    
    function checkBalanceDate(data){
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/balance/checkBalanceDate.action", //路径
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
        var balancemonth=$("#balancemonth").val();
        if (balancemonth == "" || balancemonth == null) {
            layer.msg('请选择结算月份！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#balancemonth").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;
        }

		var data = $("form").serialize();
/*         var num = checkBalanceDate(data);
        if(num != 0 && num != -1){
            layer.msg('该月份已经结算,请重新选择！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#balancemonth").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        } */
		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/balance/save.action", //路径
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
					layer.close(layer.index);
					window.parent.location.reload();
				});
			},
			error : function(result) {
				layer.msg("操作失败,请选择管理员!", {
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
		window.parent.raloadt();
	}
</script>
</html>
