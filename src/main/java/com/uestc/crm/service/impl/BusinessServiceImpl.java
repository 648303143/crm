package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.BusinessMapper;
import com.uestc.crm.pojo.BusinessPO;
import com.uestc.crm.query.BusinessListQuery;
import com.uestc.crm.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, BusinessPO> implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    public int addBusiness(BusinessPO businessPO) {
        LambdaQueryWrapper<BusinessPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusinessPO::getBizId, businessPO.getBizId());
        Long count = businessMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException();
        }
        return businessMapper.insert(businessPO);
    }

    public int updateBusinessById(BusinessPO businessPO) {
        LambdaQueryWrapper<BusinessPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusinessPO::getBizId, businessPO.getBizId());
        int update = businessMapper.update(businessPO, queryWrapper);
        return update;
    }

    public Page<BusinessPO> listBusiness(BusinessListQuery query) {
        LambdaQueryWrapper<BusinessPO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getBizId())) {
            queryWrapper.eq(BusinessPO::getBizId, query.getBizId());
        }
        if (query.getType() != null) {
            queryWrapper.eq(BusinessPO::getType, query.getType());
        }
        Page<BusinessPO> page = businessMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return page;
    }

    public int deleteBusinessById(String bizId) {
        LambdaQueryWrapper<BusinessPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusinessPO::getBizId, bizId);
        int delete = businessMapper.delete(queryWrapper);
        return delete;
    }
}
