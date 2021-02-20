package com.wtw.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.wtw.crowd.constraint.CrowdConstraint;

@Configuration
@EnableWebSecurity // 开启web权限控制
//开启全局方法权限控制 保证@PreAuthority， @PostAuthority, @PreFilter
@EnableGlobalMethodSecurity(prePostEnabled = true)	
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CrowdUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity security) throws Exception {

		security.authorizeRequests()
				.antMatchers("/admin/to/login/page.html", "/jquery/**", "/bootstrap/**", "/css/**", "/fonts/**",
						"/img/**", "/script/**", "/ztree/**", "/layer/**", "/crowd/**", "/WEB-INF/**")
				.permitAll()
				.antMatchers("/admin/get/page.html").hasRole("经理")		// 配置分页界面要拥有经理角色才能访问
				.anyRequest().authenticated() // 配置请他请求进行认证访问
				.and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {	// 设置权限不足的后续处理
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						request.setAttribute("exception", new Exception(CrowdConstraint.MESSAGE_ACCESS_DENIED));
						request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
					}
				})
				.and().csrf().disable() // 禁用csrf
				.formLogin() // 开启表单登录
				.loginPage("/admin/to/login/page.html") // 指定登录页面
				.loginProcessingUrl("/security/do/login.html")// 处理登录请求的url
				.defaultSuccessUrl("/admin/to/main/page.html")// 指定登录成功后前往的页面
				.usernameParameter("loginAcct") // 账号请求参数名称
				.passwordParameter("userPswd") // 密码请求参数名称
				.and().logout() // 开启退出登录功能
				.logoutUrl("/security/do/logout.html") // 退出登录的请求
				.logoutSuccessUrl("/admin/to/login/page.html") // 退出登录成功后跳转的地址
		;

	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {

		// builder.inMemoryAuthentication()
		// .withUser("wtw")
		// .password("123123")
		// .roles("ADMIN")
		// ;

		builder
			.userDetailsService(userDetailsService)
			.passwordEncoder(getBCryptPasswordEncoder());

	}

}
