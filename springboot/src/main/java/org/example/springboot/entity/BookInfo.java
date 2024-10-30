package org.example.springboot.entity;
import java.math.BigDecimal;
import lombok.Data;
@Data
public class BookInfo {
    private int id;
    private int user_id;
    private int room_id;
    private int room_count;
    private String guest_name;
    private String guest_phone;
    private String guest_email;
    private String guest_arrivalTime;
    private String TimeStart;
    private String TimeEnd;
    private String arrivalTime;
    private BigDecimal price;
}
