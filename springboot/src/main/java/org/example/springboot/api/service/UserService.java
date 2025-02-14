package org.example.springboot.api.service;

import org.example.springboot.api.mapper.UserMapper;
import org.example.springboot.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "userCache", keyGenerator = "customKeyGenerator")
    public User login(String email, String password) {
        return userMapper.login(email, password);
    }

    @Cacheable(value = "SelectUserByEmailCache", key = "#email")
    public User SelectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }
}
