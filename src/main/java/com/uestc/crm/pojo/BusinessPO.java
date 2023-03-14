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
@TableName("t_business")
public class BusinessPO {

    @TableId
    private Long id;

    private String bizId;

    private String name;

    private Integer sex;

    private String phone;

    private Integer type;

    private Date createTime;

    private Date updateTime;

}
