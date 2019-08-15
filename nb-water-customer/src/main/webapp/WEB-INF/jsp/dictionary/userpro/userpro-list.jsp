<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>用户性质数据字典展示页面</title>
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
</head>

<body class="layui-layout-body">
	<div class="layui-body">
		<div class="layui-fluid height100 white-bg">
			<div class="layui-row  layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-body">
							<div class="demoTable marginBottom" id="buttons">
								<div class="layui-btn-group">
									<button class="layui-btn" data-method="addUser">
										<i class="layui-icon">&#xe654;</i> 新增
									</button>
									<button class="layui-btn" data-method="deleteUsers">
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
</body>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script type="text/html" id="handle">
    <a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
    <a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
    <a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
</script>

<script type="text/javascript">
layui.use('table', function(){
        var table = layui.table;

        //var $ = layui.jquery, layer = layui.layer;
        //展示已知数据
        table.render({
            elem: '#demo'
            ,cols: [[ //标题栏
                {type:'checkbox'}
                ,{field: 'usertype', title: '用户性质', minWidth: 200,align : 'center',unresize:true}
                ,{title: '操作', width: 200,toolbar:'#handle',align : 'center',unresize:true}
            ]]
        	,url: '${ctx}/userpro/listData.action'
            ,id:'myTable'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [15,20,50]
            ,limit:15 //每页默认显示的数量
        });
        
        var $ = layui.$;
        active = {
			addUser: function(){
				layer.open({
	        		  type: 2,
	        		  area: ['700px', '385px'],
	        		  fixed: false, //不固定
	        		  maxmin: true,
	        		  content: '${ctx}/userpro/input.action'
	        		});
			},
			deleteUsers:function(){
				var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
				//layer.alert(data.length);
			    //layer.alert(JSON.stringify(data));
			    if(data.length<1){
					layer.msg('请选择用户性质！', {
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
	    			        url : "${ctx}/userpro/delete.action",//路径 
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
        		  type: 2,
        		  area: ['700px', '450px'],
        		  fixed: false, //不固定
        		  maxmin: true,
        		  content: '${ctx}/userpro/info.action?id='+data.id
        		});
          } else if(obj.event === 'del'){
        	  layer.confirm("请问是否确定删除，删除后不可恢复!", {
                  btn: ["确定","取消"] //按钮
              }, function(index){
            	  $.post("${ctx}/userpro/delete.action?id="+data.id);
                  layer.close(index);
              	 location.reload();
              });

          } else if(obj.event === 'edit'){
        	  layer.open({
        		  type: 2,
        		  area: ['700px', '385px'],
        		  fixed: false, //不固定
        		  maxmin: true,
        		  content: '${ctx}/userpro/input.action?id='+data.id
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
</html>
