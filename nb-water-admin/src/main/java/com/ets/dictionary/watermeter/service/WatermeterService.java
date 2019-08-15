package com.ets.dictionary.watermeter.service;

import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.dictionary.watermeter.dao.WatermeterDao;
import com.ets.dictionary.watermeter.entity.nb_watermeter_dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WatermeterService {
	
	@Resource
    WatermeterDao watermeterDao;

	public List<nb_watermeter_dict> getWatermeter(Map<String, Object> map) {
		try {
			return watermeterDao.selectWatermeter(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<nb_watermeter_dict> getWatermeterAll() {
		try {
			return watermeterDao.selectWatermeterAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount() {
		try {
			return watermeterDao.selectCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void opentionWatermeter(nb_watermeter_dict watermeter) {
		try {
			if(watermeter.getId()!=null && !watermeter.getId().equals(""))
			{
				watermeterDao.updateWatermeter(watermeter);
			}
			else
			{
				watermeter.setId(UUIDUtils.getUUID());
				watermeter.setCtime(DateTimeUtils.getnowdate());
				watermeterDao.insertWatermeter(watermeter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public nb_watermeter_dict infoWatermeter(String id) {
		try {
			return watermeterDao.infoWatermeter(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteWatermeter(String[] id) {
		try {
			watermeterDao.deleteWatermeter(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String infoWatermeter(String[] id) {
		try {
			String name = "";
			List<nb_watermeter_dict> wl = watermeterDao.infoWatermeterList(id);
			if(wl.size() > 0){
				for (nb_watermeter_dict w : wl) {
					name += w.getType()+",";
				}
			}
			return name.substring(0,name.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
