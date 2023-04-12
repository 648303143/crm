package com.uestc.crm.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uestc.crm.mapper.CustomerMapper;
import com.uestc.crm.pojo.CustomerPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/10 15:01
 */
@Configuration
public class ConstantsConfig {

    @Autowired
    private CustomerMapper customerMapper;

    @Bean
    public List<String> customerIds() {
        List<String> list = customerMapper.selectList(new QueryWrapper<>()).stream().map(CustomerPO::getCustId).collect(Collectors.toList());
        return list;
    }
}
