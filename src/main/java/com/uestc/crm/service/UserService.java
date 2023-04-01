package com.uestc.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uestc.crm.pojo.UserPO;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 18:09
 */

public interface UserService extends IService<UserPO> {

    UserPO getUserByUsername(String username);
}