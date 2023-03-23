package com.uestc.crm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_customer")
public class CustomerPO {

    @TableId
    private Long id;

    private String custId;

    private String nickname;

    private String name;

    private Integer sex;

    private String phone;

    private String city;

    private Integer age;

    private String custCall;

    private Integer carType;

    private String budget;

    private Boolean isLoad;

    private Integer intention;

    private Date createTime;

    private Date updateTime;

}
