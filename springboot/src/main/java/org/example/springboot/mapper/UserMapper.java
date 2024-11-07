package org.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.example.springboot.entity.Picture;
import org.example.springboot.entity.User;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserMapper {

    @Insert("insert into user(userName,email, password) values(#{userName},#{email}, #{password})")
    void insert(User user);
    @Select("SELECT * FROM user where email=#{userName} and password=#{password}")
    User login(String userName, String password);

    @Select("SELECT * from user where email=#{userName}")
    User selectUserByEmail(@Param("userName") String userName);
    @Options(useGeneratedKeys = true, keyProperty = "id") // 这里指向 Picture 对象的 id
    @Insert("INSERT INTO picture(UUID, fileName) VALUES (#{uuid}, #{fileName})")
    void addPicture(Picture picture);
    @Select("SELECT UUID FROM picture WHERE id=#{id}")
    String selectPicture(String id);
    @Delete("DELETE from picture where id=#{id}")
    void deletePicture(String id);
}
