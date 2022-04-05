package com.dev.common.base;

import org.jetbrains.annotations.NotNull;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class Resource<T> {
    public static final int CODE_EMPTY = 100;
    public static final int CODE_EXIST = 200;
    public static final int CODE_DATA_ERROR = 300;
    public static final int CODE_UNKNOWN_ERROR = 400;


    private T data;
    private int code = 0;
    private String msg;

    public Resource() {

    }

    public Resource(T t) {
        this.data = t;
    }

    public Resource(String msg) {
        this.msg = msg;
    }

    public Resource(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Resource<T> newInstance(T t) {
        return new Resource<>(t);
    }

    public static <T> Resource<T> newInstance(String msg) {
        return new Resource<>(msg);
    }

    public static <T> Resource<T> newInstance(int code, String msg) {
        return new Resource<>(code, msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public @NotNull String toString() {
        return "Resource{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
