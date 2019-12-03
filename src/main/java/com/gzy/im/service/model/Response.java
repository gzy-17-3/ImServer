package com.gzy.im.service.model;

public class Response<T> {
    String msg;
    T data;
    int code = 200;
}
