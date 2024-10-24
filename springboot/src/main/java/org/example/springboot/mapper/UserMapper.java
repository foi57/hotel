package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.springboot.entity.User;

@Mapper
public interface UserMapper {

    @Insert("insert into user(userName,email, password) values(#{userName},#{email}, #{password})")
    void insert(User user);
    @Select("SELECT * FROM user where email=#{userName} and password=#{password}")
    User login(String userName, String password);
}
