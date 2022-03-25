package com.student.app.service;

import com.student.app.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StudentService {
    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(long id);
    Student updateStudent(Student student, long id);
    void deleteStudent(long id);
    Map<String, ArrayList<Student>> getStudentsAbove15();
    Float getGradesAverage();
}
