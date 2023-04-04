package com.uestc.crm.vo;

import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.pojo.UserPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/4 16:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private UserPO user;

    private List<RolePO> roles;
}
