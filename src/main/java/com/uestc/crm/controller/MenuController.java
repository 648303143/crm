package com.uestc.crm.controller;

import com.uestc.crm.pojo.MenuPO;
import com.uestc.crm.service.impl.MenuServiceImpl;
import com.uestc.crm.util.Result;
import com.uestc.crm.util.SecurityUtils;
import com.uestc.crm.vo.MenuTreeSelectVO;
import com.uestc.crm.vo.TreeSelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/2 20:33
 */

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @PostMapping("/list")
    public Result<List<MenuPO>> list() {
        List<MenuPO> menus = menuService.selectMenuListByRoleId(SecurityUtils.getLoginUser().getUser().getRoleId());
        return Result.success(menus);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @PostMapping(value = "/roleMenuTreeselect")
    public Result<MenuTreeSelectVO> roleMenuTreeselect(@RequestBody Long roleId)
    {
        List<MenuPO> menus = menuService.selectMenuListByRoleId(SecurityUtils.getLoginUser().getUser().getRoleId());
        List<Long> menuIds = menuService.selectMenuIdListByRoleId(roleId);
        List<TreeSelectVO> treeSelectVOS = menuService.buildMenuTreeSelect(menus);
        return Result.success(new MenuTreeSelectVO(treeSelectVOS, menuIds));
    }
}
