package org.example.springboot.entity;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Table(name = "picture", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"uuid", "fileName"})
})
public class Picture {
    private Long id; // 主键
    private String uuid;
    private String fileName;
    private String picture;

    // getters 和 setters
}

