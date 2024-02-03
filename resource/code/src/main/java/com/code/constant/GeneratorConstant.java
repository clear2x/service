package com.code.constant;

/**
 * 代码生成常量
 *
 * @author yuangy
 * @create 2020/7/8 16:35
 */
public interface GeneratorConstant {

    /**
     * 数据库类型
     */
    String DATABASE_TYPE = "mysql";
    /**
     * 数据库名称
     */
    String DATABASE_NAME = "service_demo";

    /**
     * 生成代码的临时目录
     */
    String TEMP_PATH = "evm_temp/";

    /*
     ************************************************** 通用常量 *****************************************************
     */
    String JAVA_FILE_TAG = ".java";
    String XML_FILE_TAG = ".xml";
    String TEMPLATE_FILE_TAG = ".ftl";

    String VO_TAG = "VO";
    String QUERY_TAG = "Query";
    String PAGE_QUERY_TAG = "PageQuery";
    String PARAM_TAG = "Param";

    /*
     ************************************************** 文件名后缀 *****************************************************
     */

    /**
     * java类型文件后缀
     */
    String JAVA_FILE_SUFFIX = JAVA_FILE_TAG;
    /**
     * entityVO文件类型后缀
     */
    String VO_FILE_SUFFIX = VO_TAG + JAVA_FILE_TAG;
    /**
     * entityQuery文件类型后缀
     */
    String QUERY_FILE_SUFFIX = QUERY_TAG + JAVA_FILE_TAG;
    /**
     * entityPageQuery文件类型后缀
     */
    String PAGE_QUERY_FILE_SUFFIX = PAGE_QUERY_TAG + JAVA_FILE_TAG;
    /**
     * entityParam文件类型后缀
     */
    String PARAM_FILE_SUFFIX = PARAM_TAG + JAVA_FILE_TAG;


    /**
     * mapper文件类型后缀
     */
    String MAPPER_FILE_SUFFIX = "Mapper" + JAVA_FILE_TAG;
    /**
     * mapper xml文件类型后缀
     */
    String MAPPERXML_FILE_SUFFIX = "Mapper" + XML_FILE_TAG;


    /**
     * service文件类型后缀
     */
    String SERVICE_FILE_SUFFIX = "Service" + JAVA_FILE_TAG;
    /**
     * service impl文件类型后缀
     */
    String SERVICEIMPL_FILE_SUFFIX = "ServiceImpl" + JAVA_FILE_TAG;


    /**
     * controller文件类型后缀
     */
    String CONTROLLER_FILE_SUFFIX = "Controller" + JAVA_FILE_TAG;


    /*
     ************************************************** 模版文件名 *****************************************************
     */

    /**
     * entity模板
     */
    String ENTITY_TEMPLATE = "entity" + TEMPLATE_FILE_TAG;
    /**
     * entityVO模板
     */
    String ENTITY_VO_TEMPLATE = "entity" + VO_TAG + TEMPLATE_FILE_TAG;
    /**
     * entityQuery模板
     */
    String ENTITY_QUERY_TEMPLATE = "entity" + QUERY_TAG + TEMPLATE_FILE_TAG;
    /**
     * entityPageQuery模板
     */
    String ENTITY_PAGE_QUERY_TEMPLATE = "entity" + PAGE_QUERY_TAG + TEMPLATE_FILE_TAG;
    /**
     * entityParam模板
     */
    String ENTITY_PARAM_TEMPLATE = "entity" + PARAM_TAG + TEMPLATE_FILE_TAG;

    /**
     * mapper模板
     */
    String MAPPER_TEMPLATE = "mapper" + TEMPLATE_FILE_TAG;
    /**
     * mapper xml接口模板
     */
    String MAPPERXML_TEMPLATE = "mapperXml" + TEMPLATE_FILE_TAG;


    /**
     * service接口模板
     */
    String SERVICE_TEMPLATE = "service" + TEMPLATE_FILE_TAG;
    /**
     * service impl接口模板
     */
    String SERVICEIMPL_TEMPLATE = "serviceImpl" + TEMPLATE_FILE_TAG;


    /**
     * controller接口模板
     */
    String CONTROLLER_TEMPLATE = "controller" + TEMPLATE_FILE_TAG;

}
