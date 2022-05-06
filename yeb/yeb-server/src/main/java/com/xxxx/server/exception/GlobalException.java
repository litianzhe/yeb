package com.xxxx.server.exception;

import com.xxxx.server.util.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @className: GlobalException
 * @copyright: HTD
 * @description: 全局异常处理
 * 例如有数据库主键，删除失败，抛出异常   RestControllerAdvice = ControllerAdvice + ResponseBody
 * @author: 里天者
 * @date: 2022/2/23
 * @version: 1.0
 */
@RestControllerAdvice  // 都是对Controller进行增强的，可以全局捕获spring mvc抛的异常
public class GlobalException {

	@ExceptionHandler(SQLException.class)   //用来捕获指定的异常
	public RespBean mySqlException(SQLException e){
		if ( e instanceof SQLIntegrityConstraintViolationException ) {
			return  RespBean.error("该数据有关联数据，操作失败！"  );
		}
		return RespBean.error("数据库异常，操作失败！");
	}

}
