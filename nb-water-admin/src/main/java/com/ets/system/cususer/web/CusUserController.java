package com.ets.system.cususer.web;

import com.ets.common.DateTimeUtils;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus.service.CusService;
import com.ets.system.cususer.entity.nb_cus_user;
import com.ets.system.cususer.service.CusUserService;
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
 * @author 吴浩
 * @create 2019-01-22 20:32
 */
@Controller
@RequestMapping("cusUserController")
public class CusUserController {

    String baseUrl = "system/cususer/";

    @Resource
    CusUserService cusUserService;
    @Resource
    CusService cusService;
    @Resource
    LogOprService logOprService;
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

        }else{
            //将""查看职员管理列表"记录保存到用户端操作日志
            customer=cusService.infoCustomer(cid);
        }

        request.setAttribute("cid", cid);
        return baseUrl + "cususer-list";
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

        List<nb_cus_user> customerUser = cusUserService.getCustomerUsers(map);
        long count = cusUserService.getCount(map);

        PageListData<nb_cus_user> pageData = new PageListData<nb_cus_user>();

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
        nb_cus_user customerUser = null;
        if(id!=null && !id.equals(""))
        {
            customerUser = cusUserService.infoCustomerUser(id);
            String username = customerUser.getUsername().split("-")[0];
            customerUser.setUsername(username);
        }
        request.setAttribute("customerUser", customerUser);
        request.setAttribute("cid", cid);
        return baseUrl + "cususer-input";
    }


    @RequestMapping("info")
    public String customerUserInfo(String id,HttpServletRequest request,String mark)
    {
        nb_cus_user customerUser = null;

        if(id!=null && !id.equals(""))
        {
            customerUser = cusUserService.infoCustomerUser(id);
            String username = customerUser.getUsername().split("-")[0];
            customerUser.setUsername(username);
        }
        request.setAttribute("customerUser", customerUser);

        return baseUrl + "cususer-info";
    }
    //检查用户的唯一性
    @RequestMapping(value="isCheckCustomerUser" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckCustomerUser(nb_cus_user user){
        Map map = new HashMap();
        map.put("cid",user.getCid());
        map.put("username",user.getUsername() + "-" + user.getCid());
        int i = 0;
        i = cusUserService.isCheckCusUser(map);
        return  i;
    }

    //检查创建者的唯一性
    @RequestMapping(value="isCheckCusUserType" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckCusUserType(nb_cus_user user) {
        int num = 0;
        if ("1".equals(user.getType())) {
           /* nb_cus_user cus = cusUserService.infoCustomerUser(user.getId());
            if (!cus.getType().equals(user.getType())) {
                num = cusUserService.getCusUsers(user.getCid());
            }*/
            List<nb_cus_user> cusUsers = cusUserService.getCusUsers(user.getCid());
            if (cusUsers == null && cusUsers.equals("")) {
                 num=0;
            }else {
                for (nb_cus_user cusUser:cusUsers) {
                    if (cusUser.getType().equals("1")){
                        num=1;
                    }

                }
            }
      }
        return  num;
    }


    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(HttpServletRequest request, nb_cus_user customerUser, String mark)
    {
        String username = customerUser.getUsername();
        Gson gson = new Gson();
        customerUser.setCtime(DateTimeUtils.getnowdate());
        customerUser.setUsername(username + "-" + customerUser.getCid());
        if("".equals(customerUser.getIsopen()) || customerUser.getIsopen() == null){
            customerUser.setIsopen("0");
        }

        cusUserService.insetCustomerUser(customerUser);
        return gson.toJson("操作成功");
    }

    @RequestMapping(value="delete" )
    @ResponseBody
    public String delete(HttpServletRequest request,String id[],String mark)
    {
        cusUserService.deleteCustomerUser(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }

    @RequestMapping("editCustomerUserInfo")
    public String editInfo(String id,HttpServletRequest request)
    {
        nb_cus_user customerUser = null;
        if(id!=null && !id.equals(""))
        {
            customerUser = cusUserService.infoCustomerUser(id);
            String username = customerUser.getUsername().split("-")[0];
            customerUser.setUsername(username);
        }
        request.setAttribute("customerUser", customerUser);
        return baseUrl + "cususer-edit";
    }

    @RequestMapping(value="edit" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String edit(HttpServletRequest request, nb_cus_user customerUser)
    {
        Gson gson = new Gson();

        try {
            cusUserService.editCustomerUser(customerUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gson.toJson(new Message("1","编辑成功!"));
    }


    @RequestMapping(value="checkPassword" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public int checkPassword(HttpServletRequest request, nb_cus_user customerUser)
    {
        try {
            nb_cus_user dbuser = cusUserService.infoCustomerUser(customerUser.getId());
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
            Map loginMap = (Map) SecurityUtils.getSubject().getSession().getAttribute("userSession");
            if(loginMap != null){
                request.setAttribute("str",str);
            }
            request.setAttribute("id", loginMap.get("ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baseUrl + "cususer-password";
    }

    @RequestMapping(value="savePassWord" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String savePassWord(HttpServletRequest request,String id,String cnewpassword)
    {
        Gson gson = new Gson();

        try {
            nb_cus_user dbuser = cusUserService.infoCustomerUser(id);
            String pass = new SimpleHash("MD5",cnewpassword,"1024").toHex();
            dbuser.setPassword(pass);
            dbuser.setLoginnum("1");
            cusUserService.editCustomerUserPass(dbuser);
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
            nb_cus_user nbCusUser = cusUserService.infoCustomerUser(str);
            sb.append(nbCusUser.getUsername()+",");
            redisClientTemplate.del("shiro_redis_session:" + nbCusUser.getUsername());
        }

            //保存到用户端日志
            tb_log_opr log=new tb_log_opr();
            log.setModulename("客户管理-客户列表-账号管理");
            log.setOprcontent("重置"+"["+sb+"]"+"客户密码");
        logOprService.addLog(log);

        cusUserService.restPassword(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }

}
