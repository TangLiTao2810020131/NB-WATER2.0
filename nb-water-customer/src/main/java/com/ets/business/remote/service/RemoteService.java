package com.ets.business.remote.service;

import com.ets.business.remote.dao.RemoteDao;
import com.ets.business.remote.entity.RemoteListEntity;
import com.ets.common.dtree.Data;
import com.ets.common.dtree.DtreeEntity;
import com.ets.common.dtree.Status;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import com.ets.system.cus_region.entity.tb_cus_region_area;
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @create 2018- 11-20 15:19
 */
@Service
@SuppressWarnings("all")
public class RemoteService {
    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    AreaService areaService;

    @Resource
    RemoteDao remoteDao;

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

    public DtreeEntity getAreas(String pid, List<tb_cus_region_area> regionA)
    {
        List<tb_area> areaList =  areaService.getListByfather(pid);
        DtreeEntity dtreeEntity = new DtreeEntity();
        Status status = new Status();
        List<Data> dataList = new ArrayList<Data>();
        
        for (tb_cus_region_area a : regionA) {
            for (tb_area area : areaList)
            {
               if(a.getAreaid().equals(area.getId())){
            	   Data data = new Data();
                   data.setId(area.getId());
                   data.setLevel("3");
                   data.setParentId(pid);
                   data.setTitle(area.getArea());
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

    public List<RemoteListEntity> list(Map map)
    {
        return remoteDao.list(map);
    }

    public long getCount(Map map)
    {
        return remoteDao.getCount(map);
    }

    public void open(String deviceId)
    {
         remoteDao.open(deviceId);
    }

    public void close(String deviceId)
    {
        remoteDao.close(deviceId);
    }

	public void update(String deviceId) {
		 remoteDao.updateRemote(deviceId);
		
	}
}
