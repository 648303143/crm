package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.RoleMapper;
import com.uestc.crm.mapper.RoleMenuMapper;
import com.uestc.crm.mapper.UserMapper;
import com.uestc.crm.pojo.RoleMenuPO;
import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.query.RoleListQuery;
import com.uestc.crm.service.RoleService;
import com.uestc.crm.vo.RoleMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getRoleKeyById(Long roleId) {
        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getRoleId, roleId);
        RolePO rolePO = roleMapper.selectOne(queryWrapper);
        return rolePO.getRoleKey();
    }

    @Override
    public RolePO getRoleById(Long roleId) {
        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getRoleId, roleId);
        RolePO rolePO = roleMapper.selectOne(queryWrapper);
        return rolePO;
    }

    @Override
    public Page<RolePO> listRole(RoleListQuery query) {
        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        Page<RolePO> page = roleMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return page;
    }

    public List<RolePO> getAllRole() {
        List<RolePO> list = roleMapper.selectList(new QueryWrapper<>());
        return list;
    }


    @Override
    public boolean checkUnique(RolePO role) {
        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getRoleKey, role.getRoleKey())
                .ne(RolePO::getRoleId, role.getRoleId());
        Long keyCount = roleMapper.selectCount(queryWrapper);
        if (keyCount > 0) {
            throw new RuntimeException();
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getRoleName, role.getRoleName())
                .ne(RolePO::getRoleId, role.getRoleId());
        Long nameCount = roleMapper.selectCount(queryWrapper);
        if (nameCount > 0) {
            throw new RuntimeException();
        }
        return true;
    }

    public int updateRole(RolePO role) {
        int update = roleMapper.updateById(role);
        return update;


    }

    public int addRole(RolePO role) {
        int insert = roleMapper.insert(role);
        return insert;
    }

    public void updateRoleMenu(RoleMenuVO roleMenuVO) {
        LambdaQueryWrapper<RoleMenuPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenuPO::getRoleId, roleMenuVO.getRoleId());
        roleMenuMapper.delete(queryWrapper);

        for (Long menuId : roleMenuVO.getMenuIds()) {
            RoleMenuPO roleMenuPO = new RoleMenuPO();
            roleMenuPO.setRoleId(roleMenuVO.getRoleId());
            roleMenuPO.setMenuId(menuId);
            roleMenuMapper.insert(roleMenuPO);
        }
    }

    public void deleteRoleById(Long roleId) {
        LambdaQueryWrapper<UserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPOLambdaQueryWrapper.eq(UserPO::getRoleId, roleId);
        Long count = userMapper.selectCount(userPOLambdaQueryWrapper);
        if (count > 0) {
            throw new RuntimeException(StrUtil.format("roleID:'{}' 已分配,不能删除", roleId));
        }
        LambdaQueryWrapper<RoleMenuPO> roleMenuPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuPOLambdaQueryWrapper.eq(RoleMenuPO::getRoleId, roleId);
        roleMenuMapper.delete(roleMenuPOLambdaQueryWrapper);
        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getRoleId, roleId);
        roleMapper.delete(queryWrapper);
    }
}
