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
<title>小区图表页面</title>
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
							<li lay-id="0" id = "liDay" class="layui-this">近7日</li>
							<li lay-id="1" id = "liYear">每月</li>
						</ul>
						<div class="layui-tab-content">
							<div class="layui-tab-item layui-show">

								<div class="layui-form layuiadmin-card-header-auto marginBottom">
		                            <div class="demoTable" style="float: right;">
		
		                                <div class="layui-inline">
		                                    <label class="layui-form-label">开始时间:</label>
		                                    <div class="layui-input-inline">
		                                        <input type="text" name="startdateday" id="startdateday" placeholder="请选择开始时间" autocomplete="off" class="layui-input">
		                                    </div>
		                                </div>
		                                <div class="layui-inline">
		                                    <label class="layui-form-label">结束时间:</label>
		                                    <div class="layui-input-inline">
		                                        <input type="text" name="enddateday" id="enddateday" placeholder="请选择结束时间" autocomplete="off" class="layui-input">
		                                    </div>
		                                </div>
		
		                                <div class="layui-inline">
		                                    <button data-type="reloadDay" onclick="reloadDay()" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
		                                        <i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
		                                    </button>
		                                </div>
		                            </div>
		                        </div>
								<br/><br/><br/>
									<div id="DayWaterTB" style="width: 1200px;height:400px;"></div>
							</div>

							<div class="layui-tab-item">
								<div class="layui-form layuiadmin-card-header-auto marginBottom">
		                            <div class="demoTable" style="float: right;">
		
		                                <div class="layui-inline">
		                                    <label class="layui-form-label">年份:</label>
		                                    <div class="layui-input-inline">
		                                        <input type="text" name="year" id="year" placeholder="请选择结束年份" autocomplete="off" class="layui-input">
		                                    </div>
		                                </div>
		
		                                <div class="layui-inline">
		                                    <button data-type="reloadMonth" onclick="reloadMonth()" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
		                                        <i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
		                                    </button>
		                                </div>
		                            </div>
		                        </div>
								<br/><br/><br/>
								<div id="YueWaterTB" style="width: 1200px;height:400px;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/resources/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        
        			//时间控件
			laydate.render({
				elem : '#year',
				type: 'year',max:'' + new Date()
			});

        //时间控件
        laydate.render({
            elem: '#startdateday'
            ,max:'' + new Date()
            , done: function () {
                var str = $("#enddateday").val();
                if(!str.length == 0){
                    var startDate = $("#startdateday").val()
                    var endDate = $("#enddateday").val();
                    if(startDate > endDate){
                        layer.msg("开始时间不能大于结束时间!",{
                            icon:0
                        });
                        $("#startdate").val("");
                    }
                }
            }
        });

        //时间控件
        laydate.render({
            elem: '#enddateday'
            ,max:'' + new Date()
            , done: function () {
                var str = $("#startdateday").val();
                if(!str.length == 0){
                    var startDate = $("#startdateday").val()
                    var endDate = $("#enddateday").val();
                    if(startDate > endDate){
                        layer.msg("结束时间不能小于开始时间!",{
                            icon:0
                        });
                        $("#enddateday").val("");
                    }
                }
            }
        });
    });

	var categoriesDay = [];
	var seriesDay = [];
	var categoriesMonth = [];
	var seriesMonth = [];

	//僵硬控制表格高度
	$(function() {
		initDay(null,null);
		initMonth(null);
	});
	

	function reloadDay(){
		var startdate = $('#startdateday').val();
        var enddate = $('#enddateday').val();
		initDay(startdate,enddate);
	}	
	function reloadMonth(){
		var year = $('#year').val();
		initMonth(year);
	}

	function initDay(stime,etime) {
		$.ajax({
			type : "POST", //提交方式 
			url : '${ctx}/residentialstatistic/mainLineDay.action',
			data : {
               id :  '${id}',
               stime : stime,
               etime : etime
            }, 
			dataType : "json",
			async : false,
			success : function(data) {
				categoriesDay = data.categories;
				seriesDay = data.series;
				$("#liDay").html("近"+categoriesDay.length+"日");
				ititDayWaterTB(categoriesDay, seriesDay);
			}
		});
	}

	function initMonth(year) {
		$.ajax({
			type : "POST", //提交方式 
			url : '${ctx}/residentialstatistic/mainLineMonth.action',
			data : {
               id :  '${id}',
               year : year
            }, 
			dataType : "json",
			async : false,
			success : function(data) {
				categoriesMonth = data.categories;
				seriesMonth = data.series;
				$("#liYear").html(data.year + "年");
				initYueWaterTB(categoriesMonth, seriesMonth,data.year);
			}
		});
	}

	layui.use('element', function() {
		var element = layui.element;
	});

	layui.use([ 'form', 'layedit', 'laydate' ], function() {
		var form = layui.form
	});

	function ititDayWaterTB(categories, series) {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('DayWaterTB'));

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : '近'+categories.length+'日户小区水量',
				x : 'center',
				y : 'bottom',
				textAlign : 'center'
			},
			tooltip : {},
			legend : {
				data : [ '用水量(升)' ]
			},
			xAxis : {
                name: '天',
				data : categories
			},
			yAxis : {
                name: '升'
			},
			series : [ {
				name : '用水量(升)',
				type : 'line',
				smooth : true,
				data : series
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	}


	function initYueWaterTB(categories, series,year) {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('YueWaterTB'));

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : year+'年每月小区用水量',
				x : 'center',
				y : 'bottom',
				textAlign : 'center'
			},
			tooltip : {},
			legend : {
				data : [ '用水量(升)' ]
			},
			xAxis : {
                name: '天',
				data : categories
			},
			yAxis : {
                name: '升'
			},
			series : [ {
				name : '用水量(升)',
				type : 'line',
				smooth : true,
				data : series
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	}
</script>
</html>