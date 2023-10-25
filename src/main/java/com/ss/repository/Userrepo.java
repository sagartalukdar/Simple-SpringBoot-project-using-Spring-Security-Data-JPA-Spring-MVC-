package com.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ss.entity.Student;

public interface Userrepo extends JpaRepository<Student, Integer>{

	public Student findByEmail(String e);
	
	public Student findByVerificationcode(String verificationcode) ;
	
	@Query("update Student u set u.failAttempt=?1 where email=?2")
	@Modifying
	public void updateFailAttempt(int fa,String em);
}
