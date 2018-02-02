package com.xushuai.spring_boot_starter_xu.service;

/**
 * @ClassName HelloService
 * @Description 需要自动配置的Bean
 * @author yuhao.wang
 * @Date 2017年4月28日 上午10:42:41
 * @version 1.0.0
 */
public class HelloService {
	private String msg;
	
	public String sayHello() {
		return "Hello " + msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
