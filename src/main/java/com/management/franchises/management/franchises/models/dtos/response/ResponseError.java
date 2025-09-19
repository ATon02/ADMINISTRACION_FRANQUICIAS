package com.management.franchises.management.franchises.models.dtos.response;

import org.springframework.http.HttpStatus;

public class ResponseError {

    private int code;
    private String error;
    private String detail;

    public ResponseError(HttpStatus status, String detail) {
        this.code = status.value();
        this.error = status.name();
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    

}
