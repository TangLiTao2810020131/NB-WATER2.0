package com.ets.business.region.building.dao;

import com.ets.business.region.building.entity.nb_residential_building;

import java.util.List;
import java.util.Map;

/**
 * 小区楼栋数据库操作类
 * @author WH
 *
 */
public interface BuildingDao {

	List<nb_residential_building> selectBuilding(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	nb_residential_building infoBuilding(String id);

	void updateBuilding(nb_residential_building details);

	void insertBuilding(nb_residential_building details);

	void deleteBuilding(String[] id);

	List<nb_residential_building> infoBuildingList(String[] id);

	List<nb_residential_building> selectListBuilding(Map map);

	List<nb_residential_building> getTreeBuilding(Map<String, Object> map);

	int isCheckbuilding(Map<String, Object> map);

	List<String> selectListBuildId(Map<String, String> map);

	
	
}
