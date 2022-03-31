package com.student.app.service;

import com.student.app.model.Employee;

import java.util.List;

public interface ApiService {
    public List<Employee> getAllEmployeesWebClient();
    public List<Employee> getAllEmployeeRest();
}
