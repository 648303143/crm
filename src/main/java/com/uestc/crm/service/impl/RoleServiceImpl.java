package com.uestc.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.RoleMapper;
import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public String getRoleById(Long roleId) {
        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePO::getRoleId, roleId);
        RolePO rolePO = roleMapper.selectOne(queryWrapper);
        return rolePO.getRoleKey();
    }
}
