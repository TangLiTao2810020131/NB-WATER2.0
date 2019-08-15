package com.ets.system.cususer.service;

import com.ets.common.UUIDUtils;
import com.ets.system.cususer.dao.CusUserDao;
import com.ets.system.cususer.entity.nb_cus_user;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-22 20:31
 */
@Service
@Transactional
public class CusUserService {

    @Resource
    CusUserDao cusUserDao;

    public List<nb_cus_user> getCustomerUsers(Map map)
    {
        return cusUserDao.getCustomerUsers(map);
    }

    public void insetCustomerUser(nb_cus_user entity)
    {
        if(entity.getId()!=null && !entity.getId().equals(""))
        {
            //编辑更新 不能更新用户的账号和密码
            cusUserDao.updateCustomerUser(entity);
        }
        else
        {
            //新增对密码进行md5加密
            entity.setId(UUIDUtils.getUUID());
            //MD5对密码加密
            String md5pass = new SimpleHash("MD5", entity.getPassword(), "1024").toHex();
            entity.setPassword(md5pass);
            entity.setLoginnum("0");
            cusUserDao.addCustomerUser(entity);
        }
    }

    public nb_cus_user infoCustomerUser(String id)
    {
        return cusUserDao.infoCustomerUser(id);
    }

    public void deleteCustomerUser(String id[])
    {
        cusUserDao.deleteCustomerUser(id);
    }


    public long getCount(Map map)
    {
        return cusUserDao.getCount(map);
    }

    public List<Map> login(Map map)
    {
        return cusUserDao.login(map);
    }

    public List<nb_cus_user> getCustomerUsersType(Map<?,?> map)
    {
        return cusUserDao.selectCustomerUsersType(map);
    }

    public int isCheckCusUser(Map map){
        int i = cusUserDao.isCheckCustomerUser(map);
        return i;
    }

    public void editCustomerUser(nb_cus_user entity)
    {
        cusUserDao.editCustomerUser(entity);
    }

    public void editCustomerUserPass(nb_cus_user entity)
    {
        cusUserDao.updateCustomerUserPass(entity);
    }

    public List<nb_cus_user> getCusUsers(String cid) {
        return cusUserDao.getCusUsers(cid);
    }

    public void restPassword(String[] id){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id",id);
        String password=new SimpleHash("MD5", "123456", "1024").toHex();
        map.put("password",password);
        cusUserDao.restPassword(map);
    }

}
