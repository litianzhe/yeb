package com.xxxx.server.mapper;

import com.xxxx.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口  菜单角色中间表 Mapper 接口
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

	/**
	 * 更新角色菜单
	 * @param rid
	 * @param mids
	 * @return 返回受影响行数
	 */
	Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
