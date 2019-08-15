package com.ets.business.statistics.residential.service;

import com.ets.business.statistics.residential.dao.ResidentialStatisticDao;
import com.ets.business.statistics.residential.entity.LdModel;
import com.ets.business.statistics.residential.entity.nb_residential_water_statistics;
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
public class ResidentialStatisticService {
	
    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    AreaService areaService;
	
	@Resource
	ResidentialStatisticDao residentialStatisticDao;

	
	public List<nb_residential_water_statistics> queryResidentialStatisticDay(String id){
		return residentialStatisticDao.selectResidentialStatisticDay(id);
	}
	
	public List<nb_residential_water_statistics> queryResidentialStatisticDay(Map<String,String> map){
		return residentialStatisticDao.selectResidentialStatisticDayMap(map);
	}
	
	public List<nb_residential_water_statistics> queryResidentialStatisticMonth(Map<String, String> map) {
		return residentialStatisticDao.selectResidentialStatisticMonthMap(map);
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

		public long getCountDay(Map<String, Object> map) {
			return residentialStatisticDao.selectCountDay(map);
		}

		public List<nb_residential_water_statistics> queryResidentialStatisticDayPage(Map<String, Object> map) {
			return residentialStatisticDao.selectResidentialStatisticDayPage(map);
		}

		public long getCountYear(Map<String, Object> map) {
			return residentialStatisticDao.selectCountYear(map);
		}

		public List<nb_residential_water_statistics> queryResidentialStatisticMonthPage(Map<String, Object> map) {
			return residentialStatisticDao.selectResidentialStatisticMonthPage(map);
		}

		public List<LdModel> getLDStatistic(Map<String, Object> map) {
			return residentialStatisticDao.selectLDStatistic(map);
		}

		public long getLDCount(Map<String, Object> map) {
			return residentialStatisticDao.selectLDCount(map);
		}

	public List<nb_residential_water_statistics> getLDStatisticDayPage(Map<String, Object> map) {
		return residentialStatisticDao.selectLDStatisticDayPage(map);
	}

	public long getLDCountDay(Map<String, Object> map) {
		return residentialStatisticDao.selectLDCountDay(map);
	}

	public List<nb_residential_water_statistics> getLDStatisticMonthPage(Map<String, Object> map) {

		return residentialStatisticDao.selectLDStatisticMonthPage(map);
	}

	public long getLDCountYear(Map<String, Object> map) {
		return residentialStatisticDao.selectLDCountYear(map);
	}

	public List<nb_residential_water_statistics> getLDStatisticDay(Map<String, String> map) {

		return residentialStatisticDao.selectLDStatisticDayMap(map);
	}

	public List<nb_residential_water_statistics> getLDStatisticDay(String id) {
		return residentialStatisticDao.selectLDStatisticDay(id);
	}

	public List<nb_residential_water_statistics> getLDStatisticMonth(Map<String, String> map) {
		return residentialStatisticDao.selectLDStatisticMonth(map);
	}
}
