package com.ets.business.commandsendlog.service;

import com.ets.business.commandsendlog.dao.CommandSendLogDao;
import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     CommandSendLogService.java 
 * @Description:   命令下发业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:22:03
 */
@Service
@Transactional
public class CommandSendLogService {
	
	@Resource
	CommandSendLogDao commandSendLogDao;
	
	/**
	 * 
	* @Title: addCommandSendLog 
	* @Description: 新增命令下发记录
	* @param: @param entity 命令实体
	* @Date: 2019年7月25日 下午6:06:50  
	 */
	public void addCommandSendLog(nb_command_send_log entity){
		try {
			entity.setId(UUIDUtils.getUUID());
			entity.setCtime(DateTimeUtils.getnowdate());
			commandSendLogDao.insertCommandSendLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: editCommandSendLog 
	* @Description: 修改下发命令记录
	* @param: @param entity  命令记录实体
	* @return: void    
	* @Date: 2019年7月25日 下午5:53:08  
	 */
	public void editCommandSendLog(nb_command_send_log entity){
		try {
			entity.setCtime(DateTimeUtils.getnowdate());
			commandSendLogDao.updateCommandSendLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: info 
	* @Description: 根据commandId查询命令发送记录信息
	* @param: @param map （commandId：命令ID）
	* @return: nb_command_send_log    
	* @Date: 2019年7月25日 下午5:46:14  
	 */
	public nb_command_send_log info(Map<String, Object> map) {
		try {
			return commandSendLogDao.info(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
