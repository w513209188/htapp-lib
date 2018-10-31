package com.jungan.www.common_dotest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.call.HtmlTextViewCall;
import com.jungan.www.common_dotest.utils.StrUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.LinkHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.Callback;
import com.zzhoujay.richtext.callback.LinkFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.List;

import cn.droidlover.xrichtext.XRichText;

public class HtmlTextView extends RelativeLayout {
    private boolean isOption;
    private int errorOption,noDoOption,rightOption,errorImage;
    private TextView option_tv;
    private HtmlTextViewCall htmlTextViewCall;
    private RelativeLayout main_rel;
    private XRichText mRichTv;
    private View mView;
    public void setOption(boolean option) {
        isOption = option;
        initData();
    }
    private void initData(){
        if(isOption){
            LayoutParams params = new LayoutParams(dip2px(getContext(),35),dip2px(getContext(),35));
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(dip2px(getContext(),10),dip2px(getContext(),10),0,dip2px(getContext(),10));
            option_tv.setLayoutParams(params);
            LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            params1.addRule(RelativeLayout.CENTER_VERTICAL);
            params1.addRule(RelativeLayout.RIGHT_OF,R.id.option_tv);
            params1.setMargins(dip2px(getContext(),10),dip2px(getContext(),10),dip2px(getContext(),10),dip2px(getContext(),10));
//            txt_tv.setLayoutParams(params1);
        }
        option_tv.setVisibility(isOption?VISIBLE:GONE);
    }
    public void setHtmlTextViewCall(HtmlTextViewCall htmlTextViewCall) {
        this.htmlTextViewCall = htmlTextViewCall;
    }

    public HtmlTextView(Context context) {
        this(context,null);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    private void initView(Context context,AttributeSet attributeSet){
        TypedArray array=getContext().obtainStyledAttributes(attributeSet, R.styleable.HtmlTextView);
        isOption=array.getBoolean(R.styleable.HtmlTextView_isOption,false);
        errorOption=array.getResourceId(R.styleable.HtmlTextView_errorOption,R.drawable.error_bg);
        rightOption=array.getResourceId(R.styleable.HtmlTextView_rightOption,R.drawable.right_bg);
        noDoOption=array.getResourceId(R.styleable.HtmlTextView_noDoOption,R.drawable.nodo_bg);
        errorImage=array.getResourceId(R.styleable.HtmlTextView_errorImage,0);
        mView= LayoutInflater.from(context).inflate(R.layout.layout_html,this);
        mRichTv=mView.findViewById(R.id.txt_tv);
//        txt_tv=mView.findViewById(R.id.txt_tv);
        option_tv=mView.findViewById(R.id.option_tv);
        main_rel=mView.findViewById(R.id.main_rel);
    }
    /**
     * 展示选项  正确选项
     */
    public void showRightOption(){
        option_tv.setBackgroundResource(rightOption);
        option_tv.setSelected(true);
    }
    /**
     * 展示选项  错误选项
     */
    public void showErrorOption(){
        option_tv.setBackgroundResource(errorOption);
        option_tv.setSelected(true);
    }
    /**
     * 展示选项  未选择选项
     */
    public void showNoDoOption(){
        option_tv.setBackgroundResource(noDoOption);
        option_tv.setSelected(false);
    }

    /**
     * 设置类容
     * @param txt
     */
    public void showTxt(String txt){
        final int a=100,b=50;
        mRichTv.callback(new XRichText.Callback() {
            @Override
            public void onImageClick(List<String> urlList, int position) {
                htmlTextViewCall.imageClicked(urlList, position);
//                Intent intent=new Intent();
//                intent.setClass(getContext(), LookPhotoActivity.class);
//                intent.putExtra(LookPhotoActivity.PAGE_TAG,0);
//                intent.putExtra(LookPhotoActivity.IMAGES_TAG,(Serializable) urlList);
//                getContext().startActivity(intent);
            }
            @Override
            public boolean onLinkClick(String url) {
                if(htmlTextViewCall==null){
                }else {
                    htmlTextViewCall.linkFixCall(url);
                }
                return true;
            }
            @Override
            public void onFix(XRichText.ImageHolder holder) {
                holder.setStyle(XRichText.Style.LEFT);
//                if (a > 0){
//                    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//                    int width = wm.getDefaultDisplay().getWidth();
//                    int height = wm.getDefaultDisplay().getHeight();
//                    holder.setWidth(width);
//                    holder.setHeight(width/a*b*4/3);
//                }
            }
        }).text(txt);



//                 RichText
//                .from(txt) // 数据源
//                .autoFix(false) // 是否自动修复，默认true
//                .autoPlay(true) // gif图片是否自动播放
//                .showBorder(false) // 是否显示图片边框
//                .borderColor(Color.RED) // 图片边框颜色
//                .borderSize(10) // 边框尺寸
//                .borderRadius(50) // 图片边框圆角弧度
//                .scaleType(ImageHolder.ScaleType.fit_auto) // 图片缩放方式
//                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
//                .linkFix(new LinkFixCallback() {
//                    @Override
//                    public void fix(LinkHolder holder) {
//                        if(htmlTextViewCall==null)
//                            return;
//                        htmlTextViewCall.linkFixCall(holder.getUrl());
//                    }
//                }) // 设置链接自定义回调
//                .noImage(true) // 不显示并且不加载图片
//                .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
//                .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
//                .imageClick(new OnImageClickListener() {
//                    @Override
//                    public void imageClicked(List<String> imageUrls, int position) {
//                        if(htmlTextViewCall==null)
//                            return;
//                        htmlTextViewCall.imageClicked(imageUrls, position);
//                    }
//                }) // 设置图片点击回调
//                .done(new Callback() {
//                    @Override
//                    public void done(boolean imageLoadDone) {
//                        if(htmlTextViewCall==null)
//                            return;
//                        htmlTextViewCall.done(imageLoadDone);
//                    }
//                }) // 解析完成回调
//                .into(txt_tv); // 设置目标TextView
    }

    /**
     * 根据数字自动设置 A-Z
     * @param num
     */
    public void setAuotOption(int num){
        option_tv.setText(StrUtils.Instance().numberToLetter(num));
    }

    /**
     * 用户设置选项
     * @param txt
     */
    public void setOption(String txt){
        option_tv.setText(txt);
    }

    /**
     * 根据选项 得到顺序  只有自动排序时生效
     * @param le
     * @return
     */
    public int getOptionNum(String le){
        return StrUtils.Instance().letterToNumber(le);
    }
    private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public void onClickOption(OnClickListener onClickListener){
        main_rel.setOnClickListener(onClickListener);
        option_tv.setOnClickListener(onClickListener);
        mRichTv.setOnClickListener(onClickListener);
    }
}
