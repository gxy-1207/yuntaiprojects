package com.ytfu.yuntaifawu.ui.lawyer.question.act;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lawyer.question.p.QuestionSettingPresenter;
import com.ytfu.yuntaifawu.ui.lawyer.question.v.QuestionSettingView;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.QuestionResponseBean;
import com.ytfu.yuntaifawu.ui.users.act.AnnouncementDetailsActivity;
import com.ytfu.yuntaifawu.utils.SpUtil;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

class CommitBean {
    String area;
    String autoAnswer;
    boolean isOpen;
}

/** 律师设置问题抢答 */
@InjectLayout(
        value = R.layout.activity_question_setting,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(QuestionSettingPresenter.class)
public class QuestionSettingActivity
        extends BaseActivity<QuestionSettingView, QuestionSettingPresenter>
        implements QuestionSettingView {

    private CommitBean commitBean = new CommitBean();

    @BindView(R.id.cb_setting_switch)
    CheckBox cb_setting_switch;

    private static final String KEY_QUESTION = "Question";
    private static final int CODE_SETTING_ANSWER = 1;
    private static final int CODE_SETTING_AREA = 2;

    public static void start(Context context, QuestionResponseBean.DataBean data) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_QUESTION, data);
        Intent starter = new Intent(context, QuestionSettingActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    // ===Desc:================================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting_answer, menu);
        // 设置文本颜色
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_save);
        if (null != item) {
            CharSequence title = item.getTitle();
            SpannableStringBuilder ssb = new SpannableStringBuilder(title);
            // 设置文字颜色。
            ssb.setSpan(
                    new ForegroundColorSpan(Color.WHITE),
                    0,
                    title.length(),
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            item.setTitle(ssb);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            String userId = SpUtil.getString(mContext, AppConstant.UID, "");
            boolean checked = cb_setting_switch.isChecked();
            getPresenter().saveInfo(userId, checked, commitBean.autoAnswer, commitBean.area);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        cb_setting_switch.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        animatorShowMore();
                    } else {
                        animatorHideMore();
                    }
                });

        findViewById(R.id.tv_setting_what)
                .setOnClickListener(
                        v -> {
                            QuestionResponseBean.DataBean data = getBundleParcelable(KEY_QUESTION);
                            if (null == data) {
                                showToast("应用程序异常,请稍后重试");
                                return;
                            }
                            AnnouncementDetailsActivity.start(mContext, "问题帮助", data.getHelpurl());
                        });

        findViewById(R.id.ll_setting_auto)
                .setOnClickListener(
                        v -> {
                            // 设置短语
                            String content = commitBean.autoAnswer;
                            SettingAutoAnswerActivity.startActivityForResult(
                                    QuestionSettingActivity.this, CODE_SETTING_ANSWER, content);
                        });

        findViewById(R.id.ll_setting_area)
                .setOnClickListener(
                        v -> {
                            // 设置领域
                            SettingAreaActivity.startActivityForResult(
                                    QuestionSettingActivity.this,
                                    CODE_SETTING_AREA,
                                    commitBean.area);
                        });
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, R.string.txt_question_answer);

        QuestionResponseBean.DataBean data = getBundleParcelable(KEY_QUESTION);
        if (null == data) {
            showToast("应用程序异常,请稍后重试");
            finish();
            return;
        }
        // 数据回显
        boolean isOpen = data.getStatus() == 1;
        commitBean.isOpen = isOpen;
        cb_setting_switch.setChecked(isOpen);
        View ll_setting_more = findViewById(R.id.ll_setting_more);
        if (isOpen) {
            ll_setting_more.setVisibility(View.VISIBLE);
        } else {
            ll_setting_more.setVisibility(View.GONE);
        }
        TextView tv_setting_auto = findViewById(R.id.tv_setting_auto);
        tv_setting_auto.setText(data.getContent());
        commitBean.autoAnswer = data.getContent();
        TextView tv_setting_area = findViewById(R.id.tv_setting_area);
        tv_setting_area.setText(data.getDomain());
        commitBean.area = data.getDomain();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CODE_SETTING_ANSWER:
                String autoAnswer = getBundleString(data, SettingAutoAnswerActivity.KEY_INPUT, "");
                TextView tv_setting_auto = findViewById(R.id.tv_setting_auto);
                tv_setting_auto.setText(autoAnswer);
                commitBean.autoAnswer = autoAnswer;
                break;
            case CODE_SETTING_AREA:
                String select = getBundleString(data, SettingAreaActivity.KEY_SELECT, "");
                TextView tv_setting_area = findViewById(R.id.tv_setting_area);
                tv_setting_area.setText(select);
                commitBean.area = select;
                break;
        }
    }

    @Override
    public void onSaveInfoSuccess() {
        finish();
    }

    // ===Desc:================================================================================

    private void animatorShowMore() {
        View ll_setting_more = findViewById(R.id.ll_setting_more);
        ll_setting_more.setVisibility(View.VISIBLE);
        ll_setting_more.measure(0, 0);
        int maxHeight = ll_setting_more.getMeasuredHeight();
        ValueAnimator animator = ValueAnimator.ofInt(0, maxHeight);
        animator.addUpdateListener(
                animation -> {
                    ll_setting_more.getLayoutParams().height = (int) animation.getAnimatedValue();
                    ll_setting_more.requestLayout();
                });
        animator.setDuration(200);
        animator.start();
    }

    private void animatorHideMore() {
        View ll_setting_more = findViewById(R.id.ll_setting_more);
        ll_setting_more.setVisibility(View.VISIBLE);
        ll_setting_more.measure(0, 0);
        int maxHeight = ll_setting_more.getMeasuredHeight();
        ValueAnimator animator = ValueAnimator.ofInt(maxHeight, 0);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ll_setting_more.getLayoutParams().height =
                                (int) animation.getAnimatedValue();
                        ll_setting_more.requestLayout();
                    }
                });
        animator.addListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ll_setting_more.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
        animator.setDuration(200);
        animator.start();
    }
}
