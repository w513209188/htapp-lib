package com.zhiyun88.www.module_main.dotesting.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.bean.UserOptionBean;
import com.jungan.www.common_dotest.bean.UserPostBean;
import com.jungan.www.common_dotest.bean.UserPostData;
import com.jungan.www.common_dotest.call.AnswerSheetCall;
import com.jungan.www.common_dotest.call.TestViewpageCall;
import com.jungan.www.common_dotest.call.UserLookAnalisysCall;
import com.jungan.www.common_dotest.call.ViewPageCall;
import com.jungan.www.common_dotest.fragment.AnswerSheetFragment;
import com.jungan.www.common_dotest.questionview.CommonQuestionBankView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.prase.GsonUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.DialogUtils;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.dotesting.bean.AnswerDataBean;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitTestBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CommonTestContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.presenter.CommonTestPresenter;

import java.util.ArrayList;
import java.util.List;

public class CommonTestActivity extends MvpActivity<CommonTestPresenter> implements CommonTestContranct.CommonTestView, ViewPageCall, AnswerSheetCall, TestViewpageCall, UserLookAnalisysCall {
    private CommonQuestionBankView commonQuestionBankView;
    private ImageView left_img;
    private TextView current_count_tv, test_type_tv;
    private ProgressBar progressBar;
    private int testsSize;
    private Chronometer mChronometer;
    //停止的时候的时间
    private long mRecordTime;
    private ImageView answer_sheet, test_pause;
    private String testId, taskId;
    private int testType;
    private boolean looksys = false;
    private String testName;
    //current_count
    private MultipleStatusView multiplestatusview;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.dotest_commontest_layout);
        multiplestatusview=getViewById(R.id.multiplestatusview);
        multiplestatusview.showContent();
        multiplestatusview.showLoading();
        commonQuestionBankView = getViewById(R.id.dotest_lv);
        testId = getIntent().getStringExtra("testId");
        taskId = getIntent().getStringExtra("taskId");
        testType = Integer.parseInt(getIntent().getStringExtra("testType"));
        testName = getIntent().getStringExtra("testName");
        left_img = getViewById(R.id.left_img);
        mChronometer = getViewById(R.id.chronmer_ctr);
        current_count_tv = getViewById(R.id.current_count_tv);
