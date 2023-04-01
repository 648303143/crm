package com.uestc.crm.controller;

import cn.hutool.core.util.StrUtil;
import com.uestc.crm.model.LoginUser;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.service.impl.MenuServiceImpl;
import com.uestc.crm.service.impl.RoleServiceImpl;
import com.uestc.crm.util.SecurityUtils;
import com.uestc.crm.vo.LoginVO;
import com.uestc.crm.vo.RegisterVO;
import com.uestc.crm.service.impl.UserServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import com.uestc.crm.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/6 18:55
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {



}
