package com.management.franchises.management.franchises.models.dtos.response;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseDataList<T> {

    private int code;
    private List<T> data;

    public ResponseDataList(HttpStatus status, List<T> data) {
        this.code = status.value();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    

}
