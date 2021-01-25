package com.xgh.cas.viewmodel.res;

public class UserCheckResponse {

    // 0：fail，1：success
    private int status = 0;
    private String data = "当前登录已过期，请重新登录！";

    public UserCheckResponse() {
    }

    public UserCheckResponse(int status, String data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
