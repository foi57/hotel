package org.example.springboot.Controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.springboot.Service.UserService;
import org.example.springboot.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String,String> form) {
        User user = userService.login(form.get("email"), form.get("password"));
        logger.info("userid={}",user.getId());
        if (user != null) {
            String key = "your-256-bit-secret-long-string-here";
            Key keys = Keys.hmacShaKeyFor(key.getBytes());
            String token = Jwts.builder()
                    .claim("id", user.getId())
                    .claim("email", user.getEmail())
                    .claim("password", user.getPassword())
                    .claim("userName", user.getUserName())
                    .claim("iat", new Date())
                    .claim("exp", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(keys)
                    .compact();
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            return ResponseEntity.ok(resultMap);
        }
        return ResponseEntity.status(401).build();
    }
    @PostMapping("/enroll")
    public void enroll(@RequestBody Map<String,String> form) {
        User user = new User( form.get("username"),form.get("email"), form.get("password"));
        userService.insert(user);
    }


}
