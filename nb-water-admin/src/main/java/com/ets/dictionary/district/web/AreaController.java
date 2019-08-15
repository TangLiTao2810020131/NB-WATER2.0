package com.ets.dictionary.district.web;

import com.ets.common.EleTree;
import com.ets.dictionary.district.entity.AreaListPageData;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 下午3:10:22
 * @version :
 * 
 */
@Controller
@RequestMapping("area")
public class AreaController {

	@Resource
    AreaService areaService;
	@Resource
    LogOprService logService;
    @Resource
    CityService cityService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,String father)
	{
        tb_city city=areaService.findCityByCode(father);

        //将"查看城市(县)列表"信息保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("数据字典-行政区域-设置地市-设置县");
		log.setOprcontent("查看["+city.getCity()+"]城市(县)列表");
		logService.addLog(log);

		//将地市信息保存到session中
        HttpSession session=request.getSession();
        session.setAttribute("citySession",city);

		request.setAttribute("father", father);
		return "dictionary/district/area-list";
}
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String father)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		map.put("father", father);
		
		List<tb_area> area = areaService.getAreas(map);
		long count = areaService.getCount(father);
		
		
		AreaListPageData pageData = new AreaListPageData();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(area);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	@RequestMapping("input")
	public String areainput(String id,String father, HttpServletRequest request)
	{
		tb_area area = null;
		if(id!=null && !id.equals(""))
		{
			area = areaService.infoArea(id);
		}
		request.setAttribute("area", area);
		request.setAttribute("father", father);
		return "dictionary/district/area-input";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(tb_area area,HttpServletRequest request)
	{
	    //从session中拿到城市信息
        tb_city city=(tb_city)request.getSession().getAttribute("citySession");

	    //如果区域(县)id不存在,则将"新增xxx区域(县)"保存到操作日志,否则将"编辑xxx区域(县)"保存到操作日志
        tb_log_opr log=new tb_log_opr();
        log.setModulename("数据字典-行政区域-设置地市-设置县");
        if(area.getId()==null||area.getId().equals(""))
        {
            log.setOprcontent("为["+city.getCity()+"]新增区域(县)["+area.getArea()+"]");
        }else{
            log.setOprcontent("编辑["+city.getCity()+"]["+area.getArea()+"]信息");
        }
        logService.addLog(log);

		Gson gson = new Gson();
		areaService.insetArea(area);
		return gson.toJson("操作成功");
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[],HttpServletRequest request)
	{

        //从session中拿到省份信息
        HttpSession session=request.getSession();
        tb_province province=(tb_province)session.getAttribute("provinceSession");

        //将"删除[xxx,xxx]区域(县)信息"保存到操作日志
        tb_log_opr log=new tb_log_opr();
        log.setModulename("数据字典-行政区域-设置地市-设置县");
        StringBuilder sb=new StringBuilder();
        for(String str:id)
        {
            tb_area area=new tb_area();
            sb.append(area.getArea()+",");
        }
        log.setOprcontent("删除["+province.getProvince()+"]所属的区域(县)["+sb.substring(0,sb.length()-1)+"]");
        logService.addLog(log);

		areaService.deleteArea(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}
	
	
	@RequestMapping(value="treelist" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String treelist(String father)
	{
		List<EleTree> eTree = new ArrayList<EleTree>();
		List<tb_area> area = areaService.getListByfather(father);//根据区县ID查询所有小区
		for (tb_area tb_area : area) {
			String id = tb_area.getId();
			String label = tb_area.getArea();
			EleTree e = new EleTree();
			e.setId(id);;
			e.setLabel(label);
			e.setIsLeaf(true);
			eTree.add(e);
		}
		Gson gson = new Gson();
		String listJson = gson.toJson(eTree);
		return listJson;
	}
	
	@RequestMapping(value="getArea" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getArea(String father)
	{
		List<tb_area> area = areaService.getListByfather(father);//市级对象
		Gson gson = new Gson();
		String listJson = gson.toJson(area);
		return listJson;
	}

	@RequestMapping(value="isCkeckAreaid" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckAreaid(tb_area area){
       return areaService.isCkeckAreaid(area.getAreaid());
	}
}
