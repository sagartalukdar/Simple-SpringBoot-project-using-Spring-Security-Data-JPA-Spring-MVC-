package com.ss.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ss.entity.Student;
import com.ss.service.Userservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private Userservice us;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
	  Set<String>roles=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
	
	   CustomUserdetails cu=(CustomUserdetails) authentication.getPrincipal();
	   Student st=cu.getSt();
	   if(st!=null) {
		   us.resetAttempt(st.getEmail());
	   }
		   
	  if(roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("admin/profile");
		}else {
			response.sendRedirect("user/profile");
		}
	}

}
