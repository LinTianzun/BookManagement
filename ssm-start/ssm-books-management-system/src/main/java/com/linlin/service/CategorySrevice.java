package com.linlin.service;

import com.linlin.pojo.Category;
import com.linlin.utils.R;

public interface CategorySrevice {
    //  图书类别分页查询
    R page(int pageSize, int currentPage);

    R findCateBySearch(String searInput, int pageSize, int currentPage);

    R update(Category category);

    Category findByCateName(String categoryName);

    R add(Category category);

    R remove(Integer id);

    R queryAllCategorys();
}
