package com.uestc.crm;

import com.uestc.crm.controller.UserController;
import com.uestc.crm.query.LoginVO;
import com.uestc.crm.query.RegisterVO;
import com.uestc.crm.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
        registerVO.setUsername("linghuchong");
        registerVO.setPassword("linghuchong");
        registerVO.setCheckPassword("linghuchong");
        registerVO.setEmail("linghuchong@qq.com");
        registerVO.setPhoneNumber("52356246");
        Result<Long> result = userController.register(registerVO);
        System.out.println(result);

    }
}
