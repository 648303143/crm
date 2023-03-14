package com.uestc.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.CarMapper;
import com.uestc.crm.pojo.BusinessPO;
import com.uestc.crm.pojo.CarPO;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.query.ListBusinessQuery;
import com.uestc.crm.query.ListCarQuery;
import com.uestc.crm.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, CarPO> implements CarService {

    @Autowired
    private CarMapper carMapper;

    public int addCar(CarPO carPO) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPO::getCarId, carPO.getCarId());
        Long count = carMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException();
        }
        return carMapper.insert(carPO);
    }

    public int updateCarById(CarPO carPO) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPO::getCarId, carPO.getCarId());
        int update = carMapper.update(carPO, queryWrapper);
        return update;
    }

    public CarPO getCarById(String carId) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPO::getCarId,carId);
        CarPO carPO = carMapper.selectOne(queryWrapper);
        return carPO;
    }

    public Page<CarPO> listCar(ListCarQuery query) {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
        Page<CarPO> page = carMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return page;
    }
}
