package com.code.helper;

import com.alibaba.fastjson.JSONObject;
import com.bean.mysql.GeneratorConfig;
import com.bean.vo.ColumnVO;
import com.code.constant.GeneratorConstant;
import com.core.annotation.Helper;
import com.core.constant.ServiceConstant;
import com.core.util.CamelCaseUtil;
import com.google.common.io.Files;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 代码生成器工具类
 */
@Slf4j
@Helper
@SuppressWarnings("all")
public class GeneratorHelper {

    public void generateEntityFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.JAVA_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = GeneratorConstant.ENTITY_TEMPLATE;
        File entityFile = new File(path);

        // 新增/修改是否Fill
        List<ColumnVO> columnsCopy = columnFilter(columns, item -> {
            if (FieldConstant.FILL_INSERT_LIST.contains(item.getField())) {
                item.setFillType(FieldConstant.FILL_TYPE_INSERT);
            } else if (FieldConstant.FILL_UPDATE_LIST.contains(item.getField())) {
                item.setFillType(FieldConstant.FILL_TYPE_UPDATE);
            } else {
                item.setFillType(FieldConstant.FILL_TYPE_NONE);
            }
            return true;
        }, Boolean.FALSE);

        JSONObject data = generateTemplateData(columnsCopy, configure);
        generateFileByTemplate(templateName, entityFile, data);
    }

    public void generateEntityQueryFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.QUERY_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = GeneratorConstant.ENTITY_QUERY_TEMPLATE;
        File entityFile = new File(path);

        // 过滤字段
        List<ColumnVO> columnsCopy = columnFilter(columns, item -> {
            if (FieldConstant.ENTITY_QUERY_LIST.contains(item.getField())) {
                return true;
            } else {
                return false;
            }
        }, Boolean.TRUE);

        JSONObject data = generateTemplateData(columnsCopy, configure);
        generateFileByTemplate(templateName, entityFile, data);
    }

    public void generateEntityPageQueryFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.PAGE_QUERY_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = GeneratorConstant.ENTITY_PAGE_QUERY_TEMPLATE;
        File entityFile = new File(path);

        // 过滤字段
        List<ColumnVO> columnsCopy = columnFilter(columns, item -> {
            if (FieldConstant.ENTITY_QUERY_LIST.contains(item.getField())) {
                return true;
            } else {
                return false;
            }
        }, Boolean.TRUE);

        JSONObject data = generateTemplateData(columnsCopy, configure);
        generateFileByTemplate(templateName, entityFile, data);
    }

    public void generateEntityParamFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.PARAM_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = GeneratorConstant.ENTITY_PARAM_TEMPLATE;
        File entityFile = new File(path);

        // 不在需要忽略的字段中的，均排除掉
        List<ColumnVO> columnsCopy = columnFilter(columns, item -> {
            if (!FieldConstant.ENTITY_PARAM_IGNORE_LIST.contains(item.getField())) {
                return true;
            } else {
                return false;
            }
        }, Boolean.TRUE);

        JSONObject data = generateTemplateData(columnsCopy, configure);
        generateFileByTemplate(templateName, entityFile, data);
    }

    public void generateEntityVOFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.VO_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getEntityPackage(), suffix, false);
        String templateName = GeneratorConstant.ENTITY_VO_TEMPLATE;
        File entityFile = new File(path);
        JSONObject data = generateTemplateData(columns, configure);
        generateFileByTemplate(templateName, entityFile, data);
    }

    public void generateMapperFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.MAPPER_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getMapperPackage(), suffix, false);
        String templateName = GeneratorConstant.MAPPER_TEMPLATE;
        File mapperFile = new File(path);
        JSONObject data = generateTemplateData(columns, configure);
        generateFileByTemplate(templateName, mapperFile, data);
    }

    public void generateServiceFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.SERVICE_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getServicePackage(), suffix, true);
        String templateName = GeneratorConstant.SERVICE_TEMPLATE;
        File serviceFile = new File(path);
        JSONObject data = generateTemplateData(columns, configure);
        generateFileByTemplate(templateName, serviceFile, data);
    }

    public void generateServiceImplFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.SERVICEIMPL_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getServiceImplPackage(), suffix, false);
        String templateName = GeneratorConstant.SERVICEIMPL_TEMPLATE;
        File serviceImplFile = new File(path);
        JSONObject data = generateTemplateData(columns, configure);
        generateFileByTemplate(templateName, serviceImplFile, data);
    }

    public void generateControllerFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.CONTROLLER_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getControllerPackage(), suffix, false);
        String templateName = GeneratorConstant.CONTROLLER_TEMPLATE;
        File controllerFile = new File(path);
        JSONObject data = generateTemplateData(columns, configure);
        generateFileByTemplate(templateName, controllerFile, data);
    }

    public void generateMapperXmlFile(List<ColumnVO> columns, GeneratorConfig configure) throws Exception {
        String suffix = GeneratorConstant.MAPPERXML_FILE_SUFFIX;
        String path = getFilePath(configure, configure.getMapperXmlPackage(), suffix, false);
        String templateName = GeneratorConstant.MAPPERXML_TEMPLATE;
        File mapperXmlFile = new File(path);

        // 过滤字段
        List<ColumnVO> columnsCopy = columnFilter(columns, item -> {
            if (FieldConstant.ENTITY_QUERY_LIST.contains(item.getField())) {
                return true;
            } else {
                return false;
            }
        }, Boolean.TRUE);

        JSONObject data = generateTemplateData(columnsCopy, configure, new ExtraPair("originColums", columns));
        generateFileByTemplate(templateName, mapperXmlFile, data);
    }

    /*
     ***************************************** 私有方法 **********************************************************
     */

    private JSONObject generateTemplateData(List<ColumnVO> columns, GeneratorConfig configure, ExtraPair... extraPairs) {
        JSONObject data = toJsonObject(configure);
        data.put("hasDate", false);
        data.put("hasBigDecimal", false);
        columns.forEach(c -> {
            c.setField(CamelCaseUtil.underscoreToCamel(StringUtils.lowerCase(c.getName())));
            if (StringUtils.containsAny(c.getType(), FieldConstant.DATE, FieldConstant.DATETIME, FieldConstant.TIMESTAMP)) {
                data.put("hasDate", true);
            }
            if (StringUtils.containsAny(c.getType(), FieldConstant.DECIMAL, FieldConstant.NUMERIC)) {
                data.put("hasBigDecimal", true);
            }
        });
        data.put("columns", columns);

        // 额外的数据
        if (ArrayUtils.isNotEmpty(extraPairs)) {
            for (ExtraPair extraPair : extraPairs) {
                data.put(extraPair.getKey(), extraPair.getValue());
            }
        }

        return data;
    }

    private class ExtraPair {
        private String key;
        private Object value;

        public ExtraPair(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    private static String getFilePath(GeneratorConfig configure, String packagePath, String suffix, boolean serviceInterface) {
        String filePath = GeneratorConstant.TEMP_PATH + configure.getJavaPath() +
                packageConvertPath(configure.getBasePackage() + "." + packagePath);
        // 是否是Service接口文件
        if (serviceInterface) {
            filePath += "I";
        }
        filePath += configure.getClassName() + suffix;
        return filePath;
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    private void generateFileByTemplate(String templateName, File file, Object data) throws Exception {
        Template template = getTemplate(templateName);
        Files.createParentDirs(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), 10240)) {
            template.process(data, out);
        } catch (Exception e) {
            String message = "代码生成异常";
            log.error(message, e);
            throw new Exception(message);
        }
    }

    private JSONObject toJsonObject(Object o) {
        return JSONObject.parseObject(JSONObject.toJSON(o).toString());
    }

    private Template getTemplate(String templateName) throws Exception {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        String templatePath = GeneratorHelper.class.getResource("/generator/").getPath();
        File file = new File(templatePath);
        if (!file.exists()) {
            templatePath = System.getProperties().getProperty(ServiceConstant.JAVA_TEMP_DIR);
            file = new File(templatePath + File.separator + templateName);
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(GeneratorHelper.class.getClassLoader().getResourceAsStream("classpath:generator/templates/" + templateName)), file);
        }
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setDefaultEncoding(ServiceConstant.UTF8);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return configuration.getTemplate(templateName);

    }

    private static List<ColumnVO> columnFilter(List<ColumnVO> list, Function<ColumnVO, Boolean> function, boolean isFilter) {
        List<ColumnVO> copyList = new ArrayList<>(list);
        // 过滤列表
        List<ColumnVO> removeObjList = new ArrayList<>();
        copyList.forEach(item -> {
            if (function.apply(item)) {
                removeObjList.add(item);
            }
        });
        // 是否过滤
        if (isFilter) {
            removeObjList.forEach(copyList::remove);
        }
        return copyList;
    }
}
