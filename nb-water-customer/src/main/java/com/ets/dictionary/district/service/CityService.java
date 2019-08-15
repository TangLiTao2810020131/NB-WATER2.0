package com.ets.dictionary.district.service;

import com.ets.common.UUIDUtils;
import com.ets.dictionary.district.dao.CityDao;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 上午10:08:36
 * @version :
 * 
 */
@Service
@Transactional
public class CityService {

	@Resource
	CityDao cityDao;
	
	public List<tb_city> getCitys(Map map)
	{
		return cityDao.getCitys(map);
	}
	
	public void insetCity(tb_city entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			cityDao.updateCity(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			cityDao.addCity(entity);
		}
	}
	
	public tb_city infoCity(String id)
	{
		return cityDao.infoCity(id);
	}
	
	public tb_city infoCityid(String id)
	{
		return cityDao.infoCityid(id);
	}
	
	
	public void deleteCity(String id[])
	{
		cityDao.deleteCity(id);
	}
	
	
	public long getCount(String father)
	{
		return cityDao.getCount(father);
	}
	
	public List<tb_city> getListCitys(String father)
	{
		return cityDao.selectListCitys(father);
	}

	public tb_province findProvinceByCode(String provinceid)
	{
		return cityDao.findProvinceByCode(provinceid);
	}

	//根据城市ID查询省份ID
	public String findProvinceIdByCityId(String cityid)
	{
		return cityDao.findProvinceIdByCityId(cityid);
	}
	public int isCkeckCityid(String cityid){
		return cityDao.isCkeckCityid(cityid);
	}

	public List<tb_city> getListCity() {
		return cityDao.selectListCity();
	};
}
