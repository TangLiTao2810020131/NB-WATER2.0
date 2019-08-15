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
<title>远程控制</title>
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
<link rel="stylesheet" href="${ctx}/resources/js/css/common.css"/>
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
										<label class="layui-form-label">户号:</label>
										<div class="layui-input-inline">
											<input type="text" name="useraccount" id="useraccount"
												placeholder="请输入户号" autocomplete="off" class="layui-input">
										</div>
									</div>

									<div class="layui-inline" style="margin-left:20px;">
										<label class="layui-form-label">水表IMEI号:</label>
										<div class="layui-input-inline">
											<input type="text" name="imei" id="imei"
												placeholder="请输入水表IMEI号" autocomplete="off" class="layui-input">
										</div>
									</div>

									<div class="layui-inline" style="margin-left:-20px;">
										<label class="layui-form-label">状态</label>
										<div class="layui-input-inline">
											<select name="isclose" id="isclose">
												<option value="">全部</option>
												<option value="1">启用</option>
												<option value="0">禁用</option>
											</select>
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

 							<shiro:hasAnyRoles  name="创建者,管理员">
							<div class="demoTable marginBottom" id="buttons">
								<div class="layui-btn-group">
									<button class="layui-btn" data-method="open">
										<i class="layui-icon">&#xe609;</i>开阀
									</button>
									<button class="layui-btn" data-method="close">
										<i class="layui-icon">&#xe609;</i>关阀
									</button>
									<button class="layui-btn" data-method="basicNum">
										<i class="layui-icon">&#xe609;</i>设置表读数
									</button>
									<button class="layui-btn" data-method="reportCycle">
										<i class="layui-icon">&#xe609;</i>设置上报周期
									</button>
									<button class="layui-btn" data-method="commandcler">
										<i class="layui-icon">&#x1006;</i>命令取消
									</button>
								</div>
							</div>
							</shiro:hasAnyRoles>

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
	<script src="${ctx}/resources/js/common.js"></script>
	<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

	<script type="text/html" id="handle">
	<a lay-event="historyData" title="历史数据" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe60e;</i></a>
    <a lay-event="historyCmd" title="历史命令" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe665;</i></a>
    <a lay-event="deviceInfo" title="设备信息" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe60a;</i></a>
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

        var clientHeight,clientWidth,avg;
        window.onresize=tableData;
        window.onload=tableData;
        function tableData() {

            clientHeight = window.innerHeight;//获取浏览器的可用高度
            clientWidth = window.innerWidth;//获取浏览器的宽度
            avg = clientWidth / 9;//分割4等分，表示表格的7列

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
							width:avg,
                            align:'center'
                        }
                        , {
                            field : 'building',
                            title : '楼号',
                            width:avg-30,
                            align:'center'
                        }
                        , {
                            field : 'doornum',
                            title : '门牌号',
                            width:avg-30,
                            align:'center'
                        }, {
                            field : 'userCode',
                            title : '户号',
                            width:avg-5,
                            align:'center'
                        }
                        , {
                            field : 'userName',
                            title : '业主',
                            width:avg-30,
                            align:'center'
                        }
                        , {
                            field : 'watermetercode',
                            title : '水表IMEI号',
                            width:avg+20,
                            align:'center'
                        }
                        , {
                            field : 'isFloodgate',
                            title : '水阀',
                            width:avg-30,
                            templet : function(d) {
                                if (d.isFloodgate == 1) return "开启";
                                if (d.isFloodgate == 0) return "关闭";
                            },
                            align:'center'
                        }
                        , {
                            field : 'ismagnetism',
                            title : '是否有磁',
                            width:avg-30,
                            templet : function(d) {
                                if (d.ismagnetism == 0) return "无";
                                if (d.ismagnetism == 1) return "有";
                            },
                           align:'center'
                        }
                        , {
                            field : 'isclose',
                            title : '操作',
                            width:avg-20,
                            align:'center',
                            toolbar : '#handle'
                        }
                    ] ], //,data: ${list}
                    url : '${ctx}/remote/listData.action?areaId=${areaId}', //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数 //method: 'post' //如果无需自定义HTTP类型，可不加该参数 //,skin: 'line' //表格风格
                    id : 'myTable',
                    page : true, //是否显示分页
                    limits : [ 15, 20, 50 ],
                    limit : 15 ,//每页默认显示的数量
                    height:'full-130'
                });

                var $ = layui.$;
                active = {
                    reload : function() {
                        var useraccount = $('#useraccount');
                        var imei = $('#imei');

                        //执行重载
                        table.reload('myTable', {
                            page : {
                                curr : 1 //重新从第 1 页开始
                            },
                            where : {
                                useraccount : useraccount.val(),
                                imei : imei.val()
                            }
                        });
                    },


                    open : function() {
                        var checkStatus = table.checkStatus('myTable'),
                            data = checkStatus.data;
                        if (data.length < 1) {
                            layer.msg('请选择小区业户！', {
                                icon : 7,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }
                        var ids = "";
                        var initNames = "";
                        var doornums = "";
                        for (var i = 0; i < data.length; i++) {
                            var isfloodgate = data[i].isFloodgate;
                            if ("-1" == isfloodgate) {
                                layer.msg('请选择带有阀控水表业户！', {
                                    icon : 7,
                                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
                                });
                                return;
                            }
                            ids += data[i].deviceId + ",";
                            initNames += data[i].initName + ",";
                            doornums += data[i].doornum + ",";
                        }
                        ids = ids.substring(0, ids.length - 1);
                        layer.confirm("请问是否确定发送,开阀命令!", {
                            btn : [ "确定", "取消" ] //按钮
                        }, function(index) {
                            $.ajax({
                                type : "POST", //提交方式
                                url : '${ctx}/remote/open.action', //路径
                                data : {
                                    "id" : ids,
                                    "initNames" : initNames,
                                    "doornums" : doornums
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
                            layer.msg('请选择小区业户！', {
                                icon : 7,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }

                        var ids = "";
                        var initNames = "";
                        var doornums = "";
                        for (var i = 0; i < data.length; i++) {
                            var isfloodgate = data[i].isFloodgate;
                            if ("-1" == isfloodgate) {
                                layer.msg('请选择带有阀控水表业户！', {
                                    icon : 7,
                                    time : 2000 //2秒关闭（如果不配置，默认是3秒）
                                });
                                return;
                            }
                            ids += data[i].deviceId + ",";
                            initNames += data[i].initName + ",";
                            doornums += data[i].doornum + ",";
                        }
                        ids = ids.substring(0, ids.length - 1);

                        layer.confirm("请问是否确定发送关阀命令!", {
                            btn : [ "确定", "取消" ] //按钮
                        }, function(index) {
                            $.ajax({
                                type : "POST", //提交方式
                                url : '${ctx}/remote/close.action', //路径
                                data : {
                                    "id" : ids,
                                    "initNames" : initNames,
                                    "doornums" : doornums
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
                            layer.msg('请选择小区业户！', {
                                icon : 7,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }
                        var ids = "";
                        var initNames = "";
                        var doornums = "";
                        for (var i = 0; i < data.length; i++) {
                            ids += data[i].deviceId + ",";
                            initNames += data[i].initName + ",";
                            doornums += data[i].doornum + ",";
                        }
                        ids = ids.substring(0, ids.length - 1);
                        var index = layer.open({
                            type : 2,
                            title : '设置上报周期',
                            area : [ '500px', '300px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/remote/toReportCycle.action?ids=' + ids + "&initNames=" + initNames + "&doornums=" + doornums
                        });
                    },
                    basicNum : function() {
                        var checkStatus = table.checkStatus('myTable'),
                            data = checkStatus.data;
                        if (data.length < 1) {
                            layer.msg('请选择小区业户！', {
                                icon : 7,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }
                        var ids = "";
                        var initNames = "";
                        var doornums = "";
                        for (var i = 0; i < data.length; i++) {
                            var ismagnetism = data[i].ismagnetism;
                            ids += data[i].deviceId + "*"+ismagnetism+",";
                            initNames += data[i].initName + ",";
                            doornums += data[i].doornum + ",";
                        }
                        ids = ids.substring(0, ids.length - 1);
                        var index = layer.open({
                            type : 2,
                            title : '设置表读数',
                            area : [ '500px', '300px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/remote/toBasicNum.action?ids=' + ids + "&initNames=" + initNames + "&doornums=" + doornums
                        });
                    },
                    commandcler : function (){
                    	 var checkStatus = table.checkStatus('myTable'),
                            data = checkStatus.data;
                        if (data.length < 1) {
                            layer.msg('请选择小区业户！', {
                                icon : 7,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }
                        var ids = "";
                        for (var i = 0; i < data.length; i++) {
                            var ismagnetism = data[i].ismagnetism;
                            ids += data[i].deviceId + "*"+ismagnetism+",";
                        }
                        ids = ids.substring(0, ids.length - 1);
                      	var index = layer.open({
                            type : 2,
                            title : '取消命令',
                            area : [ '400px', '350px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/remote/toCommandCler.action?ids=' + ids + "&initNames=" + initNames + "&doornums=" + doornums
                        });
                    }
                };

                var height=window.innerHeight;//获取浏览器的可用高度
                var width=window.innerWidth-200;//获取浏览器的宽度

                //监听工具条
                table.on('tool(myTable)', function(obj) {
                    var data = obj.data;
                    if (obj.event === 'historyData') {
                        parent.layer.open({
                         	title: '查看历史上报数据记录',
                            type : 2,
                            area : ['800px', '520px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/remote/history-data-list.action?deviceId=' + data.deviceId
                        });
                    }
                    if (obj.event === 'historyCmd') {
                        parent.layer.open({
                        	title: '查看历史下发命令记录',
                            type : 2,
                            area : [ width+'px', '520px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/remote/history-cmd-list.action?deviceId=' + data.deviceId
                        });
                    } else if (obj.event === 'deviceInfo') {
                        parent.layer.open({
                        	title: '查看设备信息',
                            type : 2,
                            area : ['600px', '520px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/remote/device-info.action?deviceId=' + data.deviceId
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
		})
	</script>
</body>
</html>