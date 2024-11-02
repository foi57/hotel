package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.springboot.entity.Picture;
import org.example.springboot.entity.User;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserMapper {

    @Insert("insert into user(userName,email, password) values(#{userName},#{email}, #{password})")
    void insert(User user);
    @Select("SELECT * FROM user where email=#{userName} and password=#{password}")
    User login(String userName, String password);

    @Options(useGeneratedKeys = true, keyProperty = "id") // 这里指向 Picture 对象的 id
    @Insert("INSERT INTO picture(UUID, fileName) VALUES (#{uuid}, #{fileName})")
    void addPicture(Picture picture);
    @Select("SELECT UUID FROM picture WHERE fileName=#{fileName}")
    String selectPicture(String fileName);
}
