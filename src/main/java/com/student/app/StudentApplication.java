package com.student.app;

import com.student.app.model.dto.StudentDto;
import com.student.app.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class StudentApplication implements CommandLineRunner{
	private StudentService studentService;
	private RedissonClient redis;

	public StudentApplication(StudentService studentService, RedissonClient redis) {
		super();
		this.studentService = studentService;
		this.redis = redis;
	}
	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RMap<String, ArrayList<StudentDto>> map = redis.getMap("SCHOOL");
		Map<String, ArrayList<StudentDto>> schools = studentService.getStudentsAbove15();
		for (Map.Entry<String, ArrayList<StudentDto>> entry : schools.entrySet()) {
			log.info(entry.getKey() + ": ");
			for (StudentDto student : map.get(entry.getKey())) {
				log.info(
						student.getId() + ". " +
						student.getFirstName() + " " + student.getLastName() + ": " +
						student.getGrade()
				);
			}
		}
	}
}
