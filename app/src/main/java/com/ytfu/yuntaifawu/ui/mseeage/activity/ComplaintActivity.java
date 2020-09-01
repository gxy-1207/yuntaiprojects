package com.ytfu.yuntaifawu.ui.mseeage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.mseeage.bean.ComplintBean;
import com.ytfu.yuntaifawu.ui.mseeage.presenter.ComplaintPresenter;
import com.ytfu.yuntaifawu.ui.mseeage.view.ComplaintView;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/** @Auther gxy @Date 2020/4/20 @Des 我要投诉 */
@InjectLayout(
        value = R.layout.activity_complaint,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(ComplaintPresenter.class)
public class ComplaintActivity extends BaseActivity<ComplaintView, ComplaintPresenter>
        implements ComplaintView {

    @BindView(R.id.et_shuru)
    EditText etShuru;

    @BindView(R.id.tv_geshu)
    TextView tvGeshu;

    @BindView(R.id.tv_tijiao)
    TextView tvTijiao;

    private static final String KEY_LAWYER_ID = "LAWYER_ID";

    public static void start(Context context, String lawyerId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LAWYER_ID, lawyerId);
        Intent starter = new Intent(context, ComplaintActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        changeStatusBarTextColor(true);
        setToolbarBackgroud(Color.WHITE);
        setToolbarLeftImage(R.drawable.fanhui_hui, view -> onBackPressed());
        setToolbarTextColor(
                R.id.tv_global_title, getResources().getColor(R.color.textColoe_303030));
        setToolbarText(R.id.tv_global_title, "我要投诉");
        etShuru.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        Editable text = etShuru.getText();
                        int length = text.length();
                        tvGeshu.setText(length + "" + "/200字");
                        if (length > 200) {
                            ToastUtil.showCenterToast("超出字数限制");
                            int selEndIndex = Selection.getSelectionEnd(text);
                            String str = text.toString();
                            // 截取新字符串
                            String newStr = str.substring(0, 200);
                            etShuru.setText(newStr);
                            text = etShuru.getText();

                            // 新字符串的长度
                            int newLen = text.length();
                            // 旧光标位置超过字符串长度
                            if (selEndIndex > newLen) {
                                selEndIndex = text.length();
                            }
                            // 设置新光标所在的位置
                            Selection.setSelection(text, selEndIndex);
                        }
                    }
                });
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        tvTijiao.setOnClickListener(
                view -> {
                    String lawyerId = getBundleString(KEY_LAWYER_ID, "");
                    String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                    String content = etShuru.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        showToast("请输入投诉内容");
                        return;
                    }
                    getPresenter().setComplaint(uid, lawyerId, content);
                });
    }

    @Override
    public void onComplaintSuccess(ComplintBean complintBean) {
        finish();
    }

    @Override
    public void onComplaintFiled() {}
}
