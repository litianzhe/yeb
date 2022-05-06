package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.util.FastDFSUtils;
import com.xxxx.server.util.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @className: AdminInfoController
 * @copyright: HTD
 * @description: 个人中心
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/22
 * @version: 1.0
 */
@RestController
public class AdminInfoController {

	@Autowired
	private IAdminService adminService;

	@ApiOperation( value = "更新当前用户信息")
	@PutMapping("/admin/info")
	public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication){
		if ( adminService.updateById( admin ) ){
			// // 更新成功重新设置 authentication(身份验证) 对象
			SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken(
					admin, null, authentication.getAuthorities()  ) );
			return RespBean.success( "更新成功！" );
		}
		return RespBean.error( "更新失败！" );
	}

	@ApiOperation( value = "更新用户密码")
	@PutMapping("/admin/pass")
	public RespBean updateAdminPassword(@RequestBody Map<String,Object> info) {
		String oldPass = (String) info.get( "oldPass" );
		String pass = (String) info.get("pass");
		Integer adminId = (Integer) info.get("adminId");
		return adminService.updateAdminPassword( oldPass,pass,adminId );
	}

	/**
	 * FastDFS应该不行，需要安装FastDFS和NGinx
	 *
	*/
	@ApiOperation(value = "更新用户头像")
	@PostMapping("/admin/userFace")
	public RespBean updateAdminUserFace(MultipartFile file, Integer adminId, Authentication authentication){
		String[] filePath = FastDFSUtils.upload(file);
		String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
		return adminService.updateAdminUserFace(url,adminId,authentication);
	}


}
