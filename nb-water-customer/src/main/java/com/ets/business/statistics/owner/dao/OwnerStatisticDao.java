package com.ets.business.statistics.owner.dao;

import com.ets.business.statistics.owner.entity.nb_owner_water_statistics;

import java.util.List;
import java.util.Map;

public interface OwnerStatisticDao {
	
	public void insertOwnerStatistic(nb_owner_water_statistics ows);
	
	public List<nb_owner_water_statistics> selectOwnerStatisticDay(String imei);
	
	public List<nb_owner_water_statistics> selectOwnerStatisticMonth(Map<String, String> map);

	public List<nb_owner_water_statistics> selectOwnerStatisticDayMap(Map<String, Object> map);
	
	public List<nb_owner_water_statistics> selectOwnerStatisticDayMapPage(Map<String, Object> map);
	
	long getCount(Map<String, Object> map);

	public List<nb_owner_water_statistics> selectOwnerStatisticMonthPage(Map<String, Object> map);
	
	long getCountYear(Map<String, Object> map);

}
