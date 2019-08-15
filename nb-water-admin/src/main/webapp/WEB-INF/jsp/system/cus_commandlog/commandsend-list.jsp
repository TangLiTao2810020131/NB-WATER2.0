<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>命令记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
    <style type="text/css">
		.demoTable .layui-inline{margin-top:10px;}
		.layui-fluid{margin-bottom:10px;}
	</style>
</head>
<body class="layui-layout-body" >
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
                                        <input type="text" name="startdate" id="startdate" placeholder="请选择开始时间" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">结束时间:</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="enddate" id="enddate" placeholder="请选择结束时间" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">设备IMEI号:</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="imei" id="imei" placeholder="请输入设备IMEI号" autocomplete="off" class="layui-input">
                                    </div>
                                </div>

                                <div class="layui-inline">
                                    <label class="layui-form-label">命令状态:</label>
                                    <div class="layui-input-inline" style="width: 200px;">
                                        <select name="status" id="status">
                                            <option value="">---请选择命令状态---</option>
                                            <option value="DEFAULT">未下发</option>
                                            <option value="EXPIRED">已过期</option>
                                            <option value="DELIVERED">已送达</option>
                                            <option value="SUCCESSFUL">执行成功</option>
                                            <option value="FAILED">执行失败</option>
                                            <option value="TIMEOUT">执行超时</option>
                                            <option value="CANCELED">撤销执行</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <button data-type="reload" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
                                        <i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
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
<script type="text/html" id="handle">
    <a lay-event="detail" title="查看上报命令" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script type="text/javascript">

    <%--
        由于layui table组件没有自适应功能（不能根据屏幕变化显示表格数据),
        因此根据原生的javascript为表格组件添加浏览器窗口大小改变事件（onresize)
   --%>
    var clientHeight,clientWidth,avg;
    window.onresize=tableData;
    window.onload=tableData;
    function tableData() {

        clientHeight = window.innerHeight;//获取浏览器的可用高度
        clientWidth = window.innerWidth;//获取浏览器的宽度
        avg = (clientWidth - 118) / 6;//分割6等分，表示表格的六列

        layui.use('table', function() {
            var table = layui.table;

            //var $ = layui.jquery, layer = layui.layer;
            //展示已知数据
            table.render({
                elem : '#demo',
                cols : [ [ //标题栏
                     {
                        field : 'imei',
                        title : '设备IMEI号',
                        minWidth:120,
                        width:avg,
                        align:'center'
                    }
                    , {
                        field : 'command',
                        title : '下发命令',
                        minWidth:120,
                        width:avg+2,
                        align:'center'
                    }
                    , {
                        field : 'status',
                        title : '执行状态',
                        minWidth:120,
                        width:avg,
                        templet : function(row) {
                            if (row.status == 'DEFAULT') return "未下发";
                            if (row.status == 'DELIVERED') return "已送达";
                            if (row.status == 'EXPIRED') return "已过期";
                            if (row.status == 'DELIVERED') return "已送达";
                            if(row.status == 'SUCCESSFUL') return "执行成功";
                            if(row.status == 'FAILED') return "执行失败";
                            if(row.status == 'TIMEOUT') return "执行超时";
                            if(row.status == 'CANCELED') return "撤销执行";
                            else return "已送达";
                        },
                        align:'center'
                    }
                    , {
                        field : 'rcommand',
                        title : '响应结果',
                        minWidth:120,
                        width:avg+10,
                        align:'center'
                    }
                    , {
                        field : 'sendtime',
                        title : '发送时间',
                        minWidth:120,
                        width:avg,
                        align:'center'
                    }
                    , {
                        title : '操作',
                        minWidth:120,
                        width:avg+5,
                        align:'center',
                        toolbar : '#handle'
                    }
                ] ],
                url : '${ctx}/commandsendlog/listData.action',
                id : 'myTable',
                even : true,
                page : true, //是否显示分页
                limits : [ 15, 20, 50 ],
                limit : 15 //每页默认显示的数量
                ,height:clientHeight-100
            });

            var $ = layui.$;
            active = {
                reload: function(){
                    var startdate = $('#startdate');
                    var enddate = $('#enddate');
                    var imei = $('#imei');
                    var status = $('#status');
                    //执行重载
                    table.reload('myTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            startdate: startdate.val()
                            ,enddate:enddate.val()
                            ,imei:imei.val()
                            ,status:status.val()
                        }
                    });
                }
            };

            //监听工具条
            table.on('tool(myTable)', function(obj) {
                var data = obj.data;
                if (obj.event === 'detail') {
                    layer.open({
                        type : 2,
                        title: '查看命令详情',
                        area : [ '600px', '500px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/commandsendlog/info.action?id=' + data.id
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

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#startdate'
            ,max:''+new Date()
            , done: function () {
                var str=$("#enddate").val();
                if(!str.length==0)
                {
                    var startDate=$("#startdate").val()
                    var endDate=$("#enddate").val();
                    if(startDate>endDate)
                    {
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
            elem: '#enddate'
            ,max:''+new Date()
            , done: function () {
                var str=$("#startdate").val();
                if(!str.length==0)
                {
                    var startDate=$("#startdate").val()
                    var endDate=$("#enddate").val();
                    if(startDate>endDate)
                    {
                        layer.msg("结束时间不能小于开始时间!",{
                            icon:0
                        });
                        $("#enddate").val("");
                    }
                }
            }
        });
    });

</script>

<script>
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