package com.ets.business.statistics.district.dao;

import com.ets.business.statistics.district.entity.nb_district_water_statistics;

import java.util.List;
import java.util.Map;


public interface DistrictStatisticDao {
	

	public List<nb_district_water_statistics> selectDistrictStatisticMonth(Map<String, String> map);

	public List<nb_district_water_statistics> selectDistrictStatisticDayMap(Map<String, String> map);

	public List<nb_district_water_statistics> selectDistrictStatisticDay(String id);

	public List<nb_district_water_statistics> selectDistrictStatisticDayPage(Map<String, Object> map);

	public long getCountDay(Map<String, Object> map);

	public List<nb_district_water_statistics> selectDistrictStatisticMonthPage(Map<String, Object> map);

	public long getCountYear(Map<String, Object> map);

}
