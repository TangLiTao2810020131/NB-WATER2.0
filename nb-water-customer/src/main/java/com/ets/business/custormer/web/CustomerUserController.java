package com.ets.business.custormer.web;

import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.common.DateTimeUtils;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus.service.CusService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.shiro.cache.RedisClientTemplate;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
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
 * @date:2018年11月6日 上午10:27:19
 * @version :
 *
 */
@Controller
@RequestMapping("customerUser")
public class CustomerUserController {

	@Resource
    CustomerUserService customerUserService;
	@Resource
    LogOprService logService;
	@Resource
    CusService customerService;
	@Resource
    LogOprCustomerService logOprCustomerService;
	@Resource
    RedisClientTemplate redisClientTemplate;

	String modulename="客户管理-职员管理";

	@RequestMapping("list")
	public String list(HttpServletRequest request,String cid)
	{
		nb_cus customer=null;
		if(cid==null || cid.equals("")) //判断是否客户菜单，如果是客户添加职员，直接从session获取客户ID
		{
			//将""查看职员管理列表"记录保存到客户端操作日志
			Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
			cid = (String)loginMap.get("CID");
			tb_log_opr_customer log=new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));
			log.setModulename(modulename);
			log.setOprcontent("查看职员管理列表");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

		}else{
			//将""查看职员管理列表"记录保存到用户端操作日志
			tb_log_opr log=new tb_log_opr();
			log.setModulename("客户管理-客户列表-账号管理");
			customer=customerService.infoCustomer(cid);
			log.setOprcontent("查看客户为["+customer.getCustomerName()+"]账号信息");
			logService.addLog(log);
		}

		request.setAttribute("cid", cid);
		return "business/customeruser/customer-user-list";
	}




	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String cid)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		map.put("cid", cid);

		List<nb_customer_user> customerUser = customerUserService.getCustomerUsers(map);
		long count = customerUserService.getCount(map);

		PageListData<nb_customer_user> pageData = new PageListData<nb_customer_user>();

		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(customerUser);

		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}

	@RequestMapping("input")
	public String customerUserInput(String id,String cid ,HttpServletRequest request)
	{
		nb_customer_user customerUser = null;
		if(id!=null && !id.equals(""))
		{
			customerUser = customerUserService.infoCustomerUser(id);
			String username = customerUser.getUsername().split("-")[0];
			customerUser.setUsername(username);

		}
		request.setAttribute("customerUser", customerUser);
		request.setAttribute("cid", cid);
		return "business/customeruser/customer-user-input";
	}


	@RequestMapping("info")
	public String customerUserInfo(String id,HttpServletRequest request,String mark)
	{
		nb_customer_user customerUser = null;

		if(id!=null && !id.equals(""))
		{
			customerUser = customerUserService.infoCustomerUser(id);
			String username = customerUser.getUsername().split("-")[0];
			customerUser.setUsername(username);

			if(mark.equals("customer")&&mark!=null)
			{
				tb_log_opr_customer log=new tb_log_opr_customer();
				log.setIp(logOprCustomerService.getIp(request));
				log.setModulename(modulename);
				log.setOprcontent("查看"+"["+customerUser.getUsername()+"]"+"账户信息");
				nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
				log.setCustomercode(cus.getId());
				logOprCustomerService.addLog(log);

			}else{

				//将"查看xxx账户信息"保存到操作日志
				tb_log_opr log=new tb_log_opr();
				log.setModulename("客户管理-客户列表-账号管理");
				log.setOprcontent("查看"+"["+customerUser.getUsername()+"]"+"账户信息");
				logService.addLog(log);
			}

		}
		request.setAttribute("customerUser", customerUser);

		return "business/customeruser/customer-user-info";
	}
	//检查用户的唯一性
	@RequestMapping(value="isCheckCustomerUser" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckCustomerUser(nb_customer_user user){
		Map map = new HashMap();
		map.put("cid",user.getCid());
		map.put("username",user.getUsername() + "-" + user.getCid());
		int i = 0;
		i = customerUserService.isCheckCustomerUser(map);
		return  i;
	}


	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request, nb_customer_user customerUser, String mark)
	{
		//如果客户的账号不存在,则将"新增xxx账户信息"保存到操作日志
		if(mark.equals("customer") && mark!=null)
		{
			tb_log_opr_customer log=new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));
			if(customerUser.getId()==null||customerUser.getId().equals(""))
			{
				log.setOprcontent("新增"+"["+customerUser.getUsername()+"]"+"账户信息");
			}else{
				log.setOprcontent("编辑"+"["+customerUser.getUsername()+"]"+"账户信息");
			}
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		}else{
			tb_log_opr log=new tb_log_opr();
			if(customerUser.getId()==null||customerUser.getId().equals(""))
			{
				log.setOprcontent("新增"+"["+customerUser.getUsername()+"]"+"账户信息");
			}else{
				log.setOprcontent("编辑"+"["+customerUser.getUsername()+"]"+"账户信息");
			}
			log.setModulename("客户管理-客户列表-账号管理");
			logService.addLog(log);
		}

		String username = customerUser.getUsername();
		Gson gson = new Gson();
		customerUser.setCtime(DateTimeUtils.getnowdate());
		customerUser.setUsername(username + "-" + customerUser.getCid());
		if("".equals(customerUser.getIsopen()) || customerUser.getIsopen() == null){
			customerUser.setIsopen("0");
		}

		customerUserService.insetCustomerUser(customerUser);
		return gson.toJson("操作成功");
	}

	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String id[],String mark)
	{

		StringBuilder sb=new StringBuilder();
		for(String str:id)
		{
			nb_customer_user customerUser=customerUserService.infoCustomerUser(str);
			sb.append(customerUser.getUsername()+",");
		}

		//保存到客户端日志
		if(mark.equals("customer")&&mark!=null)
		{
			tb_log_opr_customer log=new tb_log_opr_customer();
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));
			log.setOprcontent("删除"+"["+sb+"]"+"客户信息");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

		}else{
			//保存到用户端日志
			tb_log_opr log=new tb_log_opr();
			log.setModulename("客户管理-客户列表-账号管理");
			log.setOprcontent("删除"+"["+sb+"]"+"客户信息");
			logService.addLog(log);
		}


		customerUserService.deleteCustomerUser(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}

	@RequestMapping("editCustomerUserInfo")
	public String editInfo(String id,HttpServletRequest request)
	{
		nb_customer_user customerUser = null;
		if(id!=null && !id.equals(""))
		{
			customerUser = customerUserService.infoCustomerUser(id);
			String username = customerUser.getUsername().split("-")[0];
			customerUser.setUsername(username);
		}
		request.setAttribute("customerUser", customerUser);
		return "business/customeruser/customer-user-edit";
	}

	@RequestMapping(value="edit" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(HttpServletRequest request, nb_customer_user customerUser)
	{
		Gson gson = new Gson();

		try {
			customerUserService.editCustomerUser(customerUser);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));
			log.setOprcontent("编辑"+"["+customerUser.getUsername()+"]"+"账户信息");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(new Message("1","编辑成功!"));
	}


	@RequestMapping(value="checkPassword" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int checkPassword(HttpServletRequest request, nb_customer_user customerUser)
	{
		try {
			nb_customer_user dbuser = customerUserService.infoCustomerUser(customerUser.getId());
			String pass = new SimpleHash("MD5",customerUser.getPassword(),"1024").toHex();
			if(!pass.equals(dbuser.getPassword())){
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@RequestMapping(value="editeditPassword" ,produces = "application/json; charset=utf-8")
	public String editeditPassword(HttpServletRequest request,String password,String str)
	{
		try {
			Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
			if(loginMap != null){
				request.setAttribute("str",str);
				tb_log_opr_customer log = new tb_log_opr_customer();
				log.setModulename(modulename);
				log.setIp(logOprCustomerService.getIp(request));
				log.setOprcontent("修改"+"["+loginMap.get("USERNAME")+"]"+"密码");
				nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
				log.setCustomercode(cus.getId());
				logOprCustomerService.addLog(log);
			}
			request.setAttribute("id", loginMap.get("ID"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "business/customeruser/customer-editpassword";
	}

	@RequestMapping(value="savePassWord" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String savePassWord(HttpServletRequest request,String id,String cnewpassword)
	{
		Gson gson = new Gson();

		try {
			nb_customer_user dbuser = customerUserService.infoCustomerUser(id);
			String pass = new SimpleHash("MD5",cnewpassword,"1024").toHex();
			dbuser.setPassword(pass);
			dbuser.setLoginnum("1");
			customerUserService.editCustomerUserPass(dbuser);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));
			log.setOprcontent("修改"+"["+dbuser.getUsername()+"]"+"密码");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(new Message("1","修改成功!"));
	}
	@RequestMapping(value = "restPassword",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String restPassword(HttpServletRequest request,String id[]){
        StringBuilder sb=new StringBuilder();
        for(String str:id)
        {
            nb_customer_user customerUser=customerUserService.infoCustomerUser(str);
            sb.append(customerUser.getUsername()+",");
			redisClientTemplate.del("shiro_redis_session:" + customerUser.getUsername());
        }

        //保存到客户端日志
            tb_log_opr_customer log=new tb_log_opr_customer();
            log.setModulename(modulename);
            log.setIp(logOprCustomerService.getIp(request));
            log.setOprcontent("重置"+"["+sb+"]"+"客户密码");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
            logOprCustomerService.addLog(log);
       customerUserService.restPassword(id);
        Gson gson = new Gson();
        return gson.toJson("");



    }



}
