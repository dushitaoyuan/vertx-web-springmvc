package com.taoyuanx.springmvc.vertx.core.core.route;

import com.taoyuanx.springmvc.vertx.core.anno.route.ResponseBody;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.core.template.TemplateBody;
import com.taoyuanx.springmvc.vertx.core.core.message.MessageConverter;
import com.taoyuanx.springmvc.vertx.core.core.template.AbstractTemplateEngineDelegate;
import com.taoyuanx.springmvc.vertx.core.util.ResponseUtil;
import com.taoyuanx.springmvc.vertx.core.util.RouteUtil;
import com.taoyuanx.springmvc.vertx.core.util.StringUtil;
import com.taoyuanx.springmvc.vertx.core.util.TemplateUtil;
import io.vertx.core.Handler;
import io.vertx.core.VertxException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author dushitaoyuan
 * @desc Handler包装
 * @date 2020/4/23
 */
public class HandlerWrapper {
    /**
     * 模板渲染
     */
    public static Handler<RoutingContext> templateWrapper(VertxHttpServerConfig serverConfig, Method method, Object instance,TemplateBody templateBody) {
        return ctx -> {
            Map<String, AbstractTemplateEngineDelegate> templateEngineMap = serverConfig.getTemplateEngineMap();
            if (Objects.isNull(templateEngineMap) || templateEngineMap.isEmpty()) {
                throw new VertxException("templateEngine not find");
            }
            String templateEngineName = templateBody.engineName();
            AbstractTemplateEngineDelegate choseEngine = null;
            if (StringUtil.isNotEmpty(templateEngineName) && templateEngineMap.containsKey(templateEngineName)) {
                choseEngine = templateEngineMap.get(templateEngineName);
            }
            String templateFilePath = null;
            try {
                /**
                 * 模板引擎查找顺序
                 * 1. 如指定 直接 选择指定
                 * 2. 如未指定或指定不存在,根据模板后缀查找
                 */
                JsonObject dataModel = new JsonObject();
                if (Objects.nonNull(choseEngine)) {
                    Object result = method.invoke(instance, ctx, dataModel);
                    if (Objects.isNull(result)) {
                        return;
                    }
                    templateFilePath = result.toString();
                    doRender(choseEngine, dataModel, templateFilePath, ctx);
                    return;
                } else {
                    Object result = method.invoke(instance, ctx, dataModel);
                    if (Objects.isNull(result)) {
                        return;
                    }
                    templateFilePath = result.toString();
                    String templateSuffix = TemplateUtil.templateSuffix(templateFilePath);
                    for (AbstractTemplateEngineDelegate templateEngine : templateEngineMap.values()) {
                        if (templateEngine.support(templateSuffix)) {
                            doRender(templateEngine, dataModel, templateFilePath, ctx);
                            return;
                        }
                    }
                    throw new VertxException("templateEngine not find for templateSuffix [" + templateSuffix + "]");
                }
            } catch (VertxException e) {
                throw e;
            } catch (Exception e) {
                throw new VertxException(e);
            }


        };
    }


    private static void doRender(AbstractTemplateEngineDelegate choseEngine, JsonObject dataModel, String templateFilePath, RoutingContext ctx) {
        choseEngine.render(dataModel, TemplateUtil.templatePath(choseEngine.getBasePath(), templateFilePath), res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
                return;
            }
            throw new VertxException(res.cause());
        });
    }

    /**
     * json 结果转换
     */
    public static Handler<RoutingContext> jsonWrapper(VertxHttpServerConfig serverConfig, Method method, Object instance) {
        return ctx -> {
            List<MessageConverter> messageConverterList = serverConfig.getMessageConverterList();
            if (StringUtil.isEmpty(messageConverterList)) {
                throw new VertxException("json messageConverter not find");
            }
            try {
                Object result = method.invoke(instance, ctx);
                if (result == null) {
                    throw new VertxException("result is null");
                }
                if (result instanceof String) {
                    ResponseUtil.responseJson(ctx,200,result.toString());
                    return;
                }
                for (MessageConverter messageConverter : messageConverterList) {
                    if (messageConverter.support(result)) {
                        messageConverter.convertTo(result).handle(ctx);
                        return;
                    }
                }
                throw new VertxException("json messageConverter not find");
            } catch (VertxException e) {
                throw e;
            } catch (Exception e) {
                throw new VertxException(e);
            }

        };

    }


}
