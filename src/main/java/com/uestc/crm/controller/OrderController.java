package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.crm.pojo.OrderPO;
import com.uestc.crm.query.OrderListQuery;
import com.uestc.crm.service.impl.OrderServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import com.uestc.crm.vo.OrderAddVO;
import com.uestc.crm.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:52
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping("/add")
    public Result addOrder(@RequestBody OrderAddVO orderAddVO) {
        try {
            orderServiceImpl.addOrder(orderAddVO);

        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateOrder(@RequestBody OrderPO orderPO) {
        try {
            orderServiceImpl.updateOrderById(orderPO);
        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/list")
    public Result<IPage<OrderVO>> listOrder(@RequestBody OrderListQuery query) {
        IPage<OrderVO> orderPOS;
        try {
            orderPOS = orderServiceImpl.listOrder(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(orderPOS);
    }

    @PostMapping("/my/list")
    public Result<IPage<OrderVO>> listMyOrder(@RequestBody OrderListQuery query) {
        IPage<OrderVO> orderPOS;
        try {
            orderPOS = orderServiceImpl.listMyOrder(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(orderPOS);
    }

    @PostMapping("/delete")
    public Result deleteOrder(@RequestBody String orderId) {
        try {
            orderServiceImpl.deleteOrder(orderId);
        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

}
