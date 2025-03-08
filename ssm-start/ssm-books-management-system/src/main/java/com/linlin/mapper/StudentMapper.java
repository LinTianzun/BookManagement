package com.linlin.mapper;

import com.linlin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

    @Select("select * from users where user_id = #{userId}")
    User getInfo(Integer userId);

    int updateUserPwd(User user);
}
