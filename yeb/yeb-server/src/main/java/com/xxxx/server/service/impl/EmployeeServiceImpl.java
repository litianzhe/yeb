package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.mapper.MailLogMapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.mapper.EmployeeMapper;
import com.xxxx.server.pojo.MailLog;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.util.MailConstants;
import com.xxxx.server.util.RespBean;
import com.xxxx.server.util.RespPageBean;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MailLogMapper mailLogMapper;
	/**
	 * 分页查询所有员工
	 * @param currentPage
	 * @param size
	 * @param employee
	 * @param  beginDateScope
	 * @return
	 */
	@Override
	public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
		// 开启分页
		Page<Employee> page = new Page<>( currentPage, size );
		IPage<Employee> employeeByPage = baseMapper.getEmployeeByPage(page, employee, beginDateScope); //EmployeeMapper
		RespPageBean respPageBean = new RespPageBean( employeeByPage.getTotal(), employeeByPage.getRecords() );
		return respPageBean;
	}

	/**
	 * 获取工号 :添加员工工号是当前最大工号+1
	 *
	 * @return String.format(" % 08d " = 获得固定8位长度的数字 （ 不足的前面补0 ）
	 */
	@Override
	public RespBean maxWorkID() {
		List<Map<String, Object>> maps = baseMapper.selectMaps( new QueryWrapper<Employee>(  ).select( "max(workId)" ) );
		return RespBean.success( null, String.format( "%08d", Integer.parseInt( maps.get( 0 ).get( "max(workId)" ).toString() ) + 1 ) );
	}

	/**
	 * 添加员工
	 *
	 * @param employee
	 * @return
	 */
	@Override
	public RespBean addEmp(Employee employee) {
		// 处理合同期限，保留两位小数
		LocalDate beginContract = employee.getBeginContract();// 合同开始时间
		LocalDate endContract = employee.getEndContract();// 合同结束时间
		// 计算 两个日期相差多少天
		Long days = beginContract.until( endContract, ChronoUnit.DAYS );
		// 保留两位小数
		DecimalFormat decimalFormat = new DecimalFormat( "##.00" );
		// 计算以年为单位
		employee.setContractTerm( Double.parseDouble( decimalFormat.format( days / 365.00 ) ) );
		if ( 1 == employeeMapper.insert( employee ) ) {
			// 获取当前行添加员工记录
			Employee emp = baseMapper.getEmployee(employee.getId()).get( 0 );
			// 数据库记录发送的消息
			String msgId = UUID.randomUUID().toString();
			// 测试消费端幂等性
			//String msgId = "123456";
			MailLog mailLog = new MailLog();
			mailLog.setMsgId( msgId );
			mailLog.setEid( employee.getId() );
			mailLog.setStatus( 0  );
			mailLog.setRouteKey( MailConstants.MAIL_ROUTING_KEY_NAME  );
			mailLog.setExchange( MailConstants.MAIL_EXCHANGE_NAME );
			mailLog.setCount( 0 );
			mailLog.setTryTime( LocalDateTime.now().plusMinutes( MailConstants.MSG_TIMEOUT ) );
			mailLog.setCreateTime( LocalDateTime.now() );
			mailLog.setUpdateTime( LocalDateTime.now() );

			// 把设置的数据插入数据表
			mailLogMapper.insert( mailLog );

			// 发送信息
			rabbitTemplate.convertAndSend( MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME,
					emp, new CorrelationData( msgId ) );  // mq 路由 key

			return RespBean.success("添加成功！");
		}
		return RespBean.error("添加失败！");
	}

	/**
	 * 查询员工
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<Employee> getEmployee(Integer id) {
		return employeeMapper.getEmployee( id );
	}



	/**
	 * 获取所有员工账套(分页）
	 * @param currentPage
	 * @param size
	 * @return
	 */
	@Override
	public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
		// 开启分页
		Page<Employee> page = new Page<>( currentPage, size );
		//IPage<Salary> salaryIPage = baseMapper.getEmployeeWithSalary( page, currentPage );
		IPage<Employee> employeeIPage = baseMapper.getEmployeeWithSalary(page); //EmployeeMapper
		RespPageBean respPageBean =  new RespPageBean( employeeIPage.getTotal(), employeeIPage.getRecords() );
		return respPageBean;
	}

}
