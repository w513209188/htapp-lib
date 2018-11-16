package com.zhiyun88.www.module_main.library.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LibraryDetailsBean implements Parcelable {

    private String id;
    private String information_title;
    private String source;
    @SerializedName("abstract")
    private String abstractX;
    private String content;
    private String updated_at;
    private String picture;
    private String click_rate;
    private String app_updated_at;
    private String app_updated_time;
    private String recommend_id;
    private String recommend_title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInformation_title() {
        return information_title;
    }

    public void setInformation_title(String information_title) {
        this.information_title = information_title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getClick_rate() {
        return click_rate;
    }

    public void setClick_rate(String click_rate) {
        this.click_rate = click_rate;
    }

    public String getApp_updated_at() {
        return app_updated_at;
    }

    public void setApp_updated_at(String app_updated_at) {
        this.app_updated_at = app_updated_at;
    }

    public String getApp_updated_time() {
        return app_updated_time;
    }

    public void setApp_updated_time(String app_updated_time) {
        this.app_updated_time = app_updated_time;
    }

    public String getRecommend_id() {
        return recommend_id;
    }

    public void setRecommend_id(String recommend_id) {
        this.recommend_id = recommend_id;
    }

    public String getRecommend_title() {
        return recommend_title;
    }

    public void setRecommend_title(String recommend_title) {
        this.recommend_title = recommend_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.information_title);
        dest.writeString(this.source);
        dest.writeString(this.abstractX);
        dest.writeString(this.content);
        dest.writeString(this.updated_at);
        dest.writeString(this.picture);
        dest.writeString(this.click_rate);
        dest.writeString(this.app_updated_at);
        dest.writeString(this.app_updated_time);
        dest.writeString(this.recommend_id);
        dest.writeString(this.recommend_title);
    }

    public LibraryDetailsBean() {
    }

    protected LibraryDetailsBean(Parcel in) {
        this.id = in.readString();
        this.information_title = in.readString();
        this.source = in.readString();
        this.abstractX = in.readString();
        this.content = in.readString();
        this.updated_at = in.readString();
        this.picture = in.readString();
        this.click_rate = in.readString();
        this.app_updated_at = in.readString();
        this.app_updated_time = in.readString();
        this.recommend_id = in.readString();
        this.recommend_title = in.readString();
    }

    public static final Creator<LibraryDetailsBean> CREATOR = new Creator<LibraryDetailsBean>() {
        @Override
        public LibraryDetailsBean createFromParcel(Parcel source) {
            return new LibraryDetailsBean(source);
        }

        @Override
        public LibraryDetailsBean[] newArray(int size) {
            return new LibraryDetailsBean[size];
        }
    };
}
