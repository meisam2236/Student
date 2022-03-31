package com.student.app.controller.api;

import com.student.app.model.Employee;
import com.student.app.service.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/api/")
public class ApiController {
    private ApiService apiService;

    public ApiController(ApiService apiService) {
        super();
        this.apiService = apiService;
    }

    @GetMapping("rest-template/")
    public List<Employee> getAllEmployees() {
        return apiService.getAllEmployeeRest();
    }

    @GetMapping("web-client/")
    public List<Employee> webClientGetAllEmployees() {
        return apiService.getAllEmployeesWebClient();
    }

}
