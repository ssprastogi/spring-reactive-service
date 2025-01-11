package com.example.reactive.controller;

import com.example.reactive.domain.Employee;
import com.example.reactive.domain.EmployeeDetail;
import com.example.reactive.service.EmployeeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/details")
public class EmployeeDetailController {
    private final EmployeeDetailService employeeDetailService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public EmployeeDetailController(EmployeeDetailService employeeDetailService,
                                    WebClient.Builder webClientBuilder) {
        this.employeeDetailService = employeeDetailService;
        this.webClientBuilder=webClientBuilder;
    }

    @GetMapping
    public Mono<List<EmployeeDetail>> fetchAllEmpDetails(){
        return employeeDetailService.getAllEmpDetails();
    }
}
