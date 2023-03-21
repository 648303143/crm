package com.uestc.crm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/18 16:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_car_picture")
public class CarPicturePO {

    @TableId
    private Long id;

    private String carId;

    private String url;

    private Date createTime;

    private Date updateTime;
}
