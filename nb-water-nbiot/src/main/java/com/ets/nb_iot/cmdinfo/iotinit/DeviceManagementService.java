package com.ets.nb_iot.cmdinfo.iotinit;

import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.*;
import com.iotplatform.client.invokeapi.DeviceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 姚轶文
 * @create 2018- 11-13 17:16
 * 设备管理
 */
@SuppressWarnings("unused")
@Service
public class DeviceManagementService {
    @Autowired
    IntiClient initClient;
    /**
     * 设备注册
     * @param deviceManagement 设备管理对象
     * @param timeout 验证码有效时间，单位秒，设备需要在有效时间内接入NB平台
     * @param nodeid 设备ID，API文档上是deviceId ,一般用IMEI
     * @return
     */
    public  RegDirectDeviceOutDTO registerDevice(DeviceManagement deviceManagement,  int timeout ,String nodeid) {
        //fill input parameters
        RegDirectDeviceInDTO2 rddid = new RegDirectDeviceInDTO2();
        Random random = new Random();
       // String nodeid = "testdemo" + (random.nextInt(9000000) + 1000000); //this is a test imei
        String verifyCode = nodeid;
        rddid.setNodeId(nodeid); //设备ID，API文档上是deviceId
        rddid.setVerifyCode(verifyCode);//验证码
        rddid.setTimeout(timeout);

        try {
            RegDirectDeviceOutDTO rddod = deviceManagement.regDirectDevice(rddid, null, initClient.getAccessToken());
            System.out.println("设备注册" + rddod);
            return rddod;
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
        return null;
    }


    /**
     * 修改设备信息
     * @param deviceManagement 设备管理对象
     * @param deviceId 设备ID
     * @param deviceName 设备名称
     * @param deviceType 设备类型，大驼峰命名方式，例如：MultiSensor、ContactSensor、Camera和WaterMeter。在NB-IoT方案中，注册设备后必须修改deviceType，且要与profile中定义的保持一致。
     * @param manufacturerId 厂商ID，唯一标识一个厂商。在NB-IoT方案中，注册设备后必须修改manufacturerId，且要与profile中定义的保持一致。
     * @param manufacturerName 厂商名称
     * @param model 设备型号。在NB-IoT方案中，注册设备后必须修改model，且要与profile中定义的保持一致。
     * @param protocolType 可选，设备使用的协议类型：CoAP，huaweiM2M，Z-Wave，ONVIF，WPS，Hue，WiFi，J808，Gateway，ZigBee，LWM2M
     */
    public  void modifyDeviceInfo(DeviceManagement deviceManagement, String deviceId, String deviceName, String deviceType, String manufacturerId,
                                        String manufacturerName,String model , String protocolType) {
        ModifyDeviceInforInDTO mdiInDTO = new ModifyDeviceInforInDTO();
        mdiInDTO.setName(deviceName);
        mdiInDTO.setDeviceType(deviceType);
        mdiInDTO.setManufacturerId(manufacturerId);
        mdiInDTO.setManufacturerName(manufacturerName);
        mdiInDTO.setModel(model);
        
        mdiInDTO.setProtocolType(protocolType);
        try {
            deviceManagement.modifyDeviceInfo(mdiInDTO, deviceId, null, initClient.getAccessToken());
            System.out.println("modify device info succeeded");
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 查看设备位置,设备位置查询需要消耗一定的时间，响应时间在10-60S，存在响应超时的可能。
     * @param deviceManagement 设备管理对象
     * @param deviceId 设备ID
     * @return
     */
    public  QueryDeviceRealtimeLocationOutDTO queryDeviceLocation(DeviceManagement deviceManagement,  String deviceId) {
        QueryDeviceRealtimeLocationInDTO qdrlInDTO = new QueryDeviceRealtimeLocationInDTO();
        qdrlInDTO.setDeviceId(deviceId); //设备ID
        qdrlInDTO.setHorAcc(1000); //水平误差，单位米，默认1000米
        QueryDeviceRealtimeLocationOutDTO qdrlOutDTO;
        try {
            qdrlOutDTO = deviceManagement.queryDeviceRealtimeLocation(qdrlInDTO, null, initClient.getAccessToken());
            System.out.println(qdrlOutDTO.toString());
            return qdrlOutDTO;
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }

        return null;
    }



    /**
     * 修改设备影子信息
     * @param deviceManagement 设备管理对象
     * @param deviceId 设备ID
     * @param serviceId 服务ID
     */
    public  void modifyDeviceShadow(DeviceManagement deviceManagement, String deviceId , String serviceId) {
        ModifyDeviceShadowInDTO mdsInDTO = new ModifyDeviceShadowInDTO();

        ServiceDesiredDTO sdDTO = new ServiceDesiredDTO();
        sdDTO.setServiceId(serviceId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("brightness", 100);
        sdDTO.setDesired(map); //设备状态

        List<ServiceDesiredDTO> serviceDesireds = new ArrayList<ServiceDesiredDTO>();
        serviceDesireds.add(sdDTO);
        mdsInDTO.setServiceDesireds(serviceDesireds);

        try {
            deviceManagement.modifyDeviceShadow(mdsInDTO, deviceId, null, initClient.getAccessToken());
            System.out.println("modify device shadow succeeded");
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 刷新设备
     * @param deviceManagement 设备管理对象
     * @param deviceId 设备ID
     * @param nodeId 节点ID
     */
    public  void refreshDeviceKey(DeviceManagement deviceManagement, String deviceId , String nodeId) {
        RefreshDeviceKeyInDTO rdkInDTO = new RefreshDeviceKeyInDTO();
        Random random = new Random();
        //String nodeid = "testdemo" + (random.nextInt(9000000) + 1000000); //this is a test imei
        rdkInDTO.setNodeId(nodeId);//设备的唯一标识，通常使用MAC，MAC地址，Serial No或IMEI作为nodeId
        rdkInDTO.setVerifyCode(nodeId);//验证码，建议跟nodeid设置成一样
        rdkInDTO.setTimeout(3600);//验证码超时时间，单位秒，默认180S，0=永不过期

        try {
            RefreshDeviceKeyOutDTO rdkOutDTO = deviceManagement.refreshDeviceKey(rdkInDTO, deviceId, null, initClient.getAccessToken());
            System.out.println(rdkOutDTO.toString());
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
    }
    
    /**
     * 删除设备
     * @param deviceManagement 设备管理对象
     * @param deviceId 设备ID
     */
    public void deleteDirectDevice(DeviceManagement deviceManagement, String deviceId){
    	try {
    		deviceManagement.deleteDirectDevice(deviceId, true, null, initClient.getAccessToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
