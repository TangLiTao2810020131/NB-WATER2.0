<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>系统水表管理</title>
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
<link rel="stylesheet" href="${ctx}/resources/css/addClass.css" />
<link rel="stylesheet" href="${ctx}/resources/js/css/common.css" />
<style type="text/css">
.demoTable .layui-inline {
	margin-top: 10px;
}

.layui-fluid {
	margin-bottom: 10px;
}
</style>
</head>
<body class="layui-layout-body">
	<!-- 主体内容 -->
	<div class="layui-body">
		<div class="layui-fluid white-bg" style="height:auto;">
			<div class="layui-row  layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-body">
							<div class="layui-form layuiadmin-card-header-auto marginBottom">
								<div class="demoTable">

									<div class="layui-inline">
										<label class="layui-form-label">开始时间:</label>
										<div class="layui-input-inline">
											<input type="text" name="startdate" id="startdate"
												placeholder="请选择开始时间" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-inline">
										<label class="layui-form-label">结束时间:</label>
										<div class="layui-input-inline">
											<input type="text" name="enddate" id="enddate"
												placeholder="请选择结束时间" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-inline">
										<label class="layui-form-label">IMEI号:</label>
										<div class="layui-input-inline">
											<input type="text" name="imei" id="imei"
												placeholder="请输入水表IMEI号" autocomplete="off"
												class="layui-input">
										</div>
									</div>

									<div class="layui-inline">
										<button data-type="reload"
											class="layui-btn layuiadmin-btn-list" lay-submit=""
											lay-filter="LAY-app-contlist-search">
											<i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
										</button>
									</div>
								</div>
							</div>
							<div class="demoTable marginBottom" id="buttons" >
								<div class="layui-btn-group">
									<button class="layui-btn" data-method="addEquipmentr">
										<i class="layui-icon">&#xe654;</i> 新增
									</button>
									<button class="layui-btn" data-method="deleteEquipmentr">
										<i class="layui-icon">&#xe640;</i> 删除
									</button>
									<button class="layui-btn" data-method="open">
										<i class="layui-icon">&#xe609;</i>开阀
									</button>
									<button class="layui-btn" data-method="close">
										<i class="layui-icon">&#xe609;</i>关阀
									</button>
									<button class="layui-btn" data-method=basicNum>
										<i class="layui-icon">&#xe609;</i>设置表读数
									</button>
									<button class="layui-btn" data-method=reportCycle>
										<i class="layui-icon">&#xe609;</i>设置上报周期
									</button>
								</div>
							</div>

							<table class="layui-hide" id="demo" lay-filter="myTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>

