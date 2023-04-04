package com.uestc.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.query.RoleListQuery;


/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 18:09
 */

public interface RoleService extends IService<RolePO>{

    String getRoleKeyById(Long roleId);

    RolePO getRoleById(Long roleId);

    Page<RolePO> listRole(RoleListQuery query);

    boolean checkUnique(RolePO role);
}
