package com.uestc.crm.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/4 16:02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListQuery extends BasePageQuery{
    private String username;
    private String phonenumber;
}
