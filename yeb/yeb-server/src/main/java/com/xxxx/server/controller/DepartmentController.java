package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.service.IDepartmentService;
import com.xxxx.server.util.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器  部门表
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;

	@ApiOperation( value = "获取所有部门")
	@GetMapping("/")
	public List<Department> getAllDepartments(){
		return departmentService.getAllDepartments();   // 有子部门列表
	}

	@ApiOperation( value = "添加部门")
	@PostMapping("/")
	public RespBean addDep (@RequestBody Department dep){
		return departmentService.addDep(dep);
		/*if ( departmentService.save( department ) ){
			return RespBean.success( "" );
		}
		return RespBean.error( "" );*/
	}

	@ApiOperation(value = "删除部门")
	@DeleteMapping("/{id}")
	public RespBean deleteDep(@PathVariable Integer id){
		return departmentService.deleteDep(id);
	}

	//@ApiOperation( value = "更新部门")

}
