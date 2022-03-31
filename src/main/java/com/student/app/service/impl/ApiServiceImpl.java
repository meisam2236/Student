package com.student.app.service.impl;

import com.student.app.model.ApiResponse;
import com.student.app.model.Employee;
import com.student.app.model.Properties;
import com.student.app.service.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    Properties properties;
    @Value("${papi.uri}")
    String apiUrl;
    private WebClient webClient;
    private RestTemplate restTemplate;

    public ApiServiceImpl(Properties properties, WebClient webClient, RestTemplate restTemplate){
        super();
        this.properties = properties;
        this.webClient = webClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Employee> getAllEmployeesWebClient() {
        ApiResponse apiResponse = webClient.get().uri(apiUrl).retrieve().bodyToMono(ApiResponse.class).block();
        return apiResponse.getData();
    }

    @Override
    public List<Employee> getAllEmployeeRest() {
        ApiResponse apiResponse = restTemplate.getForObject(properties.getUri(), ApiResponse.class);
        return apiResponse.getData();
    }
}
