package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author songgt
* @description 针对表【chart(图表数据表)】的数据库操作Service
* @createDate 2024-06-26 13:04:01
*/
public interface ChartService extends IService<Chart> {

    /**
     * 分库分表
     * 将图表原始数据放入新的数据库表中
     * @param csvData
     */
    public void createTableChartExcel(String csvData, Long id);

    /**
     * 分库分表查询
     *
     * @return
     */
    String queryChartDate(long id);
}
