<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>用户管理</title>
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
    <div class="layui-fluid white-bg" style="height:auto;">
        <div class="layui-row  layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-form layuiadmin-card-header-auto marginBottom">
                            <div class="demoTable">

                                <div class="layui-inline">
                                    <label class="layui-form-label">户号:</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="useraccount" id="useraccount" placeholder="请输入户号" autocomplete="off" class="layui-input">
                                    </div>
                                </div>

                                <div class="layui-inline" style="margin-left:10px;">
                                    <label class="layui-form-label">单价类型:</label>
                                    <div class="layui-input-inline" style="width:180px;">
                                        <select name="userunitprice" id="userunitprice">
                                            <option value="">---请选择用户单价---</option>
                                            <c:forEach items="${moneyList}" var="money">
                                                <option value="${money.id }" >${money.type}-(${money.price}元/吨)</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="layui-inline" style="margin-left:-10px;">
                                    <label class="layui-form-label">收费模式:</label>
                                    <div class="layui-input-inline" style="width:180px;">
                                        <select name="operationmode" id="operationmode">
                                            <option value="">---请选择收费模式---</option>
                                            <c:forEach items="${payList}" var="pay">
                                                <option value="${pay.id }" >${pay.paymode}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="layui-inline" style="margin-left:10px;">
                                    <button data-type="reload" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
                                        <i class="layui-icon layui-icon-search layuiadmin-button-btn">查询</i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="demoTable marginBottom" id="buttons">
                            <shiro:hasAnyRoles  name="创建者">
                                <div class="layui-btn-group">
                                    <button class="layui-btn"  data-method="addUser"><i class="layui-icon">&#xe654;</i> 新增</button>
                                    <button class="layui-btn" data-method="deleteUsers"><i class="layui-icon">&#xe640;</i> 删除</button>
                                </div>
                            </shiro:hasAnyRoles>
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
  <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="transfer">过户</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>-->

<script type="text/html" id="handle">
    <shiro:hasAnyRoles  name="创建者,管理员,职员">
        <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
        <shiro:hasAnyRoles  name="创建者,管理员">
            <a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
            <a lay-event="transfer" title="过户" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe60a;</i></a>
            <shiro:hasAnyRoles  name="创建者">
                <a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
            </shiro:hasAnyRoles>
        </shiro:hasAnyRoles>
    </shiro:hasAnyRoles>
</script>

<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#startdate'
        });

        //时间控件
        laydate.render({
            elem: '#enddate'
        });
    });


    var clientHeight,clientWidth,avg;
    window.onresize=tableData;
    window.onload=tableData;
    function tableData() {

        clientHeight = window.innerHeight;//获取浏览器的可用高度
        clientWidth = window.innerWidth;//获取浏览器的宽度
        avg = (clientWidth - 200) / 8;//分割8等分，表示表格的8列

        layui.use(['table','layer'], function() {
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
                        field : 'username',
                        title : '户名',
                        minWidth:80,
                        width:avg-10,
                        align:'center'
                    }
                    , {
                        field : 'useraccount',
                        title : '户号',
                        minWidth:80,
                        width:avg,
                        align:'center'
                    }
                    /* ,{field: 'documenttype', title: '证件类型', minWidth: 200}
                    ,{field: 'idnum', title: '证件号码', minWidth: 200} */
                    , {
                        field : 'phone',
                        title : '联系电话',
                        minWidth:80,
                        width:avg+20,
                        align:'center'
                    }
                    , {
                        field : 'sex',
                        title : '性别',
                        minWidth:80,
                        width:avg-10,
                        templet : function(row) {
                            if (row.sex == 1) {
                                return "男";
                            } else {
                                return "女";
                            }
                        },
                        align:'center'
                    }
                    /*      ,{field: 'operationmode', title: '运行模式', minWidth: 200} */
                    , {
                        field : 'type',
                        title : '单价类型',
                        minWidth:80,
                        width:avg,
                        align:'center'
                    }
                    , {
                        field : 'operationmode',
                        title : '收费模式',
                        minWidth:80,
                        width:avg-10,
                        align:'center'
                    }, {
                        field : 'watermetercode',
                        title : '绑定水表IMEI号',
                        minWidth:80,
                        width:avg+35,
                        align:'center'
                    }
                    , {
                        title : '操作',
                        minWidth:80,
                        width:avg+35,
                        toolbar : '#handle',
                        align:'center'
                    }
                ] ],
                url : '${ctx}/owner/listData.action?areaid=${areaid}',
                id : 'myTable',
                page : true, //是否显示分页
                limits : [ 15, 20, 50 ],
                limit : 15 ,//每页默认显示的数量
                height:'full-160'
            });

            var $ = layui.$;
            active = {
                reload: function(){
                    /*         	      var startdate = $('#startdate');
                                      var enddate = $('#enddate'); */
                    var useraccount = $('#useraccount');
                    var userunitprice = $('#userunitprice');
                    var operationmode = $('#operationmode');
                    //执行重载
                    table.reload('myTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            /*  startdate: startdate.val()
                             ,enddate:enddate.val()
                             , */useraccount:useraccount.val()
                            ,userunitprice:userunitprice.val()
                            ,operationmode:operationmode.val()
                        }
                    });
                },
                addUser : function() {
                    var index = parent.layer.open({
                        title:"新增用户",
                        type : 2,
                        area : [ '1050px', '700px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/owner/input.action?areaid=${areaid}'
                    });
                },
                deleteUsers : function() {
                    var checkStatus = table.checkStatus('myTable'),
                        data = checkStatus.data;
                    //layer.alert(data.length);
                    //layer.alert(JSON.stringify(data));
                    if (data.length < 1) {
                        layer.msg('请选择用户！', {
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
                            url : "${ctx}/owner/delete.action", //路径
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
                                    table.reload('myTable');
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
                    parent.layer.open({
                        title:"查看用户",
                        type : 2,
                        area : [ '700px', '650px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/owner/info.action?id=' + data.id
                    });
                } else if (obj.event === 'del') {
                    layer.confirm("请问是否确定删除，删除后不可恢复!", {
                        btn : [ "确定", "取消" ] //按钮
                    }, function(index) {
                        $.post("${ctx}/owner/delete.action?id=" + data.id,function(result){
                            layer.msg("操作成功", {
                                icon : 1,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                layer.close(index);
                                table.reload('myTable');
                            });

                        });
                    });

                } else if (obj.event === 'edit') {
                    parent.layer.open({
                        title:"编辑用户",
                        type : 2,
                        area : [ '1050px', '650px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/owner/input.action?id=' + data.id
                    });
                }else if (obj.event === 'transfer') {
                    if(data.watermetercode != null){
                        parent.layer.open({
                            title:"过户",
                            type : 2,
                            area : [ '1050px', '650px' ],
                            fixed : false, //不固定
                            maxmin : true,
                            content : '${ctx}/transferrecord/input.action?id=' + data.id
                        });
                    }else{
                        layer.alert("该住户未绑定水表，无法进行过户!");
                    }
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