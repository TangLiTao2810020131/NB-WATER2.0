package com.ets.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;

/**
 * 
 * @ClassName:     SpringCloudConfig.java 
 * @Description:   SpringCloud相关配置
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月27日 下午5:47:02
 */
@Configuration
public class SpringCloudConfig {

	/**
	 * 
	* @Title: getRestTemplate 
	* @Description: 调用服务模版对象
	* @return: RestTemplate    
	* @Date: 2019年7月27日 下午5:46:46  
	 */
	@Bean
	@LoadBalanced  // 引入ribbon负载均衡
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	/**
	 * 
	* @Title: myRule 
	* @Description: 自定义轮算算法
	* @return: IRule    
	* @Date: 2019年7月27日 下午5:46:08  
	 */
   @Bean
   public IRule myRule(){
       return new RetryRule();
   }
	
	
}
