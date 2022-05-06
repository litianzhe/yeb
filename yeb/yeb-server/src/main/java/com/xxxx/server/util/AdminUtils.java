package com.xxxx.server.util;

import com.xxxx.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.net.ssl.SSLSession;

/**
 * @className: AdminUtil
 * @copyright: HTD
 * @description: 获取当前登录的管理员
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/22
 * @version: 1.0
 */
public class AdminUtils {

	public static Admin getCurrentAdmin(){
		return (Admin) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}
}
