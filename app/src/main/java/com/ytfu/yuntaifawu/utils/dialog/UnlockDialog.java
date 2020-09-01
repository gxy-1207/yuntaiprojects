package com.ytfu.yuntaifawu.utils.dialog;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;

/**
 * @Auther gxy
 * @Date 2020/6/17
 * @Des 解锁提示弹框
 */
public class UnlockDialog extends CenterPopupView {

    private ImageView iv_close;
    private TextView tv_unlock_now;
    private String unlockTitle;
    private String unlockDescription;
    private String unlockViceTitle;
    private String unlockViceDescription;

    public UnlockDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_unlock;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        iv_close = findViewById(R.id.iv_close);
        tv_unlock_now = findViewById(R.id.tv_unlock_now);
    }

    // 设置最大宽度，看需要而定
    @Override
    protected int getMaxWidth() {
        return XPopupUtils.getWindowWidth(getContext()) - 200;
    }

    @Override
    public int getMinimumWidth() {
        return XPopupUtils.getWindowWidth(getContext()) - 200;
    }

    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    //关闭点击
    public void setCloseClick(OnClickListener listener) {
        iv_close.setOnClickListener(listener);
    }

    //解锁点击
    public void setUnlockNowClick(OnClickListener listener) {
        tv_unlock_now.setOnClickListener(listener);
    }

    public void prepare() {
        TextView tv_price = findViewById(R.id.tv_price);
        tv_price.setText(unlockTitle);
        TextView tv_private_chat = findViewById(R.id.tv_private_chat);
        tv_private_chat.setText(unlockDescription);
        TextView tv_once = findViewById(R.id.tv_once);
        tv_once.setText(unlockViceTitle);
        TextView tv_all = findViewById(R.id.tv_all);
        tv_all.setText(unlockViceDescription);
    }

    public String getUnlockTitle() {
        return unlockTitle;
    }

    public void setUnlockTitle(String unlockTitle) {
        this.unlockTitle = unlockTitle;
    }

    public String getUnlockDescription() {
        return unlockDescription;
    }

    public void setUnlockDescription(String unlockDescription) {
        this.unlockDescription = unlockDescription;
    }

    public String getUnlockViceTitle() {
        return unlockViceTitle;
    }

    public void setUnlockViceTitle(String unlockViceTitle) {
        this.unlockViceTitle = unlockViceTitle;
    }

    public String getUnlockViceDescription() {
        return unlockViceDescription;
    }

    public void setUnlockViceDescription(String unlockViceDescription) {
        this.unlockViceDescription = unlockViceDescription;
    }
}
