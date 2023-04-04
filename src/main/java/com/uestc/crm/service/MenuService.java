package com.uestc.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uestc.crm.pojo.MenuPO;
import com.uestc.crm.vo.RouterVO;
import com.uestc.crm.vo.TreeSelectVO;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 18:09
 */

public interface MenuService extends IService<MenuPO>{

    List<MenuPO> getMenuTreeByRoleId(Long roleId);

    List<RouterVO> buildMenus(List<MenuPO> menus);

    List<MenuPO> selectMenuListByRoleId(Long userId);

    List<Long> selectMenuIdListByRoleId(Long roleId);

    List<TreeSelectVO> buildMenuTreeSelect(List<MenuPO> menus);

    List<MenuPO> buildMenuTree(List<MenuPO> menus);
}