//        current_count = getViewById(R.id.current_count);
        answer_sheet = getViewById(R.id.answer_sheet);
        test_type_tv = getViewById(R.id.test_type_tv);
        test_pause = getViewById(R.id.test_pause);
        progressBar = getViewById(R.id.progressBar);
        if(testType==3||testType==4){
            mChronometer.setVisibility(View.GONE);
            test_pause.setVisibility(View.GONE);
        }
        mPresenter.getCommonTest(testId, taskId, testType);
        test_type_tv.setText(testName);
    }

    @Override
    public void SuccessData(Object o) {
        List<QuestionBankBean> questionBankBeans = (List<QuestionBankBean>) o;
        testsSize = questionBankBeans.size();
//        current_count_tv.setText("1");
        current_count_tv.setText("1/" + testsSize);
        progressBar.setMax(testsSize);
        progressBar.setProgress(1);
        commonQuestionBankView.initData(questionBankBeans, getSupportFragmentManager(), testType==3||testType==4?true:false,false);
        isStartJs();
        multiplestatusview.showContent();
    }

  /*   @Override
    public void SuccessPostTest(String type) {
       if(type.equals("1")){
            //强制提交
            if(testType==TestTypeConfig.MNKS){
                ARouter.getInstance().build("/count/mkcount").withString("reportId",commonQuestionBankView.getQuestionBankBeanList().get(0).getReport_id()+"").navigation();
            }else if(testType==TestTypeConfig.ALLAGAIN||testType==TestTypeConfig.ZNST||testType==TestTypeConfig.ZJLX||testType==TestTypeConfig.MRYT||testType==TestTypeConfig.ZJLXAGAIN){
                ARouter.getInstance().build("/count/commoncount").withString("reportId",commonQuestionBankView.getQuestionBankBeanList().get(0).getReport_id()+"").navigation();
            }
        }
        finish();
    }*/

 /*   @Override
    public void SuccessSave(int isColl) {
     //   save_img.setImageResource(isColl==0?R.drawable.public_collection:R.drawable.public_collection_y);
        commonQuestionBankView.userSaveTest(isColl);
    }*/

    @Override
    protected CommonTestPresenter onCreatePresenter() {
        return new CommonTestPresenter(this);
    }

    @Override
    protected void setListener() {
        left_img.setOnClickListener(this);
        commonQuestionBankView.setmCall(new TestViewpageCall() {
            @Override
            public void currentPage(int c) {
                Log.e("查看这个调用次数", "--------");
                if (c + 1 <= testsSize) {
                    current_count_tv.setText((c + 1) + "/"+testsSize);
                    progressBar.setProgress(c + 1);
                }
                if (looksys) {
                    if (mRecordTime != 0) {
                        mChronometer.setBase(mChronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                    } else {
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                    }
                    mChronometer.start();
                    looksys = false;
                }

            }
        });
        mChronometer.setOnClickListener(this);
        answer_sheet.setOnClickListener(this);
        test_pause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.left_img) {
            exitTest();
        } else {
            if (commonQuestionBankView == null)
                return;
            if (commonQuestionBankView.getQuestionBankBeanList() == null || commonQuestionBankView.getQuestionBankBeanList().size() == 0)
                return;
            if (commonQuestionBankView.currentPage() > commonQuestionBankView.getQuestionBankBeanList().size() - 1)
                return;
            if (v.getId() == R.id.test_pause) {
                DialogUtils.newInstance().initDialog(CommonTestActivity.this)
                        .setContent("休息一下,继续答题")
                        .setbtncentre("继续答题")
                        .hitBtn(true)
                        .setOnCentreClickListenter(new DialogUtils.OnCentreClickListenter() {
                            @Override
                            public void setCentreClickListener() {
                                commonQuestionBankView.setPause(false);
                                if (mRecordTime != 0) {
                                    mChronometer.setBase(mChronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                                } else {
                                    mChronometer.setBase(SystemClock.elapsedRealtime());
                                }
                                mChronometer.start();
                            }
                        });
                mChronometer.stop();
                mRecordTime = SystemClock.elapsedRealtime();
                commonQuestionBankView.setPause(true);
            } else if (v.getId() == R.id.answer_sheet) {
                Intent intent = new Intent(CommonTestActivity.this, AnswerSheetActivity.class);
                intent.putExtra("questionBankBeanList", (ArrayList) commonQuestionBankView.getQuestionBankBeanList());
                startActivityForResult(intent, 1003);
            } else if (v.getId() == R.id.chronmer_ctr) {

            }
        }

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void showErrorMsg(String msg) {
        showLongToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void CutCurrentViewPage(String userSelectOption) {
        Log.e("获取到的选择", userSelectOption + "---");
        commonQuestionBankView.cutCurrentQuestion(userSelectOption);
        AnswerSheetFragment.answerSheetFragment.userSelectOption(userSelectOption, commonQuestionBankView.currentPage());
    }

    @Override
    public void userPostTest() {
        mChronometer.stop();
        mRecordTime = SystemClock.elapsedRealtime();
        commonQuestionBankView.setPause(true);
        if (commonQuestionBankView.getUserDoTestNum() < commonQuestionBankView.getQuestionBankBeanList().size()) {
            DialogUtils.newInstance().initDialog(CommonTestActivity.this)
                    .setContent("您还有题目未作答,确认交卷吗?")
                    .setOnClickListenter(new DialogUtils.OnClickListener() {
                        @Override
                        public void setYesClickListener() {
                            //提交试卷
                            commitTest();
                        }

                        @Override
                        public void setNoClickListener() {
                            commonQuestionBankView.setPause(false);
                            if (mRecordTime != 0) {
                                mChronometer.setBase(mChronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                            } else {
                                mChronometer.setBase(SystemClock.elapsedRealtime());
                            }
                            mChronometer.start();
                        }
                    });
        } else {
            mChronometer.stop();
            mRecordTime = SystemClock.elapsedRealtime();
            commonQuestionBankView.setPause(true);
            commitTest();

        }

    }

    private void commitTest() {
        UserPostBean u =commonQuestionBankView.getUserPostBean();
        Log.e("用户提交了数据", "-----" + GsonUtils.newInstance().GsonToString(u));
        SubmitTestBean submitTestBean = new SubmitTestBean();
        submitTestBean.setAnswer_data(u.getAnswer_data());
        submitTestBean.setAnswer_time(getChronometerSeconds(mChronometer));
        submitTestBean.setReport_id(u.getReport_id());
        if (testType == 1) {
                submitTestBean.setType("2");
            }else if (testType == 2){
                submitTestBean.setType("1");
            }
        mPresenter.submitTest(submitTestBean);

//        for (QuestionBankBean questionBankBean : questionBankBeanList) {
//            submitTestBean.setReport_id(questionBankBean.getReport_id());
//            submitTestBean.setAnswer_time(getChronometerSeconds(mChronometer));
//            if (testType == 1) {
//                submitTestBean.setType("2");
//            }else if (testType == 2){
//                submitTestBean.setType("1");
//            }
//            ArrayList<AnswerDataBean> list = new ArrayList<>();
//
//            for (UserPostData userPostData :commonQuestionBankView.getUserPostBean().getAnswer_data()) {
//                AnswerDataBean answerDataBean = new AnswerDataBean();
//                answerDataBean.setQues_id(userPostData.getQues_id());
//                answerDataBean.setQues_time(userPostData.getUser_time());
//                answerDataBean.setUser_answer(userPostData.getUser_answer());
//                list.add(answerDataBean);
//            }
//            submitTestBean.setAnswer_data(list);
//        }
//        String json = GsonUtils.newInstance().GsonToString(questionBankBeanList);
//        Log.e("用户提交了数据", "-----" + GsonUtils.newInstance().GsonToString(json));
//        mPresenter.submitTest(submitTestBean.getReport_id(), submitTestBean.getType(),submitTestBean.getAnswer_time(),json );
    }

    /**
     * 开始试卷的计时
     */
    private void isStartJs() {
        if(testType==3||testType==4)
            return;

        if (commonQuestionBankView.getQuestionBankBeanList() == null || commonQuestionBankView.getQuestionBankBeanList().size() == 0)
            return;
        if (mChronometer == null)
            return;
        if (mRecordTime != 0) {
            mChronometer.setBase(mChronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
        } else {
            mChronometer.setBase(SystemClock.elapsedRealtime());
        }
        mChronometer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChronometer == null)
            return;
        if(testType==3||testType==4)
            return;
        isStartJs();
//        commonQuestionBankView.setPause(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mChronometer == null)
            return;
        if (commonQuestionBankView.getQuestionBankBeanList() == null || commonQuestionBankView.getQuestionBankBeanList().size() == 0) {
            return;
        }
        if(testType==3||testType==4)
            return;
        mChronometer.stop();
        mRecordTime = SystemClock.elapsedRealtime();
        commonQuestionBankView.setPause(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(testType==3||testType==4)
            return;
        commonQuestionBankView.setPause(true);
    }

    private void exitTest() {
        if(testType==3||testType==4){
            finish();
            return;
        }
        if (commonQuestionBankView.getUserPostBean() == null || commonQuestionBankView.getUserPostBean().getAnswer_data() == null || commonQuestionBankView.getUserPostDataList().size() == 0) {
            finish();
        } else {
            mChronometer.stop();
            mRecordTime = SystemClock.elapsedRealtime();
            commonQuestionBankView.setPause(true);
            DialogUtils.newInstance().initDialog(CommonTestActivity.this)
                    .setContent("退出默认放弃本次考试")
                    .setOnClickListenter(new DialogUtils.OnClickListener() {
                        @Override
                        public void setYesClickListener() {
                            finish();
                        }

                        @Override
                        public void setNoClickListener() {
                            commonQuestionBankView.setPause(false);
                            if (mRecordTime != 0) {
                                mChronometer.setBase(mChronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                            } else {
                                mChronometer.setBase(SystemClock.elapsedRealtime());
                            }
                            mChronometer.start();
                        }
                    });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exitTest();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void currentPage(int c) {
        if (c + 1 <= testsSize) {
            commonQuestionBankView.setTestPage(c);
            current_count_tv.setText((c + 1) + "/" + testsSize);
            progressBar.setProgress(c + 1);
            //       save_img.setImageResource(commonQuestionBankView.getQuestionBankBeanList().get(c).getIsCollect()==0?R.drawable.public_collection:R.drawable.public_collection_y);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("回调进来了", requestCode + "---" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1003:
                    int type = data.getIntExtra("type", 2);
                    if (type == 1) {
                        //提交
                        handler.sendEmptyMessageDelayed(1, 100);
                    } else {
                        //跳转
                        int page = data.getIntExtra("page", 0);
                        currentPage(page);
                    }

                    break;
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    userPostTest();
                    break;
            }
        }
    };


    @Override
    public void lookAnasysli(boolean isLook) {
        if (isLook) {
            looksys = true;
            commonQuestionBankView.setPause(true);
            mChronometer.stop();
            mRecordTime = SystemClock.elapsedRealtime();
        }

    }

    @Override
    public void lookMoreAnswer(String wdId) {

    }

    public String getChronometerSeconds(Chronometer cmt) {
        int totalss = 0;
        String string = cmt.getText().toString();
        if (string.length() == 7) {

            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours = hour * 3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[2]);
            totalss = Hours + Mins + SS;
            return String.valueOf(totalss);
        } else if (string.length() == 5) {

            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[1]);

            totalss = Mins + SS;
            return String.valueOf(totalss);
        }
        return String.valueOf(totalss);


    }

    @Override
    public void submitSuccess(String msg) {
        showDidlogMsg(msg);
    }

    @Override
    public void showDidlogMsg(String msg) {
        DialogUtils.newInstance().initDialog(CommonTestActivity.this)
                .setContent(msg)
                .setbtncentre("确定")
                .hitBtn(true)
                .setOnCentreClickListenter(new DialogUtils.OnCentreClickListenter() {
                    @Override
                    public void setCentreClickListener() {
                       finish();
                    }
                });



//        StyledDialog.buildMdAlert("信息", msg, new MyDialogListener() {
//            @Override
//            public void onFirst() {
//
//            }
//
//            @Override
//            public void onSecond() {
//                finish();
//            }
//        }).setBtnText("", "确定").setCancelable(false,false).show();
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }
}
