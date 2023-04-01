package com.uestc.crm.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 17:44
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_menu")
public class MenuPO {
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @TableId
    private Long menuId;

    /** 菜单名称 */
    private String menuName;

    /** 父菜单ID */
    private Long parentId;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 路由参数 */
    private String query;

    /** 是否缓存（0缓存 1不缓存） */
    private Integer isCache;

    /** 菜单类型（M目录 C菜单 F按钮） */
    private String menuType;

    /** 菜单状态（0显示 1隐藏） */
    private Integer visible;

    /** 菜单状态（0正常 1停用） */
    private Integer status;

    /** 权限标识 */
    private String perms;

    /** 菜单图标 */
    private String icon;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 子菜单 */
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<MenuPO> children = new ArrayList<>();
}
