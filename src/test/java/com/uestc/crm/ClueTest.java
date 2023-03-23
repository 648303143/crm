package com.uestc.crm;

import cn.hutool.core.lang.UUID;
import com.uestc.crm.mapper.ClueMapper;
import com.uestc.crm.pojo.CluePO;
import com.uestc.crm.service.impl.ClueServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/22 17:49
 */

@SpringBootTest
public class ClueTest {

    @Autowired
    ClueMapper clueMapper;

    @Autowired
    ClueServiceImpl clueService;

    @Test
    public void test_createClue() {
        ArrayList<String> list = new ArrayList<>();
        list.add("4087a0db-70b5-468a-bb11-e18df9aa0ce6");
        list.add("a8088213-a6df-4720-8acd-206a67d57b07");
        list.add("90b7c126-a9bc-488f-b3c2-afc85c3742cb");
        list.add("2e017099-3ee8-4f7f-96d0-5f671cecff69");
        list.add("2669afb2-7cec-4bff-a5ec-06c2e4148fd3");
        list.add("b77f1fb6-9d55-490c-8c64-b47f7114b775");
        list.add("e4a970bf-5cf5-46b3-8faf-8836dd6dc0f0");
        list.add("9c14bff0-01d7-43d8-866d-02d4aed573df");
        list.add("ce8b5c82-8eff-4d23-8f4a-7f3338d8c60c");
        list.add("dfd0d2d1-7ede-4f94-94d4-019e19e2c473");
        list.add("7e4e55ff-2a8c-4d4a-adb0-04f4aea740cb");
        for (int i = 0; i < 20; i++) {
            CluePO cluePO = new CluePO();
            cluePO.setClueId(UUID.fastUUID().toString());
            cluePO.setCustId(list.get(i % 11));
            cluePO.setClueWay((i % 3) + 1);
            cluePO.setClueType((i % 2) + 1);
            clueMapper.insert(cluePO);
        }

    }

    @Test
    public void test_distributeClue() {
        String huyidao = clueService.distributeClue("huyidao");
        System.out.println(huyidao);
    }
}
