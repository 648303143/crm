package com.uestc.crm.vo;

import lombok.Data;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/7 18:50
 */
@Data
public class RegisterVO {
    private String username;
    private String password;
    private String checkPassword;
    private String email;
    private String phoneNumber;
}
