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
<title>区域</title>
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
</head>
<body class="layui-layout-body">
	<!-- 主体内容 -->
	<div class="layui-body">
		<div class="layui-fluid height100 white-bg">
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
<script src="${ctx}/resources/js/formatTime.js"></script>

<script type="text/html" id="handle">
   <a lay-event="liebiao" title="用水量列表" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe629;</i></a>
   <a lay-event="tubiao" title="用水量图表" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe62c;</i></a>
</script>

<script>
	layui.use('element', function() {
		var element = layui.element;
	});

        var clientHeight,clientWidth,avg;
        window.onresize=tableData;
        window.onload=tableData;
        
        function tableData() {
        	  clientHeight = window.innerHeight;//获取浏览器的可用高度
            clientWidth = window.innerWidth;//获取浏览器的宽度
            avg = clientWidth / 3;//分割4等分，表示表格的7列
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
					field : 'areaid',
					title : '地区代码',
					width : avg - 40,
					align : 'center',
					unresize : true
				}
				, {
					field : 'area',
					title : '名称',
					align : 'center',
					width : avg - 45,
					unresize : true
				}
				, {
					title : '图表',
					toolbar : '#handle',
					width : avg - 40,
					align : 'center',
					unresize : true
				}
			] ],
			url : '${ctx}/districtstatistic/listData.action?father=${areaId}',
			id : 'myTable',
			even : true,
			page : true, //是否显示分页
			limits : [ 5, 10, 20 ],
			limit : 15, //每页默认显示的数量
			height : 'full-50'
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
					content : '${ctx}/districtstatistic/tubiao.action?areacode=' + data.id
				});
			}
			if (obj.event === 'liebiao') {
				parent.layer.open({
					title : '用水量数据',
					type : 2,
					area : [ '1200px', '650px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/districtstatistic/liebiao.action?areacode=' + data.id
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
</script>
</html>