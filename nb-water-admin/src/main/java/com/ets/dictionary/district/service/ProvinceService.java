package com.ets.dictionary.district.service;

import com.ets.common.UUIDUtils;
import com.ets.dictionary.district.dao.ProvinceDao;
import com.ets.dictionary.district.entity.tb_province;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月1日 上午11:50:39
 * @version :
 * 
 */
@Service
@Transactional
public class ProvinceService {

	@Resource
	ProvinceDao provinceDao;
	
	public List<tb_province> getProvinces(Map map)
	{
		return provinceDao.getProvinces(map);
	}
	
	public void insetProvince(tb_province entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			provinceDao.updateProvince(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			provinceDao.addProvince(entity);
		}
	}
	
	public tb_province infoProvince(String id)
	{
		return provinceDao.infoProvince(id);
	}
	
	public tb_province infoProvinceid(String id)
	{
		return provinceDao.infoProvinceid(id);
	}
	
	public void deleteProvince(String id[])
	{
		provinceDao.deleteProvince(id);
	}
	
	
	public long getCount()
	{
		return provinceDao.getCount();
	}
	
	public List<tb_province> getTreeProvince()
	{
		return provinceDao.selectTreeProvince();
	}

	public int isCkeckProvinceid(String provinceid){return provinceDao.isCkeckProvinceid(provinceid);}
	
}
