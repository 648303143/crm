package com.uestc.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.pojo.OrderPO;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:43
 */
@Repository
public interface OrderMapper extends MPJBaseMapper<OrderPO> {
}
