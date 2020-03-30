package com.mxjlife.tool.generator.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mxjlife.tool.generator.util.TableFieldUtil;
import lombok.Data;

/**
 * description: 基本参数信息
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 16:59
 */
@Data
public class BasicInfo {

    private String dirverClassName = "com.mysql.cj.jdbc.Driver";

    private String author;

    private String version;

    private String email;

    private String dbUrl;

    private String dbUsername;

    private String dbPassword;

    private String database;

    private String tableName;

    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());;

    private String objectName;

    private String paramName;

    private String filePath;

    private String packagePath;


    public BasicInfo(String author, String email, String version, String url, String dbUsername, String dbPass, String database, String tableName, String packagePath, String filePath) {
        super();
        this.author = author;
        this.version = version;
        this.email = email;
        this.dbUrl = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPass;
        this.database = database;
        this.packagePath = packagePath;
        this.filePath = filePath;
        this.tableName = tableName;
        this.objectName = TableFieldUtil.toUpperCamel(tableName);
        this.paramName = TableFieldUtil.toCamel(objectName);
    }

    public void setObjectName(String objectName) {
        this.objectName = TableFieldUtil.toUpperCamel(objectName);
        this.paramName = TableFieldUtil.toCamel(objectName);
    }
}
