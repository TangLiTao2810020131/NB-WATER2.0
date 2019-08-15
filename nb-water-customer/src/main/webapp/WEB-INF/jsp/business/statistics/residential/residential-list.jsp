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
<title>小区维护</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/addClass.css" />
<link rel="stylesheet" href="${ctx}/resources/plugins/layui/eleTree.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/js/css/common.css" />
<style type="text/css">
body {
	padding: 10px 30px;
}

.hide {
	display: none
}

.demoTable .layui-inline {
	margin-top: 10px;
}

.layui-fluid {
	margin-bottom: 10px;
}
</style>
</head>

<body class="layui-layout-body">
	<div>
		<div class="layui-fluid white-bg" style="height:auto;">

			<div class="layui-row  layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-body">
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
<script src="${ctx}/resources/js//formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>
<script type="text/html" id="handle">
	<a lay-event="loudong" title="楼栋用水量" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe62d;</i></a>
	<a lay-event="liebiao" title="用水量列表" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe629;</i></a>
    <a lay-event="tubiao" title="用水量图表" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe62c;</i></a>
</script>
<script>

	var clientHeight,
		clientWidth,
		avg;
	window.onresize = tableD;
	window.onload = tableD;
	function tableD() {
		clientHeight = window.innerHeight; //获取浏览器的可用高度
		clientWidth = window.innerWidth; //获取浏览器的宽度
		avg = (clientWidth - 200) / 3; //分割3等分，表示表格的3列

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
						field : 'name',
						title : '小区名称',
						minWidth : 100,
						width : avg,
						unresize : true,
						align : 'center'
					}
					, {
						title : '小区位置',
						field : 'address',
						minWidth : 100,
						width : avg + 25,
						unresize : true,
						align : 'center'
					}
					, {
						title : '操作',
						minWidth : 100,
						width : avg,
						toolbar : '#handle',
						unresize : true,
						align : 'center'
					}
				] ],
				url : '${ctx}/residential/listData.action?areaid=${areaId}',
				id : 'myTable',
				page : true, //是否显示分页
				limits : [ 15, 20, 50 ],
				limit : 15, //每页默认显示的数量
				height : 'full-100'
			});

			var $ = layui.$;

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
						content : '${ctx}/residentialstatistic/tubiao.action?id=' + data.id
					});
				}
				if (obj.event === 'liebiao') {
					parent.layer.open({
						title : '用水量数据',
						type : 2,
						area : [ '1200px', '650px' ],
						fixed : false, //不固定
						maxmin : true,
						content : '${ctx}/residentialstatistic/liebiao.action?id=' + data.id
					});
				}
				if (obj.event === 'loudong') {
					parent.layer.open({
						title : '楼栋用水量',
						type : 2,
						area : [ '1400px', '750px' ],
						fixed : false, //不固定
						maxmin : true,
						content : '${ctx}/residentialstatistic/ldlist.action?id=' + data.id
					});
				}
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
	});
</script>
</html>
