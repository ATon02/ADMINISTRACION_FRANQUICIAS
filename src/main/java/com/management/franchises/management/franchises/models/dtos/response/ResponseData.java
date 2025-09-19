package com.management.franchises.management.franchises.models.dtos.response;

import org.springframework.http.HttpStatus;

public class ResponseData<T> {

    private int code;
    private T data;

    public ResponseData(HttpStatus status, T data) {
        this.code = status.value();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    

}
