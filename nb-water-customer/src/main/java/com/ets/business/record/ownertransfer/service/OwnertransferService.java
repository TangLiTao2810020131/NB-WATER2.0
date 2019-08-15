package com.ets.business.record.ownertransfer.service;

import com.ets.business.record.ownertransfer.dao.OwnertransferDao;
import com.ets.business.record.ownertransfer.entity.nb_owner_transferrecord;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户过户操作dao类
 * @author wh
 *
 */
@Service
@Transactional
public class OwnertransferService {
	
	@Resource
	OwnertransferDao ownertransferDao;

	public List<nb_owner_transferrecord> getTransferrecord(Map<String, Object> map) {
		try {
			return ownertransferDao.selectTransferrecord(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public long getCount(Map<String, Object> map) {
		try {
			return ownertransferDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 操作接入类型字段数据
	 * @param transferrecord
	 */
	public void addTransferrecord(nb_owner_transferrecord transferrecord) {
		try {
				transferrecord.setId(UUIDUtils.getUUID());
				transferrecord.setCtime(DateTimeUtils.getnowdate());
				ownertransferDao.insertTransferrecord(transferrecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
