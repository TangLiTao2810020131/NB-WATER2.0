package com.ets.system.cus_region.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ets.common.UUIDUtils;
import com.ets.system.cus_region.dao.CusRegionDao;
import com.ets.system.cus_region.entity.tb_cus_region_area;
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;

@Service
@Transactional
public class CusRegionSerivce {
	
	@Resource
	CusRegionDao cusRegionDao;

	public void deleteRegion(String cusid) {
		
		try {
			cusRegionDao.deleteRegionProvince(cusid);
			cusRegionDao.deleteRegionCity(cusid);
			cusRegionDao.deleteRegionArea(cusid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCusRegionProvince(tb_cus_region_province regionP) {
		try {
			regionP.setId(UUIDUtils.getUUID());
			cusRegionDao.insertCusRegionProvince(regionP);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addCusRegionCity(tb_cus_region_city regionC) {
		try {
			regionC.setId(UUIDUtils.getUUID());
			cusRegionDao.insertCusRegionCity(regionC);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addCusRegionArea(tb_cus_region_area regionA) {
		try {
			regionA.setId(UUIDUtils.getUUID());
			cusRegionDao.insertCusRegionArea(regionA);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<tb_cus_region_province> queryCusRegionProvince(String cusid){
		try {
			return cusRegionDao.selectCusRegionProvince(cusid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<tb_cus_region_city> queryCusRegionCity(String cusid,String provinceid){
		try {
			tb_cus_region_city city = new tb_cus_region_city();
			city.setCusid(cusid);
			city.setProvinceid(provinceid);
			return cusRegionDao.selectCusRegionCity(city);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<tb_cus_region_area> queryCusRegionArea(String cusid,String cityid){
		try {
			tb_cus_region_area area = new tb_cus_region_area();
			area.setCusid(cusid);
			area.setCityid(cityid);
			return cusRegionDao.selectCusRegionArea(area);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
