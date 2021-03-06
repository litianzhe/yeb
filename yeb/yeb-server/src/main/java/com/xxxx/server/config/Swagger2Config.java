package com.xxxx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: Swagger2Config
 * @copyright: HTD
 * @description: Swagger2 配置类
 *    自动生成接口文档  @EnableSwagger2  开启Swagger2
 * @author: 里天者
 * @date: 2022/2/21
 * @version: 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	//规定扫描什么包下面的注解
	@Bean
	public Docket createRestAPI() {
		return new Docket( DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//指定哪个包下面生成接口文档
				.apis( RequestHandlerSelectors.basePackage("com.xxxx.server.controller"))
				.paths( PathSelectors.any())
				.build()
				.securityContexts(securityContexts())
				.securitySchemes(securitySchemes()); // 配置请求头信息
	}

	/**
	 * 文档基本信息
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.version("1.0")
				.title("云E办接口文档")
				.description("云E办接口文档")
				.contact(new Contact("Litian", "localhost:8081/doc.html", "xxxx@xxxx.com"))
				.build();

	}

	// 1. 解决访问接口登录问题
	private List<ApiKey> securitySchemes() {
		// 设置请求头信息
		List<ApiKey> result = new ArrayList<>();
		// 参数：api key 名字 { 准备的 key 名字，value 请求头 }
		result.add(new ApiKey("Authorization", "Authorization", "header"));
		return result;
	}

	// 2. 解决访问接口登录问题
	private List<SecurityContext> securityContexts() {
		// 设置需要登录认证的路径
		List<SecurityContext> result = new ArrayList<>();
		result.add(getContextByPath());
		return result;
	}

	// 3. 解决访问接口登录问题
	private SecurityContext getContextByPath() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/hello/.*"))
				.forPaths(PathSelectors.regex("/register/.*"))
				.build();
	}
	// 3 或者 在res.add(getContextByPath("/hello/*"));在
	/*private SecurityContext getContextByPath(String pathRegex) {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(pathRegex))
				.build();
	}*/

	// 4. 设置默认授权 - 解决访问接口登录问题
	private List<SecurityReference> defaultAuth() {
		List<SecurityReference> result = new ArrayList<>();
		AuthorizationScope authorizationScope = new AuthorizationScope("global",
				"accessEverything");
		AuthorizationScope[] scopes = new AuthorizationScope[1];
		scopes[0] = authorizationScope;
		result.add(new SecurityReference("Authorization", scopes));
		return result;
	}

}
