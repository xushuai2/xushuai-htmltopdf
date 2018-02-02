package com.xushuai.spring_boot_starter_xu;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {// 使用安全类型来获取属性，再application.properties中使用hello.msg来设置值，默认是world
	private static final String MSG = "world";
	
	private String msg = MSG;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
