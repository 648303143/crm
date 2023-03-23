package com.uestc.crm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.crm.pojo.CluePO;
import com.uestc.crm.query.ListClueQuery;
import com.uestc.crm.service.impl.ClueServiceImpl;
import com.uestc.crm.util.ExceptionCodeEnum;
import com.uestc.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/9 18:52
 */
@RestController
@RequestMapping("/clue")
@CrossOrigin
public class ClueController {

    @Autowired
    private ClueServiceImpl clueServiceImpl;

    @PostMapping("/list")
    public Result<IPage<CluePO>> listMyClue(@RequestBody ListClueQuery query) {
        IPage<CluePO> cluePOS;
        try {
            cluePOS = clueServiceImpl.listClue(query);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(cluePOS);
    }

    @PostMapping("/assignment/distribute")
    public Result<String> distributeClue(@RequestBody String username) {
        String clueId;
        try {
            clueId = clueServiceImpl.distributeClue(username);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ExceptionCodeEnum.ERROR);
        }
        return Result.success(clueId);
    }


}
