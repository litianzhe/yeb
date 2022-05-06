package com.xxxx.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @className: ChatMsg
 * @copyright: HTD
 * @description: 在线聊天 - 消息
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/21
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {

	private String from;
	private String to;
	private String content;
	private LocalDateTime date;
	private String formNickName;

}
