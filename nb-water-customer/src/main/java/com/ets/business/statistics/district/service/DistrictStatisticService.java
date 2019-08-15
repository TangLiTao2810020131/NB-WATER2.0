package com.ets.business.statistics.district.service;

import com.ets.business.statistics.district.dao.DistrictStatisticDao;
import com.ets.business.statistics.district.entity.nb_district_water_statistics;
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
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;
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
public class DistrictStatisticService {
	
    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    AreaService areaService;
	
	@Resource
    DistrictStatisticDao districtStatisticDao;

	public void addDistrictStatistic(nb_district_water_statistics ows){
		
		ows.setId(UUIDUtils.getUUID());
		ows.setCtime("2019-04-23 14:55:00");
	}
	
    public DtreeEntity getProvinces(List<tb_cus_region_province> regionP)
    {
        List<tb_province> provinceList = provinceService.getTreeProvince();
        DtreeEntity dtreeEntity = new DtreeEntity();
        Status status = new Status();
        List<Data> dataList = new ArrayList<Data>();
        
        for (tb_cus_region_province p : regionP) {
	        for (tb_province pro : provinceList)  {

	        	if(pro.getProvinceid().equals(p.getProvinceid())){
		        	Data data = new Data();
		            data.setId(pro.getProvinceid());
		            data.setLevel("1");
		            data.setParentId(pro.getProvinceid());
		            data.setTitle(pro.getProvince());
		            data.setIsLast(false);
		            dataList.add(data);
	        	}
	        }
		}
        dtreeEntity.setData(dataList);
        status.setCode(200);
        status.setMessage("加载完成");
        dtreeEntity.setStatus(status);
        return dtreeEntity;
    }

	    public DtreeEntity getCitys(String pid, List<tb_cus_region_city> regionC)
	    {
	        List<tb_city> cityList = cityService.getListCitys(pid);

	        DtreeEntity dtreeEntity = new DtreeEntity();
	        Status status = new Status();
	        List<Data> dataList = new ArrayList<Data>();
	        
	        for (tb_cus_region_city c : regionC) {
	            for (tb_city city : cityList)
	            {
	            	if(c.getCityid().equals(city.getCityid())){
	    	            Data data = new Data();
	    	            data.setId(city.getCityid());
	    	            data.setLevel("2");
	    	            data.setParentId(city.getCityid());
	    	            data.setTitle(city.getCity());
	    	            data.setIsLast(true);
	    	            dataList.add(data);
	            	}
	            }
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

		public List<nb_district_water_statistics> queryDistrictStatisticMonth(Map<String, String> map) {
			return districtStatisticDao.selectDistrictStatisticMonth(map);
		}

		public List<nb_district_water_statistics> queryDistrictStatisticDay(Map<String, String> map) {
			return districtStatisticDao.selectDistrictStatisticDayMap(map);
		}

		public List<nb_district_water_statistics> queryDistrictStatisticDay(String id) {
			return districtStatisticDao.selectDistrictStatisticDay(id);
		}

		public List<nb_district_water_statistics> queryDistrictStatisticDayPage(Map<String, Object> map) {
			return districtStatisticDao.selectDistrictStatisticDayPage(map);
		}

		public long getCountDay(Map<String, Object> map) {
			return districtStatisticDao.getCountDay(map);
		}

		public List<nb_district_water_statistics> queryDistrictStatisticMonthPage(Map<String, Object> map) {
			return districtStatisticDao.selectDistrictStatisticMonthPage(map);
		}

		public long getCountYear(Map<String, Object> map) {
			return districtStatisticDao.getCountYear(map);
		}
}
