package com.taoyuanx.springmvc.vertx.core.util;

import com.taoyuanx.springmvc.vertx.core.anno.Order;

import java.util.Objects;

/**
 * @author dushitaoyuan
 * @date 2020/4/20
 */
public class OrderUtil {


    public static int getOrder(Object order,int defaultOrder) {
        if (order instanceof Order) {
            Order iOrder = (Order) order;
            return iOrder.order();
        }
        Order orderAnno = order.getClass().getAnnotation(Order.class);
        return Objects.isNull(orderAnno) ? defaultOrder : orderAnno.order();
    }
}
