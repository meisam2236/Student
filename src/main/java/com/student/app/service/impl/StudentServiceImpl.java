package com.student.app.service.impl;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.model.Student;
import com.student.app.model.repr.StudentRepr;
import com.student.app.repository.StudentRepository;
import com.student.app.service.StudentService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    @Value("${redisson.url}")
    private String redisUrl;
    private Config config;

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
        Config config = new Config();
        config.useSingleServer().setAddress(redisUrl);
    }


    @Override
    public StudentRepr saveStudent(StudentRepr student) {
        studentRepository.save(new Student(
                student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                null
        ));
        System.out.println(34);
        return student;
    }

    @Override
    public List<StudentRepr> getAllStudents() {
        List<Student> dbStudents = studentRepository.findAll();
        List<StudentRepr> localStudents = new ArrayList<StudentRepr>();
        for (Student student: dbStudents) {
            localStudents.add(new StudentRepr(
                    student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                    ((student.getSchool() == null) ? "" : student.getSchool().getName())
            ));
        }
        return localStudents;
    }

    @Override
    public StudentRepr getStudentById(long id) {
//        Optional<Student> student = studentRepository.findById(id);
//        if (student.isPresent()){
//            return student.get();
//        } else {
//            throw new ResourceNotFoundException("Student", "Id", id);
//        }
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        return new StudentRepr(
                student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                ((student.getSchool() == null) ? "" : student.getSchool().getName())
        );
    }

    @Override
    public StudentRepr updateStudent(StudentRepr student, long id) {
        Student dbStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        dbStudent.setFirstName(student.getFirstName());
        dbStudent.setLastName(student.getLastName());
        dbStudent.setGrade(student.getGrade());
        studentRepository.save(dbStudent);
        return student;
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        studentRepository.deleteById(id);
    }

    @Override
    public Map<String, ArrayList<StudentRepr>> getStudentsAbove15() {
        List<Student> students = studentRepository.getStudentsAbove15();
        Map<String, ArrayList<StudentRepr>> studentsOfSchool = students.stream().collect(Collectors.toMap(
                student -> ((student.getSchool() == null) ? "" : student.getSchool().getName()),
                student -> {
                    ArrayList<StudentRepr> temp = new ArrayList<StudentRepr>();
                    temp.add(new StudentRepr(
                            student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                            ((student.getSchool() == null) ? "" : student.getSchool().getName())
                    ));
                    return temp;
                },
                (obj1, obj2) -> {
                    ArrayList<StudentRepr> temp = new ArrayList<StudentRepr>();
                    temp.addAll(obj1);
                    temp.addAll(obj2);
                    return temp;
                }
        ));
        RedissonClient redisson = Redisson.create();
        RMap<String, ArrayList<StudentRepr>> map = redisson.getMap("SCHOOL");
        map.putAll(studentsOfSchool);
        redisson.shutdown();
        return studentsOfSchool;
    }

    @Override
    public Float getGradesAverage() {
        return studentRepository.getGradesAverage();
    }
}