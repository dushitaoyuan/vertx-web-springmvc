package com.taoyuanx.web;

/**
 * @author dushitaoyuan
 * @desc 自定义异常
 * @date 2020/4/22
 */
public class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }
}
