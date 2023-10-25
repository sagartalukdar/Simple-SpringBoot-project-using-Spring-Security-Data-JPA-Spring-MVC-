package com.ss.service;

import java.util.Date;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ss.entity.Student;
import com.ss.repository.Userrepo;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class Userserviceimpl implements Userservice{

	@Autowired
	private Userrepo ur;
	@Autowired
	private Userservice us;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private JavaMailSender jms;
	
	private static final long lockDurationTime=50000;
	public static final long finalattempt=3;
	@Override
	public Student saveUser(Student s,String url) {
		
		String p=s.getPassword();
		String enp=pe.encode(p);
		s.setPassword(enp);
		s.setRole("ROLE_USER");
		s.setEnable(false);
		s.setVerificationcode(UUID.randomUUID().toString());
		s.setAccountNonLocked(true);
		s.setLockTime(null);
		s.setFailAttempt(0);
		Student ns= ur.save(s);
		if(ns!=null) {
			sendMail(s, url);
		}
		return ns;
	}
	@Override
	public void removeSessionMessage() {
		// TODO Auto-generated method stub
	    HttpSession h=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
	    h.removeAttribute("sucmsg");
	    h.removeAttribute("errmsg");
	    h.removeAttribute("msg");
	}
	@Override
	public void sendMail(Student st, String url) {
		// TODO Auto-generated method stub
		String from="sagartalukdar0123456789@gmail.com";
		String to=st.getEmail();
		String subject="Account Verification";
		String content="Dear [[name]],<br>"+"please Click the link below"+
		"to verify your registration,<br>"+
		"<h3><a href=\"[[URL]]\" target=\"_self\">Verify</a></h3>";
		
	    try {
			MimeMessage mm=jms.createMimeMessage();
			MimeMessageHelper mmh=new MimeMessageHelper(mm);
			mmh.setFrom(from,"sagar");
			mmh.setTo(to);
			mmh.setSubject(subject);
			
			content =content.replace("[[name]]", st.getName());
			String curl=url+"/verify?code="+st.getVerificationcode();
			content=content.replace("[[URL]]", curl);
			System.out.println(curl);
			mmh.setText(content,true);
			jms.send(mm);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	@Override
	public boolean verifyuser(String vc) {
		// TODO Auto-generated method stub
		System.out.println(vc);
		Student s=ur.findByVerificationcode(vc);
		System.out.println(s);
		if(s!=null) {
			
			s.setEnable(true);
			s.setVerificationcode(null);
			ur.save(s);
			return true;
		}else {
			return false;
		}
	}
	@Override
	public void increaseFailAttempt(Student st) {
		// TODO Auto-generated method stub
		int fa=st.getFailAttempt()+1;
		ur.updateFailAttempt(fa, st.getEmail());
		
	}
	@Override
	public void resetAttempt(String email) {
		// TODO Auto-generated method stub
		ur.updateFailAttempt(0, email);
	}
	@Override
	public void lock(Student st) {
		// TODO Auto-generated method stub
		st.setAccountNonLocked(false);
		st.setLockTime(new Date());
		ur.save(st);
	}
	@Override
	public boolean lockTimeExpired(Student st) {
		// TODO Auto-generated method stub
		long lockTimeInMilis=st.getLockTime().getTime();
		long currentTimeInMilis =System.currentTimeMillis();
		
		if(lockTimeInMilis+lockDurationTime<currentTimeInMilis) {
			st.setAccountNonLocked(true);
			st.setLockTime(null);
			st.setFailAttempt(0);
			ur.save(st);
			return true;
		}
		 return false;
	}

	

}
