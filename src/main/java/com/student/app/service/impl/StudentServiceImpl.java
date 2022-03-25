package com.student.app.service.impl;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.model.Student;
import com.student.app.repository.StudentRepository;
import com.student.app.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
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
        return studentsOfSchool;
    }

    @Override
    public Float getGradesAverage() {
        return studentRepository.getGradesAverage();
    }
}