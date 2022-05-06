package com.xxxx.server.service.impl;

import com.xxxx.server.pojo.Menu;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.util.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  菜单表 服务实现类
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;
	/*@Autowired
	private RedisTemplate redisTemplate;*/
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 通过用户id查询菜单列表
	 *
	 * @return
	 */
	@Override
	public List<Menu> getMenusByAdminId() {
		// Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		// 上面写法使用方法抽取工具类
		Integer adminId = AdminUtils.getCurrentAdmin().getId();
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		// 从 redis 获取菜单数据
		List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
		// 如果为空，去数据库获取
		if ( CollectionUtils.isEmpty(menus)) {
			menus = baseMapper.getMenusByAdminId(adminId); //
			// 将数据设置到 Redis 中
			valueOperations.set("menu_" + adminId, menus);
		}
		return menus;
	}

	/**
	 * 根据角色获取菜单列表
	 *
	 * @return
	 */
	@Override
	public List<Menu> getMenusWithRole() {
		return baseMapper.getMenusWithRole();
	}

	/**
	 * 查询所有菜单
	 * @return
	 */
	@Override
	public List<Menu> getAllMenus() {
		return baseMapper.getAllMenus();
	}
}
