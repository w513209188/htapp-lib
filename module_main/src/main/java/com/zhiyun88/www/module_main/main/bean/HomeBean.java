package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class HomeBean implements Parcelable {

    private List<BannerBean> banner;
    private List<HomeCourseBean> course;
    private List<HomeTransformerBean> transformer;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<HomeCourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<HomeCourseBean> course) {
        this.course = course;
    }

    public List<HomeTransformerBean> getTransformer() {
        return transformer;
    }

    public void setTransformer(List<HomeTransformerBean> transformer) {
        this.transformer = transformer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.banner);
        dest.writeList(this.course);
        dest.writeList(this.transformer);
    }

    public HomeBean() {
    }

    protected HomeBean(Parcel in) {
        this.banner = new ArrayList<BannerBean>();
        in.readList(this.banner, BannerBean.class.getClassLoader());
        this.course = new ArrayList<HomeCourseBean>();
        in.readList(this.course, HomeCourseBean.class.getClassLoader());
        this.transformer = new ArrayList<HomeTransformerBean>();
        in.readList(this.transformer, HomeTransformerBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeBean> CREATOR = new Parcelable.Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel source) {
            return new HomeBean(source);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };
}
