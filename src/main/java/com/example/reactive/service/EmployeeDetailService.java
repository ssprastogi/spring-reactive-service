package com.example.reactive.service;

import com.example.reactive.connector.DepartmentConnector;
import com.example.reactive.connector.EmployeeConnector;
import com.example.reactive.domain.Department;
import com.example.reactive.domain.EmployeeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDetailService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeDetailService.class);
    private final DepartmentConnector departmentConnector;
    private final EmployeeConnector employeeConnector;

    @Autowired
    public EmployeeDetailService(DepartmentConnector departmentConnector, EmployeeConnector employeeConnector) {
        this.departmentConnector = departmentConnector;
        this.employeeConnector = employeeConnector;
    }

    public Mono<List<EmployeeDetail>> getAllEmpDetails() {

        return employeeConnector.getEmployees().log()
                .zipWith(departmentConnector.getDepartment().log(), (employeeList, departmentList) -> {
                            List<EmployeeDetail> employeeDetails = new ArrayList<>();
                            employeeList
                                    .forEach(employee -> {
                                        EmployeeDetail employeeDetail = new EmployeeDetail();
                                        employeeDetail.setId(employee.getId());
                                        employeeDetail.setDeptId(employee.getDeptId());
                                        employeeDetail
                                                .setDepartmentName(departmentList.stream()
                                                        .filter(department -> department.getDeptId().equals(employee.getDeptId()))
                                                        .findFirst().map(Department::getDepartmentName).orElse(null));
                                        employeeDetail.setAge(employee.getAge());
                                        employeeDetail.setName(employee.getName());
                                        employeeDetail.setGender(employee.getGender());
                                        employeeDetails.add(employeeDetail);
                                    });
                            return employeeDetails;
                        }
                )
                .onErrorMap(ex -> new Throwable(ex.getMessage()));
    }
}
