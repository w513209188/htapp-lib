package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.MyCourseListBean;

import java.util.List;

import cn.bingoogolapple.progressbar.BGAProgressBar;

public class MyCourseAdapter extends BaseAdapter {
    private List<MyCourseListBean> myCourseListBeans;
    private Context mContext;

    public MyCourseAdapter(Context context, List<MyCourseListBean> myCourseListBeans) {
        this.mContext = context;
        this.myCourseListBeans = myCourseListBeans;
    }

    @Override
    public int getCount() {
        return myCourseListBeans.size();
    }

    @Override
    public MyCourseListBean getItem(int position) {
        return myCourseListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyCourseListBean myCourseListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_commom, null);
            viewHolder.imageView = convertView.findViewById(R.id.commom_image);
            viewHolder.title = convertView.findViewById(R.id.commom_title);
            viewHolder.teacher = convertView.findViewById(R.id.commom_teacher);
            viewHolder.progressbar = convertView.findViewById(R.id.commom_progressbar);
            viewHolder.cardview = convertView.findViewById(R.id.cardview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , myCourseListBean.getCover(), 4);
        viewHolder.title.setText(myCourseListBean.getTitle());
        viewHolder.teacher.setVisibility(View.VISIBLE);
        viewHolder.teacher.setText(mContext.getString(R.string.main_be_the_speaker)+myCourseListBean.getTeacher());
        viewHolder.progressbar.setVisibility(View.VISIBLE);
        viewHolder.progressbar.setProgress(myCourseListBean.getRate_progress());
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("isCourseTaskInfo",false);
                intent.putExtra("courseId", myCourseListBean.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder {
        CardView cardview;
        ImageView imageView;
        TextView title,teacher;
        BGAProgressBar progressbar;
    }
}
