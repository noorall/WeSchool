package com.noorall.weschool.dao;

import org.litepal.crud.DataSupport;

import java.util.Date;

public class ExerciseInfo extends DataSupport {
    private String LOCATION;
    private String NUM;
    private Date QSSJ;
    private String MINUTES;
    private String KM;

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }
    public String getLOCATION() {
        return LOCATION;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }
    public String getNUM() {
        return NUM;
    }

    public void setQSSJ(Date QSSJ) {
        this.QSSJ = QSSJ;
    }
    public Date getQSSJ() {
        return QSSJ;
    }

    public void setMINUTES(String MINUTES) {
        this.MINUTES = MINUTES;
    }
    public String getMINUTES() {
        return MINUTES;
    }

    public void setKM(String KM) {
        this.KM = KM;
    }
    public String getKM() {
        return KM;
    }
}
