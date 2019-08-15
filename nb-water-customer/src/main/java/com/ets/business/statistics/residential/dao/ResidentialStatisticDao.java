package com.ets.business.statistics.residential.dao;

import com.ets.business.statistics.residential.entity.LdModel;
import com.ets.business.statistics.residential.entity.nb_residential_water_statistics;

import java.util.List;
import java.util.Map;

public interface ResidentialStatisticDao {
	
	public List<nb_residential_water_statistics> selectResidentialStatisticDay(String id);

	public List<nb_residential_water_statistics> selectResidentialStatisticDayMap(Map<String, String> map);

	public List<nb_residential_water_statistics> selectResidentialStatisticMonthMap(Map<String, String> map);

	public long selectCountDay(Map<String, Object> map);

	public List<nb_residential_water_statistics> selectResidentialStatisticDayPage(Map<String, Object> map);

	public long selectCountYear(Map<String, Object> map);

	public List<nb_residential_water_statistics> selectResidentialStatisticMonthPage(Map<String, Object> map);

	public List<LdModel> selectLDStatistic(Map<String, Object> map);

	public long selectLDCount(Map<String, Object> map);

	List<nb_residential_water_statistics> selectLDStatisticDayPage(Map<String, Object> map);

	long selectLDCountDay(Map<String, Object> map);

	List<nb_residential_water_statistics> selectLDStatisticMonthPage(Map<String, Object> map);

	long selectLDCountYear(Map<String, Object> map);

	List<nb_residential_water_statistics> selectLDStatisticDayMap(Map<String, String> map);

	List<nb_residential_water_statistics> selectLDStatisticDay(String id);

	List<nb_residential_water_statistics> selectLDStatisticMonth(Map<String, String> map);

}
