package com.ets.system.cus_region.dao;

import java.util.List;

import com.ets.system.cus_region.entity.tb_cus_region_area;
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;

public interface CusRegionDao {

	void deleteRegionProvince(String cusid);
	
	void deleteRegionCity(String cusid);
	
	void deleteRegionArea(String cusid);

	void insertCusRegionProvince(tb_cus_region_province region);
	
	void insertCusRegionCity(tb_cus_region_city region);
	
	void insertCusRegionArea(tb_cus_region_area region);

	List<tb_cus_region_province> selectCusRegionProvince(String cusid);
	
	List<tb_cus_region_city> selectCusRegionCity(tb_cus_region_city city);
	
	List<tb_cus_region_area> selectCusRegionArea(tb_cus_region_area area);

}
