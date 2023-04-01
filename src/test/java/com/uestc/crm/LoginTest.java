package com.uestc.crm;

import com.uestc.crm.controller.LoginController;
import com.uestc.crm.util.Result;
import com.uestc.crm.vo.RegisterVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/9 16:58
 */
@SpringBootTest
public class LoginTest {

    @Autowired
    LoginController loginController;

    @Test
    public void testRegistry() {
        RegisterVO registerVO = new RegisterVO();
        registerVO.setUsername("huyidao");
        registerVO.setPassword("huyidao");
        registerVO.setCheckPassword("huyidao");
        registerVO.setEmail("huyidao@qq.com");
        registerVO.setPhoneNumber("12324354567");
        Result<Long> result = loginController.register(registerVO);
        System.out.println(result);
    }

    @Test
    public void test_BCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(passwordEncoder.encode("linghuchong"));
        System.out.println(passwordEncoder.encode("tianboguang"));
        System.out.println(passwordEncoder.encode("zhangsanfeng"));
        System.out.println(passwordEncoder.encode("huyidao"));

    }
}
