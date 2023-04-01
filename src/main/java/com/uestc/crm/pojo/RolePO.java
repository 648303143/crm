package com.uestc.crm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 16:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class RolePO {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @TableId
    private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 角色权限字符串 */
    private String roleKey;

    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限） */
    private String dataScope;

    /** 菜单树选择项是否关联显示 */
    private Integer menuCheckStrictly;

    /** 部门树选择项是否关联显示 */
    private Integer deptCheckStrictly;

    /** 角色状态（0正常 1停用） */
    private String status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;
}
