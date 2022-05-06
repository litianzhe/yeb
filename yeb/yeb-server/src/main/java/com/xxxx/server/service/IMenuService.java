package com.xxxx.server.service;

import com.xxxx.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类  菜单表 服务类
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 通过用户id查询菜单列表
	 * @return
	 */
	List<Menu> getMenusByAdminId();

	/**
	 * 根据角色获取菜单列表
	 * @return
	 */
	List<Menu> getMenusWithRole();

	/**
	 * 查询所有菜单
	 * @return
	 */
	List<Menu> getAllMenus();

}
