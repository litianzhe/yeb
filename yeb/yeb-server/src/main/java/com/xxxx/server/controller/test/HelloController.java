package com.xxxx.server.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: HelloController
 * @copyright: HTD
 * @description: 测试接口
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/21
 * @version: 1.0
 */
@RestController
public class HelloController {

	//测试swagger2config
	@GetMapping("hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/employee/basic/hello")
	public String hello2() {
		return "/employee/basic/hello";
	}

	@GetMapping("/employee/advanced/hello")
	public String hello3() {
		return "/employee/advanced/hello";
	}


}
