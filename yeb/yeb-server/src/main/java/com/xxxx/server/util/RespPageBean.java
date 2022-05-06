package com.xxxx.server.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: RespPageBean
 * @copyright: HTD
 * @description: 分页公共返回对象
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/3
 * @version: 1.0
 */
@Data
@ApiModel("分页公共返回对象")
@NoArgsConstructor  //无参构造
@AllArgsConstructor   //有参构造
public class RespPageBean {

	/**
	 * 总条数
	 */
	@ApiModelProperty(value = "总条数",required = true)
	private Long total;

	/**
	 * 数据 list
	 */
	@ApiModelProperty(value = "分页数据集合",required = true)
	private List<?> data;
}
