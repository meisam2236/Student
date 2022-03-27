package com.student.app.controller;

import com.student.app.helper.JsonWriter;
import com.student.app.model.RedisStudent;
import com.student.app.model.Student;
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
    public ResponseEntity<Student> saveStudent(@RequestBody Student student){
        return new ResponseEntity<Student>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{id}/")
    public Student getStudentById(@PathVariable("id") long id){
        return studentService.getStudentById(id);
    }

    @PutMapping("{id}/")
    public ResponseEntity<Student> updateStudentById(@PathVariable("id") long id, @RequestBody Student student) {
        return new ResponseEntity<Student>(studentService.updateStudent(student, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<String>("Student deleted successfully!", HttpStatus.ACCEPTED);
    }
    @GetMapping("above-15/")
    public Map<String, ArrayList<Student>> getStudentsByGrade() {
        return studentService.getStudentsAbove15();
    }
    @GetMapping("backup/")
    public String getStudentInJson() {
        List<Student> students = studentService.getAllStudents();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (Student student : students) {
            RedisStudent temp = new RedisStudent();
            temp.setId(student.getId());
            temp.setFirstName(student.getFirstName());
            temp.setLastName(student.getLastName());
            temp.setGrade(student.getGrade());
            executor.submit(JsonWriter.jsonFileWriter(String.valueOf(student.getId()), temp));
        }
        return "Backup Completed!";
    }
}
