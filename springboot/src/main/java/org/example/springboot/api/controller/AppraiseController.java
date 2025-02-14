package org.example.springboot.api.controller;

import org.example.springboot.Kafka.Producer;
import org.example.springboot.api.entity.Appraise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-02-06
 */
@RestController
@RequestMapping("/api/appraise")
public class AppraiseController {
    Logger logger = LoggerFactory.getLogger(AppraiseController.class);

    Producer producer;

    @Autowired
    public AppraiseController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/insert")
    public void insertAppraise(@RequestBody Appraise appraise) {
        logger.info("appraise{}", appraise);
        producer.send("appraise", appraise);
    }
}
