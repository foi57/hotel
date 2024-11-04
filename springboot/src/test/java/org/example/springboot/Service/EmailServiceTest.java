package org.example.springboot.Service;
import org.example.springboot.Service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService; // 假设你的 EmailService 类包含 sendVerificationEmail 方法

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendVerificationEmail() {
        String to = "2858712518@qq.com";
        String subject = "textEmail";
        String text = "Your verification code is 123456";

        emailService.sendVerificationEmail(to, subject, text);

        // 验证 emailSender 的 send 方法是否被调用
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        verify(emailSender).send(message);
    }
}
