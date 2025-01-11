package com.example.reactive.domain;

public class Department {
    private Long id;
    private Long deptId;
    private String departmentName;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptId=" + deptId +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
