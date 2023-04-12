package com.uestc.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.query.RoleListQuery;
import com.uestc.crm.service.impl.RoleServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import com.uestc.crm.vo.RoleMenuVO;
import com.uestc.crm.vo.RoleStatusUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/2 22:04
 */

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping("/list")
    public Result<Page<RolePO>> list(RoleListQuery query) {
        Page<RolePO> page = roleService.listRole(query);
        return Result.success(page);
    }

    @PostMapping("/get")
    public Result<RolePO> getRole(@RequestBody Long roleId) {
        RolePO role = roleService.getRoleById(roleId);
        return Result.success(role);
    }

    @PostMapping("/add")
    public Result addRole(@RequestBody RolePO role) {
        try {
            roleService.checkUnique(role);
            roleService.addRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR,e.getMessage());
        }
        return Result.success();
    }

    @PostMapping("/delete")
    public Result deleteRole(@RequestBody Long roleId) {
        try {
            roleService.deleteRoleById(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR,e.getMessage());
        }
        return Result.success();
    }

    /**
     * 修改保存角色
     */
    @PostMapping("/update")
    public Result updateRole(@RequestBody RolePO role) {
        try {
            roleService.checkUnique(role);
            roleService.updateRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR,"修改角色'" + role.getRoleName() + "'失败，请联系管理员");
        }
        return Result.success();
    }


    @PostMapping("/updateRoleMenu")
    public Result updateRoleMenu(@RequestBody RoleMenuVO roleMenuVO) {
        try {
            roleService.updateRoleMenu(roleMenuVO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/updateStatus")
    public Result updateRoleStatus(@RequestBody RoleStatusUpdateVO roleStatusUpdateVO) {
        try {
            RolePO role = new RolePO();
            role.setRoleId(roleStatusUpdateVO.getRoleId());
            role.setStatus(roleStatusUpdateVO.getStatus());
            roleService.updateRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

}
