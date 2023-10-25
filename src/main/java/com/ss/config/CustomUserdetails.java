package com.ss.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ss.entity.Student;

public class CustomUserdetails implements UserDetails{

	
	private Student st;
	
	public CustomUserdetails(Student s) {
		super();
		this.st = s;
	}

	public Student getSt() {
		return st;
	}

	public void setSt(Student st) {
		this.st = st;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority s=new SimpleGrantedAuthority(st.getRole());
		return Arrays.asList(s);
	}

	@Override
	public String getPassword() {
		return st.getPassword();
	}

	@Override
	public String getUsername() {
		return st.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return st.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return st.isEnable();
	}

}
