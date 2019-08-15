package com.ets.dictionary.district.service;

import com.ets.common.UUIDUtils;
import com.ets.dictionary.district.dao.AreaDao;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 下午3:05:27
 * @version :
 * 
 */
@Service
@Transactional
public class AreaService {

	@Resource
    AreaDao areaDao;
	
	public List<tb_area> getAreas(Map map)
	{
		return areaDao.getAreas(map);
	}
	
	public List<tb_area> getAreaAll(Map map)
	{
		return areaDao.getAreaAll(map);
	}
	
	public void insetArea(tb_area entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			areaDao.updateArea(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			areaDao.addArea(entity);
		}
	}
	
	public tb_area infoArea(String id)
	{
		return areaDao.infoArea(id);
	}
	
	public void deleteArea(String id[])
	{
		areaDao.deleteArea(id);
	}
	
	
	public long getCount(String father)
	{
		return areaDao.getCount(father);
	}
	
	public List<tb_area> getListByfather(String father)
	{
		return areaDao.selectListByfather(father);
	}

	//根据城市代码查询地区
	public tb_city findCityByCode(String cityid)
	{
		return areaDao.findCityByCode(cityid);
	}

	public int isCkeckAreaid(String areaid){
		return   areaDao.isCkeckAreaid(areaid);
	}

	public List<tb_area> getListArea(String cityid) {
		return areaDao.selectListArea(cityid);
	}
}
