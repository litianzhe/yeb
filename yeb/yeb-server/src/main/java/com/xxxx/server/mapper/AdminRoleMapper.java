package com.xxxx.server.mapper;

import com.xxxx.server.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口   管理员角色中间表
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

	/**
	 * 更新操作员角色
	 * @param adminId
	 * @param rids
	 * @return
	 */
	Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
