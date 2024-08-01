package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.mapper.ChartMapper;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.utils.TableCreationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author songgt
* @description 针对表【chart(图表数据表)】的数据库操作Service实现
* @createDate 2024-06-26 13:04:01
*/
@Slf4j
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    @Resource
    ChartMapper chartMapper;

    @Autowired
    private TableCreationUtils tableCreationUtils;

    @Override
    public void createTableChartExcel(String csvData, Long id) {
        //提取属性名
        //获取索引
        int index = csvData.indexOf("\n");
        //切割出所有属性名
        String colum = csvData.substring(0,index);
        //通过,分割属性名放入数组
        String[] colums = colum.split(",");
        //获得所有属性值
        String data = csvData.substring(index).trim();
        //分割出所有属性值放入数组
        String[] split_data = data.split("\n");
        //表名
        String tableName = String.format("chart_%s", id);

        //建表
        tableCreationUtils.createTable(tableName, colums);
        //插入
        tableCreationUtils.Dynamicinsert(tableName, colums, split_data);
    }

    /**
     * 查询分库分表数据
     * @param id
     * @return
     */
    @Override
    public String   queryChartDate(long id) {
        String chartId = String.valueOf(id);
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
                userInput.delete(userInput.length() - 1, userInput.length()); // 删除最后一个逗号
                userInput.append("\n");
            }
//            System.out.println(userInput.toString());
            return userInput.toString();
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "图表原始数据不存在");
        }
    }
}




