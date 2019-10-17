package com.hesc.codegen.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用Freemarker生成jsp文件
 */
public class GeneratorUtils {

    /**
     * 根据模板生成jsp文件
     *
     * @param templateName 模板名称
     * @param paramMap     模板参数
     */
    public static void generateJsp(String templateName, Map<String, Object> paramMap) {
        FileOutputStream fos = null;
        Writer writer = null;
        try {
            // 设置相关配置
            Configuration configuration = new Configuration();
            URL url = Configuration.class.getClassLoader().getResource("template");
            configuration.setDirectoryForTemplateLoading(new File(url.getPath()));//	模板存放地址
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            configuration.setDefaultEncoding("UTF-8");// 默认编码

            // 根据模板名称获取模板
            Template template = configuration.getTemplate(templateName);

            // 生成输出流
            URL outDir = Configuration.class.getClassLoader().getResource("jsp");
            File html = new File(outDir.getPath(), templateName);// html文件
            if (html.exists()) {// 文件已存在时删除原有文件
                html.delete();
            }
            fos = new FileOutputStream(html);
            writer = new OutputStreamWriter(fos, "UTF-8");

            // 生成模板
            template.process(paramMap, writer);

            System.out.println("成功生成jsp文件--" + templateName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}