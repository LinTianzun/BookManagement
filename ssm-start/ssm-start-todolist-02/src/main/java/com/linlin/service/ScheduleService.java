package com.linlin.service;

import com.linlin.pojo.Schedule;
import com.linlin.utils.R;

public interface ScheduleService {
    R page(int pageSize, int currentPage);

    R remove(Integer id);

    R save(Schedule schedule);

    R update(Schedule schedule);
}
