package com.uestc.crm.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.ClueMapper;
import com.uestc.crm.pojo.CluePO;
import com.uestc.crm.query.ClueListQuery;
import com.uestc.crm.service.ClueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
@Slf4j
public class ClueServiceImpl extends ServiceImpl<ClueMapper, CluePO> implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    public int addClue(CluePO cluePO) {
        long s = System.currentTimeMillis();
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getClueId, cluePO.getClueId());
        if (clueMapper.exists(queryWrapper)) {
            Console.error("线索重复");
            throw new RuntimeException("线索重复");
        }

        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getCustId, cluePO.getCustId())
                .ge(CluePO::getCreateTime, Date.from(LocalDateTime.now().minusHours(24).atZone(ZoneId.systemDefault()).toInstant()));
        int result = 0;
        if (!clueMapper.exists(queryWrapper)) {
            result = clueMapper.insert(cluePO);
            Console.log("插入成功::{}", result);
        }else {
            Console.log("客户已存在");
        }
        long e = System.currentTimeMillis();
        Console.log("service完成,耗时::{}", e-s);
        return result;
    }

    public int updateClueById(CluePO cluePO) {
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getClueId, cluePO.getClueId());
        int update = clueMapper.update(cluePO, queryWrapper);
        return update;
    }

    public IPage<CluePO> listClue(ClueListQuery query) {
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getUsername, query.getUsername())
                .eq(StrUtil.isNotBlank(query.getCustId()), CluePO:: getCustId, query.getCustId())
                .orderByDesc(CluePO::getId);
        Page<CluePO> CluePage = clueMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
        return CluePage;
    }

    public String distributeClue(String username) {
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getUsername, "nobody")
                .last("limit 1");
        CluePO cluePO = clueMapper.selectOne(queryWrapper);
        cluePO.setUsername(username);
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getClueId, cluePO.getClueId());
        clueMapper.update(cluePO, queryWrapper);
        return cluePO.getClueId();
    }
}
