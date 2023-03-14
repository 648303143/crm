package com.uestc.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uestc.crm.pojo.BusinessPO;
import com.uestc.crm.pojo.UserPO;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:43
 */
@Repository
public interface UserMapper extends BaseMapper<UserPO> {
}
