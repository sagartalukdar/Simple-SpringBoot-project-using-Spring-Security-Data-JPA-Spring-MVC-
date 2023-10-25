package com.ss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class Securityconfig {

	@Autowired
	private AuthenticationSuccessHandler ash;
	@Autowired
	private Customfailurehandler cfh;
	@Bean
	public BCryptPasswordEncoder pe() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public UserDetailsService uds() {
		return new Customuserdetailsservice();
	}
	@Bean
	public DaoAuthenticationProvider dap() {
		DaoAuthenticationProvider d=new DaoAuthenticationProvider();
		d.setUserDetailsService(uds());
		d.setPasswordEncoder(pe());
		return d;
	}
	@Bean
	public SecurityFilterChain sfc(HttpSecurity hs) throws Exception {
//	hs.csrf().disable().authorizeHttpRequests()
//	.requestMatchers("/","/registration","/userlogin","/registered")
//	.permitAll().requestMatchers("/user/**").authenticated()
//	. anyRequest().authenticated().and().formLogin()
//	.loginPage("/userlogin").loginProcessingUrl("/log")
//	.defaultSuccessUrl("/user/profile")
//    .permitAll();
		
		hs.csrf().disable().authorizeHttpRequests()
		.requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/**").permitAll()
		.and().formLogin().loginPage("/userlogin")
		.loginProcessingUrl("/log")
		.failureHandler(cfh)
		.successHandler(ash)
		.permitAll();
	return hs.build();
	}
}
