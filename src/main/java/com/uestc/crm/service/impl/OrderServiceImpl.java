package com.uestc.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.OrderMapper;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.pojo.OrderPO;
import com.uestc.crm.query.ListCustomerQuery;
import com.uestc.crm.query.ListOrderQuery;
import com.uestc.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPO> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public int addOrder(OrderPO orderPO) {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPO::getOrderId, orderPO.getOrderId());
        Long count = orderMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException();
        }
        return orderMapper.insert(orderPO);
    }

    public int updateOrderById(OrderPO orderPO) {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPO::getOrderId, orderPO.getOrderId());
        int update = orderMapper.update(orderPO, queryWrapper);
        return update;
    }

    public OrderPO getOrderById(String orderId) {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPO::getOrderId,orderId);
        OrderPO orderPO = orderMapper.selectOne(queryWrapper);
        return orderPO;
    }

    public IPage<OrderPO> listOrder(ListOrderQuery query) {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        Page<OrderPO> page = orderMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return page;
    }
}
