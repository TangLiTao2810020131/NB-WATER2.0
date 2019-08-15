package com.ets.dictionary.district.dao;

import com.ets.dictionary.district.entity.tb_province;

import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月1日 上午11:47:53
 * @version :
 * 
 */ 
public interface ProvinceDao {

	public List<tb_province> getProvinces(Map map);
	public void addProvince(tb_province entity);
	public void deleteProvince(String id[]);
	public void updateProvince(tb_province entity);
	public tb_province infoProvince(String id);
	public tb_province infoProvinceid(String id);
	public long getCount();
	public List<tb_province> selectTreeProvince();
	public int isCkeckProvinceid(String provinceid);
}
