package com.student.app;

import com.student.app.helper.GradeLogger;
import com.student.app.model.RedisStudent;
import com.student.app.model.Student;
import com.student.app.service.StudentService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${redisson.url}")
	private String redisUrl;
	public StudentApplication(StudentService studentService) {
		super();
		this.studentService = studentService;
		Config config = new Config();
		config.useSingleServer().setAddress(redisUrl);
	}
	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RedissonClient redisson = Redisson.create();
		RMap<String, ArrayList<RedisStudent>> map = redisson.getMap("SCHOOL");
		Map<String, ArrayList<Student>> schools = studentService.getStudentsAbove15();
		for (Map.Entry<String, ArrayList<Student>> entry : schools.entrySet()) {
			log.info(entry.getKey()+ " : " + map.get(entry.getKey()).toString());
		}
		redisson.shutdown();
	}
}
