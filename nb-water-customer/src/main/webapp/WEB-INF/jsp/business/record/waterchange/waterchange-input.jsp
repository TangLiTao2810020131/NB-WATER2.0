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
<title>换表操作页面</title>
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

<body style="background-color:white;">
	<div class="layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<form class="layui-form" method="post">
					<input type="hidden" name="equipmentid" value="${equipment.id }" />
					<input type="hidden" name="ownerid" value="${owner.id }" />
					<input type="hidden" name="oldwatermetercode" value="${equipment.watermetercode }" />
					<!-- 卡片一 -->
					<div class="layui-card">
						<div class="layui-card-header">用户信息</div>
						<div class="layui-card-body" style="float: none;height: 30px;">
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">户名:</label>
									<label class="layui-form-mid">${owner.username }</label>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">户号:</label> 
									<label class="layui-form-mid">${owner.useraccount }</label>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">详细地址:</label> 
									<label class="layui-form-mid">${owner.address }</label>
								</div>
							</div>
						</div>
					</div>

					<!-- 卡片二 -->
					<div class="layui-card">
						<div class="layui-card-header">旧水表信息</div>
						<div class="layui-card-body" style="float: none;height: 40px;">
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">水表IMEI号:</label> 
									<label class="layui-form-mid">${equipment.watermetercode }</label>
									<input type="hidden" id="oldwatermetercode" name="oldwatermetercode" value="${equipment.watermetercode }"/>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">最后读数:</label>
									<div class="layui-input-block">
										<input type="text" lay-verify="title" autocomplete="off" id="oldbasenum" name="oldbasenum"
											placeholder="请输入最后读数" class="layui-input">
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- 卡片三 -->
					<div class="layui-card">
						<div class="layui-card-header">新水表信息</div>
						<div class="layui-card-body" style="float: none;height: 200px;">
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">水表IMEI号:</label>
									<div class="layui-input-block">
										<input type="text" lay-verify="title" autocomplete="off" id="watermetercode" name="watermetercode"
											placeholder="请输入水表IMEI号" class="layui-input">
									</div>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">水表型号:</label>
									<div class="layui-input-block">
										<select name="watermeterid" id="watermeterid">
											<option value="">---请选择水表型号---</option>
											<c:forEach items="${waterList}" var="water">
												<option value="${water.id}">${water.type}(口径:${water.caliber},<c:if test="${water.control == 1}">有</c:if><c:if test="${water.control == 0}">无</c:if>阀控,<c:if test="${water.magnetism == 1}">有</c:if><c:if test="${water.magnetism == 0}">无</c:if>磁)</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">当前底数:</label>
									<div class="layui-input-block">
										<input type="text" lay-verify="title" autocomplete="off" id="basenum" name="basenum"
											placeholder="请输入当前底数" class="layui-input">
									</div>
								</div>
							</div>

							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">读表时间:</label>
									<div class="layui-input-block">
										<input type="text" name="changtime" id="changtime"
											placeholder="请选择读表时间" autocomplete="off" class="layui-input"
											readonly="readonly">
									</div>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">装表员:</label>
									<div class="layui-input-block">
										<select name="optionuser" id="optionuser">
											<option value="">---请选择换表员---</option>
											<c:forEach items="${userList}" var="user">
												<option value="${user.id}">${user.realname}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div style="float: left;">
								<div class="layui-form-item">
									<label class="layui-form-label">换表原因:</label>
									<div class="layui-input-block">
										<input type="text" lay-verify="title" autocomplete="off"  name="changreason" id="changreason"
											placeholder="请输入换表原因" class="layui-input">
									</div>
								</div>
							</div>
							<div style="float: none;">
								<div class="layui-form-item">
									<label class="layui-form-label">备注:</label>
									<div class="layui-input-block">
										<textarea placeholder="请输入内容" name="describe" id="describe"
											style="resize:none" class="layui-textarea"></textarea>
									</div>
								</div>
							</div>

							<div class="layui-form-item">
								<div class="layui-input-block">
									<button type="button" class="layui-btn" id="demo1"
											lay-filter="demo1">提交</button>
								</div>
							</div>
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

	layui.use('laydate', function() {
		var laydate = layui.laydate;

 		var date = new Date();
        //时间控件
        laydate.render({
            elem: '#changtime',type: 'datetime',
             max:'' + date
        });
	})

	//点击登录按钮触发登录验证方法
	$("#demo1").click(function() {

		$("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
		
        var oldbasenum = $("#oldbasenum").val();
        if (oldbasenum == "" || oldbasenum == null) {
            layer.msg('请填入旧水表最后读数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#oldbasenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        var re=/^[0-9]*$/;
        if (!re.test(oldbasenum)){
            layer.msg('旧水表底数是整数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#oldbasenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        if (oldbasenum==0 || oldbasenum>90000) {
            layer.msg('旧水表底数只能输入1到90000之间的数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#oldbasenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;

        }


		var watermetercode = $("#watermetercode").val();
		if (watermetercode == "" || watermetercode == null) {
			layer.msg('请输入新水表IMEI号！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#watermetercode").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
		
		var oldwatermetercode = $("#oldwatermetercode").val();
		if (watermetercode == oldwatermetercode) {
			layer.msg('新水表IMEI号不能与旧水表IMEI号一致！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			}, function() {
				$("#watermetercode").focus();
				$("#demo1").attr('disabled', false);
			});
			return;
		}
        var re=/^\d{15}$/;

        if (!re.test(watermetercode)){
            layer.msg('新水表IMEI号是15位数字！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#watermetercode").focus();
                $("#demo1").attr('disabled', false);
            });
            return;

        }
        var watermeterid = $("#watermeterid").val();
        if (watermeterid == "" || watermeterid == null) {
            layer.msg('请选择新水表型号！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#watermeterid").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        var basenum = $("#basenum").val();
        if (basenum == "" || basenum == null) {
            layer.msg('请填入新水表底数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#basenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        var re=/^[0-9]*$/;
        if (!re.test(basenum)){
            layer.msg('新水表底数是整数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#basenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        if (basenum==0 || basenum>90000) {
            layer.msg('新水表底数只能输入1到90000之间的整数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#basenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;

        }

        var changtime = $("#changtime").val();
        if (changtime == "" || changtime == null) {
            layer.msg('请选择安装日期！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#changtime").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        var optionuser = $("#optionuser").val();
        if (optionuser == "" || optionuser == null) {
            layer.msg('请选择装表员！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#optionuser").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        var changreason = $("#changreason").val();
        if (changreason == "" || changreason == null) {
            layer.msg('请输入换表原因！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#changreason").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }


		var data = $("form").serialize();
		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/waterchange/save.action", //路径 
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
	                    var index = parent.layer.getFrameIndex(window.name);
	                    parent.layer.close(index); 
	                    window.parent.raloadts();
				});
			},
			error : function(result) {
				layer.msg("操作失败,请选择管理员!", {
					icon : 1,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function() {
	                    var index = parent.layer.getFrameIndex(window.name);
	                    parent.layer.close(index); 
	                    window.parent.raloadts();
				});
			}
		});
		return false;
	});


</script>
</html>
