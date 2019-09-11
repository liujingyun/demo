package com.liujy.demo;

import com.liujy.demo.service.PublisherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName publisherServuce
 * @Description
 * @Author jingyun_liu
 * @Date 2019/9/10 11:23
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class publisherTest {
    @Autowired
    private PublisherService publisherService;

    @Test
    public void test(){
        for (int i = 0; i<100;i++){
            publisherService.sendMessage("test"+i);
        }
    }
}
