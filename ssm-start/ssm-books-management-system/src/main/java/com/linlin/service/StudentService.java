package com.linlin.service;

import com.linlin.pojo.User;
import com.linlin.utils.R;

public interface StudentService {
    R getInfo(Integer userId);

    R queryAllBook(int pageSize, int currentPage);


    R findBookBySearch(String searTit, String searInput, int pageSize, int currentPage);

    R updateUserPwd(User user);
}
