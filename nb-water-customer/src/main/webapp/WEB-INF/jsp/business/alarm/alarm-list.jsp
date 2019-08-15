<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>付费模式</title>
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
	                                    <label class="layui-form-label">户号:</label>
	                                    <div class="layui-input-inline">
	                                        <input type="text" name="useraccount" id="useraccount" placeholder="请输入户号" autocomplete="off" class="layui-input">
	                                    </div>
	                                </div>
	                                
	                                <div class="layui-inline">
	                                    <label class="layui-form-label">设备IMEI号:</label>
	                                    <div class="layui-input-inline">
	                                        <input type="text" name="imei" id="imei" placeholder="请输入设备IMEI号" autocomplete="off" class="layui-input">
	                                    </div>
	                                </div>
                                	                                
	                                <div class="layui-inline">
	                                    <label class="layui-form-label">报警类型:</label>
	                                    <div class="layui-input-inline" style="width: 200px;">
	                                        <select name="alarmtype" id="alarmtype">
	                                            <option value="">---请选择报警类型---</option>
	                                            <option value="电池告警">电池告警</option>
	                                            <option value="计量错误状态">计量错误状态</option>
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

<!--<script type="text/html" id="handle">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>-->

<script type="text/html" id="handle">
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
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
        avg = (clientWidth - 100) / 6;//分割6等分，表示表格的六列

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
						width:avg-80,
                       align:'center'
                    },{
                        field : 'useraccount',
                        title : '户号',
                        width:avg-80,
                        align:'center'
                    }
                    ,{
                        field : 'watermetercode',
                        title : '设备IMEI号',
                        width:avg-20,
                        align:'center'
                    }
                    ,{
                        field : 'address',
                        title : '住址',
                        width:avg+120,
                       align:'center'
                    },{
                        field : 'alarmtype',
                        title : '报警类型',
                        width:avg-60,
                        align:'center'
                    },{
                        field : 'alarmstatus',
                        title : '报警状态',
                        width:avg-60,
                        templet : function(row) {
                        	if(row.alarmstatus.split('-')[0] == 'MFS'){
                        		var num = parseInt(row.alarmstatus.split('-')[1]);
                        		var val = num.toString(2);
                        		if(val.length == 1){
					    			val = "00" + val;
					    		}
					    		if(val.length == 2){
					    			val = "0" + val;
					    		}
					    		var bit2 = val.substring(0,1);
					    		var bit1 = val.substring(1,2);
					    		var bit0 = val.substring(2,3);
					    		var result = "";
					    		var result1 = "";
					    		var result2 = "";
					    		var result3 = "";
					    		if(bit0 != 0) result1 = "历史磁攻击  ";
					    		if(bit1 != 0) result2 = "计量数据错误  ";
					    		if(bit2 != 0) result3 = "磁攻击";
								result = result1 + result2 + result3;
                        		return result;
                        	}
                            if (row.alarmstatus == 'BS-0') return "电池正常";
                            if (row.alarmstatus == 'BS-1') return "电池充电";
                            if (row.alarmstatus == 'BS-2') return "充电完毕，电池充满电，仍在充电";
                            if (row.alarmstatus == 'BS-3') return "损坏电池";
                            if (row.alarmstatus == 'BS-4') return "电池电量不足";
                            if (row.alarmstatus == 'BS-5') return "未安装电池";
                            if (row.alarmstatus == 'BS-6') return "电池不可用";
                            if (row.alarmstatus == 'MFS-0') return "历史磁攻击";
                            if (row.alarmstatus == 'MFS-1') return "计量数据错误";
                            if (row.alarmstatus == 'MFS-2') return "磁攻击";
                        },
                        align:'center'
                    },{
                        field : 'alarmtime',
                        title : '报警时间',
                        width:avg-5,
                        align:'center'
                    }
                    /* , {
                        title : '操作',
                        width : 55,
                        toolbar : '#handle'
                    } */
                ] ],
                url : '${ctx}/alarm/listData.action',
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
                    var useraccount = $('#useraccount');
                    var imei = $('#imei');
                    var alarmtype = $('#alarmtype');
                    //执行重载
                    table.reload('myTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            startdate: startdate.val()
                            ,enddate:enddate.val()
                            ,useraccount:useraccount.val()
                            ,imei:imei.val()
                            ,alarmtype:alarmtype.val()
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
                        area : [ '700px', '450px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/alarm/info.action?id=' + data.id
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