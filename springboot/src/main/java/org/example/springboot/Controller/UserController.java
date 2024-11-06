package org.example.springboot.Controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.springboot.Service.EmailService;
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
import java.security.SecureRandom;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    EmailService emailService;
    @Value("${jwt.secret}")
    private  String secretKey;
Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String,String> form) {
        User user = userService.login(form.get("email"), form.get("password"));
        if (user == null) {
            return ResponseEntity.status(403).body(Collections.singletonMap("error", "用户名或密码错误"));
        }
        logger.info("userid={}",user.getId());
     newToken(user);
        return ResponseEntity.status(200).body(Collections.singletonMap("token", newToken(user)));
    }
    @PostMapping("/enroll")
    public ResponseEntity<String> enroll(@RequestBody Map<String,String> form) {
        logger.info("form={}",form);
        User sUser = userService.SelectUserByEmail(form.get("email"));
        if(sUser==null){
            User user = new User( form.get("username"),form.get("email"), form.get("password"));
            logger.info("user={}",user);
            userService.insert(user);
            return ResponseEntity.status(200).body("注册成功");
        }
        return ResponseEntity.status(403).body("该用户已存在");
    }
    @PostMapping("/emailVerify")
    public int emailVerify(@RequestParam("email") String email) {
        logger.info("email={}",email);
        SecureRandom secureRandom= new SecureRandom();
        int code = 1000 + secureRandom.nextInt(9000);
       emailService.sendVerificationEmail(email, "欢迎注册酒店预订系统", "Your verification code is "+ code);
       return code;
    }
 public String newToken(User user) {

        Key keys = Keys.hmacShaKeyFor(secretKey.getBytes());
     return Jwts.builder()
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .claim("userName", user.getUserName())
                .claim("iat", new Date())
                .claim("exp", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(keys)
                .compact();
    }
}
