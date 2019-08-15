package com.ets.dictionary.district.dao;

import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;

import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 上午10:06:07
 * @version :
 * 
 */
public interface CityDao {

	public List<tb_city> getCitys(Map map);
	public void addCity(tb_city entity);
	public void deleteCity(String id[]);
	public void updateCity(tb_city entity);
	public tb_city infoCity(String id);
	public tb_city infoCityid(String id);
	
	public long getCount(String father);
	public List<tb_city> selectListCitys(String father);

	public tb_province findProvinceByCode(String provinceid);

	//根据城市ID查询省份ID
	public String findProvinceIdByCityId(String cityid);
    public int isCkeckCityid(String cityid);
	public List<tb_city> selectListCity();

}
