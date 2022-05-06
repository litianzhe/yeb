package com.xxxx.server.mapper;

import com.xxxx.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口  管理员表
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface AdminMapper extends BaseMapper<Admin> {

	/**
	 * 获取所有操作员
	 * @param keywords
	 * @return
	 */
	List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
