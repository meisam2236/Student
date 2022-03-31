package com.student.app.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Serializable {
    private static final long serialVersionUID = 2;
    private long id;
    private String firstName;
    private String lastName;
    private float grade;
    private String school;
}
