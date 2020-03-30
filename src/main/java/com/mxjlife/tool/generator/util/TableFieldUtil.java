package com.mxjlife.tool.generator.util;

import com.mxjlife.tool.generator.common.FieldDataType;

/**
 * description: 数据库字段转java字段
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 12:08
 */
public class TableFieldUtil {

    /**
     * 将数据库中的名称转换为小驼峰格式的数据
     */
    public static String toCamel(String name) {
        String[] fields=name.split("_");
        StringBuilder sb=new StringBuilder();
        sb.append(fields[0].substring(0, 1).toLowerCase());
        sb.append(fields[0].substring(1));
        if(fields.length < 2){
            return sb.toString();
        }
        for (int i = 1; i < fields.length; i++) {
            String s = fields[i];
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s.substring(1));
        }
        return sb.toString();
    }

    /**
     * 名称转换为大驼峰式
     */
    public static String toUpperCamel(String name){
        String s = toCamel(name);
        StringBuilder sb=new StringBuilder();
        sb.append(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1));
        return sb.toString();
    }


    /**
     * 将数据库类型转换为java类型
     */
    public static FieldDataType TableType2JavaType(String jdbcType){
        String t = jdbcType.toLowerCase();
        if (t.contains("char")) {
            return FieldDataType.STRING;
        } else if (t.contains("bigint")) {
            return FieldDataType.LONG;
        } else if (t.contains("int")) {
            return FieldDataType.INTEGER;
        } else if (t.contains("text")) {
            return FieldDataType.STRING;
        } else if (t.contains("bit")) {
            return FieldDataType.BOOLEAN;
        } else if (t.contains("decimal")) {
            return FieldDataType.BIG_DECIMAL;
        } else if (t.contains("clob")) {
            return FieldDataType.STRING;
        } else if (t.contains("blob")) {
            return FieldDataType.BYTE_ARRAY;
        } else if (t.contains("binary")) {
            return FieldDataType.BYTE_ARRAY;
        } else if (t.contains("float")) {
            return FieldDataType.FLOAT;
        } else if (t.contains("double")) {
            return FieldDataType.DOUBLE;
        } else if (t.contains("json") || t.contains("enum")) {
            return FieldDataType.STRING;
        } else if (t.contains("date") || t.contains("time") || t.contains("year")) {
            return FieldDataType.DATE;
        }
        return FieldDataType.STRING;
    }
}
