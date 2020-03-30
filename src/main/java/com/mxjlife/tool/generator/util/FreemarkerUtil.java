package com.mxjlife.tool.generator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.mxjlife.tool.generator.entity.DataModel;
import com.mxjlife.tool.generator.entity.ResultJson;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**   
 * description: 根据模板文件生成代码
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 10:08
 */
public class FreemarkerUtil {

    public static ResultJson createFile(DataModel dataModel, String templateName, String filePath) {
        ResultJson result=new ResultJson();
        FileWriter out = null;
        File file = new File(filePath);
        String fileName = file.getName();
        try {
            // 通过FreeMarker的Confuguration读取相应的模板文件
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            // 设置模板路径
            configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/freemarker/ftl");
            configuration.setDefaultEncoding("utf-8");
            // 获取模板
            Template template = configuration.getTemplate(templateName);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if(!file.exists()) {
                file.createNewFile();
            }else {
                result.setCode(2);
                result.setMessage(String.format("文件[%s]已经存在", fileName));
                return result;
            }
            //设置输出流
            out = new FileWriter(file);
            //模板输出静态文件
            System.out.println(dataModel);
            template.process(dataModel, out);
            result.setCode(1);
            result.setMessage(String.format("文件[%s]生成成功", fileName));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        result.setCode(9);
        result.setMessage(String.format("文件[%s]生成失败", fileName));
        return result;
    }

}
