# vertx-web-springmvc
spring mvc 风格开发 vertx

- springmvc 风格开发
- 统一异常处理
- 拦截器
- 统一结果处理
- springmvc 风格模板渲染

## springmvc 风格注解介绍

| 注解             | 用法                                                         |
| ---------------- | ------------------------------------------------------------ |
| TemplateBody     | 类似springmvc的@ ResponseBody标记模板渲染 阻塞执行,配合 VertxTemplateEngine标记 |
| ResponseBody     | 类似springmvc的 @ResponseBody 标记消息转换 (依赖 MessageConverter)阻塞执行,配合 VertxMessageConverter标记 |
| RouteHandler     | 类似springmvc的 @Controller                                  |
| RouteMapping     | 类似springmvc的 @RequestMapping                              |
| Interceptor      | vertx 拦截器标记                                             |
| RouterAdvice     | 类似springmvc的 @ControllerAdvice                            |
| ExceptionHandler | 类似springmvc的 @ExceptionHandler 异常处理                   |
|                  |                                                              |

详细开发规范参见 xx-example
