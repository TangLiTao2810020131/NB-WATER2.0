package com.ets.business.owner.service;

import com.ets.business.owner.dao.OwnerDao;
import com.ets.business.owner.entity.OwnerModel;
import com.ets.business.owner.entity.nb_owner;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 户主操作数据库类
 * @author wh
 *
 */
@Service
@Transactional
public class OwnerService {
	
	@Resource
	OwnerDao ownerDao;

	public List<nb_owner> getOwner(Map<String, Object> map) {
		try {
			return ownerDao.selectOwner(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return ownerDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void opentionOwner(nb_owner owner) {
		try {
			if(owner.getId()!=null && !owner.getId().equals(""))
			{
				ownerDao.updateOwner(owner);
			}
			else
			{
				owner.setId(UUIDUtils.getUUID());
				owner.setCtime(DateTimeUtils.getnowdate());
				ownerDao.insertOwner(owner);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OwnerModel ownerInfo(Map<String, Object> map) {
		try {
			return ownerDao.ownerInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public nb_owner infoOwner(Map<String, Object> map) {
		try {
			return ownerDao.infoOwner(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public void deleteOwner(String[] id) {
		try {
			ownerDao.deleteOwner(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String infoOwner(String[] id) {
		try {
			String usernma = "";
			List<nb_owner> list = ownerDao.infoOwnerList(id);
			if(list.size() > 0){
				for (nb_owner owner : list) {
					usernma += owner.getUsername()+",";
				}
			}
			return usernma.substring(0,usernma.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String infoUser(String[] id) {
		try {
			String usernma = "";
			List<nb_owner> list = ownerDao.infoUser(id);
			if(list.size() > 0){
				for (nb_owner owner : list) {
					if(owner==null)
					{
						usernma+="无";
					}else{
						usernma += owner.getUsername()+",";
					}
				}
			}
			return usernma.substring(0,usernma.length()-1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_owner> getOwnerList(String customerId) {
		try {
			return ownerDao.selectOwnerList(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int isCkeckUserAccount(Map<String, Object> map) {
		int sum = 0;
		try {
			sum = ownerDao.isCkeckUserAccount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	//过户更新
	public void transferOwner(nb_owner owner){
	    ownerDao.transferOwner(owner);
    }
	
	public List<nb_owner> queryOwneDoorId(Map<String, String> map) {
		try {
			return ownerDao.queryOwneDoorId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_owner> queryOwnerCusId(Map<String, String> map) {
		try {
			return ownerDao.queryOwnerCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public nb_owner queryOwner(Map<String, Object> map) {
		try {
			return ownerDao.queryOwner(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
