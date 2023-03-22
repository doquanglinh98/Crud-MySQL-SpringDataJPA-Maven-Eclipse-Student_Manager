package com.tlu.student.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlu.student.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query(value = "SELECT * FROM student u WHERE u.Full_Name = ?1", nativeQuery = true)
	public Student findByFullName(String fullName);
	
	@Query(value = "SELECT * FROM student u WHERE u.Phone_Number = ?1", nativeQuery = true)
	public Student findByPhoneNumber(int phoneNumber);
	
	@Query(value = "SELECT * FROM student u WHERE u.Data_Of_Birth = ?1", nativeQuery = true)
	public Student findByDateOfBirth(Date dateOfBirth);

}
