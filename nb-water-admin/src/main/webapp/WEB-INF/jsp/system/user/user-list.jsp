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
<body class="layui-layout-body" scroll="no">

       
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
                                            <label class="layui-form-label">账号</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="username" id="username" placeholder="请输入账号" autocomplete="off" class="layui-input">
                                            </div>
                                        </div>
                                        <div class="layui-inline">
                                            <label class="layui-form-label">邮箱</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="email" id="email" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
                                            </div>
                                        </div>
                                        <div class="layui-inline">
                                            <label class="layui-form-label">手机</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="tel" id="tel" placeholder="请输入手机号" autocomplete="off" class="layui-input">
                                            </div>
                                        </div>
                                        <div class="layui-inline">
                                            <label class="layui-form-label">注册日期</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="ctime" id="ctime" placeholder="请输入注册日期" autocomplete="off" class="layui-input">
                                            </div>
                                        </div>
                                       
                                       <div class="layui-inline">
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
                                            <button data-type="reload" class="layui-btn layuiadmin-btn-list" lay-submit="" lay-filter="LAY-app-contlist-search">
                                                <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <div class="demoTable marginBottom" id="buttons">
                                <div class="layui-btn-group">
                                	<shiro:hasPermission name="用户新增">
                                    	<button class="layui-btn"  data-method="addUser"><i class="layui-icon">&#xe654;</i>新增</button>
                                    </shiro:hasPermission>
                                    
                                    <shiro:hasPermission name="用户批量删除">
                                    	<button class="layui-btn" data-method="deleteUsers"><i class="layui-icon">&#xe640;</i>删除</button>
                                    </shiro:hasPermission>
                                    
                                    <shiro:hasPermission name="用户禁用">
                                    	<button class="layui-btn" data-method="closeUser"><i class="layui-icon">&#x1006;</i>禁用</button>
                                    </shiro:hasPermission>
                                    
                                    <shiro:hasPermission name="用户启用">
                                    	<button class="layui-btn" data-method="openUser"><i class="layui-icon">&#xe605;</i>启用</button>
                                    </shiro:hasPermission>
                                    
                                    <shiro:hasPermission name="用户角色分配">
                                    	<button class="layui-btn" data-method="inRole"><i class="layui-icon">&#xe608;</i>分配角色</button>
                                    </shiro:hasPermission>
                                    
                                    <shiro:hasPermission name="用户重置密码">
                                    <button class="layui-btn" data-method="restPassword"><i class="layui-icon">&#xe642;</i>重置密码</button>
                                    </shiro:hasPermission>
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
	<shiro:hasPermission name="用户信息查看">
    	<a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
	</shiro:hasPermission>

	<shiro:hasPermission name="用户编辑">
    	<a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
	</shiro:hasPermission>

	<shiro:hasPermission name="用户删除">
    	<a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
	</shiro:hasPermission>
</script>


