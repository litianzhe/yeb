package com.xxxx.server.mapper;

import com.xxxx.server.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据用户id查询角色列表
	 * @param adminId
	 * @return
	 */
	List<Role> getRoles(Integer adminId);
}
