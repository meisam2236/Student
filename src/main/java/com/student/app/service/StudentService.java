package com.student.app.service;

import com.student.app.model.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StudentService {
    StudentDto saveStudent(StudentDto student);
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(long id);
    StudentDto updateStudent(StudentDto student, long id);
    void deleteStudent(long id);
    Map<String, ArrayList<StudentDto>> getStudentsAbove15();
    Float getGradesAverage();
    void createStudentsInJson();
}
