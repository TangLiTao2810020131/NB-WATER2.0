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

<body style="background-color:white;">
	<div class="layui-fluid" style="background-color:white;">
		<div class="layui-row  layui-col-space15" style="padding-top:30px;">

			<form class="layui-form" method="post">
				<input type="hidden" name="id" id="id" value="${equipment.id }" />
				<input type="hidden" name="type" id="type" />
				<input type="hidden" name="deviceid" value="${equipment.deviceid }"  />
				<input type="hidden" name="control" id="control" />
				<div class="layui-form-item" id="div-pca">

				<div class="layui-form-item" id="div-adress">
					<div style="width: 300;float: left;">
						<label class="layui-form-label">小区:</label>
						<div class="layui-input-block">
							<select id="residential" name="residential" lay-filter="rselect">
								<option value="">---请选择小区---</option>
								<c:forEach items="${rlist}" var="r">
									<option value="${r.id}" <c:if test="${r.id == residentialId}">selected="selected"</c:if> >${r.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div style="width: 300;float: left;">
						<label class="layui-form-label">栋:</label>
						<div class="layui-input-block">
							<select id="building" name="building" lay-filter="bselect">
								<option value="">---请选择小区栋号---</option>
								<c:forEach items="${bList}" var="b">
									<option value="${b.id}" <c:if test="${b.id == buildingId}">selected="selected"</c:if> >${b.building}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div style="width: 300;float: left;">
						<label class="layui-form-label">门牌号:</label>
						<div class="layui-input-block">
							<select id="doornumid" name="doornumid" lay-filter="dselect">
								<option value="">---请选择门牌号---</option>
								<c:forEach items="${dList}" var="d">
									<option value="${d.id}" <c:if test="${d.id == equipment.doornumid}">selected="selected"</c:if> >${d.doornum}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">水表IMEI号:</label>
					<div class="layui-input-block" id = "div-three">
						<input type="text" name="watermetercode" id="watermetercode" value="${equipment.watermetercode }"
						<c:if test="${equipment.watermetercode != null}"> style="cursor:not-allowed" disabled="disabled" </c:if>
							   lay-verify="title" autocomplete="off" placeholder="请输入水表IMEI号"
							   class="layui-input">
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">水表型号:</label>
					<div class="layui-input-block">
						<select name="watermeterid" id="watermeterid" lay-filter="waterselect">
							<option value="" title="1">---请选择水表型号---</option>
							<c:forEach items="${waterList}" var="water">
								<option value="${water.id}" title="${water.control}" <c:if test="${water.id ==equipment.watermeterid }">selected="selected"</c:if> >${water.type}(口径:${water.caliber},<c:if test="${water.control == 1}">有</c:if><c:if test="${water.control == 0}">无</c:if>阀控,<c:if test="${water.magnetism == 1}">有</c:if><c:if test="${water.magnetism == 0}">无</c:if>磁)</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div id="controlDiv" class="layui-form-item">
					<label class="layui-form-label">阀门状态:</label>
					<div class="layui-input-block">
						<input type="checkbox"
							   <c:if test="${equipment.status==1}">checked="checked"</c:if>
							   name="status" value="1" lay-skin="switch"
							   lay-filter="switchTest" lay-text="ON|OFF">
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">水表底数:</label>
					<div class="layui-input-block">
						<input type="text" name="basenum" id="basenum"
							   value="${equipment.basenum }" lay-verify="title"
							   autocomplete="off" placeholder="请输入水表底数" class="layui-input">
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">装表员:</label>
					<div class="layui-input-block">
						<select name="optionuser" id="optionuser">
							<option value="">---请选择装表人员---</option>
							<c:forEach items="${userList}" var="user">
								<option value="${user.id}" <c:if test="${user.id == equipment.optionuser }">selected="selected"</c:if>  >${user.realname}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label">安装日期:</label>
					<div class="layui-input-block">
						<input type="text" name="installdate" id="installdate" placeholder="请选择安装日期" value="${equipment.installdate }" autocomplete="off" class="layui-input" readonly="readonly">
					</div>
				</div>

				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">描述</label>
					<div class="layui-input-block">
							<textarea placeholder="请输入内容" name="describe" id="describe"
									  style="resize:none" class="layui-textarea">${equipment.describe }</textarea>
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
</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
		src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">



    var id=$("#id").val();
    if (id != "" && id != null) {
        $("#div-adress").attr("style","display:none");
        $("#div-three").html("<label class=\"layui-form-mid\">${equipment.watermetercode }</label>");
    }

    $(function() {
        $("#code").focus();
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;
 		var date = new Date();
        //时间控件
        laydate.render({
            elem: '#installdate',type: 'datetime',
             max:'' + date
        });
    })

    function isCheckIMEI(data){
        var num;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/equipment/isCheckIMEI.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
    }
    
   	function isCheckWaterNum(data){
        var fig;
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/equipment/isCheckWaterNum.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                fig = result;
            }
        });
        return fig;
    }
    
    //点击保存按钮触发登录验证方法
    $("#demo1").click(function() {
        var type = 'add';
        var id = $("#id").val();
        if(id != null && id != ''){
            type = 'edit';

        }
        $("#type").val(type);
        $("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
        
        var data = $("form").serialize();
        
        var fig = isCheckWaterNum(data);
        if(fig == false){
            layer.msg('尊敬的客户，你添加的水表设备已经上线，请联系管理员！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#demo1").attr('disabled', false);
            });
            return;
        }

        var watermetercode = $("#watermetercode").val();
        if (watermetercode == "" || watermetercode == null) {
            layer.msg('请输入水表IMEI号！', {
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
            layer.msg('水表IMEI号是15位数字！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#watermetercode").focus();
                $("#demo1").attr('disabled', false);
            });
            return;

        }

        var num = isCheckIMEI(data);
        if(num != 0 && num != -1){
            layer.msg('该设备IMEI号已注册,请输入其他IMEI号！', {
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
            layer.msg('请选择水表型号！', {
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
            layer.msg('请填入水表底数！', {
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
            layer.msg('水表底数是整数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#basenum").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        if (basenum < 0 || basenum>90000) {
            layer.msg('水表底数只能输入0到90000之间的整数！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#basenum").focus();
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
        var installdate = $("#installdate").val();
        if (installdate == "" || installdate == null) {
            layer.msg('请选择安装日期！', {
                icon : 7,
                time : 2000 //2秒关闭（如果不配置，默认是3秒）
            }, function() {
                $("#installdate").focus();
                $("#demo1").attr('disabled', false);
            });
            return;
        }
        $.ajax({
            type : "POST", //提交方式
            url : "${ctx}/equipment/save.action", //路径
            data : data, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                console.log(result);
                var status = result.status;
                var msg = result.msg;
                if(status == 1){
                	layer.msg(msg, {
	                    icon : 1,
	                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
	                }, function() {
	                    var index = parent.layer.getFrameIndex(window.name);
	                    parent.layer.close(index); 
	                    window.parent.raloadts();
	                });
                }
                if(status == 3){
                	layer.msg(msg, {
	                    icon : 2,
	                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
	                }, function() {
	                    var index = parent.layer.getFrameIndex(window.name);
	                    parent.layer.close(index); 
	                    window.parent.raloadts();
	                });
                }
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

    layui.use([ 'layer', 'form' ], function() {
        var layer = layui.layer,
            form = layui.form;
        form.on('select(pselect)', function(data) {
            var pid = (data.value);
            $.ajax({
                type : 'POST',
                url : '${ctx}/city/getCity.action',
                data : {
                    father : pid
                },
                dataType : 'json',
                success : function(data) {
                    $("#doornumid").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornumid").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $("#residential").html("");
                    var xqoption = $("<option>").val("").text("---请选择小区---");
                    $("#residential").append(xqoption);
                    form.render('select');
                    $("#area").html("");
                    var qxoption = $("<option>").val("").text("---请选择区(县)---");
                    $("#area").append(qxoption);
                    form.render('select');
                    $("#city").html("");
                    var soption = $("<option>").val("").text("---请选择市区---");
                    $("#city").append(soption);
                    form.render('select');
                    $.each(data, function(key, val) {
                        var option1 = $("<option>").val(val.cityid).text(val.city);
                        $("#city").append(option1);
                        form.render('select');
                    });

                }
            });
        });

        form.on('select(cselect)', function(data) {
            var cid = (data.value);
            $.ajax({
                type : 'POST',
                url : '${ctx}/area/getArea.action',
                data : {
                    father : cid
                },
                dataType : 'json',
                success : function(data) {
                    $("#doornumid").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornumid").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $("#residential").html("");
                    var xqoption = $("<option>").val("").text("---请选择小区---");
                    $("#residential").append(xqoption);
                    form.render('select');
                    $("#area").html("");
                    var qxoption = $("<option>").val("").text("---请选择区(县)---");
                    $("#area").append(qxoption);
                    form.render('select');
                    $.each(data, function(key, val) {
                        var option1 = $("<option>").val(val.id).text(val.area);
                        $("#area").append(option1);
                        form.render('select');
                    });
                }
            });
        });

        form.on('select(aselect)', function(data) {
            var aid = (data.value);
            $.ajax({
                type : 'POST',
                url : '${ctx}/residential/getResidential.action',
                data : {
                    areaid : aid
                },
                dataType : 'json',
                success : function(data) {
                    $("#doornumid").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornumid").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $("#residential").html("");
                    var xqoption = $("<option>").val("").text("---请选择小区---");
                    $("#residential").append(xqoption);
                    form.render('select');
                    $.each(data, function(key, val) {
                        var option1 = $("<option>").val(val.id).text(val.name);
                        $("#residential").append(option1);
                        form.render('select');
                    });
                }
            });
        });

        form.on('select(rselect)', function(data) {
            var residentiaid = (data.value);
            $.ajax({
                type : 'POST',
                url : '${ctx}/building/getBuilding.action',
                data : {
                    residentialid : residentiaid
                },
                dataType : 'json',
                success : function(data) {
                    $("#doornumid").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornumid").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $.each(data, function(key, val) {
                        var option1 = $("<option>").val(val.id).text(val.building);
                        $("#building").append(option1);
                        form.render('select');
                    });
                }
            });
        });

        form.on('select(bselect)', function(data) {
            var buildingid = (data.value);
            $.ajax({
                type : 'POST',
                url : '${ctx}/door/getEquipmentDoor.action',
                data : {
                    buildingid : buildingid
                },
                dataType : 'json',
                success : function(data) {
                    $("#doornumid").html("");
                    var option = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornumid").append(option);
                    form.render('select');
                    $.each(data, function(key, val) {
                        var option1 = $("<option>").val(val.id).text(val.doornum);
                        $("#doornumid").append(option1);
                        form.render('select');
                    });
                }
            });
        });


        form.on('select(waterselect)', function(data) {
            var control = (data.elem[data.elem.selectedIndex].title);
            $("#control").val(control);
            if(control == 0){
                $("#controlDiv").hide();
            }else{
                $("#controlDiv").show();
            }
        });
    });
</script>
</html>
