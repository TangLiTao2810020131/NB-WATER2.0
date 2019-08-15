package com.ets.dictionary.district.dao;

import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;

import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 下午3:01:41
 * @version :
 * 
 */
public interface AreaDao {

	public List<tb_area> getAreas(Map map); 
	public void addArea(tb_area entity);
	public void deleteArea(String id[]);
	public void updateArea(tb_area entity);
	public tb_area infoArea(String id);
	public long getCount(String father);
	public List<tb_area> selectListByfather(String father);

	//根据城市代码查询地区
    tb_city findCityByCode(String cityid);

    int isCkeckAreaid(String areaid);
	public List<tb_area> selectListArea(String cityid);
	public List<tb_area> getAreaAll(Map map);
}
