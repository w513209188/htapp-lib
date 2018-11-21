package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;
import com.zhiyun88.www.module_main.community.ui.TopicDetailsActivity;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

public class CommentAdapater extends BaseAdapter{
    private Context mContext;
    private List<DetailsCommentListBean> listBeans;
    private CommunityConfig.OnReplyListener onReplyListener;

    public CommentAdapater(Context context, List<DetailsCommentListBean> listBeans) {
        this.mContext = context;
        this.listBeans = listBeans;
    }

    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public DetailsCommentListBean getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DetailsCommentListBean listBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_comment, null);
            viewHolder.comment_title = convertView.findViewById(R.id.comment_title);
            viewHolder.comment_time = convertView.findViewById(R.id.comment_time);
            viewHolder.comment_num = convertView.findViewById(R.id.comment_num);
            viewHolder.comment_name = convertView.findViewById(R.id.comment_name);
            viewHolder.comment_reply = convertView.findViewById(R.id.comment_reply);
            viewHolder.comment_image = convertView.findViewById(R.id.comment_image);
            viewHolder.comment_listview = convertView.findViewById(R.id.comment_listview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (listBean.getIs_anonymity().equals("1")) {
            viewHolder.comment_image.setImageResource(R.drawable.name_no);
            viewHolder.comment_name.setText("匿名");
        }else {
            if (listBean.getAvatar() == null || listBean.getAvatar().equals("")) {
                Picasso.with(mContext).load("www").error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.comment_image);
            }else {
                Picasso.with(mContext).load(listBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.comment_image);
            }
            viewHolder.comment_name.setText(listBean.getUser_name());
        }
        viewHolder.comment_title.setText(listBean.getContent());
        viewHolder.comment_time.setText("发表于: "+listBean.getCreated_at());
       // viewHolder.comment_num.setText();
        if (listBean.getParent() == null){

        }else {
            viewHolder.comment_listview.setAdapter(new ReplyAdapter(listBean.getParent(),mContext));
        }
        viewHolder.comment_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReplyListener == null) return;
                onReplyListener.setReplyClick(position);
            }
        });
        return convertView;
    }
    class ViewHolder {
        TextView comment_title,comment_time,comment_num,comment_name,comment_reply;
        ImageView comment_image;
        MyListView comment_listview;
    }

    public void setOnReplyListener(CommunityConfig.OnReplyListener onReplyListener) {
        this.onReplyListener = onReplyListener;
    }
}
