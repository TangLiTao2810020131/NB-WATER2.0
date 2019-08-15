package com.ets.business.init.price.web;

import com.ets.business.init.price.entity.nb_price_dic;
import com.ets.business.init.price.service.PriceDicService;
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

/**
 * @author: 姚轶文
 * @date:2018年11月7日 下午4:04:04
 * @version :
 *
 */
@Controller
@RequestMapping("price")
public class PriceDicController {

    @Resource
    PriceDicService priceSercice;
    @Resource
    LogOprCustomerService logOprCustomerService;

    String modulename="客户管理-费用设置";

    @RequestMapping("list")
    public String list(HttpServletRequest request,String father)
    {
        //request.setAttribute("father", father);
        //将查看"费用设置列表"信息保存到操作日志
        tb_log_opr_customer log=new tb_log_opr_customer();
        log.setIp(logOprCustomerService.getIp(request));//ip地址
        log.setModulename(modulename);
        log.setOprcontent("查看费用设置列表");
        nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
        log.setCustomercode(cus.getId());
        logOprCustomerService.addLog(log);
        return "business/init/price/price-list";
    }

    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page,int limit)
    {
        Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        //System.out.println("page="+page+",limit="+limit);
        Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("page", (page)*limit);//oracle
        map.put("limit", (page-1)*limit);//oracle
        map.put("customerId", loginMap.get("CID"));

        List<nb_price_dic> price = priceSercice.getPrices(map);
        long count = priceSercice.getCount((String)loginMap.get("CID"));


        PageListData<nb_price_dic> pageData = new PageListData<nb_price_dic>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(price);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    @RequestMapping("input")
    public String priceinput(String id, HttpServletRequest request)
    {
        nb_price_dic price = null;
        if(id!=null && !id.equals(""))
        {
            price = priceSercice.infoPrice(id);
        }
        request.setAttribute("price", price);
        return "business/init/price/price-input";
    }

    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(HttpServletRequest request, nb_price_dic price)
    {
        //将新增"xxx收费类型"信息保存到操作日志
        tb_log_opr_customer log=new tb_log_opr_customer();
        log.setModulename(modulename);
        log.setIp(logOprCustomerService.getIp(request));//ip地址
        if(price.getId().equals("")||price.getId()==null)
        {
            log.setOprcontent("新增收费类型["+price.getType()+"]");

        }else{
            log.setOprcontent("修改收费类型["+price.getType()+"]");
        }
        nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
        log.setCustomercode(cus.getId());
        logOprCustomerService.addLog(log);

        Gson gson = new Gson();
        Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");

        price.setCustomerId((String)loginMap.get("CID"));
        priceSercice.insetPrice(price);

        return gson.toJson("操作成功");
    }

    @RequestMapping(value="delete" )
    @ResponseBody
    public String delete(HttpServletRequest request,String id[])
    {
        StringBuilder sb=new StringBuilder();
        for(String str:id)
        {
            nb_price_dic price=priceSercice.infoPrice(str);
            sb.append(price.getType()+",");
        }

        //将查看"删除xxx收费类型"信息保存到操作日志
        tb_log_opr_customer log=new tb_log_opr_customer();
        log.setIp(logOprCustomerService.getIp(request));//ip地址
        log.setModulename(modulename);
        log.setOprcontent("删除收费类型["+sb.substring(0,sb.length()-1)+"]");
        nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
        log.setCustomercode(cus.getId());
        logOprCustomerService.addLog(log);
        priceSercice.deletePrice(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }
    //设置类型名称的唯一性
    @RequestMapping(value = "isCheckType", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCkeckType(nb_price_dic dic) {
        Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        Map map = new HashMap();
        map.put("customerId",loginMap.get("CID"));
        map.put("type",dic.getType());
        int num=0;
        if ("".equals(dic.getId())) {

            num = priceSercice.isCkeckType(map);
        }
        return num;
    }
}
