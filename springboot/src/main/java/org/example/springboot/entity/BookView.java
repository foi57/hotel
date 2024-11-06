package org.example.springboot.entity;

import lombok.Data;

@Data
public class BookView {
    private int id;
    private String name;
    private String timeStart;
    private String timeEnd;
    private String arrivalTime;
    private String room_name;
    private String room_count;
    private String price;
    private String state;
    private String bookTime;
}
