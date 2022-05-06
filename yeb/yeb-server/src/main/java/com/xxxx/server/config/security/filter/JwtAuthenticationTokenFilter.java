package com.xxxx.server.config.security.filter;

import com.xxxx.server.config.security.component.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: JwtAuthenticationTokenFilter
 * @copyright: HTD
 * @description: jwt登录授权过滤器
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/21
 * @version: 1.0
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Value("${jwt.tokenHeader}")
	private String tokenHeader; // JWT 存储的请求头( key )
	@Value("${jwt.tokenHead}")
	private String tokenHead; // JWT 负载中拿到开头( value )
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	// 前置拦截器
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		// 根据 key 获取 value（要验证的头）
		String authHeader = request.getHeader(tokenHeader);
		// token存在
		// 如果拿到 value，并且是根据 Bearer 开头的
		if (null != authHeader && authHeader.startsWith(tokenHead)){
			// 截取字符串 当令牌
			String authToken = authHeader.substring(tokenHead.length());
			String username = jwtTokenUtil.getUserNameFromToken(authToken);
			// token 存在用户名，但未登录( 在 springSecurity 上下文拿不到 用户对象 ）
			// token存在用户名但未登录
			if (null != username && null == SecurityContextHolder
					.getContext().getAuthentication()){
				// 登录(通过 username 拿到 UserDetails ）
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// 验证token是否有效，重新设置用户对象
				if (jwtTokenUtil.validateToken(authToken, userDetails)){
					// 参数：用户对象 密码 角色
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities() );
					// 重新设置用户对象到 springSecurity上下文中去
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		// 放行
		filterChain.doFilter(request, response);

	}
}
