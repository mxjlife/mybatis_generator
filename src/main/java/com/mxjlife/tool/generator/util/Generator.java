package com.mxjlife.tool.generator.util;

import com.mxjlife.tool.generator.entity.BasicInfo;
import com.mxjlife.tool.generator.entity.DataModel;
import com.mxjlife.tool.generator.entity.ResultJson;

import java.io.File;

/**
 * description: 获取文件路径调用创建文件
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 10:08
 */
public class Generator {
    //路径信息
    public static final String ENTITY="entity";
    public static final String MAPPER="mapper";
    public static final String MAPPER_XML="xml";

    /**
     * 创建实体类
     */
    public static ResultJson creatEntity(DataModel dataModel) {
        BasicInfo basicInfo = dataModel.getBasicInfo();
        String fileUrl= getGenFilePath(basicInfo.getFilePath(), basicInfo.getPackagePath(), basicInfo.getObjectName(), ENTITY);
        return FreemarkerUtil.createFile(dataModel, "entity.ftl", fileUrl);
    }

    //②创建mapper接口
    public static ResultJson creatMapper(DataModel dataModel) {
        BasicInfo basicInfo = dataModel.getBasicInfo();
        String fileUrl= getGenFilePath(basicInfo.getFilePath(), basicInfo.getPackagePath(), basicInfo.getObjectName(), MAPPER);
        return FreemarkerUtil.createFile(dataModel, "mapper.ftl", fileUrl);
    }

    //③创建mapper配置文件
    public static ResultJson creatXml(DataModel dataModel) {
        BasicInfo basicInfo = dataModel.getBasicInfo();
        String fileUrl= getGenFilePath(basicInfo.getFilePath(), basicInfo.getPackagePath(), basicInfo.getObjectName(), MAPPER_XML);
        return FreemarkerUtil.createFile(dataModel, "xml.ftl", fileUrl);
    }


    //生成文件路径和名字
    public static String getGenFilePath(String filePath, String packagePath, String objectName, String type){
        String path = null;
        switch (type){
            case ENTITY:
                path = pathCheck(filePath) + pkgToPath(packagePath) + objectName + "PO.java";
                break;
            case MAPPER:
                path = pathCheck(filePath) + pkgToPath(packagePath) + objectName + "Mapper.java";
                break;
            case MAPPER_XML:
                path = pathCheck(filePath) + pkgToPath(packagePath) + objectName + "Mapper.xml";
                break;
        }
        return path;
    }

    public static String pkgToPath(String pkg) {
        String s = pkg.replace(".", "/") + "/";
        if(!s.endsWith("/")){
            s = s + "/";
        }
        return s;
    }

    public static String pathCheck(String path) {
        if(!path.endsWith("/") && !path.endsWith(File.separator)){
            path = path + File.separator;
        }
        return path;
    }

}
