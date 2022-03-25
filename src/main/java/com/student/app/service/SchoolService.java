package com.student.app.service;

import com.student.app.model.School;

import java.util.List;

public interface SchoolService {
    School saveSchool(School school);
    List<School> getAllSchools();
    School getSchoolById(long id);
    School updateSchool(School school, long id);
    void deleteSchool(long id);
    void addStudent(long schoolId, long studentId);
}
