package com.uestc.crm.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/2 17:27
 */

@Data
public class ListCustomerQuery extends BasePageQuery{
    private String custId;
    private String nickname;
    private String name;
    private String phone;

}
