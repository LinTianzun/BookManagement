package com.linlin.controller;

import com.linlin.pojo.User;
import com.linlin.service.StudentService;
import com.linlin.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 使用 @CrossOrigin 注解允许跨域请求，这样其他源的前端应用可以访问我们的后端API
@CrossOrigin
@RestController
@RequestMapping("student")
@Slf4j
public class StudentController {

    // 自动装配 StudentService 服务类
    @Autowired
    private StudentService studentService;

    // 获取个人信息的接口
    // @GetMapping 注解定义了 HTTP GET 请求的映射路径为 "/student/{userId}"
    // {userId} 是一个路径变量，用于接收 URL 中的用户ID
    @GetMapping("/{userId}")
    public R getInfo(@PathVariable Integer userId) {
        // 调用 StudentService 的 getInfo 方法获取用户信息
        // 并返回结果
        R r = studentService.getInfo(userId);
        // 记录日志，打印查询到的个人信息
        log.info("查询的个人信息为: {}", r);
        return r;
    }

    // 分页查询图书信息的接口
    // @GetMapping 注解定义了 HTTP GET 请求的映射路径为 "/student/{pageSize}/{currentPage}"
    // {pageSize} 和 {currentPage} 是路径变量，分别用于接收每页显示的图书数量和当前页码
    @GetMapping("/{pageSize}/{currentPage}")
    public R queryAllBook(@PathVariable int pageSize, @PathVariable int currentPage) {
        // 调用 StudentService 的 queryAllBook 方法分页查询图书信息
        // 并返回结果
        R r = studentService.queryAllBook(pageSize, currentPage);
        // 记录日志，打印查询到的图书数据
        log.info("用户分页查询的图书数据为: {}", r);
        return r;
    }

    // 模糊查询图书信息的接口
    // @GetMapping 注解定义了 HTTP GET 请求的映射路径为 "/student/searchBook/{searTit}/{searInput}/{pageSize}/{currentPage}"
    // {searTit}、{searInput}、{pageSize} 和 {currentPage} 是路径变量，分别用于接收搜索标题、搜索输入、每页显示的图书数量和当前页码
    @GetMapping("searchBook/{searTit}/{searInput}/{pageSize}/{currentPage}")
    public R searchBook(@PathVariable String searTit, @PathVariable String searInput,
                        @PathVariable int pageSize, @PathVariable int currentPage) {
        // 调用 StudentService 的 findBookBySearch 方法进行模糊查询图书信息
        // 并返回结果
        R r = studentService.findBookBySearch(searTit, searInput, pageSize, currentPage);
        // 记录日志，打印查询到的图书数据
        log.info("模糊查询的图书数据为: {}", r);
        return r;
    }

    // 更新用户密码的接口
    // @PutMapping 注解定义了 HTTP PUT 请求的映射路径为 "/student"
    // @RequestBody 注解表示请求体中的 JSON 数据将被自动绑定到 User 对象上
    // @Validated 注解用于验证 User 对象的字段是否符合定义的约束
    @PutMapping
    public R updateUserPwd(@Validated @RequestBody User user, BindingResult bindingResult) {
        // 如果验证失败，将错误信息返回给前端
        if (bindingResult.hasErrors()) {
            return R.fail("核心参数为null 无法修改");
        }
        // 调用 StudentService 的 updateUserPwd 方法更新用户密码
        // 并返回结果
        R r = studentService.updateUserPwd(user);
        return r;
    }
}