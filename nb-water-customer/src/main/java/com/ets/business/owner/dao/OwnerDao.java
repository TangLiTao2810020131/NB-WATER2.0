package com.ets.business.owner.dao;

import com.ets.business.owner.entity.OwnerModel;
import com.ets.business.owner.entity.nb_owner;

import java.util.List;
import java.util.Map;

/**
 * 户主连接数据库dao
 * @author wh
 *
 */
public interface OwnerDao {

	List<nb_owner> selectOwner(Map<String, Object> map);

	long selectCount(Map<String, Object> map);
	void updateOwner(nb_owner owners);

	void insertOwner(nb_owner owners);

	nb_owner infoOwner(Map<String, Object> map);

	void deleteOwner(String[] id);

	List<nb_owner> infoOwnerList(String[] id);

	List<nb_owner> infoUser(String[] id);

	List<nb_owner> selectOwnerList(String customerId);

	OwnerModel ownerInfo(Map<String, Object> map);

	int isCkeckUserAccount(Map<String, Object> map);

	//过户更新
	void transferOwner(nb_owner owner);

	List<nb_owner> queryOwneDoorId(Map<String, String> map);

	List<nb_owner> queryOwnerCusId(Map<String, String> map);

	nb_owner queryOwner(Map<String, Object> map);

}
