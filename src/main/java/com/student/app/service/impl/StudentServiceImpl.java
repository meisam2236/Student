package com.student.app.service.impl;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.helper.JsonWriter;
import com.student.app.model.Student;
import com.student.app.model.dto.StudentDto;
import com.student.app.repository.StudentRepository;
import com.student.app.service.StudentService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private RedissonClient redis;

    public StudentServiceImpl(StudentRepository studentRepository, RedissonClient redis) {
        super();
        this.studentRepository = studentRepository;
        this.redis = redis;
    }


    @Override
    public StudentDto saveStudent(StudentDto student) {
        studentRepository.save(new Student(
                student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                null
        ));
        return student;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> dbStudents = studentRepository.findAll();
        List<StudentDto> localStudents = new ArrayList<StudentDto>();
        for (Student student: dbStudents) {
            localStudents.add(new StudentDto(
                    student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                    ((student.getSchool() == null) ? "" : student.getSchool().getName())
            ));
        }
        return localStudents;
    }

    @Override
    public StudentDto getStudentById(long id) {
//        Optional<Student> student = studentRepository.findById(id);
//        if (student.isPresent()){
//            return student.get();
//        } else {
//            throw new ResourceNotFoundException("Student", "Id", id);
//        }
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        return new StudentDto(
                student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                ((student.getSchool() == null) ? "" : student.getSchool().getName())
        );
    }

    @Override
    public StudentDto updateStudent(StudentDto student, long id) {
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
    public Map<String, ArrayList<StudentDto>> getStudentsAbove15() {
        List<Student> students = studentRepository.getStudentsAbove15();
        Map<String, ArrayList<StudentDto>> studentsOfSchool = students.stream().collect(Collectors.toMap(
                student -> ((student.getSchool() == null) ? "" : student.getSchool().getName()),
                student -> {
                    ArrayList<StudentDto> temp = new ArrayList<StudentDto>();
                    temp.add(new StudentDto(
                            student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                            ((student.getSchool() == null) ? "" : student.getSchool().getName())
                    ));
                    return temp;
                },
                (obj1, obj2) -> {
                    ArrayList<StudentDto> temp = new ArrayList<StudentDto>();
                    temp.addAll(obj1);
                    temp.addAll(obj2);
                    return temp;
                }
        ));
        RMap<String, ArrayList<StudentDto>> map = redis.getMap("SCHOOL");
        map.putAll(studentsOfSchool);
        return studentsOfSchool;
    }

    @Override
    public Float getGradesAverage() {
        return studentRepository.getGradesAverage();
    }

    @Override
    public void createStudentsInJson() {
        List<StudentDto> students = getAllStudents();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (StudentDto student : students) {
            executor.submit(JsonWriter.jsonFileWriter(String.valueOf(student.getId()), student));
        }
    }
}