package com.uestc.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/16 16:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private Long id;

    private String orderId;

    private String custId;

    private String username;

    private String carId;

    private String carTitle;

    private Boolean isLoad;

    private String lookTime;

    private Integer state;

    private String failReason;

    private String remark;

    private String clueId;

    private Date createTime;

    private Date updateTime;

}
