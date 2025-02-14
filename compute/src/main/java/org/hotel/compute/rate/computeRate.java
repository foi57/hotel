package org.hotel.compute.rate;

import org.example.springboot.api.entity.Appraise;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class computeRate {
    Logger logger = org.slf4j.LoggerFactory.getLogger(computeRate.class);
    @KafkaListener(topics = "appraise",groupId = "compute")
    public void ListenRate(Appraise appraise){
        logger.info("appraise{}",appraise);
    }
}
