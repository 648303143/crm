package com.uestc.crm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.uestc.crm.pojo.UserPO;
import com.uestc.crm.query.UserListQuery;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 18:09
 */

public interface UserService extends IService<UserPO> {

    UserPO getUserByUsername(String username);

    IPage<UserPO> listUser(UserListQuery query);
}