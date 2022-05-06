package com.xxxx.server.service;

import com.xxxx.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.util.RespBean;
import com.xxxx.server.util.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
public interface IEmployeeService extends IService<Employee> {

	/**
	 * 分页查询所有员工
	 * @param currentPage
	 * @param size
	 * @param employee
	 * @param  beginDateScope
	 * @return
	 */
	RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

	/**
	 * 获取工号
	 * @return
	 */
	RespBean maxWorkID();

	/**
	 * 添加员工
	 *
	 * @param employee
	 * @return
	 */
	RespBean addEmp(Employee employee);

	/**
	 * 查询员工
	 * @param id
	 * @return
	 */
	List<Employee> getEmployee(Integer id);



	/**
	 * 获取所有员工账套(分页）
	 * @param currentPage
	 * @param size
	 * @return
	 */
	RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
