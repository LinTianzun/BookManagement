package com.linlin.mapper;

import com.linlin.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //  查询所有的用户
    @Select("select  * from users")
    List<User> findAllUsers();

    //  根据用户名查询用户
    @Select("select  * from users where username = #{username}")
    User findByUserName(String username);

    //  注册
    int register(User user);

    //  模糊查询用户数据
    List<User> findUserBySearch(@Param("searTit") String searTit, @Param("searInput") String searInput);

    //  根据ID删除用户
    @Delete("DELETE from users where user_id = #{id}")
    int deleteById(Integer id);

    //  更新用户信息
    int update(User user);
}
