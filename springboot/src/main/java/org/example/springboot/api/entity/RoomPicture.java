package org.example.springboot.api.entity;

import lombok.Data;

@Data
public class RoomPicture {
    private int id;
    private int hotel_id;
    private String picture_url;
}
