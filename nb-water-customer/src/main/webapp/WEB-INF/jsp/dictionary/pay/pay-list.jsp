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
    <link rel="stylesheet" href="${ctx}/resources/js/css/common.css"/>
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


<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>
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
    <a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
    <a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
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
        avg = (clientWidth-200) / 2;//分割2等分，表示表格的2列

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
                        field : 'paymode',
                        title : '付费模式类型',
                        minWidth:120,
						width:avg+43,align : 'center',unresize:true
                    }
                    , {
                        title : '操作',
                        toolbar : '#handle',
						align:'center',
						unresize:true,
						width:avg
                    }
                ] ],
                url : '${ctx}/pay/listData.action',
                id : 'myTable',
                page : true, //是否显示分页
                limits : [ 15, 20, 50 ],
                limit : 15 //每页默认显示的数量
                ,height:clientHeight-100
            });

            var $ = layui.$;
            active = {
                addPay : function() {
                    layer.open({
                        type : 2,
                        area : [ '700px', '385px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/pay/input.action'
                    });
                },
                deletePay : function() {
                    var checkStatus = table.checkStatus('myTable'),
                        data = checkStatus.data;
                    //layer.alert(data.length);
                    //layer.alert(JSON.stringify(data));
                    if (data.length < 1) {
                        layer.msg('请选择付费模式！', {
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
                            url : "${ctx}/pay/delete.action", //路径
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
                                    layer.close(index);
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
                        area : [ '700px', '450px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/pay/info.action?id=' + data.id
                    });
                } else if (obj.event === 'del') {
                    layer.confirm("请问是否确定删除，删除后不可恢复!", {
                        btn : [ "确定", "取消" ] //按钮
                    }, function(index) {
                        $.post("${ctx}/pay/delete.action?id=" + data.id);
                        layer.close(index);
                        location.reload();
                    });

                } else if (obj.event === 'edit') {
                    layer.open({
                        type : 2,
                        area : [ '700px', '385px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/pay/input.action?id=' + data.id
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