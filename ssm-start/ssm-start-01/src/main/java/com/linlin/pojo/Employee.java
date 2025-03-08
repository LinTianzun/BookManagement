package com.linlin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int employeeId; //员工id
    private String employeeName; //员工名字
    private int employeeAge; //员工年龄
    private String employeePosition; //员工职位
    private double employeeSalary; //员工薪水


}
