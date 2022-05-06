package com.xxxx.server.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: RespBean
 * @copyright: HTD
 * @description: 公共返回对象
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/18
 * @version: 1.0
 */
@Data
@ApiModel("统一返回结果")
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

	@ApiModelProperty(value = "结果状态码",required = true)
	private long code;

	@ApiModelProperty(value = "状态信息")
	private String message;

	@ApiModelProperty(value = "返回数据对象")
	private Object obj;

	/**
	 * 成功返回结果 不带对象
	 *
	 * @param message
	 * @return
	 */
	public static RespBean success(String message) {
		return new RespBean(200, message, null);
	}


	/**
	 * 成功返回结果 带对象
	 *
	 * @param message
	 * @param obj
	 * @return
	 */
	public static RespBean success(String message, Object obj) {
		return new RespBean(200, message, obj);
	}

	/**
	 * 失败返回结果,不带对象
	 *
	 * @param message
	 * @return
	 */
	public static RespBean error(String message) {
		return new RespBean(500, message, null);
	}

	/**
	 * 失败返回结果,带对象
	 *
	 * @param message
	 * @param obj
	 * @return
	 */
	public static RespBean error(String message, Object obj) {
		return new RespBean(500, message, obj);
	}

}
