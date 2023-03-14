package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.query.ListCustomerQuery;
import com.uestc.crm.service.impl.CustomerServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:52
 */
@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @PostMapping("/add")
    public Result addCustomer(@RequestBody CustomerPO customerPO) {
        try {
            customerServiceImpl.addCustomer(customerPO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateCustomer(@RequestBody CustomerPO customerPO) {
        try {
            customerServiceImpl.updateCustomerById(customerPO);
        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/list")
    public Result<IPage<CustomerPO>> listCustomer(@RequestBody ListCustomerQuery query) {
        IPage<CustomerPO> customerPOS;
        try {
            customerPOS = customerServiceImpl.listCustomer(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(customerPOS);
    }


}
