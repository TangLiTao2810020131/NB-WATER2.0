package com.ets.business.commandsendlog.dao;

import com.ets.business.commandsendlog.entity.nb_command_send_log;
import java.util.Map;

/**
 * 
 * @ClassName:     CommandSendLogDao.java 
 * @Description:   命令下发数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:24:02
 */
public interface CommandSendLogDao {


	/**
	 * 
	* @Title: insertCommandSendLog 
	* @Description: 新增命令下发记录
	* @param: @param entity 命令实体 
	* @return: void    
	* @Date: 2019年7月25日 下午6:23:03  
	 */
	void insertCommandSendLog(nb_command_send_log entity);

	/**
	 * 
	* @Title: updateCommandSendLog 
	* @Description: 修改下发命令记录
	* @param: @param entity  命令记录实体
	* @return: void    
	* @Date: 2019年7月25日 下午6:23:18  
	 */
	void updateCommandSendLog(nb_command_send_log entity);


	/**
	 * 
	* @Title: info 
	* @Description: 根据commandId查询命令发送记录信息
	* @param: @param map （commandId：命令ID）
	* @return: nb_command_send_log    
	* @Date: 2019年7月25日 下午6:23:39  
	* @throws
	 */
	nb_command_send_log info(Map<String, Object> map);

}
