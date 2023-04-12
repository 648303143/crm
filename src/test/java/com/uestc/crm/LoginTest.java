package com.uestc.crm;

import com.uestc.crm.controller.LoginController;
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
    public void test_BCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(passwordEncoder.encode("linghuchong"));
        System.out.println(passwordEncoder.encode("tianboguang"));
        System.out.println(passwordEncoder.encode("zhangsanfeng"));
        System.out.println(passwordEncoder.encode("huyidao"));

    }
}
