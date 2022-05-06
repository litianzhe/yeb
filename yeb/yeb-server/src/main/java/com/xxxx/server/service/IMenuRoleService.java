package com.xxxx.server.service;

import com.xxxx.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.util.RespBean;

/**
 * <p>
 *  服务类  菜单角色中间表
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface IMenuRoleService extends IService<MenuRole> {


	/**
	 * 更新角色菜单
	 * @param rid
	 * @param mids
	 * @return
	 */
	RespBean updateMenuRole(Integer rid, Integer[] mids);
}
