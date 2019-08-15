<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
</head>
<body class="layui-layout-body" >


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


<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<script type="text/html" id="handle">
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script>

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#ctime'
        });
    })
    layui.use('element', function() {
        var element = layui.element;
    });



    layui.use('table', function(){
        var table = layui.table;

        //var $ = layui.jquery, layer = layui.layer;
        //展示已知数据
        table.render({
            elem: '#demo'
            ,cols: [[ //标题栏
                {field: 'imei', title: '设备IMEI号', minWidth:120,align:'center'}
                ,{field: 'content', title: '上报内容', minWidth:120,align:'center'}
                ,{field: 'baseNum', title: '表读数', minWidth:120,align:'center'}
                ,{field: 'ctime', title: '上报时间', minWidth: 120,align:'center'}
            	,{ title : '操作',minWidth:120,toolbar : '#handle',align:'center'}
            ]]
            //,data: ${list}
            ,url: '${ctx}/sysreadlog/history-sysreadlog-data.action?deviceid=${deviceid}'
            //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数
            //method: 'post' //如果无需自定义HTTP类型，可不加该参数
            //,skin: 'line' //表格风格
            ,id:'myTable'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [10,20,50]
            ,limit:10 //每页默认显示的数量
            ,height:'full-50'
        });

        //监听工具条
        table.on('tool(myTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.open({
                        type : 2,
                        title: '查看历史上报记录详情',
                        area : [ '650px', '400px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/sysreadlog/info.action?id=' + data.id
                });
            }
        });

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        $('#buttons .layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
    });


</script>
</body>
</html>