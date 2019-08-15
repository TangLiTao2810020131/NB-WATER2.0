package com.ets.system.user.web;

import com.ets.common.Message;
import com.ets.system.log.login.service.LogLoginService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.user.entity.tb_user;
import com.ets.system.user.service.UserService;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: 姚轶文
 * @date:2018年8月16日 下午4:44:11
 * @version :
 * 
 */
@Controller
@RequestMapping("login") 
public class LoginController {

	@Resource
	UserService userService;
	@Resource
	LogLoginService logLoginService;
	@Resource
	LogOprService logService;


	@RequestMapping("login")
	public String login(HttpServletRequest request)
	{
		//SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		return "login";
	}

	@RequestMapping("logOut")
	public String logOut()
	{
		tb_log_opr log = new tb_log_opr();
		log.setModulename("系统管理-用户登出");
		log.setOprcontent("访问系统登录页面");
		logService.addLog(log);
		SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		return "redirect:/login/login.action";
	}

	/**
	 * 登录验证
	 * @param request 请求
	 * @param user 用户信息
	 * @return  验证信息
	 */
	@RequestMapping(value="loginCheck" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loginCheck(HttpServletRequest request,tb_user user)
	{
		Gson gson = new Gson();
		SecurityUtils.getSubject().logout();
		Subject subject = SecurityUtils.getSubject();
		//转换一下password的格式
		String password = new SimpleHash("MD5",user.getPassword(),"1024").toHex();
		tb_user dbuser = userService.getUserByUserName(user.getUsername());
		if(dbuser != null){
			if(password.equals(dbuser.getPassword())){
				if("0".equals(dbuser.getIsclose())){
					return gson.toJson(new Message("3","该用户已被禁用，请联系管理员!"));
				}
				if (!subject.isAuthenticated()){
					UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
					try {
						// 执行登录.
						subject.login(token);
						logLoginService.addLog(request,user.getUsername());
						SecurityUtils.getSubject().getSession().setAttribute("userSession", dbuser);
						return gson.toJson(new Message("0","登录成功!"));
					} 
					// 所有认证时异常的父类. 
					catch (Exception ae) {
						ae.printStackTrace();
						return gson.toJson(new Message("1","登录失败!"));
					}
				}
			}else{
				return gson.toJson(new Message("2","密码错误!"));
			}

		}else{
			return gson.toJson(new Message("2","用户不存在!"));

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
			tb_user user = (tb_user) subject.getSession().getAttribute("userSession");
			if(user != null){

				tb_user dbuser = userService.infoUser(user.getId());
				if("0".equals(dbuser.getLoginnum())){
					request.setAttribute("str","updatepass");
				}else{
					request.setAttribute("str",null);
				}

				SecurityUtils.getSubject().getSession().setAttribute("userSession", dbuser);
				tb_log_opr log = new tb_log_opr();
				log.setModulename("系统管理-用户登录");
				log.setOprcontent("访问系统首页面");
				logService.addLog(log);
				return "index";
			}else{
				return "redirect:/login/login.action";
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
