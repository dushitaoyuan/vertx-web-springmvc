package com.taoyuanx.vertxdemo.nospring;

import com.taoyuanx.springmvc.vertx.core.start.ServerBootStart;


public class NoSpringBootDemo {

    public static void main(String[] args) {
        ServerBootStart.start("com.taoyuanx.vertxdemo.nospring", 8080, (springMvcRouterHandler) -> {
            System.out.println("启动前");
        }, (springMvcRouterHandler) -> {
            System.out.println("启动后");
        });


    }

}
