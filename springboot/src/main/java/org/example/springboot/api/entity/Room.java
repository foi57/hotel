package org.example.springboot.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.example.springboot.api.Config.BooleanListTypeHandler;

import java.math.BigDecimal;
import java.util.List;
@Data
@TableName(value = "room", autoResultMap = true)
public class Room {
    private int id;
       private String room_name;
       private int room_count;
    @TableField(exist = false)
       private String room_picture_url; // 使用 List<String> 存储图片
       private int bed_count;
       private String bed_type;
    @TableField(typeHandler = BooleanListTypeHandler.class)
       private List<Boolean> facilities; // 使用 List<Boolean> 存储设施的选中状态
       private BigDecimal price;
    @TableField(exist = false)
       private int available_rooms;

}
