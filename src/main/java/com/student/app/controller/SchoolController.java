package com.student.app.controller;

import com.student.app.model.dto.SchoolDto;
import com.student.app.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school/")
public class SchoolController {
    private SchoolService schoolService;
    public SchoolController(SchoolService schoolService) {
        super();
        this.schoolService = schoolService;
    }
    @PostMapping
    public ResponseEntity<SchoolDto> saveSchool(@RequestBody SchoolDto school) {
        return new ResponseEntity<SchoolDto>(schoolService.saveSchool(school), HttpStatus.CREATED);
    }
    @GetMapping
    public List<SchoolDto> getAllSchools(){
        return schoolService.getAllSchools();
    }
    @GetMapping("{id}/")
    public SchoolDto getSchoolById(@PathVariable(name = "id") long id) {
        return schoolService.getSchoolById(id);
    }
    @PutMapping("{id}/")
    public ResponseEntity<SchoolDto> updateSchoolById(@PathVariable(name = "id") long id, @RequestBody SchoolDto school){
        return new ResponseEntity<SchoolDto>(schoolService.updateSchool(school, id), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{id}/")
    public ResponseEntity<String> deleteSchoolById(@PathVariable(name = "id") long id) {
        schoolService.deleteSchool(id);
        return new ResponseEntity<String>("School deleted successfully!", HttpStatus.ACCEPTED);
    }
    @GetMapping("{schoolId}/student/{studentId}/")
    public ResponseEntity<String> addStudent(
            @PathVariable(name = "schoolId") long schoolId,
            @PathVariable(name = "studentId") long studentId
    ) {
        schoolService.addStudent(schoolId, studentId);
        return new ResponseEntity<String>("Student added to school successfully!", HttpStatus.ACCEPTED);
    }
}
