package com.uestc.crm;

import com.google.gson.Gson;
import com.uestc.crm.mapper.CustomerMapper;
import com.uestc.crm.pojo.CustomerPO;
import com.uestc.crm.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/28 20:17
 */
@SpringBootTest
public class CustomerTest {

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    public void test_update() {
        String json = "{\"id\":1,\"custId\":\"E98A9068-4871-81D0-727D-A7ED413E117B\",\"userName\":\"爷傲灬奈我何1\",\"name\":\"张大炮\",\"sex\":1,\"phone\":\"18295592077\",\"city\":\"山东烟台\",\"budget\":\"20到30w\",\"failReason\":\"资金不到位,再考虑考虑\",\"intentionLevel\":3,\"createTime\":\"2023-02-09T11:30:22.000+00:00\",\"updateTime\":null,\"isSuccess\":true}";
        Gson gson = new Gson();
        CustomerPO customerPO = gson.fromJson(json, CustomerPO.class);
        customerService.updateCustomerById(customerPO);

    }
}
