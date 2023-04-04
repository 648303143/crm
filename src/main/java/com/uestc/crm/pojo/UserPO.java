package com.uestc.crm.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class UserPO implements Serializable {

    @TableId
    private Long id;

    private String username;

    private String password;

    private String phonenumber;

    private Long roleId;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    @JSONField(serialize = false)
    private String roleName;
}
