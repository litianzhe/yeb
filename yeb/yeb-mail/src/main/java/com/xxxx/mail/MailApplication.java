package com.xxxx.mail;

import com.xxxx.server.util.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;



/**
 * @className: YebApplication
 * @copyright: HTD
 * @description: qi dong lei
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/15
 * @version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MailApplication {

	public static void main(String[] args) {
		SpringApplication.run( MailApplication.class, args );
	}

	/**
	 * 绑定队列
	 * @return
	 */
	@Bean
	public Queue queue(){
		//return new Queue( "mail.welcome" );
		return new Queue( MailConstants.MAIL_QUEUE_NAME);
	}

}
