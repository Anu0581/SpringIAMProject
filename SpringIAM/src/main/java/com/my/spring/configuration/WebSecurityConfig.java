package com.my.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		  http
		     .csrf(csrf-> csrf.ignoringAntMatchers("/postOrder/**","/console/**"))
			 .authorizeRequests()
	         .antMatchers("/", "/error**", "/favicon.ico").permitAll() 
	         .anyRequest().authenticated()
	         .and()
	     .oauth2Login()
	         .loginPage("/oauth2/authorization/appid-client")
	         .defaultSuccessUrl("/", true)
	         .permitAll()
	         .and()
	     .logout()
	         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	         .logoutSuccessUrl("/")
	         .permitAll()
	         .and()
	     .oauth2Client();
	}
}
