package com.zhiyun88.www.module_main.dotesting.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jungan.www.common_dotest.adapter.AnswerSheetItemAdapter;
import com.jungan.www.common_dotest.bean.AnswerSheetBean;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.call.TestViewpageCall;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;

import java.util.ArrayList;
import java.util.List;

public class AnswerSheetActivity extends BaseActivity implements TestViewpageCall{
    private List<QuestionBankBean> questionBankBeanList;
    public List<QuestionBankBean> getQuestionBankBeanList() {
        return questionBankBeanList;
    }
    private AnswerSheetItemAdapter mAdapter;
    private List<AnswerSheetBean> answerSheetBeanLists;
    private ListView griview;
    private TextView post_test_tv;
    private TopBarView dtk_tb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(Color.WHITE,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dotest_answersheet_layout);
        questionBankBeanList=getIntent().getParcelableArrayListExtra("questionBankBeanList");
        griview=getViewById(com.jungan.www.common_dotest.R.id.mgv);
        griview.setDivider(null);
        dtk_tb=getViewById(R.id.dtk_tb);
        answerSheetBeanLists=new ArrayList<>();
        mAdapter=new AnswerSheetItemAdapter(answerSheetBeanLists,AnswerSheetActivity.this);
        griview.setAdapter(mAdapter);
        post_test_tv=getViewById(com.jungan.www.common_dotest.R.id.post_test_tv);
        new Thread(new reshAnswerSheet()).start();
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        dtk_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(action==TopBarView.ACTION_LEFT_BUTTON){
                    finish();
                }
            }
        });
        post_test_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("type",1);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void currentPage(int c) {
        Intent intent=new Intent();
        intent.putExtra("type",2);
        intent.putExtra("page",c);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    class  reshAnswerSheet  implements Runnable{
        @Override
        public void run() {
            Log.e("questionBankBeanList",questionBankBeanList.size()+"---");
            List<AnswerSheetBean> answerSheetBeans=new ArrayList<>();
            for(int i=0;i<questionBankBeanList.size();i++){
                if(i==0){
                    AnswerSheetBean answerSheetBean=new AnswerSheetBean();
                    Log.e("添加了1","---");
                    answerSheetBean.setGroup(questionBankBeanList.get(i).getQuestionType()+"");
                    answerSheetBeans.add(answerSheetBean);
                }else {
                    if(questionBankBeanList.get(i).getQuestionType()==questionBankBeanList.get(i-1).getQuestionType()){
                        Log.e("添加了3","---");
                    }else {
                        AnswerSheetBean answerSheetBean=new AnswerSheetBean();
                        answerSheetBean.setGroup(questionBankBeanList.get(i).getQuestionType()+"");
                        answerSheetBeans.add(answerSheetBean);
                        Log.e("添加了2","---");
                    }
                }

            }
            Log.e("answerSheetBeans",answerSheetBeans.size()+"****");
            for(AnswerSheetBean answerSheetBean1:answerSheetBeans){
                Log.e("获取到的试题类型",answerSheetBean1.getGroup());
                List<QuestionBankBean> questionBankBeanss=new ArrayList<>();
                for(QuestionBankBean questionBankBean:questionBankBeanList){
                    if(answerSheetBean1.getGroup().equals(questionBankBean.getQuestionType()+"")){
                        questionBankBeanss.add(questionBankBean);
                    }
                }
                answerSheetBean1.setQuestionBankBeanList(questionBankBeanss);
            }
            Message message=new Message();
            message.what=1;
            message.obj=answerSheetBeans;
            handler.sendMessage(message);
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                List<AnswerSheetBean> answerSheetBeanList= (List<AnswerSheetBean>) msg.obj;
                answerSheetBeanLists.clear();
                answerSheetBeanLists.addAll(answerSheetBeanList);
                mAdapter.notifyDataSetChanged();
            }
        }
    };
}
