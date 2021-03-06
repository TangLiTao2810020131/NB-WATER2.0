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
                                     <!--    <div class="layui-inline">
                                            <label class="layui-form-label">户主:</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="yhname" id="yhname" placeholder="请输入户主" autocomplete="off" class="layui-input">
                                            </div>
                                        </div> -->
                                         <div class="layui-inline">
                                            <label class="layui-form-label">状态:</label>
                                            <div class="layui-input-inline" style="width: 200px;">
                                                <select name="type" id="type">
                                               		<option value="">---请选择操作状态---</option>
                                               		<option value="-1">更换水表</option>
                                               		<option value="0">安装水表</option>
                                               		<option value="1">自动抄表</option>
                                               		<option value="2">人工抄表</option>
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

<script type="text/html" id="handle">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a> 
<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="user">账号管理</a> 
</script>

<script>
	layui.use('element', function() {
		var element = layui.element;
	});
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


	layui.use('table', function(){
        var table = layui.table;

        //var $ = layui.jquery, layer = layui.layer;
        //展示已知数据
        table.render({
            elem: '#demo'
            ,cols: [[ //标题栏
                {field: 'xqname', title: '小区', minWidth:70,align:"center"}
                ,{field: 'building', title: '栋号', minWidth:50,align:"center"}
                ,{field: 'doornum', title: '门牌号',minWidth:50,align:"center"}
              /*   ,{field: 'yhname', title: '户主', minWidth: 40,align:"center"} */
                ,{field: 'basenum', title: '初始表数(升)', minWidth:90,align:"center"}
                ,{field: 'value', title: '最新表数(升)',minWidth:90,align:"center"}
                ,{field: 'type', title: '状态', minWidth:100,align:"center"
                	,templet: function(d){
                			if(d.type=='-1') return "更换水表";
	                		if(d.type==0) return "安装水表";
	                		if(d.type==1) return "自动抄表";
	                		if(d.type==2) return "手动抄表";
	                		return ""; //
                    	}                   
                }                 
                ,{field: 'issuccess', title: '抄表是否成功',minWidth:120,align:"center"
                	,templet: function(d){
	                		if(d.issuccess==0) return "抄表失败";
	                		if(d.issuccess==1) return "抄表成功";
	                		return ""; //
                    	}               
                }      
                ,{field: 'message', title: '异常信息',minWidth:110,align:"center"
                	,templet: function(d){
	                		if(d.message == '' || d.message == null){
	                 			return "无异常信息";               		
	                		}else{
	                			return d.message; //
	                		}
                    	}                    
                }                             
                ,{field: 'optionname', title: '操作人员', minWidth:110,align:"center"
                	,templet: function(d){
	                		if(d.optionname == '' || d.optionname == null){
	                 			return "--";               		
	                		}else{
	                			return d.optionname; //
	                		}
                    	}  
                 }
                ,{field: 'optiontime', title: '操作时间', minWidth:110,align:"center"}
/*                 ,{title: '操作', width: 250,toolbar:'#handle'} */
            ]]
        	,url: '${ctx}/meterreadlog/listData.action?areaid=${areaid}&equipmentid=${equipmentid}'
/*         	,data:
        	{
        		areaid:'${areaid}',
        		equipmentid:'${equipmentid}'
        	} */
            ,id:'myTable'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [10,20,50]
            ,limit:10 //每页默认显示的数量
            ,height:'full-150'
        });
        
        var $ = layui.$;
        active = {
        	reload: function(){
        	      var startdate = $('#startdate');
        	      var enddate = $('#enddate');
        	    /*   var yhname = $('#yhname'); */
        	      var type = $('#type');
        	      //执行重载
        	      table.reload('myTable', {
        	        page: {
        	          curr: 1 //重新从第 1 页开始
        	        }
        	        ,where: {
        	            startdate: startdate.val()
        	    		,enddate:enddate.val()
        	    	/* 	,yhname:yhname.val() */
        	    		,type:type.val()
        	        }
        	      });
        	    },
			addProvince: function(){
				layer.open({
	        		  type: 2,
	        		  area: ['600px', '700px'],
	        		  fixed: false, //不固定
	        		  maxmin: true,
	        		  content: '${ctx}/meterreadlog/input.action'
	        		});
			},
			
			deleteProvinces:function(){
				var checkStatus = table.checkStatus('myTable'),data = checkStatus.data;
				//layer.alert(data.length);
			    //layer.alert(JSON.stringify(data));
			    if(data.length<1){
			    	alert("请选择资源");
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
	    			        url : "${ctx}/meterreadlog/delete.action",//路径 
	    			        data : {"id":ids},//数据，这里使用的是Json格式进行传输 
	    			        dataType:"json",
	    			        success : function(result) {//返回数据根据结果进行相应的处理 
	    			        	alert("操作成功");
	    			        	layer.close(index);
	    			        	location.reload();
	    			        } 
	    			       });
	              });
			}
			
        	  };
  
        
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