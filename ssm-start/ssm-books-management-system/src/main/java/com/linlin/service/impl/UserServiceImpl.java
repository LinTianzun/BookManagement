package com.linlin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linlin.mapper.UserMapper;
import com.linlin.pojo.User;
import com.linlin.service.UserSrevice;
import com.linlin.utils.PageBean;
import com.linlin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserSrevice {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public R register(User user) {
        //  加密
//        String oldpwd = user.getPassword();
//        String newpwd = Md5Util.getMD5String(oldpwd);
//        user.setPassword(newpwd);
        //  添加
        int rows = userMapper.register(user);
        if (rows > 0) {
            return R.ok("注册成功");
        }
        return R.fail("注册失败");
    }

    @Override
    public R page(int pageSize, int currentPage) {
        //  分页
        PageHelper.startPage(currentPage, pageSize);
        //  查询
        List<User> scheduleList = userMapper.findAllUsers();
        //  分页数据装配
        PageInfo<User> pageInfo = new PageInfo<>(scheduleList);

        //  装配PageBean
        PageBean<User> pageBean = new PageBean<>(currentPage, pageSize, pageInfo.getTotal(), pageInfo.getList());

        return R.ok(pageBean);
    }

    @Override
    public R findUserBySearch(String searTit, String searInput,
                              int pageSize,
                              int currentPage) {
        //  分页
        PageHelper.startPage(currentPage, pageSize);
        List<User> searchList = userMapper.findUserBySearch(searTit, searInput);
        //  分页数据装配
        PageInfo<User> pageInfo = new PageInfo<>(searchList);

        //  装配PageBean
        PageBean<User> pageBean = new PageBean<>(currentPage, pageSize, pageInfo.getTotal(), pageInfo.getList());

        return R.ok(pageBean);
    }

    @Override
    public R remove(Integer id) {
        int rows = userMapper.deleteById(id);
        if (rows > 0) {
            return R.ok("删除成功");
        }
        return R.fail("删除失败");
    }

    //  修改更新用户数据
    @Override
    public R update(User user) {
        //  判断id不能为null
        if(user.getUserId()==null){
            return R.fail("核心参数为null 无法修改");
        }
        int rows = userMapper.update(user);
        if (rows > 0) {
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }
}
