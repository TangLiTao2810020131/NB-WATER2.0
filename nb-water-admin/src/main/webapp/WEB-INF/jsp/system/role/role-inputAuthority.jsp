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
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css" media="all"/>
    <style type="text/css">
    .layui-form-label{width:90px;}
    .layui-input-block{ margin-left:120px;}
    .check-box{ float:left;width:25%; margin-bottom: 8px;}
    .text-center{ text-align: center; margin-top: 20px;}
    .clear{ clear: both;}
    </style>
</head>
<body>
             <div class="layui-body layui-bg-white">
            <div class="layui-fluid">
               
                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                        <legend>选择权限</legend>
                    </fieldset>

                    <form class="layui-form" action="">
						<input type="hidden" name="roleId" value="${roleId }" >
                            <div class="">
                                <c:forEach items="${list }" var="authority"> 
                                <div class="check-box">
                                <input type="checkbox" name="ids" lay-skin="primary"  value="${authority.id}" title="${authority.authorityname }" 
                                <c:forEach items="${roleAuthorityList }" var="ra">
                                	<c:if test="${authority.id == ra.authorityId }">checked="checked" </c:if>
                                </c:forEach>
                                >
                                </div>
                                </c:forEach>
                            </div>
                        
          				<div class="clear"></div>
                        <div class="layui-form-item">
                            <div class="text-center">
                                <a class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</a>
                                <a type="reset" class="layui-btn layui-btn-primary">重置</a>
                            </div>
                        </div>
                    </form>
  
                
            </div>
        </div>
      
        
        
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script>
    layui.use('element', function() {
        var element = layui.element;
    });


    layui.use(['form', 'layer'], function(){
        var form = layui.form
            , layer = layui.layer


		     //监听提交
		     form.on('submit(demo1)', function(data){
		    	 var data = $("form").serialize();
                 var ids="";
                 var checkboxs=document.getElementsByName("ids");
                 for (var i=0 ; i< checkboxs.length;i++) {
                     if (checkboxs[i].checked){
                         ids += checkboxs[i].value+'','';
                     }
                 }
                 if(ids == ""){
                     layer.msg("请选择至少一个权限!", {
                         icon : 5,
                         time : 2000 //2秒关闭（如果不配置，默认是3秒）
                     });
                     return false;
                 }

		 		//alert(ids);
		    	 $.ajax({ 
				        type : "POST", //提交方式 
				        url : '${ctx}/role/saveAuthority.action',//路径 
				        data :data,//数据，这里使用的是Json格式进行传输 
				        //dataType:"json",
				        async:false,
				        success : function(result) {//返回数据根据结果进行相应的处理 
                            layer.msg("操作成功", {
                                icon: 1,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function () {
                                layer.close(layer.index);
                                window.parent.location.reload();
                            });
                            /*alert(result);*/
				        },
				        error : function(result){
                            layer.msg("操作失败!", {
                                icon : 5,
                                time : 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function() {
                                layer.close(layer.index);
                                window.parent.location.reload();
                            });
				        }
				       });
		     });
		})
</script>
</body>
</html>