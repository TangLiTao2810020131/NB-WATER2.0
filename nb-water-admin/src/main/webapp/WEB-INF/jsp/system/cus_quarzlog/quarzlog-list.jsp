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
<title>任务监控</title>
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
		<div class="layui-fluid white-bg">
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
</html>

<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>

<script type="text/javascript">
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
					field : 'triggerName',
					title : 'Trigger 名称',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					field : 'triggerGroup',
					title : 'Trigger 分组',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
								
				, {
					field : 'nextFireTime',
					title : '下次执行时间',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					field : 'prevFireTime',
					title : '上次执行时间',
					minWidth : 120,
					align : 'center',
					unresize : true
				}				
				, {
					field : 'priority',
					title : '优先级',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					field : 'triggerState',
					title : 'Trigger 状态',
					minWidth : 120,
					align : 'center',
					unresize : true
				}				
				, {
					field : 'triggerType',
					title : 'Trigger 类型',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					field : 'startTime',
					title : '开始时间',
					minWidth : 120,
					align : 'center',
					unresize : true
				}			
				, {
					field : 'endTime',
					title : '结束时间',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				
			] ],
			url : '${ctx}/quartzcontroller/listData.action',
			id : 'myTable',
			page : true, //是否显示分页
			limits : [ 15, 20, 50 ],
			limit : 15 //每页默认显示的数量
			,height:'full-200'
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