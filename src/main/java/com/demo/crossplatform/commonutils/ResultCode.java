package com.demo.crossplatform.commonutils;

public enum ResultCode {

    //成功
    SUCCESS(200, "SUCCESS"),
    //失败
    ERROR(201, "ERROR");

    //状态码
    private int key;
    //状态
    private String value;

    ResultCode(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }

}
