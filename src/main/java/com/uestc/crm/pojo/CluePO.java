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
 * @create 2023-03-2023/3/15 17:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_clue")
public class CluePO {

    @TableId
    private Long id;
    private String clueId;
    private String custId;
    private Integer clueWay;
    private Integer clueType;
    private String username;
    private Boolean isHandle;
    private Date createTime;
    private Date updateTime;

}
