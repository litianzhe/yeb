package com.xxxx.mail;

import com.rabbitmq.client.Channel;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.util.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;


/**
 * @className: MailReceiver
 * @copyright: HTD
 * @description: 消息接收者
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/15
 * @version: 1.0
 */
@Component
public class MailReceiver {

	private static final Logger LOGGER =  LoggerFactory.getLogger( MailReceiver.class );

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private RedisTemplate redisTemplate;

	@RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)   // 监听 队列
	public void handler(Message message, Channel channel){   // Channel 信道  Employee employee
		// 获取员工类
		Employee employee = (Employee) message.getPayload(); // (Employee)
		MessageHeaders headers = message.getHeaders();
		// 消息序号; tag 手动确认的时候返回
		long tag = (long) headers.get( AmqpHeaders.DELIVERY_MODE );
		String msgId = (String) headers.get( "spring_returned_message_correlation");

		HashOperations hashOperations = redisTemplate.opsForHash();

		try {
			// 看 redis hashOperations 里面包不包含 对应的 msgId
			if (hashOperations.entries("mail_log").containsKey(msgId)) {
				LOGGER.error("消息已经被消费！============> {}", msgId);
				/**
				 * 手动确认消息
				 * tag : 消息序号
				 * multiple : 是否确认多条,false 确认1条
				 */
				channel.basicAck(tag, false);
				return;
			}
			// 创建信息
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper( msg );

			// 发件人
			helper.setFrom( mailProperties.getUsername() ); // # 发送者邮箱地址  ht19950917@163.com
			//helper.setFrom( "ht19950917@163.com" );
			helper.setTo(employee.getEmail()); // 收件人
			helper.setSubject("入职欢迎邮件"); // 主题
			helper.setSentDate(new Date()); // 发送日期
			// 邮件内容
			Context context = new Context(  );
			// 设置属性
			context.setVariable( "name", employee.getName() );
			context.setVariable("posName", employee.getPosition().getName());
			context.setVariable("joblevelName", employee.getJoblevel().getName());
			context.setVariable("departmentName", employee.getDepartment().getName());

			String mail = templateEngine.process("mail", context);

			helper.setText( mail, true );
			// 发送邮件
			javaMailSender.send( msg );
			LOGGER.info( "邮件发送成功！" );
			// 将消息 id 存入 redis
			hashOperations.put( "mail_log", msgId, "OK" );
			// 手动确认
			channel.basicAck( tag, false );

		} catch (Exception e) {  // 抛最大的异常
			/**
			 * 手动确认消息
			 * tag ： 消息序号
			 * multiple: 是否确认多条
			 * requeue: 是否退回到队列
			 */
			try{
				channel.basicNack( tag, false, true );
			} catch (IOException ex){
				LOGGER.error("邮件发送失败 ===========> {}", e.getMessage());
			}
			LOGGER.error("邮件发送失败 ===========> {}", e.getMessage());
		}
	}

}
