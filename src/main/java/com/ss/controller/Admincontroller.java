package com.ss.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss.entity.Student;
import com.ss.repository.Userrepo;

@Controller
@RequestMapping("admin")
public class Admincontroller {

	@Autowired
	private Userrepo ur;
	@ModelAttribute
	public void principal(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			Student st=ur.findByEmail(email);
		    m.addAttribute("admin", st);
		}
	}
	@RequestMapping("profile")
	public String adminProfile() {
		return "adminProfile";
	}
}
