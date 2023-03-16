package com.uestc.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.CustomerMapper;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.query.ListCustomerQuery;
import com.uestc.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerPO> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public int addCustomer(CustomerPO customerPO) {
        LambdaQueryWrapper<CustomerPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerPO::getCustId, customerPO.getCustId());
        Long count = customerMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException();
        }
        return customerMapper.insert(customerPO);
    }

    public int updateCustomerById(CustomerPO customerPO) {
        LambdaQueryWrapper<CustomerPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerPO::getCustId, customerPO.getCustId());
        int update = customerMapper.update(customerPO, queryWrapper);
        return update;
    }

    public IPage<CustomerPO> listCustomer(ListCustomerQuery query) {
        LambdaQueryWrapper<CustomerPO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getCustId())){
            queryWrapper.eq(CustomerPO::getCustId, query.getCustId());
        }
        if (StringUtils.isNotEmpty(query.getNickname())){
            queryWrapper.eq(CustomerPO::getNickname, query.getNickname());
        }
        if (StringUtils.isNotEmpty(query.getName())){
            queryWrapper.eq(CustomerPO::getName, query.getName());
        }
        if (StringUtils.isNotEmpty(query.getPhone())){
            queryWrapper.eq(CustomerPO::getPhone, query.getPhone());
        }
        Page<CustomerPO> customerPage = customerMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return customerPage;
    }
    public int deleteCustomerById(String custId) {
        LambdaQueryWrapper<CustomerPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerPO::getCustId, custId);
        int delete = customerMapper.delete(queryWrapper);
        return delete;
    }

}
