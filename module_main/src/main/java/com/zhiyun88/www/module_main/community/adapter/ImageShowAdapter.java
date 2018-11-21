package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISListConfig;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.ui.ReleaseTopicActivity;

import java.util.List;

public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder>{
    private Context mContext;
    private List<String> pathList;

    public ImageShowAdapter(Context context, List<String> pathList) {
        this.mContext = context;
        this.pathList= pathList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_item_image, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == pathList.size()) {
            holder.imageView.setImageResource(R.drawable.add_image);
        }else {
            Picasso.with(mContext).load(pathList.get(position)).placeholder(R.drawable.course_image).error(R.drawable.course_image).into(holder.imageView);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathList.size() < 9 && position == pathList.size()) {
                    ISListConfig config = new ISListConfig.Builder()
                            .multiSelect(true)
                            .rememberSelected(true)
                            .maxNum(9)
                            .needCamera(true)
                            // .backResId()
                            .build();
                    ISNav.getInstance().toListActivity(mContext, config, 666);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pathList.size() < 9?pathList.size()+1 :pathList.size();
    }

    public void setData(List<String> data) {
        this.pathList = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.show_image);
        }
    }
}
