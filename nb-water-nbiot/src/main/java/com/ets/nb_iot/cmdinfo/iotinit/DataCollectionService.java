package com.ets.nb_iot.cmdinfo.iotinit;

import com.ets.common.DateTimeUtils;
import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.*;
import com.iotplatform.client.invokeapi.DataCollection;
import com.iotplatform.client.invokeapi.SignalDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @create 2018- 11-15 9:54
 * 数据采集服务
 */
@Service
public class DataCollectionService {

    @Autowired
    IntiClient initClient;

    @Autowired
    private NbIotConfig nbIotConfig;

    /**
     * 根据设备ID查询设备信息
     * @param deviceId 设备ID
     * @param select 可选，指定查询条件 imsi
     * @return
     * @throws NorthApiException
     */
    public QuerySingleDeviceInfoOutDTO querySingleDeviceInfo(String deviceId, String select) throws NorthApiException
    {
        NorthApiClient northApiClient = initClient.GetNorthApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        QuerySingleDeviceInfoOutDTO qsdiOutDTO = dataCollection.querySingleDeviceInfo(deviceId, select, nbIotConfig.getAppId(), initClient.getAccessToken());
        return qsdiOutDTO;
    }

    /**
     * 批量查询设备信息列表
     * @param pageNo
     * @param pageSize
     * @return
     * @throws NorthApiException
     */
    public QueryBatchDevicesInfoOutDTO queryBatchDevicesInfo(int pageNo, int pageSize ) throws NorthApiException
    {
        NorthApiClient northApiClient = initClient.GetNorthApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        QueryBatchDevicesInfoInDTO qbdiInDTO = new QueryBatchDevicesInfoInDTO();
        qbdiInDTO.setPageNo(pageNo);
        qbdiInDTO.setPageSize(pageSize);
        QueryBatchDevicesInfoOutDTO qbdiOutDTO = dataCollection.queryBatchDevicesInfo(qbdiInDTO, initClient.getAccessToken());
        return qbdiOutDTO;
    }

    /**
     * 根据设备ID，查询设备历史数据
     * @param deviceId
     * @return
     * @throws NorthApiException
     */
    public QueryDeviceDataHistoryOutDTO queryDeviceDataHistory(String deviceId,int pageNo,int pageSize) throws NorthApiException
    {
    	String day = DateTimeUtils.getnowdate();
    	String day7 = DateTimeUtils.getPastTime(day,7);
        NorthApiClient northApiClient = initClient.GetNorthApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        QueryDeviceDataHistoryInDTO qddhInDTO = new QueryDeviceDataHistoryInDTO();
        //qddhInDTO.setEndTime("20181115T144040Z");
        qddhInDTO.setPageNo(pageNo);
        qddhInDTO.setPageSize(pageSize);
        qddhInDTO.setDeviceId(deviceId);
        qddhInDTO.setGatewayId(deviceId);//for directly-connected device, the gatewayId is its own deviceId
        qddhInDTO.setStartTime(DateTimeUtils.getTZTime(day7));
        qddhInDTO.setEndTime(DateTimeUtils.getTZTime(day));
        QueryDeviceDataHistoryOutDTO qddhOutDTO = dataCollection.queryDeviceDataHistory(qddhInDTO, initClient.getAccessToken());
        return qddhOutDTO;
    }

    /**
     * 查询设备历史命令
     * @param deviceId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws NorthApiException
     */
    public QueryDeviceCommandOutDTO2 queryDeviceCommandHistory(String deviceId,int pageNo,int pageSize)throws NorthApiException
    {
    	String day = DateTimeUtils.getnowdate();
    	String day7 = DateTimeUtils.getPastTime(day,7);
        NorthApiClient northApiClient = initClient.GetNorthApiClient();
        SignalDelivery signalDelivery = new SignalDelivery(northApiClient);
        QueryDeviceCommandInDTO2 qdcInDTO = new QueryDeviceCommandInDTO2();
        qdcInDTO.setPageNo(pageNo);
        qdcInDTO.setPageSize(pageSize);
        qdcInDTO.setDeviceId(deviceId);
        qdcInDTO.setStartTime(DateTimeUtils.getTZTime(day7));
        qdcInDTO.setEndTime(DateTimeUtils.getTZTime(day));
        QueryDeviceCommandOutDTO2 qdcOutDTO = signalDelivery.queryDeviceCommand(qdcInDTO, initClient.getAccessToken());
        return qdcOutDTO;
    }

    /**
     * 查询设备服务能力，在NB平台里profile中定义的服务
     * @param deviceId 设备ID
     * @return
     * @throws NorthApiException
     */
    public QueryDeviceCapabilitiesOutDTO queryDeviceCapabilities(String deviceId) throws NorthApiException
    {
        NorthApiClient northApiClient = initClient.GetNorthApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        QueryDeviceCapabilitiesInDTO qdcInDTO = new QueryDeviceCapabilitiesInDTO();
        qdcInDTO.setDeviceId(deviceId);
        QueryDeviceCapabilitiesOutDTO qdcOutDTO = dataCollection.queryDeviceCapabilities(qdcInDTO, initClient.getAccessToken());
        return qdcOutDTO;
    }


}
