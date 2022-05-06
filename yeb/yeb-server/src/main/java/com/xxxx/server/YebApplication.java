package com.xxxx.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @className: YebApplication
 * @copyright: HTD
 * @description: qi dong lei
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/17
 * @version: 1.0
 */
@SpringBootApplication
@MapperScan("com.xxxx.server.mapper")
@EnableScheduling  // 开启定时任务
public class YebApplication {

	public static void main(String[] args) {
		SpringApplication.run( YebApplication.class,args );
	}

}
