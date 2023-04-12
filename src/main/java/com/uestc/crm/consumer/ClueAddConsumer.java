package com.uestc.crm.consumer;

import com.google.gson.Gson;
import com.uestc.crm.pojo.CluePO;
import com.uestc.crm.service.impl.ClueServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangqingyang
 * @create 2023-04-2023/4/10 19:17
 */

@Component
@RabbitListener(queuesToDeclare = @Queue("clueAddTopic"))
@Slf4j
public class ClueAddConsumer {

    @Autowired
    private ClueServiceImpl clueService;

    @RabbitHandler
    public void process(Map map) {
        log.info("开始消费线索入库消息,入参::{}", map);
        long s = System.currentTimeMillis();
        String msg = (String) map.get("msg");
        Gson gson = new Gson();
        CluePO cluePO = gson.fromJson(msg, CluePO.class);
        clueService.addClue(cluePO);
        long e = System.currentTimeMillis();
        log.info("消费完成,用时::{}", e-s);
    }
}
