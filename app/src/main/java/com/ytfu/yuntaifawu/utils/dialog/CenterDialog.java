package com.ytfu.yuntaifawu.utils.dialog;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;

public class CenterDialog extends CenterPopupView {

    private TextView tv_play_free;
    private TextView tv_play_money;
    private int lawyerCount;
    private int sum;

    public CenterDialog(@NonNull Context context) {
        super(context);
    }


    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_center;
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        tv_play_free = findViewById(R.id.tv_play_free);
        tv_play_money = findViewById(R.id.tv_play_money);
    }

    // 设置最大宽度，看需要而定
    @Override
    protected int getMaxWidth() {
        return XPopupUtils.getWindowWidth(getContext()) - 150;
    }

    @Override
    public int getMinimumWidth() {
        return XPopupUtils.getWindowWidth(getContext()) - 150;
    }

    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }
    //===Desc:================================================================================

    public void setFreeClick(OnClickListener listener) {
        tv_play_free.setOnClickListener(listener);
    }

    public void setMoneyClick(OnClickListener listener) {
        tv_play_money.setOnClickListener(listener);
    }

    public void prepare() {
        TextView tv_lawyer_count = findViewById(R.id.tv_lawyer_count);
        tv_lawyer_count.setText("当前在线" + lawyerCount + "名律师");

        TextView tv_play_sum = findViewById(R.id.tv_play_sum);
        Spanned fromHtml = Html.fromHtml("等待解答的免费咨询问题共<font color='#FFEF6A6A'>" + sum + "</font>件！选择悬赏咨询，律师十秒内优先为您解答！99%的问题得到了满意解决方案！");
        tv_play_sum.setText(fromHtml);

    }

    public void setLawyerCount(int count) {
        this.lawyerCount = count;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
