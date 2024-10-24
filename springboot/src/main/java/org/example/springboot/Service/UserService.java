package org.example.springboot.Service;

import org.example.springboot.mapper.UserMapper;
import org.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    UserMapper userMapper;
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void insert(User user) {
        userMapper.insert(user);
    }
    public User login(String email, String password) {
        return userMapper.login(email, password);
    }
}
