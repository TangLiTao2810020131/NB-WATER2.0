<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/resources/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/css/admin.css" media="all">
    <link rel="stylesheet" href="${ctx}/resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/css/addClass.css"/>
</head>
<body>
<div class="layui-body layui-bg-white">
    <div class="layui-fluid">
        <div class="layui-row  layui-col-space15">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 12px;">
                <legend>设备信息详情</legend>
            </fieldset>
            <div class="layui-form-item">
                <label class="layui-form-label">deviceId:</label>
                <label class="layui-form-mid">${info.deviceId }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">createTime:</label>
                <label class="layui-form-mid">${info.createTime }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">gatewayId:</label>
                <label class="layui-form-mid">${info.gatewayId }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">nodeType:</label>
                <label class="layui-form-mid">${info.nodeType }</label>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">NodeId:</label>
                <label class="layui-form-mid">${info.nodeId }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备名称:</label>
                <label class="layui-form-mid">${info.name }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备描述信息:</label>
                <label class="layui-form-mid">${info.description }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">厂家ID:</label>
                <label class="layui-form-mid">${info.manufacturerId }</label>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">MAC地址:</label>
                <label class="layui-form-mid">${info.mac }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备位置信息:</label>
                <label class="layui-form-mid">${info.location }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备类型:</label>
                <label class="layui-form-mid">${info.deviceType }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备型号:</label>
                <label class="layui-form-mid">${info.model }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备软件版本:</label>
                <label class="layui-form-mid">${info.swVersion }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备固件版本:</label>
                <label class="layui-form-mid">${info.fwVersion }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备硬件版本:</label>
                <label class="layui-form-mid">${info.hwVersion }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">使用的协议:</label>
                <label class="layui-form-mid">${info.protocolType }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">Bridge标识:</label>
                <label class="layui-form-mid">${info.bridgeId }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备状态:</label>
                <label class="layui-form-mid">${info.status }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备状态详情:</label>
                <label class="layui-form-mid">${info.statusDetail }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">冻结状态:</label>
                <label class="layui-form-mid">${info.mute }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否支持安全模式:</label>
                <label class="layui-form-mid">${info.supportedSecurity }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">启用安全模式:</label>
                <label class="layui-form-mid">${info.isSecurity }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">备信号强度:</label>
                <label class="layui-form-mid">${info.signalStrength }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">SIG版本:</label>
                <label class="layui-form-mid">${info.sigVersion }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设备序列号:</label>
                <label class="layui-form-mid">${info.serialNumber }</label>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">电池电量:</label>
                <label class="layui-form-mid">${info.batteryLevel }</label>
            </div>


        </div>
    </div>
</div>

</body>
</html>