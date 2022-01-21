package com.mooop.board.sec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Value("${msp.security.password.encoding}")
	private boolean encoding;
	
	@Value("${security.enable}")
	private String securityEnable;
	
	private final CustomAuthenticationProvider customAuthenticationProvider;

	private static final String[] IGNORE_CSRF_URI = {"/test/**" , "/h2/**"};

	public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
		this.customAuthenticationProvider = customAuthenticationProvider;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if(securityEnable.equals("N")) {
			http.csrf().disable().authorizeRequests().anyRequest().permitAll()
			.and()
			.cors().disable()
			.headers().frameOptions().sameOrigin();
		}else {
			http.headers().frameOptions().sameOrigin()
			.and()
			.authorizeRequests()
					.antMatchers("/" ,"/login","/login/**" ,"/ImageView*/**","/common/**").permitAll()
					.antMatchers("/test/**" , "/h2/**").permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/board/**").access("hasRole('GUEST') or hasRole('USER') or hasRole('ADMIN')")
					.anyRequest().authenticated()
					.and().csrf().ignoringAntMatchers(IGNORE_CSRF_URI)
					;
					
			http.formLogin().loginPage("/login")
							.usernameParameter("username")
							.passwordParameter("password")
							.loginProcessingUrl("/j_spring_security_check")
							.successHandler(loginSuccessHandler())
							.failureHandler(loginfailHandler())
							.and()
							.logout()
								.logoutUrl("/logout")
								.invalidateHttpSession(true)
								.deleteCookies("JSESSIONID");
		}
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}
	
	
	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	@Bean
	public LoginFailHandler loginfailHandler() {
		return new LoginFailHandler();
	}

}
