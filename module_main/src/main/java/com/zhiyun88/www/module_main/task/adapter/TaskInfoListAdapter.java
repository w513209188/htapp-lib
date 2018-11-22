package com.zhiyun88.www.module_main.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.utils.ToActivityUtil;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.dotesting.ui.CommonTestActivity;
import com.zhiyun88.www.module_main.dotesting.ui.CuntActivity;
import com.zhiyun88.www.module_main.task.bean.TaskData;
import com.zhiyun88.www.module_main.task.ui.TaskInfoActivity;

import java.util.List;

public class TaskInfoListAdapter extends ListBaseAdapter<TaskData> {
    private Context mContext;
    private String taskId;
    public TaskInfoListAdapter(List<TaskData> list, Context context,String taskI) {
        super(list, context);
        this.mContext=context;
        this.taskId=taskI;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskInfoListHolder holder=null;
        final TaskData taskData=getItem(position);
        if(convertView==null){
            holder=new TaskInfoListHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.main_taskinfolist_item,null);
            holder.title_tv=convertView.findViewById(R.id.title_tv);
            holder.type_t_img=convertView.findViewById(R.id.type_t_img);
            holder.type_img=convertView.findViewById(R.id.type_img);
            holder.progress_tv=convertView.findViewById(R.id.progress_tv);
            holder.wc_t_img=convertView.findViewById(R.id.wc_t_img);
            holder.rest_rel=convertView.findViewById(R.id.rest_rel);
            holder.rest_test_tv=convertView.findViewById(R.id.rest_test_tv);
            holder.look_result_tv=convertView.findViewById(R.id.look_result_tv);
            convertView.setTag(holder);
        }else {
            holder= (TaskInfoListHolder) convertView.getTag();
        }
        holder.title_tv.setText(taskData.getName());

        holder.look_result_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看结果
                if(taskData.getStates()==1){
                    Toast.makeText(mContext,"报告不能查看！",Toast.LENGTH_LONG).show();
                }else {
                    ToActivityUtil.newInsance().toNextActivity(mContext, CuntActivity.class,new String[][]{{"reportId",taskData.getReport_id()},{"testId",taskData.getId()},{"taskId",taskId},{"testName",taskData.getName()}});
                }

            }
        });

        holder.rest_test_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重考

                if(taskData.getAgain_number()==0){
                    Toast.makeText(mContext,"考试次数用尽！",Toast.LENGTH_LONG).show();
                }else {
                    ToActivityUtil.newInsance().toNextActivity(mContext, CommonTestActivity.class,new String[][]{{"testId",taskData.getId()},{"taskId",taskId},{"testType",taskData.getType().equals("3")?"1":"2"},{"testName",taskData.getName()}});
                }
            }
        });


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
            holder.wc_t_img.setVisibility(View.VISIBLE);
            holder.rest_rel.setVisibility(View.GONE);
            holder.progress_tv.setVisibility(View.VISIBLE);
        }else  if(taskData.getType().equals("2")){
//考试
            holder.type_t_img.setImageResource(R.drawable.ks_t_img);
            holder.type_img.setImageResource(R.drawable.ks_p_img);
            holder.progress_tv.setText("完成"+taskData.getComplete()+"%");

            holder.wc_t_img.setVisibility(View.GONE);
            holder.rest_rel.setVisibility(View.VISIBLE);
            holder.progress_tv.setVisibility(View.GONE);

            holder.rest_test_tv.setText("重考"+taskData.getAgain_number());
        }else  if(taskData.getType().equals("3")){
//问卷
            holder.type_t_img.setImageResource(R.drawable.wj_t_img);
            holder.type_img.setImageResource(R.drawable.wj_p_img);
            holder.progress_tv.setText("完成"+taskData.getComplete()+"%");


            holder.wc_t_img.setVisibility(View.VISIBLE);
            holder.rest_rel.setVisibility(View.GONE);
            holder.progress_tv.setVisibility(View.VISIBLE);
//            holder.wc_t_img.setVisibility(View.GONE);
//            holder.rest_rel.setVisibility(View.VISIBLE);
//            holder.progress_tv.setVisibility(View.GONE);
//            holder.rest_test_tv.setText("重考"+taskData.getAgain_number());

        }else  if(taskData.getType().equals("4")){
//课程
            holder.type_t_img.setImageResource(R.drawable.kc_t_img);
            holder.type_img.setImageResource(R.drawable.kc_p_img);
            holder.progress_tv.setText("完成"+taskData.getComplete()+"%");

            holder.wc_t_img.setVisibility(View.VISIBLE);
            holder.rest_rel.setVisibility(View.GONE);
            holder.progress_tv.setVisibility(View.VISIBLE);
        }
        try {
            holder.wc_t_img.setVisibility(taskData.getComplete().equals("100")?View.VISIBLE:View.GONE);
        }catch (Exception e){
            holder.wc_t_img.setVisibility(View.GONE);
        }

        return convertView;
    }
    class TaskInfoListHolder{
        TextView title_tv,progress_tv,rest_test_tv,look_result_tv;
        ImageView type_t_img,type_img,wc_t_img;
        RelativeLayout rest_rel;

    }
}
