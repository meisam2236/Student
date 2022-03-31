package com.student.app.service;

import com.student.app.model.dto.SchoolDto;

import java.util.List;

public interface SchoolService {
    SchoolDto saveSchool(SchoolDto school);
    List<SchoolDto> getAllSchools();
    SchoolDto getSchoolById(long id);
    SchoolDto updateSchool(SchoolDto school, long id);
    void deleteSchool(long id);
    void addStudent(long schoolId, long studentId);
}
