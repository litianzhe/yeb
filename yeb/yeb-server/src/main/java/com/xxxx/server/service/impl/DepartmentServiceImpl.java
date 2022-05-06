package com.xxxx.server.service.impl;

import com.xxxx.server.pojo.Department;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类  部门表
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

	@Autowired
	private  DepartmentMapper departmentMapper;
	/**
	 * 获取所有部门
	 *
	 * @return
	 */
	@Override
	public List<Department> getAllDepartments() {
		return baseMapper.getAllDepartments(-1);
	}

	/**
	 * 添加部门
	 *
	 * @param dep
	 * @return
	 */
	@Override
	public RespBean addDep(Department dep) {
		dep.setEnabled( true );
		//baseMapper.addDep(dep);
		departmentMapper.addDep( dep );
		if (1 == dep.getResult()) {
			return RespBean.success("添加成功！",dep);
		}
		return RespBean.error("添加失败！");
	}

	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	@Override
	public RespBean deleteDep(Integer id) {
		Department dep = new Department();
		dep.setId(id);
		baseMapper.deleteDep(dep);
		if (-2==dep.getResult()) {
			return RespBean.error("删除失败，该部门下还有子部门！");
		}
		if (-1==dep.getResult()) {
			return RespBean.error("删除失败，该部门下还有员工！");
		}
		if (1==dep.getResult()) {
			return RespBean.success("删除成功！");
		}
		return RespBean.error("删除失败！");
	}
}
