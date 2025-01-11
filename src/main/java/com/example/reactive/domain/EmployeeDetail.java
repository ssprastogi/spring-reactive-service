package com.example.reactive.domain;

public class EmployeeDetail {
    private Long id;
    private Long deptId;
    private String departmentName;
    private String name;
    private String gender;
    private int age;

    public EmployeeDetail(){}

    public EmployeeDetail(Long id, Long deptId, String departmentName, String name, String gender, int age) {
        this.id = id;
        this.deptId = deptId;
        this.departmentName = departmentName;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "EmployeeDetail{" +
                "id=" + id +
                ", deptId=" + deptId +
                ", departmentName='" + departmentName + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
