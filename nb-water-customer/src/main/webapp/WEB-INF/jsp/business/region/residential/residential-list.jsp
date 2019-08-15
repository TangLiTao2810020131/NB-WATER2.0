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
<title>小区维护</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/addClass.css" />
<link rel="stylesheet" href="${ctx}/resources/plugins/layui/eleTree.css" media="all">
<link rel="stylesheet" href="${ctx}/resources/js/css/common.css"/>
		<style type="text/css">
			body{padding: 10px 30px;}
			.hide{display:none}
			.demoTable .layui-inline{margin-top:10px;}
			.layui-fluid{margin-bottom:10px;}
		</style>
</head>

<body class="layui-layout-body">
<div >
	<div class="layui-fluid white-bg" style="height:auto;">

	<!-- 	<div class="eleTree ele1" lay-filter="data1" class="dtree" ></div> -->
		
		<div class="layui-row  layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-body">
						<div class="demoTable marginBottom" id="buttons">
							<div class="layui-btn-group">
								<button class="layui-btn" data-method="addProvince">
									<i class="layui-icon">&#xe654;</i> 新增
								</button>
								 <button class="layui-btn" data-method="deleteProvinces">
									<i class="layui-icon">&#xe640;</i> 删除
								</button> 
							</div>
						</div>
					  <table class="layui-hide" id="demo" lay-filter="myTable"></table> 
					<!-- 	<table class="layui-table layui-form" id="test-tree-table" lay-filter="table1"></table> -->
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="areaid" value="${areaid }"/>
	</div>
</div>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js//formatTime.js"></script>
<script src="${ctx}/resources/js/common.js"></script>
<script type="text/html" id="handle">
<a lay-event="building" title="设置栋" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe716;</i></a>
<a lay-event="detail" title="查看" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe63c;</i></a>
<a lay-event="edit" title="编辑" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe642;</i></a>
<a lay-event="del" title="删除" style="cursor:pointer; margin-right:5px;"><i class="layui-icon">&#xe640;</i></a>
</script>
<script>
	var tableData = [];
	//区级，县级菜单加载 start
	var data = [];
	layui.config({
		base : "${ctx}/resources/plugins/layui/lay/mymodules/"
	}).use([ 'jquery', 'eleTree' ], function() {
		var $ = layui.jquery;
		var eleTree = layui.eleTree;
		//initTree();
		var k = 1;
		eleTree.render({
			elem : '.ele1',
			// url: "/tree",
			// type: "post",
			data : data,
			drag : true,
			accordion : true,
			lazy : true,
			loadData : function(item, callback) {
				setTimeout(function() {
					$.ajax({
						type : "POST", //提交方式 
						url : "${ctx}/area/treelist.action", //路径 
						data : {
							father : item.id
						}, //数据，这里使用的是Json格式进行传输 
						dataType : "json",
						async : false,
						success : function(result) { //返回数据根据结果进行相应的处理
							var d;
							k++;
							if (k >= 2) {
								return void callback(result);
							} else {
								d[0].isLeaf = true;
								callback(d);
							}
						}
					});
				}, 500);
			}
		});

		eleTree.on("add(data1)", function(data) {
			console.log(data);
		// 若后台修改，则重新获取后台数据，然后重载
		//eleTree.reload(".ele1", {where: {data: JSON.stringify(data.data)}})
		});
		eleTree.on("edit(data1)", function(data) {
			console.log(data);
		});
		eleTree.on("remove(data1)", function(data) {
			console.log(data);
		});
		eleTree.on("toggleSlide(data1)", function(data) {
			var table = layui.table;
			if (data.currentData.isLeaf) {
				$("#areaid").val("");
				//alert(data.currentData.id);
				$("#areaid").val(data.currentData.id);
				var areaid = data.currentData.id;
			   	      //执行重载
        	      table.reload('myTable', {
        	      	url : '${ctx}/residential/listData.action',
        	        page: {
        	          curr: 1 //重新从第 1 页开始
        	        }
        	        ,where: {
        	            areaid: areaid
        	        }
        	      });
			}
			console.log(data);
		// eleTree.reload(".ele1", {where: {data: JSON.stringify(data.data)}})
		});

	});
	//区级，县级菜单加载 end

	//初始化主树形菜单 start
	function initTree() {
		$.ajax({
			type : "POST", //提交方式 
			url : "${ctx}/residential/initTreelist.action", //路径 
			dataType : "json",
			async : false,
			success : function(result) { //返回数据根据结果进行相应的处理
				data = result;
			}
		});
	}
	//初始化主树形菜单 end


