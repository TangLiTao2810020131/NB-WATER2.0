package com.ets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.alibaba.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.util.Set;

@Component
public class NacosConfig implements ApplicationRunner {

	@Autowired(required = false)
	private NacosAutoServiceRegistration registration;

	@Value("${server.port}")
	Integer port;

	@Override
	public void run(ApplicationArguments args) {
		if (registration != null && port != null) {
			registration.setPort(port);
			registration.start();
		}
	}

	public String getTomcatPort() throws Exception {
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		String port = objectNames.iterator().next().getKeyProperty("port");
		return port;
	}
}
