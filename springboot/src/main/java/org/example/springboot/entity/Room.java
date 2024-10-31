package org.example.springboot.entity;

import java.math.BigDecimal;
import java.util.List;

public class Room {
    private int id;
       private String room_name;
       private int room_count;
       private String room_picture_url; // 使用 List<String> 存储图片
       private int bed_count;
       private String bed_type;
       private List<Boolean> facilities; // 使用 List<Boolean> 存储设施的选中状态
       private BigDecimal price;
       // Constructors
       // Getters and Setters

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Boolean> getFacilities() {
           return facilities;
       }

       public void setFacilities(List<Boolean> facilities) {
           this.facilities = facilities;
       }

       public String getRoom_name() {
           return room_name;
       }

       public void setRoom_name(String room_name) {
           this.room_name = room_name;
       }

    public String getRoom_picture_url() {
        return room_picture_url;
    }

    public void setRoom_picture_url(String room_picture_url) {
        this.room_picture_url = room_picture_url;
    }

    public int getBed_count() {
           return bed_count;
       }

       public void setBed_count(int bed_count) {
           this.bed_count = bed_count;
       }

       public String getBed_type() {
           return bed_type;
       }

       public void setBed_type(String bed_type) {
           this.bed_type = bed_type;
       }


       public BigDecimal getPrice() {
           return price;
       }

       public void setPrice(BigDecimal price) {
           this.price = price;
       }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", room_name='" + room_name + '\'' +
                ", room_count=" + room_count +
                ", room_picture_url='" + room_picture_url + '\'' +
                ", bed_count=" + bed_count +
                ", bed_type='" + bed_type + '\'' +
                ", facilities=" + facilities +
                ", price=" + price +
                '}';
    }
}
