package com.ytfu.yuntaifawu.utils.dialog;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;

public class AdoptionDialog extends CenterPopupView {

    private TextView tv_ok;
    private TextView tv_cancel;

    public AdoptionDialog(@NonNull Context context) {
        super(context);
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_adoption;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_ok = findViewById(R.id.tv_ok);
        tv_cancel = findViewById(R.id.tv_cancel);
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

    public void setOkClick(OnClickListener listener) {
        tv_ok.setOnClickListener(listener);
    }

    public void setCancelClick(OnClickListener listener) {
        tv_cancel.setOnClickListener(listener);
    }
}
