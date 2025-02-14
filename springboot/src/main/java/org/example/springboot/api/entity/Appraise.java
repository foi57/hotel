package org.example.springboot.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2025-02-06
 */
@Data
@ApiModel(value = "Appraise对象", description = "")
public class Appraise implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long roomId;

    private int rating;

    private String comment;

    private List<String> pictureURL;

}
