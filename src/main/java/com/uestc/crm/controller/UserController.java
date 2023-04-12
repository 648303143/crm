package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.crm.pojo.RolePO;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.query.UserListQuery;
import com.uestc.crm.service.impl.RoleServiceImpl;
import com.uestc.crm.service.impl.UserServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import com.uestc.crm.util.SecurityUtils;
import com.uestc.crm.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/6 18:55
 */

@RestController
@RequestMapping("/system/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;


    @PostMapping("/list")
    public Result<IPage<UserPO>> listUser(@RequestBody UserListQuery query) {
        IPage<UserPO> users;
        try {
            users = userService.listUser(query);
            for (UserPO user : users.getRecords()) {
                user.setRoleName(roleService.getRoleById(user.getRoleId()).getRoleName());
            }
        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success(users);
    }


    @PostMapping("/get")
    public Result getUser(@RequestBody(required = false) String username) {
        UserVO res = new UserVO();
        try {

            List<RolePO> roles = roleService.getAllRole();
            res.setRoles(roles);
            if (username != null) {
                UserPO user = userService.getUserByUsername(username);
                res.setUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success(res);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody UserPO user) {
        try {
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody UserPO user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success();
    }

    @PostMapping("/delete")
    public Result deleteUser(@RequestBody String username) {
        try {
            if (SecurityUtils.getLoginUser().getUser().getUsername().equals(username)) {
                return Result.error(ExceptionCodeEnum.ERROR, "不能删除自己");
            }
            userService.deleteUser(username);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success();
    }


}
