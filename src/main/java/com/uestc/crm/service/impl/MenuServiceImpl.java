package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.uestc.crm.common.Constants;
import com.uestc.crm.mapper.MenuMapper;
import com.uestc.crm.pojo.MenuPO;
import com.uestc.crm.pojo.RoleMenuPO;
import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.service.MenuService;
import com.uestc.crm.vo.MetaVO;
import com.uestc.crm.vo.RouterVO;
import com.uestc.crm.vo.TreeSelectVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuPO> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public Set<String> getMenuPermsByRoleId(Long roleId) {
        MPJLambdaWrapper<MenuPO> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(MenuPO::getPerms)
                .leftJoin(RoleMenuPO.class, RoleMenuPO::getMenuId, MenuPO::getMenuId)
                .leftJoin(RolePO.class, RolePO::getRoleId, RoleMenuPO::getRoleId)
                .eq(RoleMenuPO::getRoleId, roleId)
                .eq(RolePO::getStatus, 0)
                .eq(MenuPO::getStatus, 0);
        List<String> perms = menuMapper.selectJoinList(String.class, wrapper);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StrUtil.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<MenuPO> getMenuTreeByRoleId(Long roleId) {
        List<MenuPO> menus = null;
        if (roleId == 1) {
            menus = menuMapper.selectList(new LambdaQueryWrapper<>());
        } else {
            MPJLambdaWrapper<MenuPO> queryWrapper = new MPJLambdaWrapper<>();
            queryWrapper.selectAll(MenuPO.class)
                    .leftJoin(RoleMenuPO.class, RoleMenuPO::getMenuId, MenuPO::getMenuId)
                    .leftJoin(RolePO.class, RolePO::getRoleId, RoleMenuPO::getRoleId)
                    .eq(RolePO::getRoleId, roleId)
                    .in(MenuPO::getMenuType, "M", "C")
                    .eq(RolePO::getStatus, 0)
                    .eq(MenuPO::getStatus, 0);
            menus = menuMapper.selectJoinList(MenuPO.class, queryWrapper);
        }
        return getChildPerms(menus, 0);
    }

    public List<MenuPO> getChildPerms(List<MenuPO> menus, int parentId) {
        List<MenuPO> returnList = new ArrayList<>();
        for (MenuPO menu : menus) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (menu.getParentId() == parentId) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        return returnList;
    }

    private void recursionFn(List<MenuPO> list, MenuPO t) {
        List<MenuPO> children = getChildren(list, t);

        t.setChildren(children);

        for (MenuPO child : children) {
            if (getChildren(list, t).size() > 0) {
                recursionFn(list, child);
            }
        }
    }

    private List<MenuPO> getChildren(List<MenuPO> list, MenuPO t) {
        List<MenuPO> children = new ArrayList<>();
        for (MenuPO n : list) {
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                children.add(n);
            }
        }
        return children;
    }

    @Override
    public List<RouterVO> buildMenus(List<MenuPO> menus) {
        List<RouterVO> routers = new LinkedList<>();
        for (MenuPO menu : menus) {
            RouterVO router = new RouterVO();
            router.setHidden(menu.getVisible() == 1);
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getIsCache() == 1, menu.getPath()));
            List<MenuPO> menuChildren = menu.getChildren();
            if (menuChildren != null && menuChildren.size() >= 1 && "M".equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(menuChildren));
            } else if (menu.getParentId().intValue() == 0 && "C".equals(menu.getMenuType())) {
                router.setMeta(null);
                List<RouterVO> children = new ArrayList<RouterVO>();
                RouterVO child = new RouterVO();
                child.setPath(menu.getPath());
                child.setComponent(menu.getComponent());
                child.setName(StringUtils.capitalize(menu.getPath()));
                child.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getIsCache() == 1, menu.getPath()));
                child.setQuery(menu.getQuery());
                children.add(child);
                router.setChildren(children);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVO> childrenList = new ArrayList<RouterVO>();
                RouterVO children = new RouterVO();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent("InnerLink");
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    @Override
    public List<MenuPO> selectMenuListByRoleId(Long roleId) {
        List<MenuPO> menuList = null;
        // 管理员显示所有菜单信息
        if (roleId == 1) {
            menuList = menuMapper.selectList(new QueryWrapper<>());
        } else {
            MPJLambdaWrapper<MenuPO> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(MenuPO.class)
                    .leftJoin(RoleMenuPO.class, RoleMenuPO::getMenuId, MenuPO::getMenuId)
                    .eq(RoleMenuPO::getRoleId, roleId);
            menuList = menuMapper.selectJoinList(MenuPO.class, wrapper);
        }
        return menuList;
    }

    @Override
    public List<Long> selectMenuIdListByRoleId(Long roleId) {
        // 管理员显示所有菜单信息
        MPJLambdaWrapper<MenuPO> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(MenuPO::getMenuId)
                .leftJoin(RoleMenuPO.class, RoleMenuPO::getMenuId, MenuPO::getMenuId)
                .eq(RoleMenuPO::getRoleId, roleId);
        List<Long> list = menuMapper.selectJoinList(Long.class, wrapper);
        return list;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelectVO> buildMenuTreeSelect(List<MenuPO> menus) {
        List<MenuPO> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelectVO::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<MenuPO> buildMenuTree(List<MenuPO> menus) {
        List<MenuPO> returnList = new ArrayList<>();
        List<Long> tempList = menus.stream().map(MenuPO::getMenuId).collect(Collectors.toList());
        for (MenuPO menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(MenuPO menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (menu.getParentId().intValue() == 0 && "C".equals(menu.getMenuType())) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(MenuPO menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && "M".equals(menu.getMenuType())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (menu.getParentId().intValue() == 0 && "C".equals(menu.getMenuType())) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(MenuPO menu) {
        String component = "Layout";
        if (StringUtils.isNotEmpty(menu.getComponent())
                && !(menu.getParentId().intValue() == 0 && "C".equals(menu.getMenuType()))) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = "InnerLink";
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = "ParentView";
        }
        return component;
    }


    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(MenuPO menu) {
        return StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS);
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(MenuPO menu) {
        return menu.getParentId().intValue() != 0 && "M".equals(menu.getMenuType());
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
                new String[]{"", "", "", "/"});
    }

}
