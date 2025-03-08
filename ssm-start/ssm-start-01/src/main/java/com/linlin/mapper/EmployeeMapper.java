package com.linlin.mapper;

import com.linlin.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper {

    //  查询全部员工信息
    List<Employee> queryList();
}