function raloadt(){
	var table = layui.table;
	table.reload('myTable');
}


    var clientHeight,clientWidth,avg;
    window.onresize=tableD;
    window.onload=tableD;
    function tableD() {

        clientHeight = window.innerHeight;//获取浏览器的可用高度
        clientWidth = window.innerWidth;//获取浏览器的宽度
        avg = (clientWidth - 200) / 3;//分割3等分，表示表格的3列

        layui.use('table', function() {
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
                        field : 'name',
                        title : '小区名称',
                        minWidth : 100,
						width:avg,
                        unresize:true,align:'center'
                    }
                    , {
                        title : '小区位置',
                        field : 'address',
                        minWidth : 100,
                        width:avg+25,
                        unresize:true,align:'center'
                    }
                    , {
                        title : '操作',
                        minWidth:100,
                        width:avg,
                        toolbar : '#handle',
                        unresize:true,align:'center'
                    }
                ] ],
                url : '${ctx}/residential/listData.action?areaid=${areaid}',
                id : 'myTable',
                page : true, //是否显示分页
                limits : [ 15, 20, 50 ],
                limit : 15, //每页默认显示的数量
                height:'full-100'
            });

            var $ = layui.$;
            active = {
                addProvince : function() {
                    if($("#areaid").val() == ""){
                        layer.msg('请选择区县！', {
                            icon : 7,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        });
                        return ;
                    }
                    var index = parent.layer.open({
                        title : '新建小区',
                        type : 2,
                        area : [ '900px', '600px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/residential/input.action?areaid='+$("#areaid").val()
                    });
                },

                deleteProvinces : function() {
                    var checkStatus = table.checkStatus('myTable'),
                        data = checkStatus.data;
                    if($("#areaid").val() == ""){
                        layer.msg('请选择区县！', {
                            icon : 7,
                            time : 2000 //2秒关闭（如果不配置，默认是3秒）
                        });
                        return ;
                    }
                    if (data.length < 1) {
                        layer.msg('请选择小区！', {
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
                    layer.confirm("若删除此小区，则会同步删除该小区下所有楼栋，门牌号，业主和绑定的水表设备，请问是否确定删除?", {
                        btn : [ "确定", "取消" ] //按钮
                    }, function(index) {
                        $.ajax({
                            type : "POST", //提交方式
                            url : "${ctx}/residential/delete.action", //路径
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
                                    raloadt();
                                });
                            }
                        });
                    });
                }
            }

            //监听工具条
            table.on('tool(myTable)', function(obj) {
                var data = obj.data;
                if($("#areaid").val() == ""){
                    layer.msg('请选择区县！', {
                        icon : 7,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return ;
                }
                if (obj.event === 'detail') {
                    parent.layer.open({
                        title : '查看小区',
                        type : 2,
                        area : [ '900px', '600px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/residential/info.action?id=' + data.id + '&areaid='+$("#areaid").val()
                    });
                } else if (obj.event === 'del') {
                    layer.confirm("若删除此小区，则会同步删除该小区下所有楼栋，门牌号，业主和绑定的水表设备，请问是否确定删除?", {
                        btn : [ "确定", "取消" ] //按钮
                    }, function(index) {
                        $.ajax({
                            type : "POST", //提交方式
                            url : "${ctx}/residential/delete.action", //路径
                            data : {
                                "id" : data.id
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
                                    raloadt();
                                });
                            }
                        });
                    });

                } else if (obj.event === 'edit') {
                    parent.layer.open({
                        title : '编辑小区',
                        type : 2,
                        area : [ '900px', '600px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/residential/input.action?id=' + data.id + '&areaid='+$("#areaid").val()
                    });
                }else if (obj.event === 'building') {
                    var areaid = $("#areaid").val();
                    parent.layer.open({
                        title : '设置小区栋',
                        type : 2,
                        area : [ '900px', '600px' ],
                        fixed : false, //不固定
                        maxmin : true,
                        content : '${ctx}/building/buildlist.action?id=' + data.id
                    });
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
