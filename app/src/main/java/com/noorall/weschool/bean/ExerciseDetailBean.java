/**
 * Copyright 2019 bejson.com
 */
package com.noorall.weschool.bean;
import java.util.List;

public class ExerciseDetailBean {

    private boolean isError;
    private List<ExerciseDetailData> Result;
    public void setIsError(boolean isError) {
        this.isError = isError;
    }
    public boolean getIsError() {
        return isError;
    }

    public void setResult(List<ExerciseDetailData> Result) {
        this.Result = Result;
    }
    public List<ExerciseDetailData> getResult() {
        return Result;
    }

}