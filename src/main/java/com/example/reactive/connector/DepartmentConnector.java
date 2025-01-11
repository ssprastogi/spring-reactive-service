package com.example.reactive.connector;

import com.example.reactive.domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class DepartmentConnector {

    private static final Logger log = LoggerFactory.getLogger(DepartmentConnector.class);
    private final WebClient deptWebClient;

    @Autowired
    public DepartmentConnector(WebClient deptWebClient) {
        this.deptWebClient = deptWebClient;
    }

    public Mono<List<Department>> getDepartment() {
        log.info("getDepartment called");
        return deptWebClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Department>>() {
                })
                .onErrorResume(e -> Mono.error(new Exception(e.getMessage())));
    }
}
