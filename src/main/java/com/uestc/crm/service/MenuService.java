package com.uestc.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uestc.crm.pojo.MenuPO;
import com.uestc.crm.vo.RouterVo;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 18:09
 */

public interface MenuService extends IService<MenuPO>{

    List<MenuPO> getMenuTreeByRoleId(Long roleId);

    List<RouterVo> buildMenus(List<MenuPO> menus);

    List<MenuPO> selectMenuList(Long userId);

    List<MenuPO> selectMenuList(MenuPO menu, Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);

    List<MenuPO> buildMenuTree(List<MenuPO> menus);
}
