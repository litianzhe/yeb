package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: ChatController
 * @copyright: HTD
 * @description: 在线聊天
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/21
 * @version: 1.0
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private IAdminService adminService;

	@ApiOperation( value = "获取所有操作员")
	@GetMapping("/admin")
	public List<Admin> getAllAdmins(String keywords){
		return adminService.getAllAdmins( keywords );
	}


}
