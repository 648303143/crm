package com.uestc.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/3 17:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeSelectVO {
    private List<TreeSelectVO> menus;
    private List<Long> menuIds;
}
