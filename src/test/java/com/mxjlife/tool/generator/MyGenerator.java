package com.mxjlife.tool.generator;

import java.util.Arrays;

import com.mxjlife.tool.generator.entity.BasicInfo;
import com.mxjlife.tool.generator.entity.DataModel;
import com.mxjlife.tool.generator.entity.TableInfo;
import com.mxjlife.tool.generator.util.TableInfoUtil;
import com.mxjlife.tool.generator.util.Generator;

/**
 * description: 自动生成入口
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 0:08
 */
public class MyGenerator {
    // 类注释的信息
    private static final String AUTHOR = "mxj";
    private static final String EMAIL = "realmxj@163.com";
    private static final String VERSION = "V1.0";
    // 数据库连接信息：URL,用户名,密码, 数据库
    private static final String URL = "jdbc:mysql://localhost:3306/db_distribution_config?useUnicode=true&characterEncoding=utf-8";
    private static final String NAME = "mxj";
    private static final String PASS = "123";
    private static final String DATABASE = "db_distribution_config";
    // 表名， 可以设置多个， 用逗号 , 分割
    private static final String TABLES = "tbl_dc_price_rule";
    // 要生成的文件的package路径信息
    private static final String PACKAGE_PATH = "com.mxj.app.distribution.manage";
    // 生成文件存放位置
    private static String FILE_PATH= "E:\\Data\\tmp\\";
    // 当要生成的类或mapper与数据库表名不一致时，可以设置这个值
    private static String OBJECT_NAME= "dcPriceRule";

    public static void main(String[] args) {
        Arrays.stream(TABLES.split(",")).forEach(table ->{
            BasicInfo basicInfo = new BasicInfo(AUTHOR, EMAIL, VERSION, URL, NAME, PASS, DATABASE, table, PACKAGE_PATH, FILE_PATH);
            generat(basicInfo);
        });

    }

    private static void generat(BasicInfo basicInfo){
        if(OBJECT_NAME != null && OBJECT_NAME.length()>1){
            basicInfo.setObjectName(OBJECT_NAME);
        }
        try {
            TableInfo tableInfo = TableInfoUtil.getTableInfo(basicInfo);
            DataModel dataModel = new DataModel();
            dataModel.setBasicInfo(basicInfo);
            dataModel.setTableInfo(tableInfo);

            //开始生成文件
            String res1 = Generator.creatEntity(dataModel).toString();
            System.out.println(res1);
            String res2 = Generator.creatMapper(dataModel).toString();
            System.out.println(res2);
            String res3 = Generator.creatXml(dataModel).toString();
            System.out.println(res3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
