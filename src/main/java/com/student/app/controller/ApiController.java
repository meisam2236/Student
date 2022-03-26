package com.student.app.controller;

import com.student.app.model.ApiResponse;
import com.student.app.model.Employee;
import com.student.app.model.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1/api/")
public class ApiController {
    private Properties properties;
    @Value("${papi.uri}")
    String apiUrl;

    @Autowired
    public void setProperties(Properties properties){
        this.properties = properties;
    }

    @GetMapping("rest-template/")
    public List<Employee> getAllEmployees() {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse apiResponse = restTemplate.getForObject(properties.getUri(), ApiResponse.class);
        return apiResponse.getData();
    }

    @GetMapping("web-client/")
    public List<Employee> webClientGetAllEmployees() {
        WebClient webClient = WebClient.create();
        ApiResponse apiResponse = webClient.get().uri(apiUrl).retrieve().bodyToMono(ApiResponse.class).block();
        return apiResponse.getData();
    }

}
