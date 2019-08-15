package com.ets.system.log.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.DateTimeUtils;
import com.ets.common.HttpUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.log.login.dao.LogLoginDao;
import com.ets.system.log.login.entity.IpBean;
import com.ets.system.log.login.entity.tb_log_login;
import com.google.gson.Gson;

/**
 * @author: 姚轶文
 * @date:2018年9月5日 上午9:55:14
 * @version :
 * 
 */
@Service
@Transactional
public class LogLoginService {

	@Resource
	LogLoginDao logDao;
	
	//static  RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build();
	
	public List<tb_log_login> getLogs(Map map)
	{
		return logDao.getLogs(map);
	}
	
	public long getCount(Map map)
	{
		return logDao.getCount(map);
	}
	
	public void addLog(HttpServletRequest request,String username)throws Exception
	{
		tb_log_login log = new tb_log_login();
		
		String agent = request.getHeader("user-agent");
		StringTokenizer st = new StringTokenizer(agent,";");
		st.nextToken();
		String useros = st.nextToken();
		
		String ipaddress = getIp(request);
/*		if(isInner(ipaddress)) //外网
		{	
			System.out.println("ipaddress"+ipaddress);
			String ipJson = getIpInfo(ipaddress);
			System.out.println("ipJson"+ipJson);
			IpBean ipBean = new Gson().fromJson(ipJson,IpBean.class);
			System.out.println("ipJson"+ipJson);
			if(ipBean != null){
				log.setCity(ipBean.getData().getCity());
				log.setIsp(ipBean.getData().getIsp());
				log.setRegion(ipBean.getData().getRegion());
			}else{
				log.setCity("服务器");
				log.setIsp("服务器");
				log.setRegion("服务器");
			}
		}
		else //内网
		{
			log.setCity("内网");
			log.setIsp("内网");
			log.setRegion("内网");
			
		}*/
		
		log.setIpaddress(ipaddress);	
		log.setUsername(username);
		log.setOstype(useros);
		log.setId(UUIDUtils.getUUID());
		log.setLogintime(DateTimeUtils.getnowdate());
		logDao.addLog(log);
	}
	
	public tb_log_login infoLog(String id)
	{
		return logDao.infoLog(id);
	}
	
	public String getIpInfo(String ip)throws Exception
	{
		String host = "https://api01.aliyun.venuscn.com";
	    String path = "/ip";
	    String method = "GET";
	    String appcode = "503d3e206a174c77a919d78cfe073b2d";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("ip", ip);

	    String str = null;
	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    	str = EntityUtils.toString(response.getEntity());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return str;
	}
	
	
	public  String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	//判断是否内网IP
	public boolean isInner(String ip)
	{
	    String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";//正则表达式=。 =、懒得做文字处理了、
	    Pattern p = Pattern.compile(reg);
	    Matcher matcher = p.matcher(ip);
	    return matcher.find();
	}

	
}
