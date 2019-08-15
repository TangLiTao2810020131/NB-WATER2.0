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
<title>楼栋用水量列表数据页面</title>
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
<link rel="stylesheet"
	href="${ctx}/resources/layui_ext/dtree/dtree.css " />
<link rel="stylesheet"
	href="${ctx}/resources/layui_ext/dtree/font/iconfont.css" />
</head>
<body>
	<div class="layui-body layui-bg-white">
		<div class="layui-fluid">
			<div class="layui-row  layui-col-space15">
				<div id="nav1">
					<!-- 顶部切换卡 -->
					<!--ul的id要和lay-filter一致-->
					<div class="layui-tab" lay-filter="main_tab1">
						<ul id="main_tab1" class="layui-tab-title">
							<li lay-id="0" id="liDay" class="layui-this">每日用水量</li>
							<li lay-id="1" id="liYear">每月用水量</li>
						</ul>
						<div class="layui-tab-content">
							<div class="layui-tab-item layui-show">

								<div class="layui-form layuiadmin-card-header-auto marginBottom">
									<div class="demoTable" style="float: right;">

										<div class="layui-inline">
											<label class="layui-form-label">开始时间:</label>
											<div class="layui-input-inline">
												<input type="text" name="startdateday" id="startdateday"
													placeholder="请选择开始时间" autocomplete="off"
													class="layui-input">
											</div>
										</div>
										<div class="layui-inline">
											<label class="layui-form-label">结束时间:</label>
											<div class="layui-input-inline">
												<input type="text" name="enddateday" id="enddateday"
													placeholder="请选择结束时间" autocomplete="off"
													class="layui-input">
											</div>
										</div>

										<div class="layui-inline" style="margin-left:10px;">
											<button data-type="reloadDay" id="reloadDay"
												class="layui-btn layuiadmin-btn-list" lay-submit=""
												lay-filter="LAY-app-contlist-search">
												<i
													class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
											</button>
											<button data-type="download" onclick="downloadDay()"
												class="layui-btn layuiadmin-btn-list" lay-submit=""
												lay-filter="LAY-app-contlist-download-circle">
												<i
													class="layui-icon layui-icon-download-circle layuiadmin-button-btn">导出</i>
											</button>
										</div>
									</div>
								</div>
								<br /> <br /> <br />
								<div id="DayWaterTB" style="width: 1150px;height:400px;">
									<table class="layui-hide" id="demo" lay-filter="myTable"></table>
								</div>
							</div>

							<div class="layui-tab-item">
								<div class="layui-form layuiadmin-card-header-auto marginBottom">
									<div class="demoTable" style="float: right;">

										<div class="layui-inline">
											<label class="layui-form-label">年份:</label>
											<div class="layui-input-inline">
												<input type="text" name="year" id="year"
													placeholder="请选择结束年份" autocomplete="off"
													class="layui-input">
											</div>
										</div>

										<div class="layui-inline" style="margin-left:10px;">
											<button data-type="reloadYear" id="reloadYear"
												class="layui-btn layuiadmin-btn-list" lay-submit=""
												lay-filter="LAY-app-contlist-search">
												<i
													class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
											</button>
											<button data-type="download" onclick="downloadYear()"
												class="layui-btn layuiadmin-btn-list" lay-submit=""
												lay-filter="LAY-app-contlist-download-circle">
												<i
													class="layui-icon layui-icon-download-circle layuiadmin-button-btn">导出</i>
											</button>
										</div>
									</div>
								</div>
								<br /> <br /> <br />
								<div id="YueWaterTB" style="width: 1150px;height:400px;">
									<table class="layui-hide" id="demo1" lay-filter="myTable1"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script src="${ctx}/resources/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>

	layui.use('element', function() {
		var element = layui.element;
	});

	layui.use([ 'form', 'layedit', 'laydate' ], function() {
		var form = layui.form
	});

	layui.use('laydate', function() {
		var laydate = layui.laydate;

		//时间控件
		laydate.render({
			elem : '#year',
			type : 'year',
			max : '' + new Date()
		});

		//时间控件
		laydate.render({
			elem : '#startdateday',
			max : '' + new Date(),
			done : function() {
				var str = $("#enddateday").val();
				if (!str.length == 0) {
					var startDate = $("#startdateday").val()
					var endDate = $("#enddateday").val();
					if (startDate > endDate) {
						layer.msg("开始时间不能大于结束时间!", {
							icon : 0
						});
						$("#startdate").val("");
					}
				}
			}
		});

		//时间控件
		laydate.render({
			elem : '#enddateday',
			max : '' + new Date(),
			done : function() {
				var str = $("#startdateday").val();
				if (!str.length == 0) {
					var startDate = $("#startdateday").val()
					var endDate = $("#enddateday").val();
					if (startDate > endDate) {
						layer.msg("结束时间不能小于开始时间!", {
							icon : 0
						});
						$("#enddateday").val("");
					}
				}
			}
		});
	});


	layui.use('table', function() {
		var table = layui.table;
		//展示已知数据
		table.render({
			elem : '#demo',
			cols : [ [ //标题栏
				{
					type : 'checkbox'
				}
				, {
					field : 'ctime',
					title : '用水日期',
					minWidth : 110,
					unresize : true,
					align : 'center'
				}
				, {
					field : 'degrees',
					title : '用水(升)',
					minWidth : 80,
					unresize : true,
					align : 'center'
				}
			] ], //,data: ${list}
			url : '${ctx}/residentialstatistic/ldlistDataDay.action?id=${id}', //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数 //method: 'post' //如果无需自定义HTTP类型，可不加该参数 //,skin: 'line' //表格风格
			id : 'myTable',
			page : true, //是否显示分页
			limits : [ 15, 20, 50 ],
			limit : 8, //每页默认显示的数量
			height : 'full-180'
		});

		var $ = layui.$;
		activeDay = {
			reloadDay : function() {
				var startdate = $('#startdateday').val();
				var enddate = $('#enddateday').val();
				//执行重载
				table.reload('myTable', {
					page : {
						curr : 1 //重新从第 1 页开始
					},
					where : {
						stime : startdate,
						etime : enddate
					}
				});
			}
		};


		$('#reloadDay').on('click', function() {
			var type = $(this).data('type');
			activeDay[type] ? activeDay[type].call(this) : '';
		});

	});

	layui.use('table', function() {
		var table = layui.table;
		//展示已知数据
		table.render({
			elem : '#demo1',
			cols : [ [ //标题栏
				{
					type : 'checkbox'
				}
				, {
					field : 'ctime',
					title : '用水月份',
					minWidth : 110,
					unresize : true,
					align : 'center'
				}
				, {
					field : 'degrees',
					title : '用水(升)',
					minWidth : 80,
					unresize : true,
					align : 'center'
				}
			] ], //,data: ${list}
			url : '${ctx}/residentialstatistic/ldlistDataYear.action?id=${id}', //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数 //method: 'post' //如果无需自定义HTTP类型，可不加该参数 //,skin: 'line' //表格风格
			id : 'myTable1',
			page : true, //是否显示分页
			limits : [ 15, 20, 50 ],
			limit : 9 //每页默认显示的数量
		});

		var $ = layui.$;
		activeYear = {
			reloadYear : function() {
				var year = $('#year').val();
				//执行重载
				table.reload('myTable1', {
					page : {
						curr : 1 //重新从第 1 页开始
					},
					where : {
						year : year
					}
				});
			}
		};


		$('#reloadYear').on('click', function() {
			var type = $(this).data('type');
			activeYear[type] ? activeYear[type].call(this) : '';
		});
	});
	
	function downloadDay(){
		var startdateday = $('#startdateday').val();
		var enddateday = $('#enddateday').val();
		var id = '${id}';
		//var data = {startdate:startdate,enddate:enddate,useraccount:useraccount,areaid:areaid};
		var url = "${ctx}/residentialstatistic/ldExportDay.action?" + "stime=" + startdateday + "&etime=" + enddateday + "&id=" + id;
		window.location.href = url;
	}
	
	function downloadYear(){
		var year = $('#year').val();
		var id = '${id}';
		//var data = {startdate:startdate,enddate:enddate,useraccount:useraccount,areaid:areaid};
		var url = "${ctx}/residentialstatistic/ldExportYear.action?" + "year=" + year + "&id=" + id;
		window.location.href = url;
	}
</script>