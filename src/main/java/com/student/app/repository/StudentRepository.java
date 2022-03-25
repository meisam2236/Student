package com.student.app.repository;

import com.student.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT AVG(S.grade) FROM Student AS S")
    public Float getGradesAverage();

    @Query("FROM Student AS S WHERE S.grade > 15")
    public List<Student> getStudentsAbove15();
}
