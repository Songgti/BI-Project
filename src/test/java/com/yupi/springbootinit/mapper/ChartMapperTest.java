package com.yupi.springbootinit.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description：TODO
 * @author： songgt
 * @create： 2024/7/20 下午6:05
 */
@SpringBootTest
class ChartMapperTest {

    @Resource
    private ChartMapper chartMapper;

    @Test
    void queryChartData() {
        String chartId = "1818303132599918594";
        String querySql = String.format("select * from chart_%s", chartId);
        List<Map<String, Object>> stringObjectMap = chartMapper.queryChartDate(querySql);
        StringBuffer userInput = new StringBuffer();
        if (!stringObjectMap.isEmpty()) {
            Map<String, Object> firstEntry = stringObjectMap.get(0);
            Set<String> keys = firstEntry.keySet();

            for (String key : keys) {
                userInput.append(key).append(",");
            }

            userInput.delete(userInput.length() - 1, userInput.length());
            userInput.append("\n");

            for (Map<String, Object> entry : stringObjectMap) {
                for (String key : keys) {
                    Object value = entry.get(key);
                    userInput.append(value == null ? "" : value).append(",");
                }
                userInput.delete(userInput.length() - 1, userInput.length()); // 删除最后一个逗号和空格
                userInput.append("\n");
            }
            System.out.println(userInput.toString());
        } else {
            System.out.println("stringObjectMap is empty.");
        }
    }
}
