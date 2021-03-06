<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>过户记录</title>
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
		<div class="layui-fluid white-bg" style="height:auto;">
			<div class="layui-row  layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-body">
							<div class="demoTable">

								<div class="layui-inline">
									<label class="layui-form-label">原户号:</label>
									<div class="layui-input-inline">
										<input type="text" name="olduseraccount" id="olduseraccount"
											placeholder="请输入原户号" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label" style="width: 110px;">原户主身份证号:</label>
									<div class="layui-input-inline">
										<input type="text" name="oldidcard" id="oldidcard"
											placeholder="请输入原户主身份证号" autocomplete="off"
											class="layui-input">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">新户号:</label>
									<div class="layui-input-inline">
										<input type="text" name="newuseraccount" id="newuseraccount"
											placeholder="请输入新户号" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label" style="width: 110px;">新户主身份证号:</label>
									<div class="layui-input-inline">
										<input type="text" name="newidcard" id="newidcard"
											placeholder="请输入新户主身份证号" autocomplete="off"
											class="layui-input">
									</div>
								</div>

								<div class="layui-inline">
									<label class="layui-form-label">开始时间:</label>
									<div class="layui-input-inline">
										<input type="text" name="startdate" id="startdate"
											placeholder="请选择开始时间" autocomplete="off" class="layui-input"
											readonly="readonly">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label" style="width: 110px;">结束时间:</label>
									<div class="layui-input-inline">
										<input type="text" name="enddate" id="enddate"
											placeholder="请选择结束时间" autocomplete="off" class="layui-input"
											readonly="readonly">
									</div>
								</div>
								<div class="layui-inline">
									<button data-type="reload"
										class="layui-btn layuiadmin-btn-list" lay-submit=""
										lay-filter="LAY-app-contlist-search">
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

	<script type="text/javascript">
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

    var clientHeight,clientWidth,avg;
    window.onresize=tableData;
    window.onload=tableData;
    function tableData() {

        clientHeight = window.innerHeight;//获取浏览器的可用高度
        clientWidth = window.innerWidth;//获取浏览器的宽度
        avg = (clientWidth - 165) / 7;//分割6等分，表示表格的六列

        layui.use('table', function() {
            var table = layui.table;

            //var $ = layui.jquery, layer = layui.layer;
            //展示已知数据
            table.render({
                elem : '#demo',
                cols : [ [ //标题栏
                    /*  {
                        field : 'watermetercode',
                        title : 'IMEI号',
                        minWidth : 100
                    }
                    , */ {
                        field : 'olduseraccount',
                        title : '原户号',
                        minWidth :120,
                        width:avg+10,
                        align:'center'
                    }
                    , {
                        field : 'oldusername',
                        title : '原户名',
                        minWidth :120,
                        width:avg+10,
                        align:'center'
                    }
                    , {
                        field : 'newuseraccount',
                        title : '新户号',
                        minWidth :120,
                        width:avg+10,
                        align:'center'
                    }
                    , {
                        field : 'newuaername',
                        title : '新户名',
                        minWidth :120,
                        width:avg+10,
                        align:'center'
                    }
                    , {
                        field : 'transferread',
                        title : '过户读数',
                        minWidth :120,
                        width:avg,
						align:'center'
                    },{
                        field : 'address',
                        title : '详细地址',
                        minWidth :120,
                        width:avg+15,
                        align:'center'
                    }

                    , {
                        field : 'ctime',
                        title : '过户时间',
                        minWidth :120,
                        width:avg+20,
                        align:'center'
                    }

                ] ],
                url : '${ctx}/transferrecord/listData.action',
                id : 'myTable',
                even : true,
                page : true, //是否显示分页
                limits : [ 15, 20, 50 ],
                limit : 15, //每页默认显示的数量
                height:clientHeight-100
            });
            var $ = layui.$;
            active = {
                reload: function(){
                    var startdate = $('#startdate');
                    var enddate = $('#enddate');
                    var olduseraccount = $('#olduseraccount');
                    var oldidcard = $('#oldidcard');
                    var newuseraccount = $('#newuseraccount');
                    var newidcard = $('#newidcard');
                    //执行重载
                    table.reload('myTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            startdate: startdate.val()
                            ,enddate:enddate.val()
                            ,olduseraccount:olduseraccount.val()
                            ,oldidcard:oldidcard.val()
                            ,newuseraccount:newuseraccount.val()
                            ,newidcard:newidcard.val()
                        }
                    });
                }
            };

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
    
    function raloadt(){
        var table = layui.table;
        table.reload('myTable');
    }
    
    //僵硬控制表格高度
    $(function(){
        var tree_height = $(window).height()+'px';
        $(".layui-fluid ").css("min-height",tree_height);


        $(window).resize(function() {
            var tree_height = $(window).height()+'px';
            $(".layui-fluid ").css("min-height",tree_height);


        });
    });

</script>
</body>
</html>