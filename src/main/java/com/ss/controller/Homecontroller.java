package com.ss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss.entity.Student;
import com.ss.service.Userservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Homecontroller {
	@Autowired
	private Userservice us;
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("home")
	public String home() {
		return "home";
	}
	@GetMapping("userlogin")
	public String login() {
		return "login";
	}
	@GetMapping("registration")
	public String registration() {
		return "register";
	}
	
	@PostMapping("registered")
	public String register(@ModelAttribute Student s,HttpSession hs,HttpServletRequest req) {
		String url=req.getRequestURL().toString();
		url=url.replace(req.getServletPath(), "");
	   Student st=us.saveUser(s,url);
	   if(st!=null) {
		   hs.setAttribute("sucmsg", "User Registered");
	   }else {
		   hs.setAttribute("errmsg", "User not Registered YET !"); 
	   }
		return "redirect:/registration";
	}
	
//	@GetMapping("verify")
//	public String verify(@Param("code")String code,Model m) {
//		// TODO Auto-generated method stub
//		boolean b=us.verifyuser(code);
//		if(b) {
//			m.addAttribute("msg","verified");
//		}else {
//			m.addAttribute("msg", "bad occurence");
//		}
//     return "message";
//	}

	@GetMapping("verify")
	public String uv(@Param("code")String code,Model m) {
		boolean b=us.verifyuser(code);
		if(b) {
			m.addAttribute("msg","user Verified");
		}else {
			m.addAttribute("msg", "error or has been verified");
		}
		return "message";
	}
}
