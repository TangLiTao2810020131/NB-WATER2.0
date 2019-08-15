<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>操作日志</title>
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
                                            <label class="layui-form-label">操作人</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="username" id="username" placeholder="请输入操作人" autocomplete="off" class="layui-input">
                                            </div>
                                        </div>
                                       
                                 
                                        <div class="layui-inline">
                                            <label class="layui-form-label">时间</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="oprtime" id="oprtime" placeholder="请输入时间" autocomplete="off" class="layui-input">
                                            </div>
                                        </div>
                                      
                                        <div class="layui-inline">
                                            <button data-type="reload" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
                                                <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
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
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>

<script type="text/html" id="handle">
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
</script>

<script>

    layui.use('laydate', function(){
         var laydate = layui.laydate;

         //时间控件
         laydate.render({
             elem: '#oprtime',
             max:maxDate()
         });
    })
    function maxDate(){
        var now = new Date();
        return now.getFullYear()+"-" + (now.getMonth()+1) + "-" + now.getDate();

    }
        layui.use('element', function() {
        var element = layui.element;
    });

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
        avg = (clientWidth - 200) / 5;//分割5等分，表示表格的5列

        layui.use('table', function(){
            var table = layui.table;

            //var $ = layui.jquery, layer = layui.layer;
            //展示已知数据
            table.render({
                elem: '#demo'
                ,cols: [[ //标题栏
                    {field: 'username', title: '操作人', minWidth:120,width:avg,align:'center',unresize:true}
                    ,{field: 'modulename', title: '模块名称', minWidth:120,width:avg,align:'center',unresize:true}
                    ,{field: 'oprcontent', title: '操作内容', minWidth:120,width:avg+100,align:'center',unresize:true}
                    ,{field: 'oprtime', title: '操作时间', minWidth:120,width:avg,align:'center',unresize:true}
                    ,{field: 'isclose', title: '操作',width:avg-14,toolbar:'#handle',align:'center',unresize:true}
                ]]
                //,data: ${list}
                ,url: '${ctx}/logOpr/listData.action'
                //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数
                //method: 'post' //如果无需自定义HTTP类型，可不加该参数
                //,skin: 'line' //表格风格
                ,id:'myTable'
                ,even: true
                ,page: true //是否显示分页
                ,limits: [15,20,50]
                ,limit:15 //每页默认显示的数量
                ,height:clientHeight-100
            });

            var $ = layui.$;
            active = {
                reload: function(){
                    var username = $('#username');
                    var oprtime = $('#oprtime');


                    //执行重载
                    table.reload('myTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            username: username.val()
                            ,oprtime:oprtime.val()
                        }
                    });
                }
            };

            //监听工具条
            table.on('tool(myTable)', function(obj){
                var data = obj.data;
                if(obj.event === 'detail'){
                    layer.open({
                        type: 2,
                        area: ['700px', '450px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '${ctx}/logOpr/info.action?id='+data.id
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