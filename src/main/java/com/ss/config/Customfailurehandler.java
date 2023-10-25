package com.ss.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ss.entity.Student;
import com.ss.repository.Userrepo;
import com.ss.service.Userservice;
import com.ss.service.Userserviceimpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Customfailurehandler extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	private Userservice us;
	@Autowired
	private Userrepo ur;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String email=request.getParameter("username");
		Student st=ur.findByEmail(email);
		if(st!=null) {
		
		 if(st.isEnable()) {
			 if(st.isAccountNonLocked()) {
				 if(st.getFailAttempt()<Userserviceimpl.finalattempt-1) {
					 us.increaseFailAttempt(st);
				 }else {
					us.lock(st);
					exception=new LockedException("Accout locked for a while cause of 3 failure attempt ");
			     }
		     }else if (!st.isAccountNonLocked()) {
				if(us.lockTimeExpired(st)) {
					exception=new LockedException("account retrived ! please login now");
				}else {
					exception=new LockedException("account is locked please try later");
				}
			}
		}
		 else {
				exception=new LockedException("Please verify first");
		}
		}
		super.setDefaultFailureUrl("/userlogin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

}
