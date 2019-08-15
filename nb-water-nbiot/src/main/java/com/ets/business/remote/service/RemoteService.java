package com.ets.business.remote.service;

import com.ets.business.remote.dao.RemoteDao;
import com.ets.common.dtree.Data;
import com.ets.common.dtree.DtreeEntity;
import com.ets.common.dtree.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     RemoteService.java 
 * @Description:   操作设备业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:54:42
 */
@Service
@SuppressWarnings("all")
public class RemoteService {

    @Resource
    RemoteDao remoteDao;

    /**
     * 
    * @Title: open 
    * @Description: 开阀动作
    * @param: @param deviceId 设备ID   
    * @return: void    
    * @Date: 2019年7月25日 下午7:53:11  
    * @throws
     */
    public void open(String deviceId)
    {
         remoteDao.open(deviceId);
    }

    /**
     * 
    * @Title: close 
    * @Description: 关阀动作
    * @param: @param deviceId    
    * @return: void    
    * @Date: 2019年7月25日 下午7:53:41  
    * @throws
     */
    public void close(String deviceId)
    {
        remoteDao.close(deviceId);
    }
}
