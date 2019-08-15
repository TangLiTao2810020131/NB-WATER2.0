package com.ets.business.remote.dao;

/**
 * 
 * @ClassName:     RemoteDao.java 
 * @Description:   操作设备数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:55:01
 */
public interface RemoteDao {

	/**
	 * 
	* @Title: open 
    * @Description: 开阀动作
    * @param: @param deviceId 设备ID     
	* @return: void    
	* @Date: 2019年7月25日 下午7:55:36  
	 */
    public void open(String deviceId);
    
    /**
     * 
    * @Title: close 
    * @Description: 关阀动作
    * @param: @param deviceId   
    * @return: void    
    * @Date: 2019年7月25日 下午7:55:50  
     */
    public void close(String deviceId);

}
