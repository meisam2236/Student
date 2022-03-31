package com.student.app.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private long id;
    private String employee_name;
    private int employee_salary;
    private byte employee_age;
    private String profile_image;
}
