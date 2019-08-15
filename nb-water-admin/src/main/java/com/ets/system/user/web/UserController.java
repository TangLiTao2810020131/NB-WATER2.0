package com.ets.system.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.role.entity.tb_role;
import com.ets.system.role.service.RoleService;
import com.ets.system.shiro.cache.RedisClientTemplate;
import com.ets.system.user.entity.tb_user;
import com.ets.system.user.service.UserService;
import com.ets.system.user_role.entity.tb_user_role;
import com.ets.system.user_role.service.UserRoleService;
import com.google.gson.Gson;

/**
 * @version :
 * @author: 姚轶文
 * @date:2018年8月17日 上午10:41:26
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
    RedisClientTemplate redisClientTemplate;
    @Resource
    UserService userService;
    @Resource
    UserRoleService urService;
    @Resource
    RoleService roleService;
    @Resource
    LogOprService logService;


    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        //将用户访问"用户列表信息"记录到操作日志中
        tb_log_opr log = new tb_log_opr();
        log.setModulename("系统管理-用户管理");
        log.setOprcontent("访问用户管理列表");
        logService.addLog(log);
        return "system/user/user-list";
    }

    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit, String username, String tel, String email, String ctime, String isclose) {
        //System.out.println("page="+page+",limit="+limit);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("email", email);
        map.put("tel", tel);
        map.put("ctime", ctime);
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("page", (page) * limit);//oracle
        map.put("limit", (page - 1) * limit);//oracle
        map.put("isclose", isclose);

        List<tb_user> users = userService.getUsers(map);
        long count = userService.getCount(map);

        PageListData<tb_user> pageData = new PageListData<tb_user>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(users);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    @RequestMapping("info")
    public String userinfo(String id, HttpServletRequest request) {
        //System.out.println(id);
        tb_user user = userService.infoUser(id);
        List list = urService.getRoleNameByUserId(id);

        //查看用户管理列表中"xxx用户信息"记录到操作到操作列表中
        tb_log_opr log = new tb_log_opr();
        log.setModulename("系统管理-用户管理");
        log.setOprcontent("查看[" + user.getUsername() + "]用户");
        logService.addLog(log);

        request.setAttribute("list", list);
        request.setAttribute("user", user);
        return "system/user/user-info";
    }


    //检查用户名是否存在
    @RequestMapping(value = "isCheckUser", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckUser(tb_user user) {
        int num = 0;
        if ("".equals(user.getId())) {
            num = userService.isCheckUserName(user.getUsername());
        }
        return num;
    }


    //前台点击新增所跳的Conller
    @RequestMapping("usersave")
    public String usersave(String id,HttpServletRequest request) {
        tb_user user=null;
        if(id!=null && !id.equals(""))
        {
            user = userService.infoUser(id);
        }
        request.setAttribute("user", user);

        return "system/user/user-input";
    }


    @RequestMapping(value = "save")
    @ResponseBody
    public String save(tb_user user) {
        Gson gson = new Gson();
        tb_log_opr log = new tb_log_opr();
        log.setModulename("系统管理-用户管理");
        //如果用户的ID为空,则将"新增xxx用户信息"记录到操作列表中,否则将"编辑xxx用户信息"记录到列表中
        if (user.getId() == null || user.getId().equals("")) {
            log.setOprcontent("新增[" + user.getUsername() + "]用户");
        } else {
            log.setOprcontent("修改[" + user.getUsername() + "]用户");
        }
        if("".equals(user.getIsclose()) || user.getIsclose() == null){
            user.setIsclose("0");
        }
        logService.addLog(log);
        userService.insetUser(user);
        return gson.toJson(new Message("1", "操作成功!"));
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(String id[]) {
        userService.deleteUser(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }

    @RequestMapping(value = "close")
    @ResponseBody
    public String close(String id[]) {
        userService.closeUser(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }

    @RequestMapping(value = "open")
    @ResponseBody
    public String open(String id[]) {
        userService.openUser(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }

    @RequestMapping(value = "inRole")
    public String inRole(String id, HttpServletRequest request) {
        List<tb_role> roleList = roleService.getAllRoles();
        List<tb_user_role> userRoleList = urService.getRoleByUserId(id);

        request.setAttribute("userId", id);
        request.setAttribute("list", roleList);
        request.setAttribute("userRoleList", userRoleList);
        return "system/user/user-inRole";
    }

    @RequestMapping(value = "saveRole")
    @ResponseBody
    public String saveRole(String userId, String[] ids) {
        urService.deleteRoleByUserId(userId);

        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            tb_user_role userRole = new tb_user_role();
            userRole.setUserId(userId);
            userRole.setRoleId(id);

            //根据角色ID查找角色信息
            tb_role role = roleService.infoRole(id);
            sb.append(role.getRolename() + ",");
            urService.save(userRole);
        }

        tb_user user = userService.infoUser(userId);
        
        redisClientTemplate.del("shiro_redis_session:" + user.getUsername());//删除重新分配角色的用户
        
        tb_log_opr log = new tb_log_opr();
        log.setModulename("系统管理-用户管理");
        log.setOprcontent("为[" + user.getUsername() + "]分配[" + sb.substring(0, sb.length() - 1) + "]角色");
        logService.addLog(log);

        Gson gson = new Gson();
        return gson.toJson("操作成功");
    }

    //前台点击编辑后所跳的controller
    @RequestMapping("useredit")
    public String useredit(String id, HttpServletRequest request) {
        System.out.println(id);
        tb_user user = null;
        if (id != null && !id.equals("")) {
            user = userService.infoUser(id);
        }
        request.setAttribute("user", user);
        return "system/user/user-edit";
    }

    //用户的编辑
    @RequestMapping(value = "edit")
    @ResponseBody
    public String edit(tb_user user) {
        Gson gson = new Gson();
        userService.editUser(user);
        return gson.toJson(new Message("1", "操作成功!"));
    }

    //前台点击修改密码所跳的Conller
    @RequestMapping("userEditPassword")
    public String userEditPassword(String id,String str, HttpServletRequest request) {
        tb_user user = null;
        if (id != null && !id.equals("")) {
            user = userService.infoUser(id);
            request.setAttribute("str",str);
        }
        return "system/user/user-editpassword";

    }

    //用户密码的编辑
    @RequestMapping(value = "editPassword",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String editPassword(String id, String newpwd) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        //MD5多次加密
        String md5pass = new SimpleHash("MD5", newpwd, "1024").toHex();
        map.put("newpwd", md5pass);
        map.put("loginnum","1");
        Gson gson = new Gson();
        userService.updatePassword(map);
        tb_user user = userService.infoUser(id);
        redisClientTemplate.del("shiro_redis_session:" + user.getUsername());//删除重新分配角色的用户
        return gson.toJson(new Message("1", "操作成功!"));
    }

    //检查旧密码输入是否正确
    @RequestMapping(value = "pwdCheck", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int pwdCheck(tb_user user) {
        tb_user dbuser = userService.pwdCheck(user.getId());
        String password = new SimpleHash("MD5", user.getPassword(), "1024").toHex();
        if (password.equals(dbuser.getPassword())) {
            return 1;
        } else {
            return 0;
        }
    }
    //重置密码对应的Controller
    @RequestMapping(value = "restPassword", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String restPassword(String id[]) {
        userService.restPassword(id);
        for (int i = 0;i < id.length;i++){
            tb_user user = userService.infoUser(id[i]);
            redisClientTemplate.del("shiro_redis_session:" + user.getUsername());//删除重新分配角色的用户
        }
        Gson gson = new Gson();
        return gson.toJson("");
    }

}




