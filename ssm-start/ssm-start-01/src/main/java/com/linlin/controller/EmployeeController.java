package com.linlin.controller;

import com.linlin.pojo.Employee;
import com.linlin.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("emp")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("find")
    public List<Employee> findAll() {
        List<Employee> employees = employeeService.findAll();
        log.info("员工数据:{}",employees);
        return employees;
    }

}
