<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>业主操作页面</title>
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
          media="all"/>
    <style type="text/css">
        .layui-form-label {
            width: 90px;
        }

        .layui-input-block {
            margin-left: 120px;
        }

        .ddd {
            background-color: red;
        }
    </style>
</head>

<body>
<div class="layui-bg-white">
    <div class="layui-fluid">
        <div class="layui-row  layui-col-space15">
            <div id="nav1">
                <!-- 顶部切换卡 -->
                <!--ul的id要和lay-filter一致-->
                <div class="layui-tab" lay-filter="main_tab1">
                    <ul id="main_tab1" class="layui-tab-title">
                        <li lay-id="0" class="layui-this">老用户信息</li>
                        <li lay-id="1">新用户信息</li>
                    </ul>
                    <div class="layui-tab-content">
                        <div class="layui-tab-item layui-show">
                            <div class="layui-form-item">
                                <label class="layui-form-label">户号:</label> <label
                                    class="layui-form-mid">${owner.useraccount }</label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">户名:</label> <label
                                    class="layui-form-mid">${owner.username }</label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">证件类型:</label> <label
                                    class="layui-form-mid">
                                <c:if test="${owner.documenttype == 0}">
                                    居民身份证
                                </c:if>
                            </label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">证件号码:</label> <label
                                    class="layui-form-mid">${owner.idnum }</label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">手机号码:</label> <label
                                    class="layui-form-mid">${owner.phone }</label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">性别:</label> <label
                                    class="layui-form-mid">
                                <c:if test="${owner.sex == 0}">
                                    女
                                </c:if>
                                <c:if test="${owner.sex == 1}">
                                    男
                                </c:if>
                            </label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">住址:</label> <label
                                    class="layui-form-mid">${owner.address }</label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">收费模式:</label> <label
                                    class="layui-form-mid">
                                ${owner.operationmode}
                            </label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">单价类型:</label> <label
                                    class="layui-form-mid">${owner.type }</label>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">描述:</label>
                                <label class="layui-form-mid">${owner.describe }</label>
                            </div>
                        </div>

                        <div class="layui-tab-item">
                            <form class="layui-form" method="post">
                                <input type="hidden" name="id" value="${owner.id }"/>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">住址:</label>
                                    <label class="layui-form-mid">${owner.address }</label>
                                </div>
                                <div style="">
                                    <div class="layui-form-item" style="">
                                        <label class="layui-form-label">户号:</label>
                                        <div class="layui-input-block">
                                            <input type="text" style="width: 210px;" name="useraccount" id="useraccount"
                                                   lay-verify="required|useraccount" autocomplete="off"
                                                   placeholder="请输入业主账户（必填）" class="layui-input"/>
                                        </div>
                                    </div>
                                </div>

                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">户名:</label>
                                        <div class="layui-input-block">
                                            <input type="text" style="width: 210px;" name="username" id="username"
                                                   lay-verify="required|username" autocomplete="off"
                                                   placeholder="请输入业主名称（必填）" class="layui-input"/>
                                        </div>
                                    </div>
                                </div>

                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">证件类型:</label>
                                        <div class="layui-input-block" id="cesji">
                                            <select name="documenttype" id="documenttype" lay-filter="number">
                                                <option value="">---请选择证件类型（必填）---</option>
                                                <option value="0">居民身份证</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">证件号码:</label>
                                        <div class="layui-input-block">
                                            <input type="text" style="width: 210px;" name="idnum" id="idnum"
                                                   lay-verify="idnum" autocomplete="off" placeholder="请输入证件号码（必填）"
                                                   class="layui-input" disabled/>
                                        </div>
                                    </div>
                                </div>

                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">性别:</label>
                                        <div class="layui-input-block" style="width: 210px;">
                                            <input name="sex" type="radio" value="1" title="男"/>
                                            <input name="sex" type="radio" value="0" title="女"/>
                                        </div>
                                    </div>
                                </div>
                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">手机号码:</label>
                                        <div class="layui-input-block">
                                            <input type="text" style="width: 210px;" name="phone" id="phone"
                                                   lay-verify="phone" autocomplete="off" placeholder="请输入手机码号（必填）"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </div>


                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">收费模式:</label>
                                        <div class="layui-input-block">
                                            <select name="operationmode" id="operationmode">
                                                <option value="">---请选择收费模式（必填）---</option>
                                                <c:forEach items="${payList}" var="pay">
                                                    <option value="${pay.id }">${pay.paymode}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div style="">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">用户单价:</label>
                                        <div class="layui-input-block">
                                            <select name="userunitprice" id="userunitprice">
                                                <option value="">---请选择用户单价（必填）---</option>
                                                <c:forEach items="${moneyList}" var="money">
                                                    <option value="${money.id }">${money.type}-(${money.price}元/吨)</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>


                                <div class="layui-form-item layui-form-text">
                                    <label class="layui-form-label">描述</label>
                                    <div class="layui-input-block">
											<textarea placeholder="请输入内容" name="describe" id="describe"
                                                      style="resize:none" class="layui-textarea"></textarea>
                                    </div>
                                </div>

                                <div class="layui-form-item">
                                    <div class="layui-input-block">
                                        <button class="layui-btn" lay-submit="" lay-filter="demo1" id="demo1">提交
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

