package com.ets.business.statistics.owner.service;

import com.ets.business.statistics.owner.dao.OwnerStatisticDao;
import com.ets.business.statistics.owner.entity.nb_owner_water_statistics;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.common.dtree.Data;
import com.ets.common.dtree.DtreeEntity;
import com.ets.common.dtree.Status;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小区楼栋维护
 * @author wh
 *
 */
@Service
@Transactional
public class OwnerStatisticService {
	
    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    AreaService areaService;
	
	@Resource
    OwnerStatisticDao ownerStatisticDao;

	public void addOwnerStatistic(nb_owner_water_statistics ows){
		
		ows.setId(UUIDUtils.getUUID());
		//ows.setCtime("2019-04-23 14:55:00");
		ows.setCtime(DateTimeUtils.getnowdate());
		ownerStatisticDao.insertOwnerStatistic(ows);
	}
	
	public List<nb_owner_water_statistics> queryOwnerStatisticDay(String imei){
		try {
			return ownerStatisticDao.selectOwnerStatisticDay(imei);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<nb_owner_water_statistics> queryOwnerStatisticDay(Map<String,Object> map){
		try {
			return ownerStatisticDao.selectOwnerStatisticDayMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<nb_owner_water_statistics> queryOwnerStatisticDayMapPage(Map<String,Object> map){
		try {
			return ownerStatisticDao.selectOwnerStatisticDayMapPage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public long getCount(Map<String, Object> map) {
		try {
			return ownerStatisticDao.getCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<nb_owner_water_statistics> queryOwnerStatisticMonth(Map<String,String> map){
		try {
			return ownerStatisticDao.selectOwnerStatisticMonth(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<nb_owner_water_statistics> queryOwnerStatisticMonthPage(Map<String,Object> map){
		try {
			return ownerStatisticDao.selectOwnerStatisticMonthPage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCountYear(Map<String, Object> map) {
		try {
			return ownerStatisticDao.getCountYear(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	  public DtreeEntity getProvinces()
	    {
	        List<tb_province> provinceList = provinceService.getTreeProvince();
	        DtreeEntity dtreeEntity = new DtreeEntity();
	        Status status = new Status();
	        List<Data> dataList = new ArrayList<Data>();
	        for (tb_province pro : provinceList)
	        {
	            Data data = new Data();
	            data.setId(pro.getProvinceid());
	            data.setLevel("1");
	            data.setParentId(pro.getProvinceid());
	            data.setTitle(pro.getProvince());
	            data.setIsLast(false);
	            dataList.add(data);
	        }
	        dtreeEntity.setData(dataList);
	        status.setCode(200);
	        status.setMessage("加载完成");
	        dtreeEntity.setStatus(status);
	        return dtreeEntity;
	    }

	    public DtreeEntity getCitys(String pid)
	    {
	        List<tb_city> cityList = cityService.getListCitys(pid);

	        DtreeEntity dtreeEntity = new DtreeEntity();
	        Status status = new Status();
	        List<Data> dataList = new ArrayList<Data>();
	        for (tb_city city : cityList)
	        {
	            Data data = new Data();
	            data.setId(city.getCityid());
	            data.setLevel("2");
	            data.setParentId(city.getCityid());
	            data.setTitle(city.getCity());
	            data.setIsLast(false);
	            dataList.add(data);
	        }
	        dtreeEntity.setData(dataList);
	        status.setCode(200);
	        status.setMessage("加载完成");
	        dtreeEntity.setStatus(status);
	        return dtreeEntity;
	    }

	    public DtreeEntity getAreas(String pid)
	    {
	        List<tb_area> areaList =  areaService.getListByfather(pid);
	        DtreeEntity dtreeEntity = new DtreeEntity();
	        Status status = new Status();
	        List<Data> dataList = new ArrayList<Data>();
	        for (tb_area area : areaList)
	        {
	            Data data = new Data();
	            data.setId(area.getId());
	            data.setLevel("3");
	            data.setParentId(pid);
	            data.setTitle(area.getArea());
	            data.setIsLast(true);
	            dataList.add(data);
	        }
	        dtreeEntity.setData(dataList);
	        status.setCode(200);
	        status.setMessage("加载完成");
	        dtreeEntity.setStatus(status);
	        return dtreeEntity;
	    }
}
