package com.yupi.springbootinit.mapper;
import java.util.List;
import java.util.Map;

import com.yupi.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author songgt
* @description 针对表【chart(图表数据表)】的数据库操作Mapper
* @createDate 2024-06-26 13:04:01
* @Entity com.yupi.springbootinit.model.entity.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    List<Map<String, Object>> queryChartDate(String querySql);
}




