package com.linlin.mapper;

import com.linlin.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM categories")
    List<Category> queryAll();

//    @Select("SELECT * FROM categories where category_name = #{searInput}")
    List<Category> findCateBySearch(@Param("searInput") String searInput);

//    @Update("update categories set category_name=#{categoryName} ,description=#{description} where category_id =#{categoryId} ")
    int update(Category category);

    @Select("SELECT * from categories where category_name = #{categoryName}")
    Category findByCateName(String categoryName);

    int add(Category category);

    @Delete("delete from categories where category_id = #{id}")
    int deleteById(Integer id);
}
