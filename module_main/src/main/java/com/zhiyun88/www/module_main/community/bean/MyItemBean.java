package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MyItemBean implements Parcelable {

    private int total;
    private int current_page;
    private List<MyItemListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<MyItemListBean> getList() {
        return list;
    }

    public void setList(List<MyItemListBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.current_page);
        dest.writeList(this.list);
    }

    public MyItemBean() {
    }

    protected MyItemBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<MyItemListBean>();
        in.readList(this.list, MyItemListBean.class.getClassLoader());
    }

    public static final Creator<MyItemBean> CREATOR = new Creator<MyItemBean>() {
        @Override
        public MyItemBean createFromParcel(Parcel source) {
            return new MyItemBean(source);
        }

        @Override
        public MyItemBean[] newArray(int size) {
            return new MyItemBean[size];
        }
    };
}
