package com.uestc.crm.query;

import lombok.Data;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/2 17:27
 */

@Data
public class ListOrderQuery extends BasePageQuery{

    private String orderId;
    private String custId;
    private String username;
    private Integer state;

}
