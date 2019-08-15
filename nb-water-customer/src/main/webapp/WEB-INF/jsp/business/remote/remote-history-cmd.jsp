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



    layui.use(['table','layer'], function(){
        var table = layui.table,
            layer = layui.layer;

        //var $ = layui.jquery, layer = layui.layer;
        //展示已知数据
        table.render({
            elem: '#demo'
            ,cols: [[ //标题栏
                   {
                        field : 'commandid',
                        title : '命令ID',
                        minWidth:120
                    }
                    , {
                        field : 'deviceid',
                        title : '设备ID',
                        minWidth:120
                    }
                    , {
                        field : 'command',
                        title : '下发命令',
                        minWidth:120
                    }
                    , {
                        field : 'status',
                        title : '执行状态',
                        minWidth:120,
                        templet : function(row) {
                            if (row.status == 'DEFAULT') return "未下发";
                            if (row.status == 'EXPIRED') return "已过期";
                            if (row.status == 'DELIVERED') return "已送达";
                            if(row.status == 'SUCCESSFUL') return "执行成功";
                            if(row.status == 'FAILED') return "执行失败";
                            if(row.status == 'TIMEOUT') return "执行超时";
                            if(row.status == 'CANCELED') return "撤销执行";
                        }
                    }
                    , {
                        field : 'rcommand',
                        title : '响应结果',
                        minWidth:120
                    }
                    , {
                        field : 'sendtime',
                        title : '发送时间',
                        minWidth:120
                    }
                    , {
                        title : '操作',
                        minWidth:120,
                        align:'center',
                        toolbar : '#handle'
                    }


            ]]
            //,data: ${list}
            ,url: '${ctx}/remote/history-cmd-data.action?deviceId=${deviceId}'
            //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数
            //method: 'post' //如果无需自定义HTTP类型，可不加该参数
            //,skin: 'line' //表格风格
            ,id:'myTable'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [10,20,50]
            ,limit:15 //每页默认显示的数量
            ,height:'full-50'
        });

        var $ = layui.$;
        active = {
            reload: function(){
                var username = $('#username');
                var email = $('#email');
                var tel = $('#tel');
                var ctime = $('#ctime');
                var isclose = $("#isclose option:selected");

                //执行重载
                table.reload('myTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        username: username.val()
                        ,email:email.val()
                        ,tel:tel.val()
                        ,ctime:ctime.val()
                        ,isclose:isclose.val()
                    }
                });
            },

            addUser: function(){
                layer.open({
                    type: 2,
                    area: ['700px', '375px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/user/input.action'
                });
            },
            closeUser:function(){
                var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                if(data.length<1){
                    //alert("请选择用户");


                    return;
                }
                var ids = "";
                for(var i=0 ; i<data.length ; i++){
                    ids += data[i].id+",";
                }
                ids=ids.substring(0,ids.length-1);
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/user/close.action",//路径
                    data : {"id":ids},//数据，这里使用的是Json格式进行传输
                    dataType:"json",
                    success : function(result) {//返回数据根据结果进行相应的处理
                        //alert("操作成功");
                        layer.msg('操作成功',{
                            icon:1,
                            time:2000
                        },function(){
                            location.reload();
                        })
                    }
                });
            },
            openUser:function(){
                var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                if(data.length<1){
                   // alert("请选择用户");
                    layer.msg('请选择用户',{
                        icon:1,
                        time:2000
                    })
                    return;
                }
                var ids = "";
                for(var i=0 ; i<data.length ; i++){
                    ids += data[i].id+",";
                }
                ids=ids.substring(0,ids.length-1);
                $.ajax({
                    type : "POST", //提交方式
                    url : "${ctx}/user/open.action",//路径
                    data : {"id":ids},//数据，这里使用的是Json格式进行传输
                    dataType:"json",
                    success : function(result) {//返回数据根据结果进行相应的处理
                        //alert("操作成功");
                        layer.msg('操作成功',{
                            icon:1,
                            time:2000
                        },function(){
                            location.reload();
                        })
                    }
                });
            },
            deleteUsers:function(){
                var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                //layer.alert(data.length);
                //layer.alert(JSON.stringify(data));
                if(data.length<1){
                    //alert("请选择用户");
                    layer.msg('操作成功', {
                        icon: 7,
                        time: 2000
                    })
                    return;
                }
                var ids = "";
                for(var i=0 ; i<data.length ; i++){
                    ids += data[i].id+",";
                }
                ids=ids.substring(0,ids.length-1);
                layer.confirm("请问是否确定删除，删除后不可恢复!", {
                    btn: ["确定","取消"] //按钮
                }, function(index){
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/user/delete.action",//路径
                        data : {"id":ids},//数据，这里使用的是Json格式进行传输
                        dataType:"json",
                        success : function(result) {//返回数据根据结果进行相应的处理
                           // alert("操作成功");
                            layer.msg('操作成功',{
                                icon:1,
                                time:2000
                            },function(){
                                layer.close(index);
                                location.reload();
                            })
                        }
                    });
                });
            },
            inRole:function(){
                var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                if(data.length != 1){
                   // alert("请选择一个用户");
                    layer.msg('请选择一个用户', {
                        icon: 7,
                        time: 2000
                    })
                    return;
                }

                layer.open({
                    type: 2,
                    area: ['700px', '375px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/user/inRole.action?id='+data[0].id
                });
            }
        };

        //监听工具条
        table.on('tool(myTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.open({
                        type : 2,
                        title: '查看历史上报命令',
                        area : [ '600px', '400px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/commandsendlog/info.action?id=' + data.id
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