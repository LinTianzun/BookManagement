package com.linlin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linlin.mapper.BookMapper;
import com.linlin.pojo.Book;
import com.linlin.service.BookSrevice;
import com.linlin.utils.PageBean;
import com.linlin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSreviceImpl implements BookSrevice {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> queryAllBooks() {
        return bookMapper.queryAllBooks();
    }

    @Override
    public R page(int pageSize, int currentPage) {
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
    public R update(Book book) {
        //  判断id不能为null
        if(book.getBookId()==null){
            return R.fail("核心参数为null 无法修改");
        }
        int rows = bookMapper.update(book);
        if (rows > 0) {
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }

    @Override
    public R addBook(Book book) {
        int rows = bookMapper.addBook(book);
        if (rows > 0) {
            return R.ok("添加图书成功");
        }
        return R.fail("添加图书失败");
    }

    @Override
    public R remove(Integer id) {
        int rows = bookMapper.deleteById(id);
        if (rows > 0) {
            return R.ok("删除成功");
        }
        return R.fail("删除失败");
    }
}
