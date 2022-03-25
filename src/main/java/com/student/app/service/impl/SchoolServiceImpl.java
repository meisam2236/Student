package com.student.app.service.impl;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.model.School;
import com.student.app.model.Student;
import com.student.app.repository.SchoolRepository;
import com.student.app.repository.StudentRepository;
import com.student.app.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;
    private StudentRepository studentRepository;
    public SchoolServiceImpl(SchoolRepository schoolRepository, StudentRepository studentRepository) {
        super();
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public School saveSchool(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public School getSchoolById(long id) {
        return schoolRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("School", "Id", id)
        );
    }

    @Override
    public School updateSchool(School school, long id) {
        School dbSchool = schoolRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("School", "Id", id)
        );
        dbSchool.setName(school.getName());
        schoolRepository.save(dbSchool);
        return dbSchool;
    }

    @Override
    public void deleteSchool(long id) {
        schoolRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("School", "Id", id)
        );
        schoolRepository.deleteById(id);
    }

    @Override
    public void addStudent(long schoolId, long studentId) {
        School school = schoolRepository.findById(schoolId).orElseThrow(
                () -> new ResourceNotFoundException("School", "Id", schoolId)
        );
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", studentId)
        );
        student.setSchool(school);
        studentRepository.save(student);
    }
}
