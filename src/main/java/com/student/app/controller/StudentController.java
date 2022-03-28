package com.student.app.controller;

import com.student.app.helper.JsonWriter;
import com.student.app.model.repr.StudentRepr;
import com.student.app.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/student/")
public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentRepr> saveStudent(@RequestBody StudentRepr student){
        return new ResponseEntity<StudentRepr>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentRepr> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{id}/")
    public StudentRepr getStudentById(@PathVariable("id") long id){
        return studentService.getStudentById(id);
    }

    @PutMapping("{id}/")
    public ResponseEntity<StudentRepr> updateStudentById(@PathVariable("id") long id, @RequestBody StudentRepr student){
        return new ResponseEntity<StudentRepr>(studentService.updateStudent(student, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<String>("Student deleted successfully!", HttpStatus.ACCEPTED);
    }
    @GetMapping("above-15/")
    public Map<String, ArrayList<StudentRepr>> getStudentsByGrade() {
        return studentService.getStudentsAbove15();
    }
    @GetMapping("backup/")
    public String getStudentInJson() {
        List<StudentRepr> students = studentService.getAllStudents();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (StudentRepr student : students) {
            executor.submit(JsonWriter.jsonFileWriter(String.valueOf(student.getId()), student));
        }
        return "Backup Completed!";
    }
}
