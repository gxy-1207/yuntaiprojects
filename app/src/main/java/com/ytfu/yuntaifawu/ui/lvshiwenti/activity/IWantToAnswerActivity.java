package com.ytfu.yuntaifawu.ui.lvshiwenti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.IWantToAnswerBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.presenter.IWantToAnswerPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwenti.view.IWantToAnswerView;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
import com.ytfu.yuntaifawu.utils.GlideManager;
import com.ytfu.yuntaifawu.utils.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;

/** @Auther gxy @Date 2020/6/18 @Des 我要回答 */
@InjectLayout(
        value = R.layout.activity_i_want_to_ansure,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(IWantToAnswerPresenter.class)
public class IWantToAnswerActivity extends BaseActivity<IWantToAnswerView, IWantToAnswerPresenter>
        implements IWantToAnswerView {
    private static final int MAX_INPUT = 300;

    @BindView(R.id.iv_header)
    ImageView ivHeader;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_type)
    TextView tvType;

    @BindView(R.id.ll_name)
    LinearLayout llName;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.iv_urgent)
    ImageView ivUrgent;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_connect)
    TextView tvConnect;

    @BindView(R.id.tv_num)
    TextView tvNum;

    @BindView(R.id.edit_text)
    EditText editText;

    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private static final String KEY_BEAN = "BEAN";

    public static void start(Context context, ConsultationDetailsBean.ContentBean contentBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_BEAN, contentBean);
        Intent starter = new Intent(context, IWantToAnswerActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "我要回答");
        editText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        String input = editText.getText().toString().trim();
                        if (input.length() > MAX_INPUT) {
                            String newStr = input.substring(0, MAX_INPUT);
                            editText.setText(newStr);
                        }
                        tvNum.setText(input.length() + "/" + MAX_INPUT);
                    }
                });
        ConsultationDetailsBean.ContentBean contentBean =
                (ConsultationDetailsBean.ContentBean) getBundleParcelable(KEY_BEAN);
        GlideManager.loadImageByUrl(this, contentBean.getAvatar(), ivHeader);
        tvName.setText(contentBean.getUser_login());
        tvTime.setText(contentBean.getConsult_date());
        tvType.setText(contentBean.getConsult_type());
        tvConnect.setText(contentBean.getConsult_content());
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            showCenterToast("请输入回答的内容");
        } else {
            showWaitingDialog("", true);
            ConsultationDetailsBean.ContentBean contentBean =
                    (ConsultationDetailsBean.ContentBean) getBundleParcelable(KEY_BEAN);
            String lsid = SpUtil.getString(mContext, AppConstant.UID, "");
            String consultid = contentBean.getId();
            String content = editText.getText().toString().trim();
            getPresenter().getIWantToAnswer(consultid, lsid, content);
        }
    }

    @Override
    public void iWantToAnswerSuccess(IWantToAnswerBean wantToAnswerBean) {
        hideWaitingDialog();
        if (wantToAnswerBean != null) {
            int code = wantToAnswerBean.getCode();
            if (code == 200) {
                finish();
            }
        }
    }
}
