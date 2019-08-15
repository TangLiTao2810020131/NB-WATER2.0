<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css" media="all"/>
    <style type="text/css">
        .layui-form-label {
            width: 90px;
        }

        .layui-input-block {
            margin-left: 120px;
        }
    </style>
</head>
<body>
<div class="layui-body layui-bg-white">
    <div class="layui-fluid">
        <div class="layui-row  layui-col-space15">
            <fieldset class="layui-elem-field layui-field-title"
                      style="margin-top: 12px;">
                <legend>客户信息</legend>
            </fieldset>
            <form class="layui-form" method="post">
                <input type="hidden" name="id" id="id" value="${customer.id }"/>


                <div class="layui-form-item">
                    <label class="layui-form-label">客户名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="customerName" id="customerName" value="${customer.customerName }"
                               lay-verify="customerName" autocomplete="off" placeholder="请输入客户名称"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">最大水表数量:</label>
                    <div class="layui-input-block">
                        <input type="text" name="maxNum" id="maxNum" value="${customer.maxNum }"
                               lay-verify="maxNum" autocomplete="off" placeholder="请输入最大水表数量"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">联系人:</label>
                    <div class="layui-input-block">
                        <input type="text" name="linkman" value="${customer.linkman }"
                               lay-verify="linkman" autocomplete="off" placeholder="请输入联系人"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">手机号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="tel" value="${customer.tel }"
                               lay-verify="tel" autocomplete="off" placeholder="请输入联系人电话"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱:</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" value="${customer.email }"
                               lay-verify="email" autocomplete="off" placeholder="请输入邮箱"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" class="layui-textarea"
                                  name="address">${customer.address }</textarea>
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" class="layui-textarea"
                                  name="remarks">${customer.remarks }</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">状态:</label>
                    <div class="layui-input-block">
                        <input type="checkbox"
                               <c:if test="${customer.customerStatus==1}">checked="checked"</c:if>
                               name="customerStatus" value="1" lay-skin="switch"
                               lay-filter="switchTest" lay-text="ON|OFF">
                    </div>
                </div>


                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit="" lay-filter="demo1" id="demo1">提交</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script>
    layui.use('element', function () {
        var element = layui.element;
    });

    layui.use(['form', 'layedit', 'laydate','layer'], function () {
        var form = layui.form,
            layer = layui.layer;
        form.verify({
            customerName: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (value.length==0){
                    return '客户名称不能为空';
                }
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    return '客户名称不能有特殊字符';
                }
                if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                    return '客户名称首尾不能出现下划线\'_\'';
                }
                if (/^\d+\d+\d$/.test(value)) {
                    return '客户名称不能全为数字';
                }
            },
            linkman: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (value.length==0){
                    return '联系人不能为空';
                }
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    return '联系人不能有特殊字符';
                }
                if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                    return '联系人首尾不能出现下划线\'_\'';
                }
                if (/^\d+\d+\d$/.test(value)) {
                    return '联系人不能全为数字';
                }
            },
            tel: [/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/, '手机号必须是正确的格式！'],
            email:  [/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/, '邮箱格式不正确']
        });
        //检查客户名称的唯一性
        function isCheckCustomerName(data){
            var num;
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/cusController/isCheckCustomerName.action", //路径
                data : data, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    num = result;
                }
            });
            return num;
        }


        //监听提交
        //监听提交
        form.on('submit(demo1)', function (data) {

            var id=$("#id").val();
            if(id != "" && id != null){
                var customerName=$("#customerName").val();
                var old = '${customer.customerName }';
                if (customerName != '${customer.customerName }'){
                    var data = $("form").serialize();
                    var num=isCheckCustomerName(data);
                    if(num != 0 && num != -1) {
                        layer.msg('客户名已存在,请重新输入！', {
                            icon: 7,
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function () {
                            $("#customerName").focus();
                            $("#demo1").attr('disabled', false);
                        });
                        return false;
                    }
                }
            }else{
                var data = $("form").serialize();
                var num=isCheckCustomerName(data);
                if(num != 0 && num != -1) {
                    layer.msg('客户名已存在,请重新输入！', {
                        icon: 7,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        $("#customerName").focus();
                        $("#demo1").attr('disabled', false);
                    });
                    return false;
                }
            }
            var data = $("form").serialize();
            $.ajax({
                type : "POST", //提交方式
                url : "${ctx}/cusController/save.action", //路径
                data : data, //数据，这里使用的是Json格式进行传输
                dataType : "json",
                async : false,
                success : function(result) { //返回数据根据结果进行相应的处理
                    layer.msg("操作成功", {
                        icon : 1,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        layer.close(layer.index);
                        window.parent.location.reload();
                    });
                },
                error : function(result) {
                    layer.msg("操作失败!", {
                        icon : 5,
                        time : 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function() {
                        layer.close(layer.index);
                        window.parent.location.reload();
                    });
                }
            });
            return false;
        });
    })
</script>
</body>
</html>