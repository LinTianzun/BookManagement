package com.linlin.service.impl;

import com.linlin.mapper.EmployeeMapper;
import com.linlin.pojo.Employee;
import com.linlin.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> findAll() {
        return employeeMapper.queryList();
    }
}
