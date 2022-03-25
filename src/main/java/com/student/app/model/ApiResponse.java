package com.student.app.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private String status;
    private List<Employee> data;
    private String message;
}
