package com.ets.business.region.door.dao;

import com.ets.business.region.door.entity.nb_residential_door;

import java.util.List;
import java.util.Map;

/**
 * 小区门牌号接数据库操作类
 * @author WH
 *
 */
public interface DoorDao {

	List<nb_residential_door> selectDoor(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	void updateDoor(nb_residential_door door);

	void insertDoor(nb_residential_door door);

	nb_residential_door infoDoor(String id);

	void deleteDoor(String[] id);

	List<nb_residential_door> infoDoorList(String[] id);

	List<nb_residential_door> selectListDoor(Map<String, Object> map);

	List<String> selectOwnerDoor(Map<String, Object> map);

	List<String> selectEquipmentDoor(Map<String, Object> map);

	int isCheckDoor(Map<String, Object> map);

	List<String> selectListDoorId(Map<String, Object> map);


}
