package com.linlin.service;

import com.linlin.pojo.User;
import com.linlin.utils.R;

import java.util.List;

public interface UserSrevice {
    //  查询所有的用户信息
    List<User> findAllUsers();

    //  根据用户名查找用户
    User findByUserName(String username);

    //  注册
    R register(User user);

    //  用户分页查询
    R page(int pageSize, int currentPage);

    //  用户模糊分页查询
    R findUserBySearch(String searTit, String searInput,int pageSize, int currentPage);

    //  删除用户信息
    R remove(Integer id);

    //  更新用户信息
    R update(User user);
}
