package com;

import com.domain.User;
import com.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {
        try {
            logger.info("开始执行sql.....");
            List<User> list = userDao.findAll();
            logger.info("sql执行完毕...");
        } catch (Exception e) {
            logger.error("sql执行过程中出现异常...",e);
        }
    }

}
