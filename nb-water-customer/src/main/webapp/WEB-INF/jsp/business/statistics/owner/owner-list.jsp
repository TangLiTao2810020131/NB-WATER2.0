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
<title>图标</title>
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
		<div class="layui-fluid  white-bg" style="height:auto;">
			<div class="layui-row  layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-body">
							<div class="layui-form layuiadmin-card-header-auto marginBottom">
								<div class="demoTable">

									<div class="layui-inline">
										<label class="layui-form-label">户号</label>
										<div class="layui-input-inline">
											<input type="text" name="useraccount" id="useraccount"
												placeholder="请输入户号" autocomplete="off"
												class="layui-input">
										</div>
									</div>
									
									<div class="layui-inline">
										<label class="layui-form-label">业主</label>
										<div class="layui-input-inline">
											<input type="text" name="username" id="username"
												placeholder="请输入业主名称" autocomplete="off"
												class="layui-input">
										</div>
									</div>

									<div class="layui-inline">
										<button data-type="reload"
											class="layui-btn layuiadmin-btn-list" lay-submit=""
											lay-filter="LAY-app-contlist-search">
											<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
										</button>
									</div>
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

<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<script type="text/html" id="handle">
	<a lay-event="liebiao" title="用水量列表" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe629;</i></a>
    <a lay-event="tubiao" title="用水量图表" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe62c;</i></a>
</script>

<script>

	layui.use('laydate', function() {
		var laydate = layui.laydate;

		//时间控件
		laydate.render({
			elem : '#ctime'
		});
	})
	layui.use('element', function() {
		var element = layui.element;
	});

	function raloadt() {
		var table = layui.table;
		table.reload('myTable');
	}

	var clientHeight,
		clientWidth,
		avg;
	window.onresize = tableData;
	window.onload = tableData;
	function tableData() {
		clientHeight = window.innerHeight; //获取浏览器的可用高度
		clientWidth = window.innerWidth; //获取浏览器的宽度
		avg = clientWidth / 6; //分割4等分，表示表格的7列

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
						field : 'initName',
						title : '小区',
						minWidth : 110,
						width : avg+3,
						unresize : true,
						align : 'center'
					}
					, {
						field : 'building',
						title : '楼号',
						minWidth : 80,
						width : avg - 30,
						unresize : true,
						align : 'center'
					}
					, {
						field : 'doornum',
						title : '门牌号',
						minWidth : 80,
						width : avg - 30,
						unresize : true,
						align : 'center'
					}
					, {
						field : 'userCode',
						title : '户号',
						minWidth : 80,
						width : avg - 30,
						unresize : true,
						align : 'center'
					}
					, {
						field : 'userName',
						title : '业主',
						minWidth : 110,
						width : avg - 20,
						unresize : true,
						align : 'center'
					}
					/* , {
						field : 'watermetercode',
						title : 'IMEI',
						minWidth : 110,
						width : avg - 20,
						unresize : true,
						align : 'center'
					} */
					, {
						field : 'isclose',
						title : '图表',
						minWidth : 100,
						width : avg-50,
						unresize : true,
						align : 'center',
						toolbar : '#handle'
					}
				] ], //,data: ${list}
				url : '${ctx}/remote/listData.action?areaId=${areaId}', //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数 //method: 'post' //如果无需自定义HTTP类型，可不加该参数 //,skin: 'line' //表格风格
				id : 'myTable',
				page : true, //是否显示分页
				limits : [ 15, 20, 50 ],
				limit : 15, //每页默认显示的数量
				height : 'full-130'
			});

			var $ = layui.$;
			active = {
				reload : function() {
					var username = $('#username');
					var useraccount = $('#useraccount');
					//执行重载
					table.reload('myTable', {
						page : {
							curr : 1 //重新从第 1 页开始
						},
						where : {
							username : username.val()
							,useraccount : useraccount.val()
						}
					});
				}
			};

			var height = window.innerHeight; //获取浏览器的可用高度
			var width = window.innerWidth - 200; //获取浏览器的宽度

			//监听工具条
			table.on('tool(myTable)', function(obj) {
				var data = obj.data;
				if (obj.event === 'tubiao') {
					parent.layer.open({
						title : '用水量图表',
						type : 2,
						area : [ '1200px', '600px' ],
						fixed : false, //不固定
						maxmin : true,
						content : '${ctx}/ownerstatistic/tubiao.action?imei=' + data.watermetercode
					});
				}
				if (obj.event === 'liebiao') {
					parent.layer.open({
						title : '用水量数据',
						type : 2,
						area : [ '1200px', '650px' ],
						fixed : false, //不固定
						maxmin : true,
						content : '${ctx}/ownerstatistic/liebiao.action?imei=' + data.watermetercode
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
	}

	//僵硬控制表格高度
	$(function() {
		var tree_height = $(window).height() + 'px';
		$(".layui-fluid ").css("min-height", tree_height);



		$(window).resize(function() {
			var tree_height = $(window).height() + 'px';
			$(".layui-fluid ").css("min-height", tree_height);

		});
	})
</script>
</html>