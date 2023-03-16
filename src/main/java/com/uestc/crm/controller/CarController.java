package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.uestc.crm.pojo.BusinessPO;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.query.ListBusinessQuery;
import com.uestc.crm.query.ListCarQuery;
import com.uestc.crm.service.impl.CarServiceImpl;
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
@RequestMapping("/car")
@CrossOrigin
public class CarController {

    @Autowired
    private CarServiceImpl carServiceImpl;

    @PostMapping("/add")
    public Result addCar(@RequestBody CarPO carPO) {
        try {
            carServiceImpl.addCar(carPO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();

    }

    @PostMapping("/update")
    public Result updateCar(@RequestBody CarPO carPO) {
        try {
            carServiceImpl.updateCarById(carPO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/list")
    public Result<IPage<CarPO>> listCar(@RequestBody ListCarQuery query) {
        IPage<CarPO> carPOS;
        try {

            carPOS = carServiceImpl.listCar(query);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(carPOS);
    }


}
