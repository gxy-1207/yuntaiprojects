package com.ytfu.yuntaifawu.ui.lawyer.question.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseView;

import qiu.niorgai.StatusBarCompat;

@InjectLayout(value = R.layout.activity_setting_answer, toolbarLayoutId = R.layout.layout_toolbar_center_title)
public class SettingAutoAnswerActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> {

    private static final String KEY_ANSWER = "ANSWER";
    public static final String KEY_INPUT = "KEY_INPUT";

    public static void startActivityForResult(Activity activity, int requestCode, String answer) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ANSWER, answer);
        Intent starter = new Intent(activity, SettingAutoAnswerActivity.class);
        starter.putExtras(bundle);
        activity.startActivityForResult(starter, requestCode);
    }
    //===Desc:================================================================================


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting_answer, menu);
        //设置文本颜色
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_save);
        if (null != item) {
            CharSequence title = item.getTitle();
            SpannableStringBuilder ssb = new SpannableStringBuilder(title);
            //设置文字颜色。
            ssb.setSpan(new ForegroundColorSpan(Color.WHITE), 0, title.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            item.setTitle(ssb);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            EditText et_setting_answer = findViewById(R.id.et_setting_answer);
            String input = et_setting_answer.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                showToast(getString(R.string.hint_input_auto_answer));
                return false;
            }

            Bundle bundle = new Bundle();
            bundle.putString(KEY_INPUT, input);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "自动回复");

        EditText et_setting_answer = findViewById(R.id.et_setting_answer);
        et_setting_answer.setText(getBundleString(KEY_ANSWER, ""));

    }
}
