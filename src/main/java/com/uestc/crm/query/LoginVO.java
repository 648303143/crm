package com.uestc.crm.query;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/6 19:13
 */

@Data
public class LoginVO {

    private String username;
    private String password;
    private Long loginTime;
    private String token;
}
