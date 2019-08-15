package com.ets.system.cususer.dao;

import com.ets.system.cususer.entity.nb_cus_user;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-22 20:30
 */
public interface CusUserDao {
    public List<nb_cus_user> getCustomerUsers(Map map);
    public void addCustomerUser(nb_cus_user entity);
    public void deleteCustomerUser(String id[]);
    public void updateCustomerUser(nb_cus_user entity);
    public nb_cus_user infoCustomerUser(String id);
    public long getCount(Map map);
    public List<Map> login(Map map);
    public List<nb_cus_user> selectCustomerUsersType(Map<?, ?> map);
    //检查用户账号的唯一性
    public int isCheckCustomerUser(Map map);
    public void editCustomerUser(nb_cus_user entity);
    public void updateCustomerUserPass(nb_cus_user entity);

    public  List<nb_cus_user> getCusUsers(String cid);

    public void restPassword(Map map);
}
