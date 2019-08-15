package com.ets.business.loginlog.web;

import com.ets.business.loginlog.entity.nb_customer_login_log;
import com.ets.business.loginlog.service.LoginLogService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.common.PageListData;
import com.ets.system.cus.entity.nb_cus;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("loginLog")
public class LoginLogController {
    @Resource
    LoginLogService loginLogService;
    @Resource
    LogOprCustomerService logOprCustomerService;

    String modulename="客户管理-登录日志";

    //前台点击查看登录日志所跳的Controller
    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {

        //将查看"登录日志列表"信息保存到操作日志
        tb_log_opr_customer log=new tb_log_opr_customer();
        log.setIp(logOprCustomerService.getIp(request));//ip地址
        log.setModulename(modulename);
        log.setOprcontent("查看登录日志列表");
        nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
        log.setCustomercode(cus.getId());
        logOprCustomerService.addLog(log);

        return "business/loginlog/log-list";
    }
    //对数据库进行分页查询展现数据库中的数据
    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit, nb_customer_login_log loginLog)
    {
    	Map<?,?> loginMap = (Map<?,?>) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cid = (String)loginMap.get("CID");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",loginLog.getUsername());
        map.put("customerName",loginLog.getCustomerName());
        map.put("ipaddress",loginLog.getIpaddress());
        map.put("logintime",loginLog.getLogintime());
        map.put("logintState",loginLog.getLoginState());
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("page", (page)*limit);//oracle
        map.put("limit", (page-1)*limit);//oracle
        map.put("customercode", cid);
        List<nb_customer_login_log> logs = loginLogService.getLogs(map);
        long count = loginLogService.getCount(map);


        PageListData<nb_customer_login_log> pageData = new PageListData<nb_customer_login_log>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(logs);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }
    //操作对应的Controller
    @RequestMapping("info")
    public String loginfo(String id,HttpServletRequest request)
    {
        nb_customer_login_log loginfo=loginLogService.infoLog(id);
        request.setAttribute("loginfo", loginfo);
        return "business/loginlog/log-info";
    }

}
