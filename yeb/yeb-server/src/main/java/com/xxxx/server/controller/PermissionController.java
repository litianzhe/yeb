package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuRoleService;
import com.xxxx.server.service.IMenuService;
import com.xxxx.server.service.IRoleService;
import com.xxxx.server.util.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: PermissionController
 * @copyright: HTD
 * @description: 权限组 - 角色
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/23
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IMenuRoleService menuRoleService;

	@ApiOperation(value = "获取所有角色")
	@GetMapping("/")
	public List<Role> getAllRoles() {
		return roleService.list();
	}

	@ApiOperation(value = "添加角色")
	@PostMapping("/role")
	public RespBean addRole(@RequestBody Role role){
		//role.setCreateDate( LocalDateTime.now() );
		if (!role.getName().startsWith("ROLE_")) {
			role.setName("ROLE_" + role.getName());
		}
		if ( roleService.save( role ) ){
			return RespBean.success( "添加成功！" );
		}
		return RespBean.error( "添加失败！" );

	}

	@ApiOperation(value = "删除角色")
	@DeleteMapping("/role/{rid}")
	public RespBean deleteRole(@PathVariable Integer rid){
		//role.setCreateDate( LocalDateTime.now() );
		if ( roleService.removeById( rid ) ){
			return RespBean.success( "删除成功！" );
		}
		return RespBean.error( "删除失败！" );

	}

	@ApiOperation(value = "查询所有菜单")
	@GetMapping("/menus")
	public List<Menu> getAllMenus() {
		//return menuService.list(); // 有子菜单
		return menuService.getAllMenus();
	}

	@ApiOperation(value = "根据角色 id 查询菜单 id")
	@GetMapping("/mid/{rid}")
	public List<Integer> getMidByRid(@PathVariable Integer rid) {
		//需要条件
		return menuRoleService.list(new QueryWrapper<MenuRole>()
				.eq("rid", rid))
				.stream().map(MenuRole::getMid)
				.collect( Collectors.toList());
	}


	@ApiOperation(value = "更新角色菜单")
	@PutMapping("/")
	public RespBean updateMenuRole(Integer rid, Integer[] mids) {
		return menuRoleService.updateMenuRole(rid, mids);
	}


}
