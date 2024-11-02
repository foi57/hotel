package org.example.springboot.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class Room {
    private int id;
       private String room_name;
       private int room_count;
       private String room_picture_url; // 使用 List<String> 存储图片
       private int bed_count;
       private String bed_type;
       private List<Boolean> facilities; // 使用 List<Boolean> 存储设施的选中状态
       private BigDecimal price;
       private int available_rooms;

}
