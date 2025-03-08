package com.linlin.controller;

import com.linlin.pojo.Category;
import com.linlin.service.CategorySrevice;
import com.linlin.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin    //  允许其它源访问我们的controller
@RestController
@RequestMapping("category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategorySrevice categorySrevice;

    @GetMapping("allCategory")
    public R page() {
        R r = categorySrevice.queryAllCategorys();
        log.info("查询的数据为: {}", r);
        return r;
    }

    @GetMapping("/{pageSize}/{currentPage}")
    public R page(@PathVariable int pageSize, @PathVariable int currentPage) {
        R r = categorySrevice.page(pageSize, currentPage);
        log.info("查询的数据为: {}", r);
        return r;
    }

    @GetMapping("searchCate/{searInput}/{pageSize}/{currentPage}")
    public R searchUser( @PathVariable  String searInput,
                        @PathVariable int pageSize, @PathVariable int currentPage) {
        R r = categorySrevice.findCateBySearch(searInput,pageSize,currentPage);
        log.info("搜索查询的数据为: {}", r);
        return r;
    }

    @PutMapping
    public R update(@Validated @RequestBody Category category, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("参数为空 不能修改");
        }
        R r = categorySrevice.update(category);
        return r;
    }


    @PostMapping("add")
    public R register(@Validated @RequestBody Category category) {
        //  查询用户
        Category u = categorySrevice.findByCateName(category.getCategoryName());
        if (u == null) {
            //  用户名没有被占用
            //  注册
            return categorySrevice.add(category);
        } else {
            //  用户名已被占用
            return R.fail("用户名已被占用");
        }

    }

    @DeleteMapping("/{id}")
    public R remove(@PathVariable Integer id){
        R r = categorySrevice.remove(id);
        return r;
    }
}
