package com.uestc.crm.query;

import lombok.Data;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/15 17:50
 */

@Data
public class ListClueQuery extends BasePageQuery{
    private String custId;
    private String username;
}
