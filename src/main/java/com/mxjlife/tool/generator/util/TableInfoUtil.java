package com.mxjlife.tool.generator.util;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import com.mxjlife.tool.generator.entity.BasicInfo;
import com.mxjlife.tool.generator.entity.TableInfo;

/**
 * description: 链接数据库并获取表信息
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 10:08
 */
public class TableInfoUtil {

    public static TableInfo getTableInfo(BasicInfo info){
        String dirverClassName = info.getDirverClassName();
        String url = info.getDbUrl();
        String username = info.getDbUsername();
        String password = info.getDbPassword();
        Connection conn = null;
        //sql
        TableInfo tableInfo = new TableInfo();
        try {
            Class.forName(dirverClassName);
            conn = DriverManager.getConnection(url, username, password);
            tableInfo.setTableName(info.getTableName());
            tableInfo.setDatabase(info.getDatabase());
            getTableInfo(conn, tableInfo);
            getColumns(conn, tableInfo);
            getIndexs(conn, tableInfo);
            // 合并索引中重复的组合索引
            mergeIndex(tableInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("自动生成实体类错误："+e.getMessage());
        } finally {
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return tableInfo;
    }

    /**
     * 获取表字段信息
     */
    private static void getColumns(Connection conn, TableInfo tableInfo) throws Exception{
        String sql=String.format("select * from information_schema.columns where table_schema='%s' and TABLE_NAME='%s';", tableInfo.getDatabase(), tableInfo.getTableName()) ;
        PreparedStatement pstemt = conn.prepareStatement(sql);
        ResultSet rs = pstemt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<TableInfo.ColumnInfo> columns = new ArrayList<>();
        while (rs.next()) {
            TableInfo.ColumnInfo column = new TableInfo.ColumnInfo();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < columnCount; i++) {
                //获取列名
                String name = rsmd.getColumnName(i+1);
                //获取列值
                String value = rs.getString(i+1);
                map.put(name.toUpperCase(), value);
            }
            column.setColumn(map.get("COLUMN_NAME"));
            column.setProperty(TableFieldUtil.toCamel(column.getColumn()));
            column.setPropertyUp(TableFieldUtil.toUpperCamel(TableFieldUtil.toCamel(column.getColumn())));
            column.setComment(map.get("COLUMN_COMMENT"));
            column.setJdbcType(map.get("DATA_TYPE"));
            column.setJavaType(TableFieldUtil.TableType2JavaType(column.getJdbcType()).getType());
            column.setJavaPkg(TableFieldUtil.TableType2JavaType(column.getJdbcType()).getPkg());
            if(Objects.equals("PRI", map.get("COLUMN_KEY"))){
                column.setPrimaryKey(true);
                tableInfo.setPrimary(column);
            }
            columns.add(column);
        }

        tableInfo.setColumns(columns);
        Set<String> pkgs = columns.stream().map(TableInfo.ColumnInfo::getJavaPkg).collect(Collectors.toSet());
        tableInfo.setPackages(pkgs);
        pstemt.close();
        rs.close();
    }

    /**
     * 获取表索引信息
     */
    private static void getIndexs(Connection conn, TableInfo tableInfo) throws Exception{
        String sql=String.format("show index FROM %s.%s", tableInfo.getDatabase(), tableInfo.getTableName()) ;
        PreparedStatement pstemt = conn.prepareStatement(sql);
        ResultSet rs = pstemt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<TableInfo.IndexInfo> indexs = new ArrayList<>();
        while (rs.next()) {
            TableInfo.IndexInfo index = new TableInfo.IndexInfo();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < columnCount; i++) {
                //获取列名
                String name = rsmd.getColumnName(i+1);
                //获取列值
                String value = rs.getString(i+1);
                map.put(name.toUpperCase(), value);
            }
            String key_name = map.get("INDEX_NAME");
            if("primary".equalsIgnoreCase(key_name)){
                continue;
            }
            index.setIndexName(key_name);
            index.setColumn(map.get("COLUMN_NAME"));
            index.setProperty(TableFieldUtil.toCamel(index.getColumn()));
            index.setColumnSeq(Integer.parseInt(map.get("SEQ_IN_INDEX")));
            if("0".equals(map.get("NON_UNIQUE"))){
                index.setUniqueKey(true);
            }
            indexs.add(index);
        }

        tableInfo.setIndexs(indexs);
        pstemt.close();
        rs.close();
    }


    /**
     * 获取表信息， 表注释
     */
    private static void getTableInfo(Connection conn, TableInfo tableInfo) throws Exception{
        String sql=String.format("select * from information_schema.tables where table_schema='%s' and TABLE_NAME='%s';", tableInfo.getDatabase(), tableInfo.getTableName()) ;
        PreparedStatement pstemt = conn.prepareStatement(sql);
        ResultSet rs = pstemt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 0; i < columnCount; i++) {
                //获取列名
                String name = rsmd.getColumnName(i+1);
                if("TABLE_COMMENT".equalsIgnoreCase(name)){
                    //获取列值
                    tableInfo.setComment(rs.getString(i+1));
                }
            }
        }
        pstemt.close();
        rs.close();
    }

    private static void mergeIndex(TableInfo tableInfo) {
        List<TableInfo.IndexInfo> indexs = tableInfo.getIndexs();
        List<TableInfo.ColumnInfo> columnInfoList = tableInfo.getColumns();
        Map<String, TableInfo.ColumnInfo> columnMap = columnInfoList.stream().collect(Collectors.toMap(TableInfo.ColumnInfo::getProperty, p -> p, (key1, key2) -> key2));

        List<TableInfo.IndexInfo> indexList = new ArrayList<>();
        Map<String, List<TableInfo.IndexInfo>> collect = indexs.stream().collect(Collectors.groupingBy(TableInfo.IndexInfo::getIndexName));
        for (Map.Entry<String, List<TableInfo.IndexInfo>> entry : collect.entrySet()){
            TableInfo.IndexInfo index = new TableInfo.IndexInfo();
            List<TableInfo.IndexInfo> value = entry.getValue();
            if(value.size() > 1){
                List<TableInfo.IndexInfo> sorted = value.stream().sorted(Comparator.comparing(TableInfo.IndexInfo::getColumnSeq)).collect(Collectors.toList());
                List<TableInfo.ColumnInfo> columns = index.getColumns();
                if(columns == null || columns.isEmpty()){
                    columns = new ArrayList<>();
                }
                for (TableInfo.IndexInfo i: sorted){
                    TableInfo.ColumnInfo columnInfo = columnMap.get(i.getProperty());
                    columns.add(columnInfo);
                    index = i;
                }
                index.setColumns(columns);
            }else {
                index = value.get(0);
                List<TableInfo.ColumnInfo> columns = new ArrayList<>();
                columns.add(columnMap.get(index.getProperty()));
                index.setColumns(columns);
            }
            String res = joinString(index.getColumns(), "And");
            index.setProperty(res);
            indexList.add(index);
        }
        tableInfo.setIndexs(indexList);

    }

    /**
     * 将列表中的字符串连接起来
     * @param columns
     * @param join
     * @return
     */
    private static String joinString(List<TableInfo.ColumnInfo> columns, String join) {
        if(columns == null || columns.size() == 0){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for(TableInfo.ColumnInfo co : columns){
            String s = co.getProperty();
            if(sb.length() > 0){
                sb.append(join);
            }
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s.substring(1));
        }

        return sb.toString();
    }
}
