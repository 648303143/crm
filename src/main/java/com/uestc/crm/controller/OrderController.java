package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.pojo.OrderPO;
import com.uestc.crm.query.ListCustomerQuery;
import com.uestc.crm.query.ListOrderQuery;
import com.uestc.crm.service.impl.OrderServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public Result addOrder(@RequestBody String orderJson) {
        try {
            Gson gson = new Gson();
            OrderPO orderPO = gson.fromJson(orderJson, OrderPO.class);
            orderServiceImpl.addOrder(orderPO);

        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateOrder(@RequestBody String orderJson) {
        try {
            Gson gson = new Gson();
            OrderPO orderPO = gson.fromJson(orderJson, OrderPO.class);
            orderServiceImpl.updateOrderById(orderPO);
        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/list")
    public Result<IPage<OrderPO>> listOrder(@RequestBody String listQuery) {
        IPage<OrderPO> orderPOS;
        try {
            Gson gson = new Gson();
            ListOrderQuery query = gson.fromJson(listQuery, ListOrderQuery.class);
            orderPOS = orderServiceImpl.listOrder(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(orderPOS);
    }


}
