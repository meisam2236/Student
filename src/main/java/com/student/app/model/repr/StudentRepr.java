package com.student.app.model.repr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRepr implements Serializable {
    private static final long serialVersionUID = 2;
    private long id;
    private String firstName;
    private String lastName;
    private float grade;
    private String school;
}
