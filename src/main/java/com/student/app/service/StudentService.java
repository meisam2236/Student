package com.student.app.service;

import com.student.app.model.repr.StudentRepr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StudentService {
    StudentRepr saveStudent(StudentRepr student);
    List<StudentRepr> getAllStudents();
    StudentRepr getStudentById(long id);
    StudentRepr updateStudent(StudentRepr student, long id);
    void deleteStudent(long id);
    Map<String, ArrayList<StudentRepr>> getStudentsAbove15();
    Float getGradesAverage();
}
