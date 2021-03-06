package com.student.app.service.impl;

import com.student.app.exception.ResourceNotFoundException;
import com.student.app.model.School;
import com.student.app.model.Student;
import com.student.app.model.dto.SchoolDto;
import com.student.app.model.dto.StudentDto;
import com.student.app.repository.SchoolRepository;
import com.student.app.repository.StudentRepository;
import com.student.app.service.SchoolService;
import com.vividsolutions.jts.geom.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public SchoolDto saveSchool(SchoolDto school) {
        GeometryFactory geometryFactory = new GeometryFactory();
        schoolRepository.save(new School(
                school.getId(), school.getName(), null,
                geometryFactory.createPoint(new Coordinate(school.getLat(), school.getLon()))
        ));
        return school;
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        List<School> dbSchools = schoolRepository.findAll();
        List<SchoolDto> localSchools = new ArrayList<>();
        for (School school: dbSchools) {
            Point location = (Point) school.getLocation();
            List<StudentDto> students = new ArrayList<>();
            for (Student student: school.getStudents()) {
                students.add(new StudentDto(
                        student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                        ((student.getSchool() == null) ? "" : student.getSchool().getName())
                ));
            }
            localSchools.add(new SchoolDto(
                    school.getId(), school.getName(), students,
                    ((location == null) ? 0 :  location.getX()),
                    ((location == null) ? 0 :  location.getY())
            ));
        }
        return localSchools;
    }

    @Override
    public SchoolDto getSchoolById(long id) {
        School school = schoolRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("School", "Id", id)
        );
        Point location = (Point) school.getLocation();
        List<StudentDto> students = new ArrayList<>();
        for (Student student: school.getStudents()) {
            students.add(new StudentDto(
                    student.getId(), student.getFirstName(), student.getLastName(), student.getGrade(),
                    ((student.getSchool() == null) ? "" : student.getSchool().getName())
            ));
        }
        return new SchoolDto(
                school.getId(), school.getName(), students,
                ((location == null) ? 0 :  location.getX()),
                ((location == null) ? 0 :  location.getY())
        );
    }

    @Override
    public SchoolDto updateSchool(SchoolDto school, long id) {
        GeometryFactory geometryFactory = new GeometryFactory();
        School dbSchool = schoolRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("School", "Id", id)
        );
        dbSchool.setName(school.getName());
        dbSchool.setLocation(geometryFactory.createPoint(new Coordinate(school.getLat(), school.getLon())));
        schoolRepository.save(dbSchool);
        return school;
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
