package com.xushuai.spring_boot_starter_xu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xushuai.spring_boot_starter_xu.service.HelloService;

@Configuration // 声明是配置类
@EnableConfigurationProperties(HelloServiceProperties.class) // 开启属性注入,以@EnableConfigurationProperties声明，使用@Autowired注入
@ConditionalOnClass(HelloService.class)// 当HelloService在类路劲的条件下
@ConditionalOnProperty(prefix = "hello", value = "enabled", matchIfMissing = true) // // 当设置hello=enabled的情况下，如果没有设置默认是true，即符合条件
public class HelloServiceAutoconfiguration {
	
	@Autowired
	private HelloServiceProperties helloServiceProperties;
	
	@Bean
	@ConditionalOnMissingBean(HelloService.class)
	public HelloService helloService(){
		HelloService helloService = new HelloService();
		helloService.setMsg(helloServiceProperties.getMsg());
		return helloService;
	}
}
