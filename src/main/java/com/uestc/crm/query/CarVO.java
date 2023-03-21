package com.uestc.crm.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/17 22:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarVO {

    @TableId
    private Long id;

    private String carId;

    private String title;

    private String bizId;

    private Double price;

    private Boolean isBargain;

    private String brand;

    private Date licensingTime;

    private Double engine;

    private Integer fuelType;

    private String series;

    private Integer carType;

    private Double kilometer;

    private String city;

    private String licensePlate;

    @TableField("transmission_case")
    private Integer transmissionCase;

    private String color;

    private String[] picture;

    private String info;

    private Date createTime;

    private Date updateTime;

}
