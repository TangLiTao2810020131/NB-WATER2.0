package com.ets.nb_iot.cmdinfo.iotinit;

import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.BatchTaskCreateInDTO2;
import com.iotplatform.client.dto.BatchTaskCreateOutDTO;
import com.iotplatform.client.dto.CommandDTOV4;
import com.iotplatform.client.dto.DeviceCmd;
import com.iotplatform.client.invokeapi.BatchProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 姚轶文
 * @create 2018- 11-14 13:56
 * 批量任务
 */
@Service
public class BathTaskService {
    @Autowired
    private NbIotConfig nbIotConfig;

    @Autowired
    IntiClient initClient;
    /**
     * 批处理命令任务
     * @param batchProcess 批处理对象
     * @param deviceList 批处理设备ID
     * @param taskName 批处理任务名称,任务名称不能重复
     * @param command 命令对象
     * @param callbackUrl 命令执行结果的回调地址
     * @throws NorthApiException
     */
    public  BatchTaskCreateOutDTO batchDeviceCmdTask(BatchProcess batchProcess, List<String> deviceList,
                                                            String taskName,CommandDTOV4 command,String callbackUrl) throws NorthApiException {

        BatchTaskCreateInDTO2 btcInDTO = new BatchTaskCreateInDTO2();

        btcInDTO.setTaskName(taskName);
        btcInDTO.setTimeout(300);
        btcInDTO.setAppId(nbIotConfig.getAppId());
        btcInDTO.setTaskType("DeviceCmd");

        //set DeviceCmd
        //type	必选	String	body	批量命令类型，取值范围： DeviceList/DeviceType/DeviceArea/GroupList/Broadcast。
        //deviceList	可选	List<String>		deviceId列表，当命令类型为DeviceList时，需要填写。
        //deviceType	可选	String		设备类型，当命令类型为DeviceType时需要填写，其值应当与profile中定义的一致。
        //manufacturerId	可选	String		厂商ID，当命令类型为DeviceType时可填写，其值应当与profile中定义的一致。
        //model	可选	String		设备型号，当命令类型为DeviceType时可填写，其值应当与profile中定义的一致。
        //deviceLocation	可选	String		设备位置，当命令类型为DeviceArea时需要填写。
        //groupList	可选	List<String>		设备组名字列表，当命令类型为GroupList时需要填写。
        //command	必选	CommandDTOV4		命令，查看CommandDTOV4结构定义
        //callbackUrl	可选	String		命令执行结果的回调地址。
        //maxRetransmit	可选	Integer(0~3)		命令下发最大重传次数，取值范围：0-3。
        DeviceCmd deviceCmd = new DeviceCmd();
        deviceCmd.setType("DeviceList");
        deviceCmd.setDeviceList(deviceList);
        deviceCmd.setCommand(command);
        deviceCmd.setCallbackUrl(callbackUrl);

        try {
            btcInDTO.setParam(deviceCmd);
            return batchProcess.createBatchTask(btcInDTO, initClient.getAccessToken());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    /**
     * 电信SDK未实现此功能，需要在电信NB平台门户上传文件
     */
    @Deprecated
    public  BatchTaskCreateOutDTO batchDeviceRegTask()
    {
        return null;
    }

}
