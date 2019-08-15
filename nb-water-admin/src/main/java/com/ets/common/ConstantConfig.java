package com.ets.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName:     ConstantConfig.java 
 * @Description:   读取properties文件类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月26日 下午4:19:46
 */
@Service
@PropertySource("classpath:constant.properties")
public class ConstantConfig {
	
	@Value("${quarzt.quarzturl}")
	public String quarztPreHost;
	
	@Value("${nbiot.nbioturl}")
	public String nbiotPreHost;
	
	@Value("${sys.uploadurl}")
	public String sysUploadUrl;

	public String getQuarztPreHost() {
		return quarztPreHost;
	}

	public void setQuarztPreHost(String quarztPreHost) {
		this.quarztPreHost = quarztPreHost;
	}

	public String getNbiotPreHost() {
		return nbiotPreHost;
	}

	public void setNbiotPreHost(String nbiotPreHost) {
		this.nbiotPreHost = nbiotPreHost;
	}

	public String getSysUploadUrl() {
		return sysUploadUrl;
	}

	public void setSysUploadUrl(String sysUploadUrl) {
		this.sysUploadUrl = sysUploadUrl;
	}
	
	
	
	
	
	
}
