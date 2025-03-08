package com.linlin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linlin.mapper.CategoryMapper;
import com.linlin.pojo.Category;
import com.linlin.service.CategorySrevice;
import com.linlin.utils.PageBean;
import com.linlin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySreviceImpl implements CategorySrevice {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R page(int pageSize, int currentPage) {
        //  分页
        PageHelper.startPage(currentPage, pageSize);
        //  查询
        List<Category> categoryList = categoryMapper.queryAll();
        //  分页数据装配
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);

        //  装配PageBean
        PageBean<Category> pageBean = new PageBean<>(currentPage, pageSize, pageInfo.getTotal(), pageInfo.getList());

        return R.ok(pageBean);
    }

    @Override
    public R findCateBySearch(String searInput, int pageSize, int currentPage) {
        //  分页
        PageHelper.startPage(currentPage, pageSize);
        //  查询
        List<Category> categoryList = categoryMapper.findCateBySearch(searInput);
        //  分页数据装配
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);

        //  装配PageBean
        PageBean<Category> pageBean = new PageBean<>(currentPage, pageSize, pageInfo.getTotal(), pageInfo.getList());

        return R.ok(pageBean);
    }

    @Override
    public R update(Category category) {
        //  判断id不能为null
        if(category.getCategoryId()==null){
            return R.fail("核心参数为null 无法修改");
        }
        int rows = categoryMapper.update(category);
        if (rows > 0) {
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }

    @Override
    public Category findByCateName(String categoryName) {
        return categoryMapper.findByCateName(categoryName);
    }

    @Override
    public R add(Category category) {
        //  添加
        int rows = categoryMapper.add(category);
        if (rows > 0) {
            return R.ok("添加图书类别成功");
        }
        return R.fail("添加图书类别失败");
    }

    @Override
    public R remove(Integer id) {
        int rows = categoryMapper.deleteById(id);
        if (rows > 0) {
            return R.ok("删除成功");
        }
        return R.fail("删除失败");
    }

    @Override
    public R queryAllCategorys() {
        List<Category> categoryList = categoryMapper.queryAll();
        return  R.ok(categoryList);
    }
}
