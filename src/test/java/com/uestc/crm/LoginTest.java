package com.uestc.crm;

import com.uestc.crm.controller.UserController;
import com.uestc.crm.vo.RegisterVO;
import com.uestc.crm.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/9 16:58
 */
@SpringBootTest
public class LoginTest {

    @Autowired
    UserController userController;

    @Test
    public void testRegistry() {
        RegisterVO registerVO = new RegisterVO();
        registerVO.setUsername("huyidao");
        registerVO.setPassword("huyidao");
        registerVO.setCheckPassword("huyidao");
        registerVO.setEmail("huyidao@qq.com");
        registerVO.setPhoneNumber("12324354567");
        Result<Long> result = userController.register(registerVO);
        System.out.println(result);

    }
}
