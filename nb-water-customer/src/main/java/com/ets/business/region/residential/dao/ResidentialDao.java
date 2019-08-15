package com.ets.business.region.residential.dao;

import com.ets.business.region.residential.entity.ResidentialModel;
import com.ets.business.region.residential.entity.nb_residential_init;

import java.util.List;
import java.util.Map;


/**
 * 小区维护接数据库操作类
 * @author WH
 *
 */
public interface ResidentialDao {

	List<nb_residential_init> selectResidential(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	nb_residential_init infoResidential(String id);

	void updateResidential(nb_residential_init residential);

	void insertResidential(nb_residential_init residential);

	ResidentialModel infoResidentialModel(Map<String, Object> map);

	void deleteResidential(String[] id);

	List<nb_residential_init> infoResidentialList(String[] id);

	List<nb_residential_init> selectListResidential(Map map);


    int isCheckName(Map<String, Object> map);
}
