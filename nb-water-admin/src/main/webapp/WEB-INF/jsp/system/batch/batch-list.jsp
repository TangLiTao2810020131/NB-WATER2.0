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
<title>付费模式</title>
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
  .html-body-overflow
  {
    overflow-x:hidden;
    overflow-y:hidden;
  }
</style>
</head>
<body class="layui-layout-body" >
	<!-- 主体内容 -->
	<div class="layui-body">
		<div class="layui-fluid white-bg">
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
										<label class="layui-form-label">批次名称:</label>
										<div class="layui-input-inline">
											<input type="text" name="batchname" id="batchname"
												placeholder="请输入批次名称" autocomplete="off"
												class="layui-input">
										</div>
								</div>

									<div class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button data-type="reload"
											class="layui-btn layuiadmin-btn-list" lay-submit=""
											lay-filter="LAY-app-contlist-search">
											<i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
										</button>
									</div>
								</div>
							</div>
						
							
						
							<div class="demoTable marginBottom" id="buttons">
								<div class="layui-btn-group">
									<button class="layui-btn" data-method="addPay">
										<i class="layui-icon">&#xe654;</i> 新增
									</button>
									<button class="layui-btn" data-method="deletePay">
										<i class="layui-icon">&#xe640;</i> 删除
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
    <a lay-event="detail" title="水表管理" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script>
	//僵硬控制表格高度
	$(function() {
		var tree_height = $(window).height() + 'px';
		$(".layui-fluid ").css("min-height", tree_height);



		$(window).resize(function() {
			var tree_height = $(window).height() + 'px';
			$(".layui-fluid ").css("min-height", tree_height);

		});
	});
	
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
					field : 'name',
					title : '批次名称',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					field : 'id',
					title : '拥有表数',
					minWidth : 120,
					align : 'center',
					 templet : function(row) {
					 		var num = getBatchWater(row.id);
							return num;
                            
                        },
					unresize : true
				}
				, {
					field : 'describe',
					title : '备注',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					field : 'ctime',
					title : '创建时间',
					minWidth : 120,
					align : 'center',
					unresize : true
				}
				, {
					title : '操作',
					toolbar : '#handle',
					align : 'center',
					unresize : true,
				}
			] ],
			url : '${ctx}/batch/listData.action',
			id : 'myTable',
			page : true, //是否显示分页
			limits : [ 15, 20, 50 ],
			limit : 15, //每页默认显示的数量
		});

		var $ = layui.$;
		active = {
			reload : function() {
				var startdate = $('#startdate');
				var enddate = $('#enddate');
				var batchname = $('#batchname');
				//执行重载
				table.reload('myTable', {
					page : {
						curr : 1 //重新从第 1 页开始
					},
					where : {
						startdate : startdate.val(),
						enddate : enddate.val(),
						batchname : batchname.val()
					}
				});
			},
			addPay : function() {
				layer.open({
					type : 2,
					area : [ '700px', '385px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/batch/input.action'
				});
			},
			deletePay : function() {
				var checkStatus = table.checkStatus('myTable'),
					data = checkStatus.data;
				//layer.alert(data.length);
				//layer.alert(JSON.stringify(data));
				if (data.length < 1) {
					layer.msg('请选择批次！', {
						icon : 7,
						time : 2000 //2秒关闭（如果不配置，默认是3秒）
					});
					return;
				}
				var ids = "";
				for (var i = 0; i < data.length; i++) {
					ids += data[i].id + ",";
				}
				ids = ids.substring(0, ids.length - 1);
				layer.confirm("请问是否确定删除，删除后不可恢复!", {
					btn : [ "确定", "取消" ] //按钮
				}, function(index) {
					$.ajax({
						type : "POST", //提交方式
						url : "${ctx}/batch/delete.action", //路径
						data : {
							"id" : ids
						}, //数据，这里使用的是Json格式进行传输
						dataType : "json",
						success : function(result) { //返回数据根据结果进行相应的处理
							var status = result.status;
							var msg = result.msg;
							layer.msg(msg, {
								icon : 1,
								time : 2000 //2秒关闭（如果不配置，默认是3秒）
							}, function() {
								location.reload();
							});
						}
					});
				});
			}
		};

		//监听工具条
		table.on('tool(myTable)', function(obj) {
			var data = obj.data;
			if (obj.event === 'detail') {
				layer.open({
					type : 2,
					title : '水表注册管理',
					area : [ '1524px', '700px' ],
					fixed : false, //不固定
					maxmin : true,
					content : '${ctx}/sysEquipment/list.action?batchid=' + data.id
				});
			} else if (obj.event === 'del') {
				layer.confirm("请问是否确定删除，删除后不可恢复!", {
					btn : [ "确定", "取消" ] //按钮
				}, function(index) {
					$.post("${ctx}/batch/delete.action?id=" + data.id);
					layer.close(index);
					location.reload();
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
	
	function getBatchWater(id){
        var num;
        $.ajax({
            type : "POST", //提交方式
			url : "${ctx}/sysEquipment/getWaterNum.action", //路径
            data : { "batchid" :id }, //数据，这里使用的是Json格式进行传输
            dataType : "json",
            async : false,
            success : function(result) { //返回数据根据结果进行相应的处理
                num = result;
            }
        });
        return num;
	}
</script>