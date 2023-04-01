package com.uestc.crm.vo;

import com.uestc.crm.pojo.UserPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 22:24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private UserPO user;

    private String role;

    private Set<String> permissions;
}
