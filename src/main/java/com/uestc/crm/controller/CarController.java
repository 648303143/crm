package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.query.ListCarQuery;
import com.uestc.crm.service.impl.CarServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Result addCar(CarPO carPO, @RequestParam(value = "file", required = false) MultipartFile[] files) {
        try {
            carServiceImpl.addCar(carPO, files);
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

    @PostMapping("/brand/list")
    public Result<List<String>> listBrand() {
        List<String> list;
        try {
            list = carServiceImpl.listBrand();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(list);
    }


    @PostMapping("/series/getByBrand")
    public Result<List<String>> getSeriesListByBrand(@RequestBody String brand) {
        List<String> list;
        try {
            list = carServiceImpl.getSeriesListByBrand(brand);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(list);
    }

    @PostMapping("/delete")
    public Result deleteCar(@RequestBody String carId) {
        try {
            LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CarPO::getCarId, carId);
            carServiceImpl.remove(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success();
    }

    @PostMapping("/getPictures")
    public Result<List<String>> getPicturesByCarId(@RequestBody String carId) {
        List<String> pictures;
        try {
            pictures = carServiceImpl.getPicturesByCarId(carId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(pictures);
    }

}
