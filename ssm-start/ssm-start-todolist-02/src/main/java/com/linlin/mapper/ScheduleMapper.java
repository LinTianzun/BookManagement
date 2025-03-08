package com.linlin.mapper;

import com.linlin.pojo.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    List<Schedule> queryList();

    int deleteById(Integer id);

    int insert(Schedule schedule);

    int update(Schedule schedule);
}