<script>

    layui.use('laydate', function(){
         var laydate = layui.laydate;

         //时间控件
         laydate.render({
             elem: '#ctime',
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

    layui.use('layer',function(){
       var layer=layui.layer;
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
        avg = (clientWidth - 200) / 6;//分割6等分，表示表格的6列

        layui.use('table', function(){
            var table = layui.table;

            //var $ = layui.jquery, layer = layui.layer;
            //展示已知数据
            table.render({
                elem: '#demo'
                ,cols: [[ //标题栏
                    {type:'checkbox'}
                    ,{field: 'username', title: '账号', minWidth:120,width:avg,align:'center',unresize:true }
                    ,{field: 'email', title: '邮箱', minWidth:120,width:avg+20,align:'center',unresize:true}
                    ,{field: 'tel', title: '电话', minWidth:120,width:avg,align:'center',unresize:true}
                    ,{field: 'ctime', title: '注册时间', minWidth:120,width:avg+20,align:'center',unresize:true
                        ,templet: function(d){
                            return formatlistdate(d.ctime); //处理时间戳格式
                        }
                    }
                    ,{field: 'isclose', title: '状态', minWidth:120,width:avg,align:'center',unresize:true
                        ,templet: function(d){
                            if(d.isclose==1) return "启用";
                            return "禁用"; //
                        }
                    }
                    ,{field: 'isclose', title: '操作',width:avg-1,toolbar:'#handle',align : 'center',unresize:true}
                ]]
                //,data: ${list}
                ,url: '${ctx}/user/listData.action'
                //,where: {username: 'admin', id: 123} //如果无需传递额外参数，可不加该参数
                //method: 'post' //如果无需自定义HTTP类型，可不加该参数
                //,skin: 'line' //表格风格
                ,id:'myTable'
                ,page: true //是否显示分页
                ,limits: [10,20,50]
                ,limit:15 //每页默认显示的数量
                ,height:clientHeight-100
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
                        content: '${ctx}/user/usersave.action'
                    });
                },
                closeUser:function(){
                    var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                    if(data.length<1){
                        layer.msg('请选择用户！', {
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
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/user/close.action",//路径
                        data : {"id":ids},//数据，这里使用的是Json格式进行传输
                        dataType:"json",
                        success : function(result) {//返回数据根据结果进行相应的处理
                            layer.msg("操作成功", {
                                icon: 1,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function () {
                                layer.close();
                                location.reload();
                            });
                        }
                    });
                },
                openUser:function(){
                    var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                    if(data.length<1){
                        layer.msg('请选择用户！', {
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
                    $.ajax({
                        type : "POST", //提交方式
                        url : "${ctx}/user/open.action",//路径
                        data : {"id":ids},//数据，这里使用的是Json格式进行传输
                        dataType:"json",
                        success : function(result) {//返回数据根据结果进行相应的处理
                            layer.msg("操作成功", {
                                icon: 1,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function () {
                                layer.close();
                                location.reload();
                            });
                        }
                    });
                },
                deleteUsers:function(){
                    var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                    //layer.alert(data.length);
                    //layer.alert(JSON.stringify(data));
                    if(data.length<1){
                        layer.msg('请选择用户！', {
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
                    layer.confirm("请问是否确定删除，删除后不可恢复!", {
                        btn: ["确定","取消"] //按钮
                    }, function(index){
                        $.ajax({
                            type : "POST", //提交方式
                            url : "${ctx}/user/delete.action",//路径
                            data : {"id":ids},//数据，这里使用的是Json格式进行传输
                            dataType:"json",
                            success : function(result) {//返回数据根据结果进行相应的处理
                                layer.msg("操作成功", {
                                    icon: 1,
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    layer.close(index);
                                    location.reload();
                                });
                            }
                        });
                    });
                },
                inRole:function(){
                    var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                    if(data.length != 1){
                        layer.msg('只能选择一个用户进行操作！', {
                            icon : 7,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        });
                        return;
                    }

                    layer.open({
                        type: 2,
                        area: ['700px', '375px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '${ctx}/user/inRole.action?id='+data[0].id
                    });
                },
                restPassword:function(){
                    var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
                    if(data.length<1){
                        layer.msg('请选择用户！', {
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
                    layer.confirm("请问是否确定重置密码!", {
                        btn: ["确定","取消"] //按钮
                    }, function(index){
                        $.ajax({
                            type : "POST", //提交方式
                            url : "${ctx}/user/restPassword.action",//路径
                            data : {"id":ids},//数据，这里使用的是Json格式进行传输
                            dataType:"json",
                            success : function(result) {//返回数据根据结果进行相应的处理
                                layer.msg("操作成功", {
                                    icon: 1,
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
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
                        type: 2,
                        area: ['700px', '450px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '${ctx}/user/info.action?id='+data.id
                    });
                } else if(obj.event === 'del'){
                    layer.confirm("请问是否确定删除，删除后不可恢复!", {
                        btn: ["确定","取消"] //按钮
                    }, function(index){
                        $.post("${ctx}/user/delete.action?id="+data.id);
                        layer.close(index);
                        location.reload();
                    });

                } else if(obj.event === 'edit'){
                    layer.open({
                        type: 2,
                        area: ['700px', '450px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '${ctx}/user/usersave.action?id='+data.id
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