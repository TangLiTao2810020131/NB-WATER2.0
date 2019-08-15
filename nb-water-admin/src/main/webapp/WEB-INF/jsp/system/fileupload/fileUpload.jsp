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
    <style>
        #fileUpload{
            margin-left:30px;
        }
    </style>
</head>
<body>

    <div class="layui-body layui-bg-white">
        <div class="layui-fluid">
            <div class="layui-row  layui-col-space15">
                <fieldset class="layui-elem-field layui-field-title"
                          style="margin-top: 12px;">
                    <legend>文件上传</legend>
                </fieldset>
                <form class="layui-form" action="">
                    <div class="layui-form-item">
                        <label class="layui-form-label">文件名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fileName"  autocomplete="off" class="layui-input" id="fileName" disabled value="${filename}">
                    </div>
            </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">文件大小:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="fileSize"  autocomplete="off" class="layui-input" id="fileSize" disabled value="${filesize}">
                        </div>
                    </div>
                    <br/>
                    <button type="button" class="layui-btn" id="fileUpload">
                        <i class="layui-icon">&#xe67c;</i>上传PDF文件
                    </button>
                    <button type="button" class="layui-btn" id="oldFile" onclick="findOldFile();">
                        <i class="layui-icon">&#xe655;</i>查看当前PDF文件
                    </button>
                </form>
            </div>
        </div>
    </div>


<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>
    layui.use(['upload','layer'], function(){
        var upload = layui.upload;
        var layer =layui.layer;

        //执行实例
        var uploadInst = upload.render({
            elem: '#fileUpload' //上传按钮id
            ,url: '${ctx}/fileManage/fileUpload.action?'+new Date().getTime() //上传接口
            ,done: function(res){
                //上传完毕回调
                layer.msg(res.message,{
                    time:4000,
                    icon:0
                });
            }
            ,error: function(){
                //请求异常回调
                layer.msg(res.message,{
                    time:4000,
                    icon:1
                });
            }
            ,accept: 'file' //允许上传的文件类型
            ,size: 10240 //最大允许上传的文件大小(KB)
            ,before: function(obj){

                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function(index, file, result){

                    $("#fileName").val(file.name);

                    $("#fileSize").val((parseInt(file.size)/(1024*1024)).toFixed(2)+' M');
                });
            }
        });

        window.findOldFile=function()
        {
            layer.open({
                type : 2,
                title:['用户使用手册','font-size:14px'],
                area : [ '800px', '550px' ],
                fixed : false, //不固定
                maxmin:true,
                content : '${ctx}/fileManage/manual.action'
            });
        }

    });
</script>
</body>
</html>