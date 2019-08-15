package com.ets.system.cus.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ets.system.cus.dao.CusDao;
import com.ets.system.cus.entity.nb_cus;

/**
 * 
 * @ClassName:     CusService.java 
 * @Description:   客户操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 上午11:57:31
 */
@Service
@Transactional
public class CusService {

    @Resource
    CusDao cusDao;
    
    /**
     * 
    * @Title: queryCusByKey 
    * @Description: 根据客户秘钥查询客户信息
    * @param: @param secretkey
    * @return: nb_cus    
    * @Date: 2019年7月25日 上午11:58:05  
     */
    public nb_cus queryCusByKey(String secretkey){
    	return cusDao.selectCusByKey(secretkey);
    }
}
