package org.example.springboot.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class HotelForm {
    private Integer id;
    private String name;
    private String  introduction;
    private List<String> picture_url; // 使用 List<String> 存储酒店图片
    private String province;
    private String city;
    private String district;
    private String address;
    private List<String> locations; // 存储位置
    private List<Room> rooms; // 房间信息的列表
    private String picture_urls;
    private int total_rooms;
    private int booked_rooms;

}

