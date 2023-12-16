package com.example.shik2;

import com.google.firebase.Timestamp;

public class UserModel {

    String marka, model,  datatime, otsyv;
    String usluga;
    Timestamp timestamp;


    public UserModel() {
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getOtsyv() {
        return otsyv;
    }

    public void setOtsyv(String otsyv) {
        this.otsyv = otsyv;
    }

    public String getUsluga() {
        return usluga;
    }

    public void setUsluga(String usluga) {
        this.usluga = usluga;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
