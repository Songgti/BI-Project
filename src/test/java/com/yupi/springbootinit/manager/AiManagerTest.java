package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description：TODO
 * @author： songgt
 * @create： 2024/7/13 下午3:09
 */
@SpringBootTest
class AiManagerTest {

    @Resource
    private AiManager aiManager;

    @Test
    void doChat() {
//        String s = aiManager.doChat("分析需求：分析用户增长趋势\n日期,用户人数,1,10,2,20,3,30");
//        System.out.println(s);
    }
}
