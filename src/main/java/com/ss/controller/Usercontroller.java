package com.ss.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss.entity.Student;
import com.ss.repository.Userrepo;

@Controller
@RequestMapping("user")
public class Usercontroller {

	@Autowired
	private Userrepo ur;
	@ModelAttribute
	public void principal(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			Student st=ur.findByEmail(email);
			m.addAttribute("user", st);
		}
	}
	@RequestMapping("profile")
	public String userprofile() {
		return "profile";
	}
}
