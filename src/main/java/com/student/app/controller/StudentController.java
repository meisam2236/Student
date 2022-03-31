package com.student.app.controller;

import com.student.app.model.dto.StudentDto;
import com.student.app.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/student/")
public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentDto student){
        return new ResponseEntity<StudentDto>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDto> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{id}/")
    public StudentDto getStudentById(@PathVariable("id") long id){
        return studentService.getStudentById(id);
    }

    @PutMapping("{id}/")
    public ResponseEntity<StudentDto> updateStudentById(@PathVariable("id") long id, @RequestBody StudentDto student){
        return new ResponseEntity<StudentDto>(studentService.updateStudent(student, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<String>("Student deleted successfully!", HttpStatus.ACCEPTED);
    }

    @GetMapping("above-15/")
    public Map<String, ArrayList<StudentDto>> getStudentsByGrade() {
        return studentService.getStudentsAbove15();
    }

    @GetMapping("backup/")
    public String getStudentInJson() {
        studentService.createStudentsInJson();
        return "Backup Completed!";
    }
}