<script type="text/html" id="handle">
	<a lay-event="historyData" title="历史数据" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe60e;</i></a>
    <a lay-event="historyCmd" title="历史命令" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe665;</i></a>
    <a lay-event="detail" title="设备信息" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script type="text/javascript">

    var batchid= '${batchid}';
    if (batchid == "" && batchid == null) {
        $("#buttons").hide();
    }

	layui.use('laydate', function() {
		var laydate = layui.laydate;

		//时间控件
		laydate.render({
			elem : '#startdate',
			type:'datetime',
			max : '' + new Date(),
			done : function(value) {
				var str = $("#enddate").val();
				if (str.trim().length>0) {
					var startDate = new Date(value).getTime();
					var endDate = new Date($("#enddate").val()).getTime();
					if (startDate > endDate) {
						layer.msg("开始时间不能大于结束时间!", {
							icon : 0
						},function(){
                            $("#startdate").val("");
						});
					}
				}
			}
		});

		//时间控件
		laydate.render({
			elem : '#enddate',
			type:'datetime',
			max : '' + new Date(),
			done : function(value) {
				var str = $("#startdate").val();
				if (str.trim().length>0) {
					var startDate = new Date($("#startdate").val()).getTime();
					var endDate = new Date(value).getTime();
					if (startDate > endDate) {
						layer.msg("结束时间不能小于开始时间!", {
							icon : 0
						},function(){
                            $("#enddate").val("");
						});
					}
				}
			}
		});
	});

	layui.use('table', function() {
		var table = layui.table;

		//var $ = layui.jquery, layer = layui.layer;
		//展示已知数据
		table.render({
			elem : '#demo',
			cols : [ [ //标题栏
				{
					type : 'checkbox'
				}
				, {
					field : 'imei',
					title : 'IMEI号',
					width : 205,
					align : 'center'
				}
				, {
					field : 'basenum',
					title : '初始表底数',
					width : 205,
					align : 'center'
				}
				, {
					field : 'control',
					title : '阀控',
					width : 205,
					 templet : function(row) {
                            if (row.control == 1) {
                                return "开启";
                            } else if (row.control == 0) {
                                return "关闭";
                            }
                        },
					align : 'center'
				}
				, {
					field : 'control',
					title : '阀控动作',
					width : 205,
					 templet : function(row) {
                            if (row.cstatus == 1) {
                                return "已执行";
                            } else if (row.cstatus == 0) {
                                return "未执行";
                            }
                        },
					align : 'center'
				}
				, {
					field : 'control',
					title : '数据上报',
					width : 205,
					 templet : function(row) {
                            if (row.dstatus == 1) {
                                return "已上报";
                            } else if (row.dstatus == 0) {
                                return "未上报";
                            }
                        },
					align : 'center'
				}, {
					field : 'ctime',
					title : '注册时间',
					width : 205,
					align : 'center'
				}
				, {
					title : '操作',
					width : 105,
					toolbar : '#handle',
					align : 'center'
				}
			] ],
			url : '${ctx}/sysEquipment/listData.action?batchid=${batchid}',
			id : 'myTable',
			page : true, //是否显示分页
			limits : [ 10, 20, 30 ],
			limit : 10, //每页默认显示的数量
			height : 'full-130'
		});

		var $ = layui.$;
		active = {
			reload : function() {
				var startdate = $('#startdate');
				var enddate = $('#enddate');
				var imei = $('#imei');
				//执行重载
				table.reload('myTable', {
					page : {
						curr : 1 //重新从第 1 页开始
					},
					where : {
						startdate : startdate.val(),
						enddate : enddate.val(),
						imei : imei.val()
					}
				});
			},
			addEquipmentr : function() {
				layer.open({
					type : 2,
					title : '新注册水表',
					area : [ '955px', '460px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/sysEquipment/input.action?batchid=${batchid}',
				});
			},
			deleteEquipmentr : function() {
				var checkStatus = table.checkStatus('myTable'),
					data = checkStatus.data;
				//layer.alert(data.length);
				//layer.alert(JSON.stringify(data));
				if (data.length < 1) {
					layer.msg('请选择接入类型！', {
						icon : 7,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					});
					return;
				}
				var uuids = "";
				var deviceids = "";
				for (var i = 0; i < data.length; i++) {
					uuids += data[i].uuid + ",";
					deviceids += data[i].deviceid + ",";
				}
				uuids = uuids.substring(0, uuids.length - 1);
				deviceids = deviceids.substring(0, deviceids.length - 1);
				layer.confirm("请问是否确定删除，删除后不可恢复!", {
					btn : [ "确定", "取消" ] //按钮
				}, function(index) {
					$.ajax({
						type : "POST", //提交方式
						url : "${ctx}/sysEquipment/delete.action", //路径
						data : {
							"uuids" : uuids,
							"deviceids" : deviceids
						}, //数据，这里使用的是Json格式进行传输
						dataType : "json",
						success : function(result) { //返回数据根据结果进行相应的处理
							var status = result.status;
							var msg = result.msg;
							layer.msg(msg, {
								icon : 1,
								time : 2000 //2秒关闭（如果不配置，默认是3秒）
							}, function() {
								layer.close(index);
								location.reload();
							});
						}
					});
				});
			},

			open : function() {
				var checkStatus = table.checkStatus('myTable'),
					data = checkStatus.data;
				if (data.length < 1) {
					layer.msg('请选择设备！', {
						icon : 7,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					});
					return;
				}
				var ids = "";

				for (var i = 0; i < data.length; i++) {
					var isfloodgate = data[i].isFloodgate;
					if ("-1" == isfloodgate) {
						layer.msg('请选择带有阀控水表！', {
							icon : 7,
							time : 2000 //2秒关闭（如果不配置，默认是3秒）
						});
						return;
					}
					ids += data[i].deviceid + ",";
				}

				ids = ids.substring(0, ids.length - 1);

				layer.confirm("请问是否确定发送,开阀命令!", {
					btn : [ "确定", "取消" ] //按钮
				}, function(index) {
					$.ajax({
						type : "POST", //提交方式
						url : '${ctx}/sysEquipment/open.action', //路径
						data : {
							"id" : ids,
						}, //数据，这里使用的是Json格式进行传输
						dataType : "json",
						async : false,
						success : function(result) { //返回数据根据结果进行相应的处理
							var status = result.status;
							var msg = result.msg;
							if (status == 1) {
								layer.msg(msg, {
									icon : 1,
									time : 2000 //2秒关闭（如果不配置，默认是3秒）
								}, function() {
									raloadt();
								});
							} else if (status == 2) {
								layer.msg(msg, {
									icon : 2,
									time : 2000 //2秒关闭（如果不配置，默认是3秒）
								}, function() {
									raloadt();
								});
							}
						}
					});
				});
			},
			close : function() {
				var checkStatus = table.checkStatus('myTable'),
					data = checkStatus.data;
				if (data.length < 1) {
					layer.msg('请选择设备！', {
						icon : 7,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					});
					return;
				}

				var ids = "";
				for (var i = 0; i < data.length; i++) {
					var isfloodgate = data[i].isFloodgate;
					if ("-1" == isfloodgate) {
						layer.msg('请选择带有阀控水表！', {
							icon : 7,
							time : 2000 //2秒关闭（如果不配置，默认是3秒）
						});
						return;
					}
					ids += data[i].deviceid + ",";
				}

				ids = ids.substring(0, ids.length - 1);

				layer.confirm("请问是否确定发送关阀命令!", {
					btn : [ "确定", "取消" ] //按钮
				}, function(index) {
					$.ajax({
						type : "POST", //提交方式
						url : '${ctx}/sysEquipment/close.action', //路径
						data : {
							"id" : ids,
						}, //数据，这里使用的是Json格式进行传输
						dataType : "json",
						async : false,
						success : function(result) { //返回数据根据结果进行相应的处理
							var status = result.status;
							var msg = result.msg;
							if (status == 1) {
								layer.msg(msg, {
									icon : 1,
									time : 2000 //2秒关闭（如果不配置，默认是3秒）
								}, function() {
									raloadt();
								});
							} else if (status == 2) {
								layer.msg(msg, {
									icon : 2,
									time : 2000 //2秒关闭（如果不配置，默认是3秒）
								}, function() {
									raloadt();
								});
							}
						}
					});
				});
			},
			reportCycle : function() {
				var checkStatus = table.checkStatus('myTable'),
					data = checkStatus.data;
				if (data.length < 1) {
					layer.msg('请选择设备！', {
						icon : 7,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					});
					return;
				}
				var ids = "";

				for (var i = 0; i < data.length; i++) {
					ids += data[i].deviceid + ",";
				}
				ids = ids.substring(0, ids.length - 1);

				var index = layer.open({
					type : 2,
					title : '设置上报周期',
					area : [ '500px', '300px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/sysEquipment/toReportCycle.action?ids=' + ids
				});
			},
			basicNum : function() {
				var checkStatus = table.checkStatus('myTable'),
					data = checkStatus.data;
				if (data.length < 1) {
					layer.msg('请选择设备！', {
						icon : 7,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					});
					return;
				}
				var ids = "";

				for (var i = 0; i < data.length; i++) {
					var ismagnetism = data[i].ismagnetism;
					ids += data[i].deviceid + "*" + ismagnetism + ",";
				}
				ids = ids.substring(0, ids.length - 1);

				var index = layer.open({
					type : 2,
					title : '设置表读数',
					area : [ '500px', '300px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/sysEquipment/toBasicNum.action?ids=' + ids
				});
			}
		};

		//监听工具条
		table.on('tool(myTable)', function(obj) {
			var data = obj.data;
			if (obj.event === 'detail') {
				layer.open({
					type : 2,
					title : '查看设备信息',
					area : [ '600px', '500px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/sysEquipment/device-info.action?deviceid=' + data.deviceid
				});
			}
			if (obj.event == 'historyData') {
				parent.layer.open({
					title : '查看历史上报数据记录',
					type : 2,
					area : [ '800px', '600px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/sysreadlog/history-sysreadlog-list.action?deviceid=' + data.deviceid
				});
			}
			if (obj.event == 'historyCmd') {
				parent.layer.open({
					title : '查看历史下发命令记录',
					type : 2,
					area : [ '900px', '600px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/syscommandsendlog/history-syscmd-list.action?deviceid=' + data.deviceid
				});
			}
		});

		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		$('#buttons .layui-btn').on('click', function() {
			var othis = $(this),
				method = othis.data('method');
			active[method] ? active[method].call(this, othis) : '';
		});
	});

	function raloadt() {
		var table = layui.table;
		table.reload('myTable');
	}

	//僵硬控制表格高度
	$(function() {
		var tree_height = $(window).height() + 'px';
		$(".layui-fluid ").css("min-height", tree_height);



		$(window).resize(function() {
			var tree_height = $(window).height() + 'px';
			$(".layui-fluid ").css("min-height", tree_height);


		});
	});
</script>