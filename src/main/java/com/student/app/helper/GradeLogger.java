package com.student.app.helper;

import com.student.app.model.School;
import com.student.app.model.Student;
import com.student.app.service.SchoolService;
import com.student.app.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GradeLogger {
    StudentService studentService;
    SchoolService schoolService;
    private static final Logger log = LoggerFactory.getLogger(GradeLogger.class);
    public GradeLogger(StudentService studentService, SchoolService schoolService) {
        super();
        this.studentService = studentService;
        this.schoolService = schoolService;
    }
    @PostConstruct
    private void postConstruct() {
//        School school = new School();
//        school.setName("Baharestan");
//        schoolService.saveSchool(school);
//        Student student = new Student();
//        student.setFirstName("Alireza");
//        student.setLastName("Asghari");
//        student.setGrade(20);
//        studentService.saveStudent(student);
//        List<Student> students = studentService.getAllStudents();
//        List<School> schools = schoolService.getAllSchools();
//        schoolService.addStudent(schools.get(schools.size()-1).getId() ,students.get(students.size()-1).getId());
    }

    @Scheduled(fixedRate = 300000)
    public void reportGrade() {
        Float gradesAverage = studentService.getGradesAverage();
        log.info("Current average grade is {}!", gradesAverage);
    }
}
