package com.uestc.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.RoleMenuMapper;
import com.uestc.crm.pojo.RoleMenuPO;
import com.uestc.crm.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuPO> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
}
