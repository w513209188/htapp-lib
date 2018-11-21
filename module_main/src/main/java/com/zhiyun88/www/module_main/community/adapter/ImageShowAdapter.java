package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.wb.baselib.image.GlideManager;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.config.ISListConfig;
import com.zhiyun88.www.module_main.R;

import java.util.List;

public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder>{
    private Context mContext;
    private List<String> pathList;

    public ImageShowAdapter(Context context, List<String> pathList) {
        this.mContext = context;
        this.pathList= pathList;
        Log.e("ImageShowAdapter: ", pathList.size()+"");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_item_image, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("onBindViewHolder: ", position+"||||||"+pathList.size());
        if (position == pathList.size()) {
            holder.imageView.setImageResource(R.drawable.add_image);
        }else {
            GlideManager.getInstance().setCommonPhoto(holder.imageView, R.drawable.course_image, mContext, pathList.get(position), false);
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
