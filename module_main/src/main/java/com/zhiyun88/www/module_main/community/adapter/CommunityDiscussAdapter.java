package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.IconMarginSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.utils.SpanUtil;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.DiscussListBean;
import com.zhiyun88.www.module_main.main.adapter.ListViewsAdapter;
import com.zhiyun88.www.module_main.main.bean.HomeInformationBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.thefinestartist.utils.service.ClipboardManagerUtil.getText;
import static com.thefinestartist.utils.service.ClipboardManagerUtil.setText;

public class CommunityDiscussAdapter extends ListBaseAdapter{

    public CommunityDiscussAdapter(Context context, List<DiscussListBean> discussListBeans) {
        super(discussListBeans,context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiscussListBean discussListBean = (DiscussListBean) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_item_community_discuss, null);
            viewHolder.title = convertView.findViewById(R.id.discuss_title);
            viewHolder.read = convertView.findViewById(R.id.discuss_read);
          //  viewHolder.content = convertView.findViewById(R.id.discuss_content);
            viewHolder.time = convertView.findViewById(R.id.discuss_time);
            viewHolder.group = convertView.findViewById(R.id.discuss_group);
            viewHolder.image = convertView.findViewById(R.id.discuss_image);
            viewHolder.name = convertView.findViewById(R.id.discuss_name);
            viewHolder.like = convertView.findViewById(R.id.discuss_like);
            viewHolder.comment = convertView.findViewById(R.id.discuss_comment);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (discussListBean.getIs_top().equals("1")) {
            SpanUtil.create().addSection(discussListBean.getTitle()+" ")
                    .addImage(context, R.drawable.ding)
                    .showIn(viewHolder.title);
        }else {
            viewHolder.title.setText(discussListBean.getTitle());

        }
        if (discussListBean.getIs_anonymity().equals("1")) {
            viewHolder.name.setText("匿名");
            viewHolder.image.setImageResource(R.drawable.name_no);
        }else {
            viewHolder.name.setText(discussListBean.getUser_name());
            try {
                GlideManager.getInstance().setGlideRoundTransImage(viewHolder.image,R.drawable.user_head ,context , discussListBean.getAvatar());
            }catch (Exception e){
                GlideManager.getInstance().setGlideRoundTransImage(viewHolder.image,R.drawable.user_head ,context , "http://www.baiu.com");
            }

        }
        viewHolder.read.setText(discussListBean.getRead_count());
      //  viewHolder.content.setText(discussListBean.getContent());

        viewHolder.like.setText(discussListBean.getLike_count());
        viewHolder.comment.setText(discussListBean.getComment_count());
        viewHolder.time.setText(discussListBean.getCreated_at());
        viewHolder.group.setText("来自【"+discussListBean.getGroup_name()+"】小组");
        return convertView;
    }
    class ViewHolder{
        ImageView image;
        TextView title,read,name,like,comment,time,group;
    }
}
