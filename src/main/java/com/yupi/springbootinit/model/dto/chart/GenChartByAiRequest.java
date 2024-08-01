package com.yupi.springbootinit.model.dto.chart;

import java.io.Serializable;
import lombok.Data;

/**
 * @description：数据文件传输请求
 * @author： songgt
 * @create： 2024/7/11 下午9:58
 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     *
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}
