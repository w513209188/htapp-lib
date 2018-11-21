package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.ParentCommentBean;


public class ReplyAdapter extends BaseAdapter {
    private Context mContext;
    private ParentCommentBean parentCommentBean;

    public ReplyAdapter(ParentCommentBean parentCommentBean, Context context) {
       this.mContext = context;
       this.parentCommentBean = parentCommentBean;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_reply, null);
            viewHolder.reply_name = convertView.findViewById(R.id.reply_name);
            viewHolder.reply_time = convertView.findViewById(R.id.reply_time);
            viewHolder.reply_content = convertView.findViewById(R.id.reply_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (parentCommentBean.getIs_anonymity().equals("1")) {
            viewHolder.reply_name.setText("匿名");
        }else {
            viewHolder.reply_name.setText(parentCommentBean.getUser_name());
        }
        viewHolder.reply_time.setText(parentCommentBean.getCreated_at()+"的原帖:");
        viewHolder.reply_content.setText(parentCommentBean.getContent());
        return convertView;
    }
    class ViewHolder {
        TextView reply_name,reply_time,reply_content;
    }

}
