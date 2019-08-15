package com.ets.business.region.building.service;

import com.ets.business.region.building.dao.BuildingDao;
import com.ets.business.region.building.entity.nb_residential_building;
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
 * 小区楼栋维护
 * @author wh
 *
 */
@Service
@Transactional
public class BuildingService {
	
	@Resource
    BuildingDao buildingailsDao;

	public List<nb_residential_building> getBuilding(Map<String, Object> map) {
		
		try {
			return buildingailsDao.selectBuilding(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getCount(Map<String, Object> map) {
		try {
			return buildingailsDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public nb_residential_building infoBuilding(String id) {
		try {
			return buildingailsDao.infoBuilding(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void opentionBuilding(nb_residential_building residential) {
		try {
			if(residential.getId()!=null && !residential.getId().equals(""))
			{
				buildingailsDao.updateBuilding(residential);
			}
			else
			{
				residential.setId(UUIDUtils.getUUID());
				residential.setCtime(new Timestamp(System.currentTimeMillis()));
				buildingailsDao.insertBuilding(residential);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void deleteBuilding(String[] id) {
		try {
			buildingailsDao.deleteBuilding(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String infoBuilding(String[] id) {
		try {
			String building = "";
			List<nb_residential_building> list = buildingailsDao.infoBuildingList(id);
			if(list.size() > 0){
				for (nb_residential_building residential : list) {
					building += residential.getBuilding()+",";
				}
			}
			return building.substring(0,building.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_residential_building> getListBuilding(Map<String,String> map) {
		try {
			return buildingailsDao.selectListBuilding(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DtreeEntity getTreeBuilding(String customerId, String residentialid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		map.put("residentialid", residentialid);
        List<nb_residential_building> buildList = buildingailsDao.getTreeBuilding(map);
        DtreeEntity dtreeEntity = new DtreeEntity();
        Status status = new Status();
        List<Data> dataList = new ArrayList<Data>();
        for (nb_residential_building build : buildList)
        {
            Data data = new Data();
            data.setId(build.getId());
            data.setLevel("1");
            data.setParentId(build.getId());
            data.setTitle(build.getBuilding());
            data.setIsLast(false);
            dataList.add(data);
        }
        dtreeEntity.setData(dataList);
        status.setCode(200);
        status.setMessage("加载完成");
        dtreeEntity.setStatus(status);
        return dtreeEntity;
	}

	public int isCheckbuilding(Map<String, Object> map) {
		int num = 0;
		try {
			num = buildingailsDao.isCheckbuilding(map);
			return num;
		}catch (Exception e){
			e.printStackTrace();
			num = -1;
		}
		return num;
	}

	public List<String> getListBuildId(Map<String,String> map) {
		try {
			return buildingailsDao.selectListBuildId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
