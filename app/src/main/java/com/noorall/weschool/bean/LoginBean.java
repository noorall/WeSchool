/**
 * Copyright 2019 bejson.com
 */
package com.noorall.weschool.bean;

public class LoginBean {

    private boolean isError;
    private LoginData Result;
    public void setIsError(boolean isError) {
        this.isError = isError;
    }
    public boolean getIsError() {
        return isError;
    }

    public void setResult(LoginData Result) {
        this.Result = Result;
    }
    public LoginData getResult() {
        return Result;
    }

}