package com.xxxx.server.mapper;

import com.xxxx.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 通过用户ID查询菜单列表
	 * @param id
	 * @return
	 */
	List<Menu> getMenusByAdminId(Integer id);

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
