package com.ets.business.record.ownertransfer.dao;

import com.ets.business.record.ownertransfer.entity.nb_owner_transferrecord;

import java.util.List;
import java.util.Map;


/**
 * 用户过户链接数据库操作类dao
 * @author WH
 *
 */
public interface OwnertransferDao {

	List<nb_owner_transferrecord> selectTransferrecord(Map<String, Object> map);
	long selectCount(Map<String, Object> map);
	void insertTransferrecord(nb_owner_transferrecord transferrecord);

	

}
