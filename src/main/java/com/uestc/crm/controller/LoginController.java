package com.uestc.crm.controller;

import com.uestc.crm.model.LoginUser;
import com.uestc.crm.pojo.MenuPO;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.service.impl.LoginService;
import com.uestc.crm.service.impl.MenuServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import com.uestc.crm.util.SecurityUtils;
import com.uestc.crm.vo.LoginVO;
import com.uestc.crm.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/31 22:29
 */
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuServiceImpl menuService;

    @PostMapping("/login")
    public Result<String> Login(@RequestBody LoginVO loginVO) {
        if (loginVO == null) {
            return Result.error(ExceptionCodeEnum.EMPTY_PARAM);
        }
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        String token;
        try {
            token = loginService.userDoLogin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }
        return Result.success(token);
    }

    @PostMapping("/userinfo")
    public Result<UserInfoVO> getUserInfo() {
        UserInfoVO userInfo = new UserInfoVO();
        try {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            userInfo.setUser(loginUser.getUser());
            userInfo.setPermissions(loginUser.getPermissions());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR, e.getMessage());
        }

        return Result.success(userInfo);
    }

    @PostMapping("/getRouters")
    public Result getRouters() {
        UserPO user = SecurityUtils.getLoginUser().getUser();
        List<MenuPO> menus = menuService.getMenuTreeByRoleId(user.getRoleId());
        return Result.success(menuService.buildMenus(menus));
    }

}
