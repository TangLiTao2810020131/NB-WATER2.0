package com.ets.business.region.residential.service;

import com.ets.business.region.residential.dao.ResidentialDao;
import com.ets.business.region.residential.entity.ResidentialModel;
import com.ets.business.region.residential.entity.nb_residential_init;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * 小区维护
 * @author WH
 *
 */
@Service
@Transactional
public class ResidentialService {
	
	@Resource
	ResidentialDao residentialDao;

	public List<nb_residential_init> getResidential(Map<String, Object> map) {
		
		try {
			return residentialDao.selectResidential(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getCount(Map<String, Object> map) {
		try {
			return residentialDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public nb_residential_init infoResidential(String id) {
		try {
			return residentialDao.infoResidential(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void opentionResidential(nb_residential_init residential) {
		try {
			if(residential.getId()!=null && !residential.getId().equals(""))
			{
				residentialDao.updateResidential(residential);
			}
			else
			{
				residential.setId(UUIDUtils.getUUID());
				residential.setCtime(new Timestamp(System.currentTimeMillis()));
				residentialDao.insertResidential(residential);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public ResidentialModel infoResidentialModel(Map<String, Object> map) {
		try {
			return residentialDao.infoResidentialModel(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteResidential(String[] id) {
		try {
			residentialDao.deleteResidential(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String infoResidential(String[] id) {
		try {
			String name = "";
			List<nb_residential_init> list = residentialDao.infoResidentialList(id);
			if(list.size() > 0){
				for (nb_residential_init residential : list) {
					name += residential.getName()+",";
				}
			}
			return name.substring(0,name.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_residential_init> getListResidential(Map<String,String> map) {
		try {
			return residentialDao.selectListResidential(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int isCheckName(Map<String, Object> map) {
		int num = 0;
		try {
			num = residentialDao.isCheckName(map);
			return num;
		}catch (Exception e){
			e.printStackTrace();
			num = -1;
		}
		return num;
	}
}
