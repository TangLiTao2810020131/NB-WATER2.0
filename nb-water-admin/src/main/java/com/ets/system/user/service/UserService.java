package com.ets.system.user.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.UUIDUtils;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.user.dao.UserDao;
import com.ets.system.user.entity.tb_user;

/**
 * @version :
 * @author: 姚轶文
 * @date:2018年8月16日 下午2:39:29
 */
@Service
@Transactional
public class UserService {


	@Resource
	UserDao userDao;

	@Resource
	LogOprService logService;

	public List<tb_user> getUsers(Map map)
	{
		return userDao.getUsers(map);
	}
	
	public void addUser(tb_user user)
	{
		userDao.addUser(user);
	}
	
	public void deleteUser(String id[])
	{
		StringBuilder sb=new StringBuilder();
		for (String str : id) {
			tb_user user = infoUser(str);
			sb.append(user.getUsername()+",");
		}
		tb_log_opr log = new tb_log_opr();
		log.setModulename("系统管理-用户管理");
		log.setOprcontent("删除["+sb.substring(0,sb.length()-1)+"]用户");
		logService.addLog(log);

		userDao.deleteUser(id);
	}
	public void updateUser(tb_user user)
	{
		userDao.updateUser(user);
	}
	//新增时进行加密的操作
	public void insetUser(tb_user user)
	{
		if (user.getId()!=null && !user.getId().equals("")){
			userDao.updateUser(user);
		}
		else {
			try {
				user.setId(UUIDUtils.getUUID());
				user.setCtime(new Timestamp(System.currentTimeMillis()));
				String source = user.getPassword();
				//MD5多次加密
				String newPass = new SimpleHash("MD5", source, "1024").toHex();
				user.setPassword(newPass);
				user.setLoginnum("0");
				userDao.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public tb_user infoUser(String id)
	{
		return userDao.infoUser(id);
	}
	
	public long getCount(Map map)
	{
		return userDao.getCount(map);
	}
	
	public void closeUser(String id[])
	{
		for (String str : id) {
			tb_user user = infoUser(str);
			tb_log_opr log = new tb_log_opr();
			log.setModulename("系统管理-用户管理");
			log.setOprcontent("关闭["+user.getUsername()+"]用户");
			logService.addLog(log);
		}
		 userDao.closeUser(id);
	}
	public void openUser(String id[])
	{
		for (String str : id) {
			tb_user user = infoUser(str);
			tb_log_opr log = new tb_log_opr();
			log.setModulename("系统管理-用户管理");
			log.setOprcontent("开通["+user.getUsername()+"]用户");
			logService.addLog(log);
		}
		 userDao.openUser(id);
	}
	public tb_user checkUser(Map<String,String> map)
	{
		return userDao.checkUser(map);
	}
	//检查用户名是否存在
	public int isCheckUserName(String username) {
		int num = 0;
		num = userDao.isCheckUser(username);
		return num;
	}
	//根据用户名查询用户
	public tb_user getUserByUserName(String username){
		tb_user user = userDao.getUserByUserName(username);
		return user;
	}
	//修改密码
	public void updatePassword(Map<String,String> map){
        userDao.updatePassword(map);
	}
	//对应的是index头部信息的用户编辑
	public void editUser(tb_user user){
		userDao.editUser(user);
	}
	//检查输入原密码是否正确
	public tb_user pwdCheck(String id){
		tb_user user = userDao.pwdCheck(id);
		return  user;
	}
	//重置密码
	public void restPassword(String id[])
	{

		String password = new SimpleHash("MD5", "123456", "1024").toHex();
		Map<String,Object> map=new HashMap<>();
		map.put("id",id);
		map.put("password",password);
		userDao.restPassword(map);

	}
}
