package com.ets.dictionary.pay.service;

import com.ets.common.UUIDUtils;
import com.ets.dictionary.pay.dao.PayDao;
import com.ets.dictionary.pay.entity.nb_pay_dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 付费模式字典表操作数据库类
 * @author wh
 *
 */
@Service
@Transactional
public class PayService {
	
	@Resource
    PayDao payDao;

	/**
	 * 根据条件查询付费模式字典表
	 * @param map
	 * @return
	 */
	public List<nb_pay_dict> getPay(Map<String, Object> map) {
		try {
			return payDao.selectPay(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询付费模式字典表
	 * @param map
	 * @return
	 */
	public List<nb_pay_dict> getPayAll() {
		try {
			return payDao.selectPayAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 查询付费模式字典表总数
	 * @return
	 */
	public long getCount() {
		try {
			return payDao.selectCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 操作付费模式字典表数据
	 * @param Pay
	 */
	public int opentionPay(nb_pay_dict pay) {
		int result=1;
		long num = payDao.fingPayDictype(pay.getPaymode());
		try {

			if(pay.getId()!=null && !pay.getId().equals(""))
			{
				payDao.updatePay(pay);
				return result=-1;
			}
			else {
				if (num == 0) {
					pay.setId(UUIDUtils.getUUID());
					pay.setCtime(new Timestamp(System.currentTimeMillis()));
					pay.setRemarks(pay.getDescribe());
					payDao.insertPay(pay);
					return result=0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据ID查询付费模式字典表信息
	 * @param id
	 * @return
	 */
	public nb_pay_dict infoPay(String id) {
		try {
			return payDao.infoPay(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 根据ID删除付费模式字典表信息
	 * @param id
	 */
	public void deletePay(String[] id) {
		try {
			payDao.deletePay(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String infoPay(String[] id) {
		try {
			String paymode = "";
			List<nb_pay_dict> p = payDao.infoPayList(id);
			if(p.size() > 0){
				for (nb_pay_dict pay : p) {
					paymode += pay.getPaymode()+",";
				}
			}
			return paymode.substring(0,paymode.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
