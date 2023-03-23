package com.uestc.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uestc.crm.mapper.ClueMapper;

import com.uestc.crm.pojo.CluePO;

import com.uestc.crm.query.ListClueQuery;
import com.uestc.crm.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:44
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, CluePO> implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    public int addClue(CluePO cluePO) {
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getClueId, cluePO.getClueId());
        Long count = clueMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException();
        }
        return clueMapper.insert(cluePO);
    }

    public int updateClueById(CluePO cluePO) {
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getClueId, cluePO.getClueId());
        int update = clueMapper.update(cluePO, queryWrapper);
        return update;
    }

    public IPage<CluePO> listClue(ListClueQuery query) {
        LambdaQueryWrapper<CluePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CluePO::getUsername, query.getUsername())
                .eq(StrUtil.isNotBlank(query.getCustId()), CluePO:: getCustId, query.getCustId());
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
