package com.example.moneylist;

import android.content.Intent;

import java.util.Date;
import java.util.UUID;

public class Amoney {
    private UUID mId;
    private String mTitle;
    private String mMoney;
    private Date mDate;
    private String mMethod;
    private String mNotes;
    private String mContanter;

    public Amoney(){
        this(UUID.randomUUID());
    }
    public Amoney(UUID id){
        mId = id;
        mDate = new Date();
    }

    public String getmContanter() {
        return mContanter;
    }

    public void setmContanter(String mContanter) {
        this.mContanter = mContanter;
    }

    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String mMethod) {
        this.mMethod = mMethod;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMoney() {
        return mMoney;
    }

    public void setMoney(String mMoney) {
        this.mMoney = mMoney;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        this.mNotes = notes;
    }

    public String getPhotoFilename(){
        return "IMG_" + getId().toString() + ".jdg";
    }
}
