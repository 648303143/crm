package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.crm.common.CacheConstants;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.query.CustomerListQuery;
import com.uestc.crm.service.impl.CustomerServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
    public Result<IPage<CustomerPO>> listCustomer(@RequestBody CustomerListQuery query) {
        IPage<CustomerPO> customerPOS;
        try {
            customerPOS = customerServiceImpl.listCustomer(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(customerPOS);
    }

    @PostMapping("/delete")
    public Result deleteCustomer(@RequestBody String custId) {
        try {
            customerServiceImpl.deleteCustomerById(custId);
        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }


    @PostMapping("/getById")
    public Result getCustomerById(@RequestBody String custId) {
        CustomerPO customerPO;
        try {
            String key = CacheConstants.CUSTOMER_QUERY_KEY + custId;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                customerPO = (CustomerPO) redisTemplate.opsForValue().get(key);
            } else {
                LambdaQueryWrapper<CustomerPO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CustomerPO::getCustId, custId);
                customerPO = customerServiceImpl.getOne(queryWrapper);
                redisTemplate.opsForValue().set(key, customerPO);
            }

        } catch (Exception e) {
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(customerPO);
    }

}
