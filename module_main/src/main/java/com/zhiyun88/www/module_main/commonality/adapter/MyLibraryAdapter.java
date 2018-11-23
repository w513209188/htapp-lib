package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryListBean;

import java.util.List;

public class MyLibraryAdapter extends BaseAdapter{
    private Context mContext;
    private List<MyLibraryListBean> libraryListBeans;

    public MyLibraryAdapter(Context context, List<MyLibraryListBean> libraryListBeans) {
        this.mContext = context;
        this.libraryListBeans = libraryListBeans;
    }

    @Override
    public int getCount() {
        return libraryListBeans.size();
    }

    @Override
    public MyLibraryListBean getItem(int position) {
        return libraryListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final MyLibraryListBean myLibraryListBean = getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_library, null);
            viewHolder.type = convertView.findViewById(R.id.library_type);
            viewHolder.imageView = convertView.findViewById(R.id.library_image);
            viewHolder.title = convertView.findViewById(R.id.library_title);
            viewHolder.collect = convertView.findViewById(R.id.library_collected);
            viewHolder.subtitle = convertView.findViewById(R.id.library_subtitle);
            viewHolder.library_rl = convertView.findViewById(R.id.library_rl);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView, R.drawable.course_image, mContext, myLibraryListBean.getImg(), 4);
        viewHolder.title.setText(myLibraryListBean.getName());
        viewHolder.subtitle.setText(myLibraryListBean.getAbstractX());
        if (myLibraryListBean.getIs_collection().equals("1")) {
            viewHolder.collect.setSelected(true);
        } else {
            viewHolder.collect.setSelected(false);
        }
       /* if (myLibraryListBean.getExt().equals("pdf")) {
            viewHolder.type.setText("[PDF]");
            viewHolder.type.setTextColor(Color.parseColor("#e60303"));
        } else if (myLibraryListBean.getExt().equals("doc")|| myLibraryListBean.getExt().equals("docx")) {
            viewHolder.type.setText("[WORD]");
            viewHolder.type.setTextColor(Color.parseColor("#005aff"));
        } else if (myLibraryListBean.getExt().equals("xlsx")){
            viewHolder.type.setText("[EXEL]");
            viewHolder.type.setTextColor(Color.parseColor("#00cb21"));
        }else if (myLibraryListBean.getExt().equals("ppt")){
            viewHolder.type.setText("[PPT]");
            viewHolder.type.setTextColor(Color.parseColor("#ff7800"));
        }*/
        return convertView;
    }
    class ViewHolder {
        ImageView imageView, collect;
        TextView title, subtitle, type;
        RelativeLayout library_rl;
    }
}
