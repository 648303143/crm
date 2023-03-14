package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.uestc.crm.pojo.BusinessPO;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.query.ListBusinessQuery;
import com.uestc.crm.query.ListCustomerQuery;
import com.uestc.crm.service.impl.BusinessServiceImpl;
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
@RequestMapping("/business")
@CrossOrigin
public class BusinessController {

    @Autowired
    private BusinessServiceImpl businessServiceImpl;

    @PostMapping("/add")
    public Result addBusiness(@RequestBody BusinessPO businessPO) {
        try {
            businessServiceImpl.addBusiness(businessPO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();

    }

    @PostMapping("/update")
    public Result updateBusiness(@RequestBody String businessJson) {
        try {
            Gson gson = new Gson();
            BusinessPO businessPO = gson.fromJson(businessJson, BusinessPO.class);
            businessServiceImpl.updateBusinessById(businessPO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/list")
    public Result<IPage<BusinessPO>> listBusiness(@RequestBody ListBusinessQuery query) {
        IPage<BusinessPO> businessPOS;
        try {
            businessPOS = businessServiceImpl.listBusiness(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(businessPOS);
    }

    @PostMapping("/delete")
    public Result deleteBusiness(@RequestBody String bizId) {
        try {
            businessServiceImpl.deleteBusinessById(bizId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

}
