package com.uestc.crm.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uestc.crm.pojo.MenuPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/2 20:26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeSelectVO {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVO> children;


    public TreeSelectVO(MenuPO menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelectVO::new).collect(Collectors.toList());
    }

}
