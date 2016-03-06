package com.jerry.jingdong.entity;


/**
 * 收藏封装类
 */
public class CollectBean {

    private String error;
    private String error_code;
    private String response;

    public void setError(String error) {
        this.error = error;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public String getError_code() {
        return error_code;
    }

    public String getResponse() {
        return response;
    }
}
