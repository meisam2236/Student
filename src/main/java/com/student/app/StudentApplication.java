package com.student.app;

import com.student.app.helper.GradeLogger;
import com.student.app.model.Student;
import com.student.app.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Map;


@SpringBootApplication
@EnableScheduling
public class StudentApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(GradeLogger.class);
	private StudentService studentService;
	public StudentApplication(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String, ArrayList<Student>> schools = studentService.getStudentsAbove15();
		for (Map.Entry<String, ArrayList<Student>> entry : schools.entrySet()) {
			String school = entry.getKey();
			log.info(school+ "...");
			ArrayList<Student> students = entry.getValue();
			for (Student student: students) {
				String firstName = student.getFirstName();
				String lastName = student.getLastName();
				String grade = String.valueOf(student.getGrade());
				log.info(firstName + " " + lastName + ": " + grade);
			}
		}
	}
}
