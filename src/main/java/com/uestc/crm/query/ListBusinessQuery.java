package com.uestc.crm.query;

import lombok.Data;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/2 17:27
 */

@Data
public class ListBusinessQuery extends BasePageQuery{
    private Integer type;
    private String bizId;


}
