package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.service.ISalaryService;
import com.xxxx.server.util.RespBean;
import com.xxxx.server.util.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @className: SalarySobCfgController
 * @copyright: HTD
 * @description: 员工账套
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/18
 * @version: 1.0
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

	@Autowired
	private ISalaryService salaryService;

	@Autowired
	private IEmployeeService employeeService;

	@ApiOperation(value = "获取所有工资账套")
	@GetMapping("/salaries")
	public List<Salary> getAllSalaries() {
		return salaryService.list();
	}

	@ApiOperation(value = "获取所有员工账套(分页）")
	@GetMapping("/")
	public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage, // 当前页
	                                @RequestParam(defaultValue = "10") Integer size
	                                //Employee employee, LocalDate[] beginDateScope   //没有搜索，所以不需要条件
									){
		return employeeService.getEmployeeWithSalary(currentPage, size);

	}

	@ApiOperation(value = "更新员工账套")
	@PutMapping("/")
	public RespBean updateEmployeeSalary(Integer eid, Integer sid) {
		if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))) {
			return RespBean.success("更新成功！");
		}
		return RespBean.error("更新失败！");
	}


}
