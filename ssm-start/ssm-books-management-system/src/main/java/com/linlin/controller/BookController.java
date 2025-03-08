package com.linlin.controller;

import com.linlin.pojo.Book;
import com.linlin.service.BookSrevice;
import com.linlin.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin    //  允许其它源访问我们的controller
@RestController
@RequestMapping("book")
@Slf4j
public class BookController {
    @Autowired
    private BookSrevice bookSrevice;

    @GetMapping("allbook")
    public List<Book> getAllBooks(/*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*/) {
        List<Book> r = bookSrevice.queryAllBooks();
        log.info("查询的数据为: {}", r);
        return r;
    }

    @GetMapping("/{pageSize}/{currentPage}")
    public R page(@PathVariable int pageSize, @PathVariable int currentPage) {
        R r = bookSrevice.page(pageSize, currentPage);
        log.info("分页查询的数据为: {}", r);
        return r;
    }

    @GetMapping("searchBook/{searTit}/{searInput}/{pageSize}/{currentPage}")
    public R searchBook(@PathVariable  String searTit, @PathVariable  String searInput,
                        @PathVariable int pageSize, @PathVariable int currentPage) {
        R r = bookSrevice.findBookBySearch(searTit,searInput,pageSize,currentPage);
        log.info("模糊查询的图书数据为: {}", r);
        return r;
    }

    @PutMapping
    public R update(@Validated @RequestBody Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("参数为空 不能修改");
        }
        R r = bookSrevice.update(book);
        return r;
    }

    @PostMapping("addBook")
    public R addBook(@Validated @RequestBody Book book) {
            //  新增图书
            return bookSrevice.addBook(book);
    }

    @DeleteMapping("/{id}")
    public R remove(@PathVariable Integer id){
        R r = bookSrevice.remove(id);
        return r;
    }
}
