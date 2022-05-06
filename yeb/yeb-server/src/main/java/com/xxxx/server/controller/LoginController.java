package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.Joblevel;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.util.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * @className: LoginController
 * @copyright: HTD
 * @description: 登录
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/18
 * @version: 1.0
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

	@Autowired
	private IAdminService adminService;

	@Resource
	private PasswordEncoder passwordEncoder;

	@ApiOperation(value = "登录-返回token")
	@PostMapping("/login")
	public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
		return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(),
				adminLoginParam.getCode(),request);
	}

	@ApiOperation(value = "获取当前登录用户的信息")
	@GetMapping("/admin/info")
	public Admin getAdminInfo(Principal principal) { // 通过 Principal 对象，获取当前登录用户对象
		if (null == principal) {
			return null;
		}
		String username = principal.getName();
		Admin admin = adminService.getAdminByUserName(username); // 新建方法 getAdminByUserName 根据用户名获取用户
		admin.setPassword(null); // 安全起见，不给前端返回用户密码
		admin.setRoles(adminService.getRoles(admin.getId())); // 获取登录用户对应的角色列表,即 权限Role
		return admin;
	}

	@ApiOperation(value = "退出登录")
	@PostMapping("/logout")
	public RespBean logout() {
		return RespBean.success("退出成功！");
	}


	// 注册
	// 注册
	@ApiOperation(value = "用户注册")
	@PostMapping("/register")
	public RespBean registerUser(@RequestBody String username, String password, String phone,Boolean enabled) {

		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPhone(phone);
		admin.setEnabled(enabled );
		admin.setPassword(passwordEncoder.encode(password));
		adminService.save(admin);
		return RespBean.success("注册成功", admin);
	}

	// 注册 可以注册，但是不可以加密
//	@ApiOperation(value = "用户注册")
//	@PostMapping("/register")
//	public RespBean registerUser(@RequestBody Admin admin) {
//
//		if ( adminService.save(admin) ){
//			return RespBean.success("注册成功", admin);
//		}
//		return RespBean.error( "添加失败！" );
//	}

	/*@ApiOperation(value = "添加职称")
	@PostMapping("/")
	public RespBean addJobLevel(@RequestBody Joblevel joblevel){
		joblevel.setCreateDate( LocalDateTime.now() );
		if ( joblevelService.save( joblevel ) ){
			return RespBean.success( "添加成功！ " );
		}
		return RespBean.error( "添加失败！" );
	}*/


}
