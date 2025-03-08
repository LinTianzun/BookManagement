package com.linlin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linlin.mapper.BookMapper;
import com.linlin.mapper.StudentMapper;
import com.linlin.pojo.Book;
import com.linlin.pojo.User;
import com.linlin.service.StudentService;
import com.linlin.utils.PageBean;
import com.linlin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public R getInfo(Integer userId) {
        User u = studentMapper.getInfo(userId);
        if (u == null) {
            return R.fail("没有当前用户信息");
        }
        return R.fail(u);
    }

    @Override
    public R queryAllBook(int pageSize, int currentPage) {
        //  分页
        PageHelper.startPage(currentPage, pageSize);
        //  查询
        List<Book> bookList = bookMapper.queryAllBooks();
        //  分页数据装配
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);

        //  装配PageBean
        PageBean<Book> pageBean = new PageBean<>(currentPage, pageSize, pageInfo.getTotal(), pageInfo.getList());

        return R.ok(pageBean);
    }

    @Override
    public R findBookBySearch(String searTit, String searInput, int pageSize, int currentPage) {
        //  分页
        PageHelper.startPage(currentPage, pageSize);
        //  查询
        List<Book> bookList = bookMapper.findBookBySearch(searTit,searInput);
        //  分页数据装配
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);

        //  装配PageBean
        PageBean<Book> pageBean = new PageBean<>(currentPage, pageSize, pageInfo.getTotal(), pageInfo.getList());

        return R.ok(pageBean);
    }

    @Override
    public R updateUserPwd(User user) {
        //  判断id不能为null
        if(user.getUserId()==null){
            return R.fail("核心参数为null 无法修改");
        }
        int rows = studentMapper.updateUserPwd(user);
        if (rows > 0) {
            return R.ok("修改成功");
        }
        return R.fail("修改失败");
    }


}
