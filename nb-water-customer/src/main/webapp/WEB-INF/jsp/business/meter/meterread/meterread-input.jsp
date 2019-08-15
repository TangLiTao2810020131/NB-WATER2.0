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
<title>手动抄表操作页面</title>
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
					<legend>手动抄表</legend>
				</fieldset>
				<form class="layui-form" method="post">
					<input type="hidden" name="id" value="${meterread.meterreadid }" />
					<input type="hidden" name="watermeterid" value="${meterread.equipmentid }" />
					<input type="hidden" name="xqname" value="${xqname}" />
					<input type="hidden" name="doornum" value="${doornum }" />
					<input type="hidden" name="building" value="${building}" />

					<div class="layui-form-item">
						<label class="layui-form-label">水表IMEI号:</label>
						<div class="layui-input-block">
							<%--<input type="text" name="watermetercode" id="watermetercode"
								value="${meterread.watermetercode }" lay-verify="title"
								autocomplete="off" readonly class="layui-input">--%>
								<label class="layui-form-mid" name="watermetercode" id="watermetercode">${meterread.watermetercode }</label>
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">最新表数:</label>
						<div class="layui-input-block">
							<input type="text" name="value" id="value" lay-verify="title"
								   autocomplete="off" placeholder="请输入最新表数" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">抄表日期:</label>
						<div class="layui-input-block">
							<input type="text" name="optiontime" id="optiontime" placeholder="请选择抄表日期"   autocomplete="off" class="layui-input" readonly="readonly">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">抄表人员:</label>
						<div class="layui-input-block">
							<select name="optionuser" id="optionuser">
								<option value="">---请选择抄表人员---</option>
								<c:forEach items="${userList}" var="user">
									<option value="${user.id}"  >${user.realname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">描述</label>
						<div class="layui-input-block">
							<textarea placeholder="请输入内容" name="describe" id="describe"
								style="resize:none" class="layui-textarea"></textarea>
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
            elem: '#optiontime',
            type: 'datetime'
            
        });
    });
	//点击登录按钮触发登录验证方法
	$("#demo1").click(function() {
	
		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
		var value=$("#value").val();
        var re=/^[0-9]*$/;
        if (!re.test(value)){
            layer.msg('最新表数是整数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#value").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        if (value==0 || value>90000) {
            layer.msg('最新表数只能输入1到90000之间的数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#value").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;

        }
        var optiontime=$("#optiontime").val();
        if (optiontime == "" || optiontime == null) {
            layer.msg('请选择抄表日期！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#optiontime").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;
        }
        var optionuser=$("#optionuser").val();
        if (optionuser == "" || optionuser == null) {
            layer.msg('请选择抄表人员！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#optionuser").focus();
                $("#demo1").attr('disabled', false);
            });
            return false;
        }

		

		var data = $("form").serialize();
		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/meterread/save.action", //路径
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
