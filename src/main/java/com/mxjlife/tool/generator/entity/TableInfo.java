package com.mxjlife.tool.generator.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * description: 数据库信息
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 0:12
 */
@Data
public class TableInfo {

    /**
     * 数据库名
     */
    private String database;
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 数据库表注解
     */
    private String comment;
    /**
     * 主键
     */
    private ColumnInfo primary;
    /**
     * 数据库表索引
     */
    private List<IndexInfo> indexs;
    /**
     * 数据库表字段列表
     */
    private List<ColumnInfo> columns;
    /**
     * java 类包路径
     */
    private Set<String> packages;

    @Data
    public static class IndexInfo{
        private String indexName;
        private String column;
        // java 变量名
        private String property;
        private int columnSeq;
        private List<ColumnInfo> columns;
        private boolean uniqueKey;
    }

    @Data
    public static class ColumnInfo{
        private String column;
        // java 变量名
        private String property;
        //首字母大写的变量名
        private String propertyUp;
        private String jdbcType;
        private String Comment;
        private boolean primaryKey;
        private String javaType;
        private String javaPkg;
    }

}
