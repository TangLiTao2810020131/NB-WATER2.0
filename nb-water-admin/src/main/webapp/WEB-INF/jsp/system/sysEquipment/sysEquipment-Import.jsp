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
<meta http-equiv="Content-Type"
	content="multipart/form-data;         charset=utf-8" />
<title>系统水表管理</title>
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
<link rel="stylesheet" href="${ctx}/resources/js/css/common.css" />
<style type="text/css">
.demoTable .layui-inline {
	margin-top: 10px;
}

.layui-fluid {
	margin-bottom: 10px;
}
</style>
</head>
<body class="layui-layout-body">

	<!-- 主体内容 -->
	<div class="layui-body">
		<div id="mask"
			style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;width: 100%;height: 100%;z-index: 102;opacity: 0.3;display: none;background: #000;">
		</div>
		<div id="loading"
			style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 103;width: 200px;height: 350px;display: none;text-align: center;margin: auto;">
			<img src="${ctx }/resources/images/loading.gif" alt="正在处理，请稍等。。。">
			<p style="color:#FFFFFF;">正在处理，请稍等。。。</p>
		</div>
		
		<div class="layui-fluid white-bg" style="height:auto;">
			<div class="layui-row  layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-row  layui-col-space15">
					
						<div class="groups-2"
							style="width: 100%;height: 30px;margin: 15px;">
							<span style="float:left;height:30px;line-height: 30px;">选择文件：</span>
							<div
								style="width: 200px;height: 30px;display: inline-block;vertical-align: middle;margin-bottom: 4px;line-height: 29px;margin-left: 10px;">
								<form id="importForm" method="post" style="float:left;"
									class="form-horizontal" enctype="multipart/form-data">
									<div class="form-group" style="margin-left:0;">
										<input type="file" id = "file" name="file" multiple="multiple"
											style="height:25px;width: 200px;padding: 2px;display: inline-block;border: 1px solid #ccc;">
									</div>
								</form>
							</div>
							
							<div class="layui-inline">
										<label class="layui-form-label">选择批次:</label>
										<div class="layui-input-inline">
											<select name="batchid" id="batchid" style="height: 32px;width: 200px;padding: 2px;display:inline-block;border: 1px solid #ccc;">
	                                            <option value="0">---请选择选择批次---</option>
	                                            <c:forEach items="${list}" var="b">
	                                             	<option value="${b.id}">${b.name}</option>
	                                            </c:forEach>
                                        	</select>
										</div>
									</div>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="layui-btn" id = "uploadfile" onclick="uploadfile()">
								<i class="layui-icon">&#xe681;</i><span>开始导入</span>
							</button>
							<button class="layui-btn" id = "uploadTemplate" onclick="uploadTemplate()">
								<i class="layui-icon">&#xe630;</i><span>模板下载</span>
							</button>
						</div>
						<div class="groups-3"
							style="width: 100%;height: 30px;margin: 15px;">
							<p style="margin: 0;display: inline-block;">
								<span>上传结果：</span> <span>总记录数</span> <span id="tatalNum"
									style="display: inline-block;width: 80px;height: 30px;border: 0px solid #ccc;vertical-align: middle;line-height: 30px;text-align: center;">0</span>
								<span>&nbsp;条，成功</span> <span id="successNum"
									style="display: inline-block;width: 80px;height: 30px;border: 0px solid #ccc;vertical-align: middle;line-height: 30px;text-align: center;">0</span>
								<span>&nbsp;条，失败</span> <span id="errorNum"
									style="display: inline-block;width: 80px;height: 30px;border: 0px solid #ccc;vertical-align: middle;line-height: 30px;text-align: center;color: red;">0</span>
								<span>&nbsp;条。</span>
							</p>
						</div>

						<div style="float:right;margin-right: 300px;">
							<img alt="" src="${ctx }/resources/images/2.jpg"
								style="width: 350px;height: 150px;margin-top:-100px;">
						</div>
						<div style="margin: 10px auto 0;">
							<div
								style="width:100%;height: 58px;background-color: #fff;padding-top: 15px;">
								<span
									style=" margin-left: 15px;font-size: 18px;font-weight: bold;color: #383838;">上传失败表</span>&nbsp;&nbsp;
								<button class="layui-btn" id="download">
									<i class="layui-icon">&#xe601;</i><span>导出失败记录</span>
								</button>
							</div>
							<table class="layui-hide" id="demo" lay-filter="myTable"></table>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>

<script type="text/javascript">

	function uploadfile() {
	
	
	    var filename = $("#file").val();
	    console.log(filename);
	    if(filename == ''){
	    	layer.msg('请选择文件！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			});
	    	return;
	    }else{
	    	var ext = filename.substring(filename.indexOf('\.'));
	    	if(ext == '.xls' || ext == '.xlsx'){
	    		
	    	}else{
		    	layer.msg('文件格式不正确！', {
					icon : 7,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				});
	    		return;
	    	}
	    }
	    
	    var batch  = $("#batchid").val();
	    if(batch == '0'){
	    	layer.msg('请选择批次！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			});
	    	return;
	    }

		$('#mask').fadeToggle(300);
		$('#loading').css('display', 'block');
		var formData = new FormData($('#importForm')[0]);
		console.log(formData);
		$.ajax({
			type : "POST", //提交方式
			url : "${ctx}/sysEquipment/execlImport.action?batchid=" + batch, //路径
			data : formData,
			cache : false,
			processData : false,
			contentType : false,
			success : function(result) { //返回数据根据结果进行相应的处理
				$('#mask').css('display', 'none');
				$('#loading').css('display', 'none');
				layer.msg("导入成功", {
					icon : 1,
					time : 2000 //2秒关闭（如果不配置，默认是3秒）
				}, function() {
					$("#errorNum").text(result.errorNum);
					$("#successNum").text(result.successNum);
					$("#tatalNum").text(result.total);
					reloadData(result.list);
				});
			}
		});

	}

	function reloadData(errorList) {
		layui.use('table', function() {

			var table = layui.table;

			//var $ = layui.jquery, layer = layui.layer;
			//展示已知数据
			table.render({
				elem : '#demo',
				cols : [ [ //标题栏
					 {
						field : 'id',
						title : '水表ID',
						minWidth : 120,
						align : 'center'
					}, {
						field : 'imei',
						title : '水表IMEI号',
						minWidth : 120,
						align : 'center'
					}

				] ],
				data : errorList,
				id : 'myTable',
				page : true, //是否显示分页
				limits : [ 15, 20, 50 ],
				limit : 15, //每页默认显示的数量
				height : 'full-250'
			});
		});

	}

	//导出
	$("#download").click(function() {
		var table = layui.table;
		console.log(table.cache.myTable);
		if (table.cache.myTable == undefined) {
			layer.msg('无导入错误列表！', {
				icon : 7,
				time : 2000 //2秒关闭（如果不配置，默认是3秒）
			});
			return;
		}
		window.location.href = "${ctx}/sysEquipment/exportError.action";
	});
	
		//导出
	$("#uploadTemplate").click(function() {
		window.location.href = "${ctx}/sysEquipment/exportTemplate.action";
	});

	//僵硬控制表格高度
	$(function() {
		reloadData(null);
		var tree_height = $(window).height() + 'px';
		$(".layui-fluid ").css("min-height", tree_height);



		$(window).resize(function() {
			var tree_height = $(window).height() + 'px';
			$(".layui-fluid ").css("min-height", tree_height);


		});
	});
</script>