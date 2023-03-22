package com.tlu.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlu.student.model.Student;
import com.tlu.student.repository.StudentRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@GetMapping("get_all")
	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}

	@GetMapping("get_student/{id}")
	public ResponseEntity<Student> getStudentByID(@PathVariable(value = "id") Long studentID) {
		Student student = studentRepository.findById(studentID).orElse(null);

		return ResponseEntity.ok().body(student);
	}

	@PostMapping("/add")
	public String createStudent(@Valid @RequestBody Student student) {

		if (!isRecordFullNameExist(student) && !isRecordPhoneNumberExist(student)) {
			 studentRepository.save(student);
			 return "add thanh cong";
		} else {
			return "ten da ton tai";
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long studentID,
			@Valid @RequestBody Student studentClient) {
		Student student = studentRepository.findById(studentID).orElseThrow();
		student.setFullName(studentClient.getFullName());
		student.setPhoneNumber(studentClient.getPhoneNumber());
		student.setDateOfBirth(studentClient.getDateOfBirth());

		final Student updateStudent = studentRepository.save(student);
		return ResponseEntity.ok().body(updateStudent);
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Long studentID) {
		Student student = studentRepository.findById(studentID).orElseThrow();
		studentRepository.delete(student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	public boolean isRecordFullNameExist(Student student) {
		Student studentByName = studentRepository.findByFullName(student.getFullName());
		if (studentByName != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isRecordPhoneNumberExist(Student student) {
		Student studentByPhone = studentRepository.findByPhoneNumber(student.getPhoneNumber());
		if (studentByPhone != null) {
			return true;
		} else {
			return false;
		}
	}

}
