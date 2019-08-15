package com.ets.dictionary.watermeter.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.dictionary.watermeter.entity.nb_watermeter_dict;
import com.ets.dictionary.watermeter.service.WatermeterService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;

/**
 * 水表口径数据字典
 * @author wh
 *
 */
@Controller
@RequestMapping("watermeter")
public class WatermeterController {

	@Resource
	WatermeterService watermeterService;
	@Resource
    LogOprService logService;

	String baseUrl = "dictionary/watermeter/";
	
	String modulename = "数据字典-水表型号";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		    //将用户"查看水表型号列表"信息保存到操作日志
			tb_log_opr log = new tb_log_opr();
			log.setModulename(modulename);
			log.setOprcontent("访问水表型号列表");
			logService.addLog(log);
			return baseUrl+"watermeter-list";
	}

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
			nb_watermeter_dict watermeter = null;
			if(id!=null && !id.equals(""))
			{
				watermeter = watermeterService.infoWatermeter(id);
			}
			request.setAttribute("watermeter", watermeter);
			return baseUrl+"watermeter-input";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
			nb_watermeter_dict watermeter = null;
			if(id!=null && !id.equals(""))
			{
				watermeter = watermeterService.infoWatermeter(id);

				//将用户查看"水表型号"列表中"xxx水表型号信息"保存到操作日志
				tb_log_opr log = new tb_log_opr();
				log.setModulename(modulename);
				log.setOprcontent("查看水表型号"+"["+watermeter.getType()+"]");
				logService.addLog(log);
			}
			request.setAttribute("watermeter", watermeter);
			return baseUrl+"watermeter-info";
	}

	/**
	 * 分页查询水表口径数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit)
	{
		Gson gson = new Gson();
		try {
				//System.out.println("page="+page+",limit="+limit);
				Map<String,Object> map = new HashMap<String,Object>();
				//			map.put("page", (page-1)*limit);//mysql
				//			map.put("limit", limit);//mysql
				map.put("page", (page)*limit);//oracle
				map.put("limit", (page-1)*limit);//oracle

				List<nb_watermeter_dict> watermeter = watermeterService.getWatermeter(map);
				long count = watermeterService.getCount();


				PageListData<nb_watermeter_dict> pageData = new PageListData<nb_watermeter_dict>();

				pageData.setCode("0");
				pageData.setCount(count);
				pageData.setMessage("");
				pageData.setData(watermeter);


				String listJson = gson.toJson(pageData);
				return listJson;
		} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(nb_watermeter_dict watermeter)
	{
		Gson gson = new Gson();
		try {
				tb_log_opr log = new tb_log_opr();
				log.setModulename(modulename);
			    //如果水表的ID不存在将"新增XXX水表型号"保存到操作日志,否则将"编辑XXX水表信息"保存到操作日志
				if(watermeter.getId() == null || watermeter.getId().equals(""))
				{
					log.setOprcontent("新增水表型号"+"["+watermeter.getType()+"]");
				}
				else
				{
					log.setOprcontent("修改水表型号"+"["+watermeter.getType()+"]");
				}
				if("0".equals(watermeter.getMagnetism())){
					watermeter.setControl("0");
				}
				logService.addLog(log);
				watermeterService.opentionWatermeter(watermeter);
				return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		Gson gson = new Gson();
		try {
			    String name = watermeterService.infoWatermeter(id);
			    //将删除"xxx水表"保存到操作日志
				tb_log_opr log = new tb_log_opr();
				log.setModulename(modulename);
				log.setOprcontent("删除水表型号"+"["+name+"]");
				logService.addLog(log);

			    watermeterService.deleteWatermeter(id);
				return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","删除失败!"));
		}
	}
}
