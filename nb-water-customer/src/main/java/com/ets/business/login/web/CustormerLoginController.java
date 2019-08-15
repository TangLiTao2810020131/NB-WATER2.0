package com.ets.business.login.web;

import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.loginlog.entity.nb_customer_login_log;
import com.ets.business.loginlog.service.LoginLogService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.common.DateTimeUtils;
import com.ets.common.Message;
import com.ets.common.UUIDUtils;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus.service.CusService;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月6日 下午4:52:16
 * @version :
 * 
 */
@Controller
@RequestMapping("custormerLogin")
public class CustormerLoginController {


	@Resource
	LoginLogService loginLogService;
	@Resource
	LogOprCustomerService logOprCustomerService;
	@Resource
    CusService customerService;
	@Resource
	CustomerUserService customerUserService;
	
	@RequestMapping("login")
	public String login(HttpServletRequest request)
	{
		//SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		return "login";
	}
	
	@RequestMapping("logOut")
	public String logOut(HttpServletRequest request)
	{
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename("系统管理-安全退出");
		log.setOprcontent("访问系统登录页面");
		logOprCustomerService.addLog(log);
		SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		return "redirect:/login/login.action";
	}
	
	/**
	 * 登录验证
	 * @param request 请求
	 * @return  验证信息
	 */
	@RequestMapping(value="loginCheck" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loginCheck(HttpServletRequest request,String customerName ,String username ,String password)
	{
		Gson gson = new Gson();
		//SecurityUtils.getSubject().logout();
		
		Subject subject = SecurityUtils.getSubject();

		nb_cus cus = customerService.infoCustomerByName(customerName);

		username = (username + "-" + cus.getId());
		
		String pass = new SimpleHash("MD5",password,"1024").toHex();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", pass);
		map.put("customerName", customerName);
		
		List<Map> loginList = customerUserService.login(map);
		if(loginList != null && loginList.size() != 0){
			
			if (!subject.isAuthenticated()){
	            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	            try {
	            	// 执行登录. 
	                subject.login(token);
	                //添加日志的操作
					nb_customer_login_log log=new nb_customer_login_log();
					String realname= (String) loginList.get(0).get("REALNAME");
					log.setCustomerName(customerName);
					log.setUsername(realname);
					log.setId(UUIDUtils.getUUID());
					log.setLogintime(DateTimeUtils.getnowdate());
					log.setLoginState("登录成功");
					log.setCustomercode(cus.getId());
	                loginLogService.addLog(request,log);
	                Map loginMap = loginList.get(0);
	                subject.getSession().setAttribute("userSession", loginMap);
					subject.getSession().setAttribute("customerSession", cus);
	        		return gson.toJson(new Message("0","登录成功!"));
	            } 
	            // 所有认证时异常的父类. 
	            catch (Exception ae) {
	            	ae.printStackTrace();

	            }
			}
		}else{
			return gson.toJson(new Message("2","用户名或密码错误!"));
		}

		return null;
	}
	
	/**
	 * 跳转到首页
	 * @return 首页或登录页
	 */
	@RequestMapping("loginSuccess")
	public String loginSuccess(HttpServletRequest request)
	{
		try {
			Subject subject = SecurityUtils.getSubject();
			Map loginMap = (Map) subject.getSession().getAttribute("userSession");
			//将用户"访问系统首页面"信息保存到操作日志
			if(loginMap != null){

				nb_customer_user dbuser = customerUserService.infoCustomerUser(loginMap.get("ID").toString());
				if("0".equals(dbuser.getLoginnum())){
					request.setAttribute("str","updatepass");
				}else{
					request.setAttribute("str",null);
				}

				tb_log_opr_customer log = new tb_log_opr_customer();
				log.setModulename("系统管理-用户登录");
				log.setOprcontent("访问系统首页面");
				log.setIp(logOprCustomerService.getIp(request));
				log.setCustomercode("");
				logOprCustomerService.addLog(log);
				return "business/login/index";
			}else{
				return "redirect:/login/login.action";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
