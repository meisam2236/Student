package com.student.app.service.impl;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.model.RedisStudent;
import com.student.app.model.Student;
import com.student.app.repository.StudentRepository;
import com.student.app.service.StudentService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(long id) {
//        Optional<Student> student = studentRepository.findById(id);
//        if (student.isPresent()){
//            return student.get();
//        } else {
//            throw new ResourceNotFoundException("Student", "Id", id);
//        }
        return studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
    }

    @Override
    public Student updateStudent(Student student, long id) {
        Student dbStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        dbStudent.setFirstName(student.getFirstName());
        dbStudent.setLastName(student.getLastName());
        dbStudent.setGrade(student.getGrade());
        studentRepository.save(dbStudent);
        return dbStudent;
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        studentRepository.deleteById(id);
    }

    @Override
    public Map<String, ArrayList<Student>> getStudentsAbove15() {
        List<Student> students = studentRepository.getStudentsAbove15();
        Map<String, ArrayList<Student>> studentsOfSchool;
        studentsOfSchool = students.stream().collect(Collectors.toMap(
                student -> student.getSchool().getName(),
                student -> {
                    ArrayList<Student> temp = new ArrayList<Student>();
                    temp.add(student);
                    return temp;
                },
                (obj1, obj2) -> {
                    ArrayList<Student> temp = new ArrayList<Student>();
                    temp.addAll(obj1);
                    temp.addAll(obj2);
                    return temp;
                }
        ));
        RedissonClient redisson = Redisson.create();
        RMap<String, ArrayList<RedisStudent>> map = redisson.getMap("SCHOOL");
        for (Map.Entry<String, ArrayList<Student>> entry : studentsOfSchool.entrySet()) {
            ArrayList<RedisStudent> redisStudents = new ArrayList<RedisStudent>();
            for (Student student: entry.getValue()) {
                RedisStudent redisStudent = new RedisStudent();
                redisStudent.setId(student.getId());
                redisStudent.setFirstName(student.getFirstName());
                redisStudent.setLastName(student.getLastName());
                redisStudent.setGrade(student.getGrade());
                redisStudents.add(redisStudent);
            }
            map.put(entry.getKey(), redisStudents);
        }
        redisson.shutdown();
        return studentsOfSchool;
    }

    @Override
    public Float getGradesAverage() {
        return studentRepository.getGradesAverage();
    }
}