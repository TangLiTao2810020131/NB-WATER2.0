package com.ets.system.batch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.batch.dao.BatchDao;
import com.ets.system.batch.entity.tb_sys_batch;

/**
 * 水表批次操作数据库类
 * @author wh
 *
 */
@Service
@Transactional
public class BatchService {
	
	@Resource
	BatchDao batchDao;

	public tb_sys_batch info(String id) {
		try {
			return batchDao.info(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getCount(Map<String, Object> map) {
		try {
			return batchDao.getCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<tb_sys_batch> getListData(Map<String, Object> map) {
		try {
			return batchDao.getListData(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void opention(tb_sys_batch entity) {
		
		if(entity.getId() != null && !entity.getId().equals(""))
		{
			batchDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			entity.setCtime(DateTimeUtils.getnowdate());
			batchDao.insert(entity);
		}
	}

	public void delete(String[] id) {
		batchDao.delete(id);		
	}

	public List<tb_sys_batch> getAll() {
		try {
			return batchDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
