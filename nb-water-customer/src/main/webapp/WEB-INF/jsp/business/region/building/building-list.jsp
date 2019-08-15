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
    <title>楼层信息</title>
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
    <style type="text/css">
        .layui-form-label {
            width: 90px;
        }

        .layui-input-block {
            margin-left: 120px;
        }
        -webkit-scrollbar{
            width:0px;
            height:0px;
        }
    </style>
</head>
<body class="layui-layout-body" style="" >
<div class="layui-body" style="overflow-y:hidden;">
    <div class="layui-fluid height100 white-bg">
        <div class="layui-row  layui-col-space15">
            <fieldset class="layui-elem-field layui-field-title"
                      style="margin-top: 12px;">
                <legend>${residential.province }-${residential.city}-${residential.area }-${residential.name}</legend>
            </fieldset>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="demoTable marginBottom" id="buttons">
                            <div class="layui-btn-group">
                                <input type="hidden" id="residentialid" value="${residentialid}">
                                <button class="layui-btn"  data-method="addbuilding"><i class="layui-icon">&#xe654;</i> 新增</button>
                                <button class="layui-btn" data-method="deletebuilding"><i class="layui-icon">&#xe640;</i> 删除</button>
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
<script type="text/html" id="handle">
    <a lay-event="door" title="设置门牌号" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe716;</i></a>
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
    <a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
    <a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
</script>
<script>
    var id
    $(function() {
        id = $("#residentialid").val();
    });
    layui.use('table', function(){
        var table = layui.table;

        //var $ = layui.jquery, layer = layui.layer;
        //展示已知数据
        table.render({
            elem: '#demo'
            ,cols: [[ //标题栏
                {type:'checkbox'}
                ,{field: 'building', title: '栋', minWidth: 200}
                ,{title: '操作', minWidth: 200,toolbar:'#handle' , align:"center"}
            ]]
            ,url: '${ctx}/building/listData.action'
            ,id:'myTable'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [15,20,50]
            ,limit:15, //每页默认显示的数量
            height:'full-150'
        });


        //执行重载
        table.reload('myTable', {
            page: {
                curr: 1 //重新从第 1 页开始
            }
            ,where: {
                residentialid:id
            }
        });

        var $ = layui.$;
        active = {
            addbuilding: function(){
                var residentialid = $("#residentialid").val();
                layer.open({
                    type: 2,
                    area: ['600px', '300px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/building/input.action?residentialid='+residentialid
                });
            },
            deletebuilding:function(){
                var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                //layer.alert(data.length);
                //layer.alert(JSON.stringify(data));
                if(data.length<1){
                    layer.msg('请选择接入类型！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return;
                }
                var ids = "";
                for(var i=0 ; i<data.length ; i++){
                    ids += data[i].id+",";
                }
                ids=ids.substring(0,ids.length-1);
                layer.confirm("若删除此楼栋，则会同步删除该楼栋下所有门牌号，业主和绑定的水表设备，请问是否确定删除?", {
                    btn: ["确定","取消"] //按钮
                }, function(index){
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/building/delete.action",//路径
                        data : {"id":ids},//数据，这里使用的是Json格式进行传输
                        dataType:"json",
                        success : function(result) {//返回数据根据结果进行相应的处理
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
        table.on('tool(myTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.open({
                    title : '查看小区门牌号',
                    type: 2,
                    area: ['600px', '380px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/building/info.action?id=' + data.id  + '&residentialid=' + $("#residentialid").val()
                });
            } else if(obj.event === 'del'){
                layer.confirm("若删除此楼栋，则会同步删除该楼栋下所有门牌号，业主和绑定的水表设备，请问是否确定删除?", {
                    btn: ["确定","取消"] //按钮
                }, function(index){
                    $.post("${ctx}/building/delete.action?id="+data.id,function(result){
                        layer.msg("操作成功", {
                            icon : 1,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function() {
                            layer.close(index);
                            table.reload('myTable');
                        });

                    });
                });

            } else if(obj.event === 'edit'){
                layer.open({
                    title : '编辑小区门牌号',
                    type: 2,
                    area: ['600px', '380px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/building/input.action?id=' + data.id  + '&residentialid=' + $("#residentialid").val()
                });
            }
            else if(obj.event == 'door'){
                layer.open({
                    title : '设置小区门牌号',
                    type: 2,
                    area : [ '600px', '500px' ],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/door/doorlist.action?id='+data.id
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