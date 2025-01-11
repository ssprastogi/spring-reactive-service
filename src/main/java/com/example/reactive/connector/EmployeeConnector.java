package com.example.reactive.connector;

import com.example.reactive.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class EmployeeConnector {
    private static final Logger log = LoggerFactory.getLogger(EmployeeConnector.class);
    private final WebClient empWebClient;

    @Autowired
    public EmployeeConnector(WebClient empWebClient) {
        this.empWebClient = empWebClient;
    }

    public Mono<List<Employee>> getEmployees() {
        log.info("getEmployees called");
        return empWebClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Employee>>() {
                })
                .onErrorResume(e -> Mono.error(new Exception(e.getMessage())));
    }
}
