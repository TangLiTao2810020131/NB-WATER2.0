package com.ets.system.user.dao;

import com.ets.system.user.entity.tb_user;
import java.util.List;
import java.util.Map;

public interface UsDao {
    public List<tb_user> getUsers(Map map);
    public void addUser(tb_user entity);
    public void deleteUser(String id[]);
    public void updateUser(tb_user entity);
    public tb_user infoUser(String id);
    public long getCount(Map map);
    public void closeUser(String id[]);
    public void openUser(String id[]);
    public tb_user checkUser(Map<String,String> map);
    public int isCheckUser(String username);
    public tb_user getUserByUserName(String username);
    public void updatePassword(Map<String, String> map);
    public tb_user pwdCheck(String id);
    public void editUser(tb_user user);
    public void restPassword(Map map);
}
