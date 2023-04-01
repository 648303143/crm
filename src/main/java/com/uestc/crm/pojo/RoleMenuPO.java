package com.uestc.crm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 17:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role_menu")
public class RoleMenuPO {

    private Long roleId;

    private Long menuId;
}
