package com.ets.system.cus.dao;

import com.ets.system.cus.entity.nb_cus;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-22 20:25
 */
public interface CusDao {
    public List<nb_cus> getCustomers(Map map);
    public void addCustomer(nb_cus entity);
    public void deleteCustomer(String id[]);
    public void updateCustomer(nb_cus entity);
    public nb_cus infoCustomer(String id);
    public long getCount();
    nb_cus selectCustomerByName(String customerName);
    public long isCheckCustomerName(String customerName);
	public nb_cus selectCusByKey(String secretkey);

}
