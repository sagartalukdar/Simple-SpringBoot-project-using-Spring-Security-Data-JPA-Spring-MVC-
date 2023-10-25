package com.ss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ss.entity.Student;
import com.ss.repository.Userrepo;

@Component
public class Customuserdetailsservice implements UserDetailsService{

	@Autowired
	private Userrepo ur;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student s=ur.findByEmail(username);
		if(s!=null) {
			return new CustomUserdetails(s);
		}else {
			throw new UsernameNotFoundException("Entry not found");
		}
	}

}
