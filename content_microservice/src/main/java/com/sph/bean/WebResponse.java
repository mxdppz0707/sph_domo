package com.sph.bean;

import java.io.Serializable;

public class WebResponse<T> implements Serializable {

    private static final String SUCCESS_CODE = "10000";
    private static final String FAILD_CODE = "99999";
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.msg;
    }

    public void setDesc(String desc) {
        this.msg = desc;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public WebResponse() {
    }


    public WebResponse(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

    public static <T> WebResponse<T> create() {
        WebResponse<T> response = new WebResponse();
        response.code = FAILD_CODE;
        return response;
    }

    public WebResponse success() {
        this.code = SUCCESS_CODE;
        return this;
    }

    public WebResponse<T> success(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
        return this;
    }

    public WebResponse<T> successMsg(String message) {
        this.msg = message;
        return this;
    }

    public WebResponse<T> fail(String code, String message) {
        this.code = code;
        this.msg = message;
        return this;
    }


    public static <T> WebResponse<T> suc(T data) {
        WebResponse<T> webResponse = new WebResponse();
        webResponse.setData(data);
        webResponse.setCode(SUCCESS_CODE);
        return webResponse;
    }


    public static <T> WebResponse<T> fai(String code, String desc) {
        WebResponse<T> webResponse = new WebResponse();
        webResponse.setCode(code);
        webResponse.setDesc(desc);
        return webResponse;
    }

    public String toString() {
        return "WebResponse{code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + ", data=" + this.data + '}';
    }

}
