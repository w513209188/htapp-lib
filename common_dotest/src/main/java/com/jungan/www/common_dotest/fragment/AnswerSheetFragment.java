package com.jungan.www.common_dotest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.adapter.AnswerSheetAdapter;
import com.jungan.www.common_dotest.adapter.AnswerSheetItemAdapter;
import com.jungan.www.common_dotest.base.LazyFragment;
import com.jungan.www.common_dotest.bean.AnswerSheetBean;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.call.AnswerSheetCall;
import com.jungan.www.common_dotest.view.mGriview;

import java.util.ArrayList;
import java.util.List;

public class AnswerSheetFragment extends LazyFragment {
    private List<QuestionBankBean> questionBankBeanList;
    public List<QuestionBankBean> getQuestionBankBeanList() {
        return questionBankBeanList;
    }
    private AnswerSheetItemAdapter mAdapter;
    private List<AnswerSheetBean> answerSheetBeanLists;
    private ListView griview;
    private TextView post_test_tv;
    public static AnswerSheetFragment answerSheetFragment;
    private AnswerSheetCall mCall;
    private boolean isShowPost;
    public static AnswerSheetFragment newInstance(List<QuestionBankBean> questionBankBeans,boolean isShowPosts){
        if(answerSheetFragment==null){
            answerSheetFragment=new AnswerSheetFragment();
        }
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("QuestionBankBean", (ArrayList<? extends Parcelable>) questionBankBeans);
        bundle.putBoolean("isShowPost",isShowPosts);
        answerSheetFragment.setArguments(bundle);
        return answerSheetFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.layou_answersheet_main);
        isShowPost=getArguments().getBoolean("isShowPost",true);
        questionBankBeanList=getArguments().getParcelableArrayList("QuestionBankBean");
        griview=getViewById(R.id.mgv);
        griview.setDivider(null);
        answerSheetBeanLists=new ArrayList<>();
        mAdapter=new AnswerSheetItemAdapter(answerSheetBeanLists,getActivity());
        griview.setAdapter(mAdapter);
        post_test_tv=getViewById(R.id.post_test_tv);
        post_test_tv.setVisibility(isShowPost?View.VISIBLE:View.GONE);
        new Thread(new reshAnswerSheet()).start();
        setListener();
    }
    public void userSelectOption(String option,int page){
        questionBankBeanList.get(page).setUser_answer(option);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        post_test_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交试卷
                if(mCall==null)
                    return;
                mCall.userPostTest();
            }
        });
    }

    class  reshAnswerSheet  implements Runnable{

        @Override
        public void run() {
            List<AnswerSheetBean> answerSheetBeans=new ArrayList<>();

            for(int i=0;i<questionBankBeanList.size();i++){
                if(i==0){
                    AnswerSheetBean answerSheetBean=new AnswerSheetBean();
                    answerSheetBean.setGroup(questionBankBeanList.get(i).getQuestionType()+"");
                    answerSheetBeans.add(answerSheetBean);
                }else {
                    if(questionBankBeanList.get(i).getQuestionType()==questionBankBeanList.get(i-1).getQuestionType()){
                    }else {
                        AnswerSheetBean answerSheetBean=new AnswerSheetBean();
                        answerSheetBean.setGroup(questionBankBeanList.get(i).getQuestionType()+"");
                        answerSheetBeans.add(answerSheetBean);
                    }
                }

            }
            for(AnswerSheetBean answerSheetBean1:answerSheetBeans){
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
                answerSheetBeanLists.clear();
                List<AnswerSheetBean> answerSheetBeanList= (List<AnswerSheetBean>) msg.obj;
                answerSheetBeanLists.addAll(answerSheetBeanList);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCall= (AnswerSheetCall) context;
    }
}