</body>
<script src="${ctx}/resources/js/layui.all.js"></script>
<script src="${ctx}/resources/js/jquery-1.11.2.min.js"></script>
<script src="${ctx}/resources/plugins/layui/layui.js"></script>
<script src="${ctx}/resources/js/formatTime.js"></script>
<script
        src="${ctx}/resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">

    $(function () {
        $("#code").focus();
    });


    layui.use(['layer', 'form'], function () {
        var layer = layui.layer,
            form = layui.form;
        /* //自定义验证规则
         form.verify({
             //户号
             useraccount:function(value, item){ //value：表单的值、item：表单的DOM对象
                 if (value.length==0) {
                     return '户号不能为空'
                 }
             },


             //户名
             username:function(value, item){ //value：表单的值、item：表单的DOM对象
                 if (value.length==0) {
                     return '户名不能为空'
                 }
             },
             //身份证号的正则验证
             idnum:[/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,'请输入正确的身份证格式'],

             //手机号码的正则
             phone:[/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/, '手机号必须是正确的格式！']
         });*/

        form.on('submit(demo1)', function (data) {
            $("#demo1").attr('disabled', true); //设置提交禁用防止重复提交数据
            var useraccount = $("#useraccount").val();//检测用户的户号是不是个原户主的户号相同
            if (useraccount == "${owner.useraccount }") {
                layer.msg('户号与原户主户号相同，请检查户号！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#useraccount").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;

            }
            var documenttype = $("#documenttype").val();
            if (documenttype == null || documenttype == '') {
                layer.msg('请选择证件类型！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#documenttype").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }
            var idnum = $("#idnum").val();
            if (idnum == null || idnum == '') {
                layer.msg('请输入身份证号！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#idnum").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }
            var re = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
            if (!re.test(idnum)) {
                layer.msg('请输入正确的身份证号格式！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#idnum").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;

            }
            var sex = $('input:radio[name="sex"]:checked').val();
            if (sex == null || sex == '') {
                layer.msg('请选择性别！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }
            var phone = $("#phone").val();
            if (phone == null || phone == '') {
                layer.msg('请输入身份证号！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#phone").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }
            var res = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
            if (!res.test(phone)) {
                layer.msg('请输入正确的手机号格式！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#phone").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;

            }
            var operationmode = $("#operationmode").val();
            if (operationmode == null || operationmode == '') {
                layer.msg('请选择收费模式！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#operationmode").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }
            var userunitprice = $("#userunitprice").val();
            if (userunitprice == null || userunitprice == '') {
                layer.msg('请选择用户单价！', {
                    icon: 5,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#userunitprice").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }


            function isCkeckTransferUserAccount(data) {
                var num;
                $.ajax({
                    type: "POST", //提交方式
                    url: "${ctx}/owner/isCkeckTransferUserAccount.action", //路径
                    data: data, //数据，这里使用的是Json格式进行传输
                    dataType: "json",
                    async: false,
                    success: function (result) { //返回数据根据结果进行相应的处理
                        num = result;
                    }
                });
                return num;
            };
            var data = $("form").serialize();
            var num = isCkeckTransferUserAccount(data);
            if (num != 0 && num != -1) {
                layer.msg('户号已存在,请重新输入！', {
                    icon: 7,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    $("#useraccount").focus();
                    $("#demo1").attr('disabled', false);
                });
                return false;
            }
            ;

            $.ajax({
                type: "POST", //提交方式
                url: "${ctx}/owner/savetransfer.action", //路径
                data: data, //数据，这里使用的是Json格式进行传输
                dataType: "json",
                async: false,
                success: function (result) { //返回数据根据结果进行相应的处理
                    console.log(result);
                    var status = result.status;
                    var msg = result.msg;
                    layer.msg(msg, {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        close();
                    });
                },
                error: function (result) {
                    layer.msg("操作失败,请选择管理员!", {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        close();
                    });
                }
            });
            return false;
        });
        function close(){
            var index = window.parent.layer.getFrameIndex(window.name); //获取窗口索引
            window.parent.layer.close(index);
            window.parent.raloadts();
        }


        form.on('select(pselect)', function (data) {
            var pid = (data.value);
            $.ajax({
                type: 'POST',
                url: '${ctx}/city/getCity.action',
                data: {
                    father: pid
                },
                dataType: 'json',
                success: function (data) {
                    $("#doornum").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornum").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $("#residential").html("");
                    var xqoption = $("<option>").val("").text("---请选择小区---");
                    $("#residential").append(xqoption);
                    form.render('select');
                    $("#area").html("");
                    var qxoption = $("<option>").val("").text("---请选择区(县)---");
                    $("#area").append(qxoption);
                    form.render('select');
                    $("#city").html("");
                    var soption = $("<option>").val("").text("---请选择市区---");
                    $("#city").append(soption);
                    form.render('select');
                    $.each(data, function (key, val) {
                        var option1 = $("<option>").val(val.cityid).text(val.city);
                        $("#city").append(option1);
                        form.render('select');
                    });

                }
            });
        });

        form.on('select(cselect)', function (data) {
            var cid = (data.value);
            $.ajax({
                type: 'POST',
                url: '${ctx}/area/getArea.action',
                data: {
                    father: cid
                },
                dataType: 'json',
                success: function (data) {
                    $("#doornum").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornum").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $("#residential").html("");
                    var xqoption = $("<option>").val("").text("---请选择小区---");
                    $("#residential").append(xqoption);
                    form.render('select');
                    $("#area").html("");
                    var qxoption = $("<option>").val("").text("---请选择区(县)---");
                    $("#area").append(qxoption);
                    form.render('select');
                    $.each(data, function (key, val) {
                        var option1 = $("<option>").val(val.id).text(val.area);
                        $("#area").append(option1);
                        form.render('select');
                    });
                }
            });
        });

        form.on('select(aselect)', function (data) {
            var aid = (data.value);
            $.ajax({
                type: 'POST',
                url: '${ctx}/residential/getResidential.action',
                data: {
                    areaid: aid
                },
                dataType: 'json',
                success: function (data) {
                    $("#doornum").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornum").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $("#residential").html("");
                    var xqoption = $("<option>").val("").text("---请选择小区---");
                    $("#residential").append(xqoption);
                    form.render('select');
                    $.each(data, function (key, val) {
                        var option1 = $("<option>").val(val.id).text(val.name);
                        $("#residential").append(option1);
                        form.render('select');
                    });
                }
            });
        });

        form.on('select(rselect)', function (data) {
            var residentiaid = (data.value);
            $.ajax({
                type: 'POST',
                url: '${ctx}/building/getBuilding.action',
                data: {
                    residentialid: residentiaid
                },
                dataType: 'json',
                success: function (data) {
                    $("#doornum").html("");
                    var mphoption = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornum").append(mphoption);
                    form.render('select');
                    $("#building").html("");
                    var xqdoption = $("<option>").val("").text("---请选择小区栋号---");
                    $("#building").append(xqdoption);
                    form.render('select');
                    $.each(data, function (key, val) {
                        var option1 = $("<option>").val(val.id).text(val.building);
                        $("#building").append(option1);
                        form.render('select');
                    });
                }
            });
        });

        form.on('select(bselect)', function (data) {
            var buildingid = (data.value);
            $.ajax({
                type: 'POST',
                url: '${ctx}/door/getDoor.action',
                data: {
                    buildingid: buildingid
                },
                dataType: 'json',
                success: function (data) {
                    $("#doornum").html("");
                    var option = $("<option>").val("").text("---请选择门牌号---");
                    $("#doornum").append(option);
                    form.render('select');
                    $.each(data, function (key, val) {
                        var option1 = $("<option>").val(val.id).text(val.doornum);
                        $("#doornum").append(option1);
                        form.render('select');
                    });
                }
            });
        });


        form.on('select(dselect)', function (data) {
            //alert(data.value);
        });

        form.on('select(number)', function (data) {

            var content = $("#documenttype").val();

            if (content == "" || content == null) {
                $("#idnum").attr("disabled", true);
                $("#idnum").val("");
            } else {
                $("#idnum").attr("disabled", false);
            }

        });
    });
</script>
</html>
