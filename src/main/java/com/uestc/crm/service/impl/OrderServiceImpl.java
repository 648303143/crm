package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.uestc.crm.mapper.OrderMapper;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.pojo.OrderPO;
import com.uestc.crm.query.ListCustomerQuery;
import com.uestc.crm.query.ListOrderQuery;
import com.uestc.crm.service.OrderService;
import com.uestc.crm.vo.OrderVO;
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

    public int deleteOrder(String orderId) {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPO::getOrderId,orderId);
        return orderMapper.delete(queryWrapper);
    }

    public IPage<OrderVO> listOrder(ListOrderQuery query) {
        MPJLambdaWrapper<OrderPO> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.selectAll(OrderPO.class)
                .selectAs(CarPO::getTitle, OrderVO::getCarTitle)
                .leftJoin(CarPO.class, CarPO::getCarId, OrderPO::getCarId);
        if (StrUtil.isNotBlank(query.getOrderId())) {
            queryWrapper.eq(OrderPO::getOrderId, query.getOrderId());
        }
        if (StrUtil.isNotBlank(query.getCustId())) {
            queryWrapper.eq(OrderPO::getCustId, query.getCustId());
        }
        if (StrUtil.isNotBlank(query.getUsername())) {
            queryWrapper.eq(OrderPO::getUsername, query.getUsername());
        }
        if (query.getState() != null) {
            queryWrapper.eq(OrderPO::getState, query.getState());
        }
        IPage<OrderVO> page = orderMapper.selectJoinPage(new Page<OrderVO>(query.getCurrent(), query.getSize()), OrderVO.class, queryWrapper);
        return page;
    }
}
