package com.uestc.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/22 15:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddVO {
    private String orderId;
    private String custId;
    private String carId;
    private String username;
    private String lookTime;
    private Integer state;
    private String failReason;
    private String remark;
    private String clueId;

}
