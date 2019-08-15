package com.ets.system.cus.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.DateTimeUtils;
import com.ets.common.ObjectCode;
import com.ets.common.RandomUtil;
import com.ets.common.UUIDUtils;
import com.ets.system.cus.dao.CusDao;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus_daelaytime.dao.BasicNumDao;
import com.ets.system.cus_daelaytime.dao.DeliveryDao;
import com.ets.system.cus_daelaytime.dao.ValveControlDao;
import com.ets.system.cus_daelaytime.entity.nb_delay_time_basicnum;
import com.ets.system.cus_daelaytime.entity.nb_delay_time_delivery;
import com.ets.system.cus_daelaytime.entity.nb_delay_time_valvecontrol;
import com.ets.system.cus_job.dao.JobDao;
import com.ets.system.cus_job.entity.nb_schedule_job;

/**
 * @author 吴浩
 * @create 2019-01-22 20:26
 */
@Service
@Transactional
public class CusService {

    @Resource
    CusDao cusDao;

    @Resource
    BasicNumDao basicNumDao;
    @Resource
    DeliveryDao deliveryDao;
    @Resource
    ValveControlDao valveControlDao;
    @Resource
    private JobDao jobDao;

    public List<nb_cus> getCustomers(Map map)
    {
        return cusDao.getCustomers(map);
    }

    public void insetCustomer(nb_cus entity)
    {
        if(entity.getId()!=null && !entity.getId().equals(""))
        {
            cusDao.updateCustomer(entity);
        }
        else
        {
            entity.setId(UUIDUtils.getUUID());
            entity.setSecretkey(RandomUtil.getIntString(16));	
            cusDao.addCustomer(entity);
            init(entity.getId());
        }
    }

    public void init(String customerId){

        nb_delay_time_basicnum basicnum = new nb_delay_time_basicnum();
        nb_delay_time_delivery delivery = new nb_delay_time_delivery();
        nb_delay_time_valvecontrol valvecontrol = new nb_delay_time_valvecontrol();

        basicnum.setId(UUIDUtils.getUUID());
        basicnum.setValue("5");
        basicnum.setCustomerId(customerId);

        delivery.setId(UUIDUtils.getUUID());
        delivery.setValue("6");
        delivery.setCustomerId(customerId);

        valvecontrol.setId(UUIDUtils.getUUID());
        valvecontrol.setValue("7");
        valvecontrol.setCustomerId(customerId);

        basicNumDao.add(basicnum);
        deliveryDao.add(delivery);
        valveControlDao.add(valvecontrol);

        nb_schedule_job job = new nb_schedule_job();
        job.setJobid(UUIDUtils.getUUID());
        job.setJobname("自动结算");
        job.setJobgroup("Balance"+job.getExecutordate()+customerId);
        job.setCronexpression("0 00 00 1 * ?");
        job.setIsconcurrent(ObjectCode.TASK_CONCURRENT_IS);
        job.setCustomercode(customerId);
        job.setJobstatus(ObjectCode.TASK_STATUS_NOT_RUNNING);
        job.setSpringid("task");
        job.setBeanclass("com.ets.business.timertask.task.Task");
        job.setMethodname("runBalance");
        job.setExecutordate("1");
        job.setCreatetime(DateTimeUtils.getnowdate());
        jobDao.insertJob(job);
    }

    public nb_cus infoCustomer(String id)
    {
        return cusDao.infoCustomer(id);
    }

    public void deleteCustomer(String id[])
    {
        cusDao.deleteCustomer(id);
    }


    public long getCount()
    {
        return cusDao.getCount();
    }
    
	public nb_cus infoCustomerByName(String customerName) {
		return cusDao.selectCustomerByName(customerName);
	}
    public long isCheckCustomerName(String customerName){return cusDao.isCheckCustomerName(customerName);}
    
    public nb_cus queryCusByKey(String secretkey){
    	return cusDao.selectCusByKey(secretkey);
    }
}
