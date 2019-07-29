package com.example.data;

import com.example.data.controller.DataUserApiController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataApplicationTests {

    @Autowired
    private DataUserApiController dataUserApiController;
    @Test
    public void contextLoads() {
//        dataUserApiController.selectName("admin");
    }

}
