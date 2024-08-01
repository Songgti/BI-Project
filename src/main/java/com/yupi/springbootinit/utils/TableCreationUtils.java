package com.yupi.springbootinit.utils;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.ThrowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @description：TODO
 * @author： songgt
 * @create： 2024/7/21 下午3:53
 */
@Component
public class TableCreationUtils {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TableCreationUtils(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 动态构建sql建表语句并执行
     * @param tableName 表名字符串
     * @param columnNames 属性名数组
     */
    public void createTable(String tableName, String[] columnNames) {
        String sql = "CREATE TABLE " + tableName + "(";
        for (int i = 0; i < columnNames.length; i++) {
            sql += columnNames[i] + " varchar(255)";
            if (i < columnNames.length - 1) {
                sql += ",";
            }
        }
        sql += ")";
        jdbcTemplate.update(sql);
    }

    /**
     * 动态构建sql插入语句并执行
     * @param tableName 表名字符串
     * @param columnName 属性名数组
     * @param rowData 属性值数组
     */
    public void Dynamicinsert(String tableName, String[] columnName,String[] rowData){
        //判断属性值是否为空
        ThrowUtils.throwIf(rowData == null || rowData.length == 0, ErrorCode.PARAMS_ERROR,"列表数据为空");

        for (int i = 0; i < rowData.length; i++) {
            //获取每行元素,以,切割
            String[] row = rowData[i].split(",");
            //构建占位符
            StringBuilder columnNames = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();
            for (int j = 0; j<row.length; j++) {
                if (j>0){
                    columnNames.append(",");
                    placeholders.append(",");
                }
                columnNames.append(columnName[j]);
                placeholders.append("?");
            }
            String sql = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + placeholders + ")";
            jdbcTemplate.update(sql,row);
        }

    }
}
