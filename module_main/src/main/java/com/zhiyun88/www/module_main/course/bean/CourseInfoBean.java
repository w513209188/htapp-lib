package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CourseInfoBean implements Parcelable {
    private CourseIntroBean info;
    private List<ChapterBean> chapter;
    public List<ChapterBean> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterBean> chapter) {
        this.chapter = chapter;
    }

    public CourseIntroBean getInfo() {
        return info;
    }

    public void setInfo(CourseIntroBean info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.info, flags);
        dest.writeTypedList(this.chapter);
    }

    public CourseInfoBean() {
    }

    protected CourseInfoBean(Parcel in) {
        this.info = in.readParcelable(CourseIntroBean.class.getClassLoader());
        this.chapter = in.createTypedArrayList(ChapterBean.CREATOR);
    }

    public static final Creator<CourseInfoBean> CREATOR = new Creator<CourseInfoBean>() {
        @Override
        public CourseInfoBean createFromParcel(Parcel source) {
            return new CourseInfoBean(source);
        }

        @Override
        public CourseInfoBean[] newArray(int size) {
            return new CourseInfoBean[size];
        }
    };
}
