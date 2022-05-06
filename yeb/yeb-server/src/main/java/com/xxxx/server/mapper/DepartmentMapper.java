package com.xxxx.server.mapper;

import com.xxxx.server.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口  部门表
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface DepartmentMapper extends BaseMapper<Department> {

	/**
	 * 获取所有部门
	 * @return
	 */
	List<Department> getAllDepartments(Integer parentId);

	/**
	 * 添加部门
	 * @param dep
	 * @return
	 */
	void addDep(Department dep);

	/**
	 * 删除部门
	 * @param dep
	 * @return
	 */
	void deleteDep(Department dep);
}
