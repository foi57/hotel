package org.example.springboot.entity;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookInfo {

    private int id;
    private int user_id;
    private int room_id;
    private int room_count;
    private String name;
    private String phone;
    private String email;
    private String timeStart;
    private String timeEnd;
    private String arrivalTime;
    private BigDecimal price;
    private String bookInfo;


}
