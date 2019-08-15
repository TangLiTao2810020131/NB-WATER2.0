package com.ets.business.ownerrecord.serivce;

import com.ets.business.ownerrecord.dao.OwnerRecordDao;
import com.ets.business.ownerrecord.entity.nb_owner_record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-02-22 14:34
 */
@Service
@Transactional
public class OwnerRecordService {

	@Resource
	OwnerRecordDao ownerRecordDao;

	public List<nb_owner_record> queryOwnerRecordList(Map<String,Object> map){
		try {
			return ownerRecordDao.getOwnerRecordList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long queryCount(Map<String,Object> map){
		try {
			return ownerRecordDao.getCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public nb_owner_record queryOwnerRecord(Map<String,Object> map){
		try {
			return ownerRecordDao.getOwnerRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addOwnerRecord(nb_owner_record entity){
		try {
			ownerRecordDao.insertOwnerRecord(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editOwnerRecord(nb_owner_record entity){
		try {
			ownerRecordDao.updateOwnerRecord(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
