package com.taoyuanx.springmvc.vertx.core.core;

import com.taoyuanx.springmvc.vertx.core.core.message.MessageConverter;
import com.taoyuanx.springmvc.vertx.core.core.template.AbstractTemplateEngineDelegate;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * @author dushitaoyuan
 * @desc http server配置
 * @date 2020/4/22
 */
@Data
public class VertxHttpServerConfig {
    /**
     * http端口
     */
    private Integer httpPort;
    /**
     * 工作线程池大小
     */
    private Integer workPoolSize;
    /**
     * event 执行超时时间
     */
    private Integer eventBusconnectTimeout;
    private Vertx vertx;
    private Router router;
    /**
     * 静态资源目录 多个,分隔
     */
    private String staticDir;
    /**
     * , split
     */
    private String basePackages;
    /**
     * bean 创建工厂
     */
    private BeanFactory beanFactory;

    /**
     * 模板引擎
     */
    private Map<String, AbstractTemplateEngineDelegate> templateEngineMap;
    /**
     * 模板基础目录
     */
    private String templateDir;
    /**
     * 消息转换器
     */
    private List<MessageConverter> messageConverterList;


    /**
     * 服务基础路径
     */
    private String serverBasePath;
}
