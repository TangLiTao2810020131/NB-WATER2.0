package com.ets.system.log.opr.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.log.opr.dao.LogOprDao;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.user.entity.tb_user;

/**
 * @author: 姚轶文
 * @date:2018年9月5日 下午4:27:31
 * @version :
 * 
 */
@Service
@Transactional
public class LogOprService {

	@Resource
	LogOprDao logDao;
	
	public List<tb_log_opr> getLogs(Map map)
		{
			return logDao.getLogs(map);
		}

		public long getCount(Map map)
		{
			return logDao.getCount(map);
		}

		public void addLog(tb_log_opr log)
		{
			//tb_user user = (tb_user)SecurityUtils.getSubject().getSession().getAttribute("userSession");

			Object obj = SecurityUtils.getSubject().getSession().getAttribute("userSession");
			if(obj instanceof tb_user)
			{
				tb_user user = (tb_user)obj;
				log.setUsername(user.getUsername());
			}
			else
			{
				Map<String,String> map = (Map)obj;
				log.setUsername((String)map.get("CUSTOMERNAME")+"-"+(String)map.get("USERNAME"));
			}
		
			log.setId(UUIDUtils.getUUID());
			log.setOprtime(DateTimeUtils.getnowdate());
			logDao.addLog(log);
	}
	
	public tb_log_opr infoLog(String id)
	{
		return logDao.infoLog(id);
	}
}
