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
@TableName("t_order")
public class OrderPO {

    @TableId
    private Long id;

    private String orderId;

    private String custId;

    private String bizId;

    private String lookTime;

    private Date createTime;

    private Date updateTime;

}
