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
<title>抄表结算</title>
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
										<label class="layui-form-label">结算月份:</label>
										<div class="layui-input-inline">
											<input type="text" name="balancemonth" id="balancemonth"
												placeholder="请选择结算月份" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-inline">
										<label class="layui-form-label">户号:</label>
										<div class="layui-input-inline">
											<input type="text" name="useraccount" id="useraccount"
												placeholder="请输入户号" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-inline">
										<button data-type="reload"
											class="layui-btn layuiadmin-btn-list" lay-submit=""
											lay-filter="LAY-app-contlist-search">
											<i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
										</button>
										<button data-type="download" onclick="download()"
											class="layui-btn layuiadmin-btn-list" lay-submit=""
											lay-filter="LAY-app-contlist-download-circle">
											<i
												class="layui-icon layui-icon-download-circle layuiadmin-button-btn">导出</i>
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


	<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
	<script src="${ctx}/resources/plugins/layui/layui.js"></script>
	<script src="${ctx}/resources/js/formatTime.js"></script>
	<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

	<!--<script type="text/html" id="handle">
	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
</script>-->

	<script type="text/html" id="handle">
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
    
</script>

	<script>
		layui.use('element', function() {
			var element = layui.element;
		});

        layui.use('laydate', function(){
            var laydate = layui.laydate;

	        //时间控件
	        laydate.render({
	            elem: '#balancemonth',type: 'month'
	            
	        });

        });
	
		function raloadt() {
			var table = layui.table;
			table.reload('myTable');
		}

        var clientHeight,clientWidth,avg;
        window.onresize=tableData;
        window.onload=tableData;
        function tableData() {

            clientHeight = window.innerHeight;//获取浏览器的可用高度
            clientWidth = window.innerWidth;//获取浏览器的宽度
            avg = clientWidth / 11;//分割4等分，表示表格的9列

            layui.use('table', function() {
                var table = layui.table;

                //var $ = layui.jquery, layer = layui.layer;
                //展示已知数据
                table.render({
                    elem : '#demo',
                    cols : [ [ //标题栏
                        {
                            field : 'username',
                            title : '户名',
                            minWidth :80,
                            width:avg+10,
                            align:'center'
                        }
                        , {
                            field : 'startnum',
                            title : '起始读数(升)',
                            minWidth : 100,
                            width:avg,
                            align:'center'
                        }
                        , {
                            field : 'startnumdate',
                            title : '起始读表日期',
                            minWidth : 120,
                            width:avg+80,
                            align:'center'
                        }
                        , {
                            field : 'endnum',
                            title : '截至读数(升)',
                            minWidth : 100,
                            width:avg,
                            align:'center'
                        }
                        , {
                            field : 'endnumdate',
                            title : '截至读表日期',
                            minWidth : 120,
                            width:avg+80,
                            align:'center'
                        }
                        , {
                            field : 'waternum',
                            title : '实际用水(升)',
                            minWidth : 100,
                            width:avg-10,
                            templet : function(d) {
                                var waternum = d.endnum - d.startnum;
                                return waternum;
                            },
                            align:'center'
                        }
                         , {
                            field : 'balancemonth',
                            title : '结算月份',
                            minWidth : 100,
                            width:avg,
                            align:'center'
                        }
                        , {
                            field : 'ctime',
                            title : '结算日期',
                            minWidth : 100,
                            width:avg,
                            align:'center'
                        }                          , {
                            field : 'describe',
                            title : '结算类型',
                            minWidth : 100,
                            width:avg,
                            align:'center'
                        }, {
                            title : '操作',
                            width:avg-10,
                            toolbar : '#handle',
                            align:'center'
                        }
                    ] ],
                    url : '${ctx}/balance/listData.action?areaid=${areaid}',
                    id : 'myTable',
                    even : true,
                    page : true, //是否显示分页
                    limits : [ 10, 20, 50 ],
                    limit : 15 ,//每页默认显示的数量
                    height:'full-100'
                });

                var $ = layui.$;
                active = {
                    reload : function() {
                        var balancemonth = $('#balancemonth');
                        var useraccount = $('#useraccount');
                        //执行重载
                        table.reload('myTable', {
                            page : {
                                curr : 1 //重新从第 1 页开始
                            },
                            where : {
                                balancemonth : balancemonth.val(),
                                useraccount : useraccount.val()
                            }
                        });
                    }
                };

                table.on('tool(myTable)', function(obj) {
                    var data = obj.data;
                    if (obj.event === 'detail') {
                        layer.open({
                            title : '结算',
                            type : 2,
                            area : [ '600px', '510px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/balance/info.action?id=' + data.id
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
        };
	

function download(){
	var balancemonth = $('#balancemonth').val();
	var useraccount = $('#useraccount').val();
	var areaid = '${areaid}';
	//var data = {startdate:startdate,enddate:enddate,useraccount:useraccount,areaid:areaid};
	var url = "${ctx}/balance/export.action?" + "balancemonth=" + balancemonth + "&useraccount=" + useraccount + "&areaid=" + areaid;
	window.location.href = url;
}

//僵硬控制表格高度
$(function(){
	var tree_height = $(window).height()+'px';
	$(".layui-fluid ").css("min-height",tree_height);
	
	
	
	$(window).resize(function() {
		var tree_height = $(window).height()+'px';
		$(".layui-fluid ").css("min-height",tree_height);
		
	});
})


</script>
</body>
</html>