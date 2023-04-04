package com.uestc.crm.vo;

import lombok.Data;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/3 20:40
 */
@Data
public class RoleMenuVO {
    private Long roleId;
    private Long[] menuIds;
}
