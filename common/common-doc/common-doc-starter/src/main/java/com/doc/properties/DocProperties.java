package com.doc.properties;

import com.core.constant.ServiceConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文档参数
 *
 * @author yuangy
 * @create 2020/7/8 11:43
 */
@ConfigurationProperties(prefix = ServiceConstant.PROPERTIES_PREFIX + "doc")
@Data
public class DocProperties {

    /**
     * 是否开启doc功能
     */
    private Boolean enable = false;
    /**
     * 1. 接口扫描路径，如Controller路径 <br>
     * 2. 多个路径用","分隔 <br>
     * 3. 路径可为包的全名或者类的全名，如：com.oauth.controller,org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
     */
    private String basePackage;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档描述颜色
     */
    private String descriptionColor = "#42b983";
    /**
     * 文档描述字体大小
     */
    private String descriptionFontSize = "14";
    /**
     * 服务url
     */
    private String termsOfServiceUrl;
    /**
     * 联系方式：姓名
     */
    private String name;
    /**
     * 联系方式：个人网站url
     */
    private String url;
    /**
     * 联系方式：邮箱
     */
    private String email;
    /**
     * 协议
     */
    private String license = "Apache 2.0";
    /**
     * 协议地址
     */
    private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0.html";
    /**
     * 版本
     */
    private String version;

}
