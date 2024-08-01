package com.yupi.springbootinit.model.vo;

import lombok.Data;

/**
 * @description：BI的返回结果
 * @author： songgt
 * @create： 2024/7/13 下午4:28
 */
@Data
public class BiResponse {

    private String genChart;

    private String getResult;

    private Long chartId;
}
