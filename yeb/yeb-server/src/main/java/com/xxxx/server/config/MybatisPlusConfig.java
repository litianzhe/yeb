package com.xxxx.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: MybatisPlusConfig
 * @copyright: HTD
 * @description: 分页
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/3/3
 * @version: 1.0
 */
@Configuration
public class MybatisPlusConfig {

	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor(){
		return new PaginationInterceptor();

	}

	/**
	 * 分页插件
	 * @return
	 */
//	@Bean
//	public MybatisPlusInterceptor paginationInterceptor() {
//		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//		PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor( DbType.MYSQL);
//		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//		//paginationInterceptor.setOverflow(true);
//		// 设置最大单页限制数量，默认 500 条，-1 不受限制
//		//paginationInterceptor.setMaxLimit(500L);
//		interceptor.addInnerInterceptor( paginationInterceptor );
//		return interceptor;
//	}

}
