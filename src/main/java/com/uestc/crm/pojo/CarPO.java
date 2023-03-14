package com.uestc.crm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_car")
public class CarPO {

    @TableId
    private Long id;

    private String carId;

    private String title;

    private String bizID;

    private Integer price;

    private Boolean isBargain;

    private String brand;

    private Date licensingTime;

    private Integer engine;

    private Integer fuelType;

    private String series;

    private Integer carType;

    private Integer kilometer;

    private String city;

    private String licensePlate;

    private Integer transmissionCase;

    private String color;

    private byte[] picture;

    private String info;

    private Date createTime;

    private Date updateTime;

}
