package com.example.shop_inventory.bin;

import org.springframework.http.HttpStatus;

public class ExceptionBean {

    private HttpStatus http;
    private String ErrMsg;
    private Exception e;

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public HttpStatus getHttp() {
        return http;
    }

    public void setHttp(HttpStatus http) {
        this.http = http;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }
}
