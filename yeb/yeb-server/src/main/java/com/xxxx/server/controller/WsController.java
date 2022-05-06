package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * @className: WsController
 * @copyright: HTD
 * @description: websocket  在线聊天
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/21
 * @version: 1.0
 */
@Controller
public class WsController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/ws/chat")
	public void handleMsg(Authentication authentication, ChatMsg chatMsg){
		// 获取当前用户对象
		Admin admin = (Admin) authentication.getPrincipal();
		// 登录用户名
		chatMsg.setFrom( admin.getUsername() );
		// 显示用户名
		chatMsg.setFormNickName( admin.getName() );
		chatMsg.setDate( LocalDateTime.now() );
		// 发送消息  参数：发送给谁，队列名，消息内容
		simpMessagingTemplate.convertAndSendToUser( chatMsg.getTo(),"/queue/chat", chatMsg );
	}

}
