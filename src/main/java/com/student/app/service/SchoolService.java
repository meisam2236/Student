package com.student.app.service;

import com.student.app.model.repr.SchoolRepr;

import java.util.List;

public interface SchoolService {
    SchoolRepr saveSchool(SchoolRepr school);
    List<SchoolRepr> getAllSchools();
    SchoolRepr getSchoolById(long id);
    SchoolRepr updateSchool(SchoolRepr school, long id);
    void deleteSchool(long id);
    void addStudent(long schoolId, long studentId);
}
