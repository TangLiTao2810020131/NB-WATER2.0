package com.ets.business.region.door.service;

import com.ets.business.region.door.dao.DoorDao;
import com.ets.business.region.door.entity.nb_residential_door;
import com.ets.common.UUIDUtils;
import com.ets.common.dtree.Data;
import com.ets.common.dtree.DtreeEntity;
import com.ets.common.dtree.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 小区门牌号维护
 * @author wh
 *
 */
@Service
@Transactional
public class DoorService {
	
	@Resource
	DoorDao doorDao;

	public List<nb_residential_door> getDoor(Map<String, Object> map) {
		try {
			return doorDao.selectDoor(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 根据条件欻性能接入类型字典表总数
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		try {
			return doorDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void opentionDoor(nb_residential_door door) {
		try {
			if(door.getId()!=null && !door.getId().equals(""))
			{
				doorDao.updateDoor(door);
			}
			else
			{
				door.setId(UUIDUtils.getUUID());
				door.setCtime(new Timestamp(System.currentTimeMillis()));
				doorDao.insertDoor(door);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public nb_residential_door infoDoor(String id) {
		try {
			return doorDao.infoDoor(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteDoor(String[] id) {
		try {
			doorDao.deleteDoor(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String infoDoor(String[] id) {
		try {
			String doornum = "";
			List<nb_residential_door> door = doorDao.infoDoorList(id);
			if(door.size() > 0){
				for (nb_residential_door d : door) {
					doornum += d.getDoornum()+",";
				}
			}
			return doornum.substring(0,doornum.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<nb_residential_door> getListDoor(Map<String, Object> map) {
		try {
			return doorDao.selectListDoor(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<String> getListOwnerDoor(Map<String, Object> map) {
		try {
			return doorDao.selectOwnerDoor(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<String> getListEquipmentDoor(Map<String, Object> map) {
		try {
			return doorDao.selectEquipmentDoor(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public DtreeEntity getTreeDoor(String customerId, String parentId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		map.put("buildingid", parentId);
        List<nb_residential_door> doorList = doorDao.selectListDoor(map);

        DtreeEntity dtreeEntity = new DtreeEntity();
        Status status = new Status();
        List<Data> dataList = new ArrayList<Data>();
        for (nb_residential_door door : doorList)
        {
            Data data = new Data();
            data.setId(door.getId());
            data.setLevel("2");
            data.setParentId(parentId);
            data.setTitle(door.getDoornum());
            data.setIsLast(true);
            dataList.add(data);
        }
        dtreeEntity.setData(dataList);
        status.setCode(200);
        status.setMessage("加载完成");
        dtreeEntity.setStatus(status);
        return dtreeEntity;
	}


	public int isCheckDoor(Map<String, Object> map) {
		int num = 0;
		try {
			num = doorDao.isCheckDoor(map);
			return num;
		}catch (Exception e){
			e.printStackTrace();
			num = -1;
		}
		return num;
	}

	public List<String> getListDoorId(Map<String, Object> map) {
		try {
			return doorDao.selectListDoorId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
