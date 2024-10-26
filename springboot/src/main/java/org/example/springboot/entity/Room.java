package org.example.springboot.entity;

import java.math.BigDecimal;
import java.util.List;

public class Room {
    private int roomId;
       private String roomName;
       private List<String> roomPictures; // 使用 List<String> 存储图片
       private int roomCount;
       private String bedType;
       private int bedCount;
       private List<Boolean> facilities; // 使用 List<Boolean> 存储设施的选中状态
       private BigDecimal price;
       // Constructors
       // Getters and Setters

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<Boolean> getFacilities() {
           return facilities;
       }

       public void setFacilities(List<Boolean> facilities) {
           this.facilities = facilities;
       }

       public String getRoomName() {
           return roomName;
       }

       public void setRoomName(String roomName) {
           this.roomName = roomName;
       }

       public List<String> getRoomPictures() {
           return roomPictures;
       }

       public void setRoomPictures(List<String> roomPictures) {
           this.roomPictures = roomPictures;
       }

       public int getRoomCount() {
           return roomCount;
       }

       public void setRoomCount(int roomCount) {
           this.roomCount = roomCount;
       }

       public String getBedType() {
           return bedType;
       }

       public void setBedType(String bedType) {
           this.bedType = bedType;
       }

       public int getBedCount() {
           return bedCount;
       }

       public void setBedCount(int bedCount) {
           this.bedCount = bedCount;
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
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomPictures=" + roomPictures +
                ", roomCount=" + roomCount +
                ", bedType='" + bedType + '\'' +
                ", bedCount=" + bedCount +
                ", facilities=" + facilities +
                ", price=" + price +
                '}';
    }
}
