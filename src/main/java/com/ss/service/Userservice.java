package com.ss.service;

import com.ss.entity.Student;

public interface Userservice {

	public Student saveUser(Student s,String url);
	
	public void removeSessionMessage();
	
	public void sendMail(Student st,String url) ;
	
	public boolean verifyuser(String vc);
	
	public void increaseFailAttempt(Student st) ;
	
	public void resetAttempt(String email);
	
	public void lock(Student st) ;
	
	public boolean lockTimeExpired(Student st);
	
}
