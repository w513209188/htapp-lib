package com.zhiyun88.www.module_main.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wb.baselib.adapter.ListBaseAdapter;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.task.bean.TaskData;

import java.util.List;

public class TaskInfoListAdapter extends ListBaseAdapter<TaskData> {
    private Context mContext;
    public TaskInfoListAdapter(List<TaskData> list, Context context) {
        super(list, context);
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskInfoListHolder holder=null;
        TaskData taskData=getItem(position);
        if(convertView==null){
            holder=new TaskInfoListHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.main_taskinfolist_item,null);
            holder.title_tv=convertView.findViewById(R.id.title_tv);
            holder.type_t_img=convertView.findViewById(R.id.type_t_img);
            holder.type_img=convertView.findViewById(R.id.type_img);
            holder.progress_tv=convertView.findViewById(R.id.progress_tv);
            holder.wc_t_img=convertView.findViewById(R.id.wc_t_img);
            convertView.setTag(holder);
        }else {
            holder= (TaskInfoListHolder) convertView.getTag();
        }
        holder.title_tv.setText(taskData.getName());
        if(taskData.getType().equals("1")){
        //直播
            holder.type_t_img.setImageResource(R.drawable.kc_t_img);
            holder.type_img.setImageResource(R.drawable.zb_p_img);
            if(taskData.getVideo_states().equals("1")){
                //已结束
                holder.progress_tv.setText("已结束");
            }else  if(taskData.getVideo_states().equals("2")){
                //未开始
                holder.progress_tv.setText("未开始");
            }else  if(taskData.getVideo_states().equals("3")){
                //正在直播
                holder.progress_tv.setText("正在直播");
            }

        }else  if(taskData.getType().equals("2")){
//考试
            holder.type_t_img.setImageResource(R.drawable.ks_t_img);
            holder.type_img.setImageResource(R.drawable.ks_p_img);
            holder.progress_tv.setText("完成"+taskData.getComplete()+"%");
        }else  if(taskData.getType().equals("3")){
//问卷
            holder.type_t_img.setImageResource(R.drawable.wj_t_img);
            holder.type_img.setImageResource(R.drawable.wj_p_img);
            holder.progress_tv.setText("完成"+taskData.getComplete()+"%");
        }else  if(taskData.getType().equals("4")){
//课程
            holder.type_t_img.setImageResource(R.drawable.kc_t_img);
            holder.type_img.setImageResource(R.drawable.kc_p_img);
            holder.progress_tv.setText("完成"+taskData.getComplete()+"%");
        }
        try {
            holder.wc_t_img.setVisibility(taskData.getComplete().equals("100")?View.VISIBLE:View.GONE);
        }catch (Exception e){
            holder.wc_t_img.setVisibility(View.GONE);
        }

        return convertView;
    }
    class TaskInfoListHolder{
        TextView title_tv,progress_tv;
        ImageView type_t_img,type_img,wc_t_img;
    }
}
