package com.ytfu.yuntaifawu.ui.lvshiwode.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.presenter.EditCommonWordsPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.EditCommonWordsView;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther gxy
 * @Date 2020/7/14
 * @Des 编辑常用语
 */
@InjectLayout(value = R.layout.activity_common_words, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(EditCommonWordsPresenter.class)
public class EditCommonWordsActivity extends BaseActivity<EditCommonWordsView, EditCommonWordsPresenter> implements EditCommonWordsView {
    private static final String KEY_REPLY_ID = "REPLY_ID";
    private static final String KEY_CONTENT = "CONTENT";
    @BindView(R.id.ed_common_words)
    EditText edCommonWords;
    @BindView(R.id.tv_ed_num)
    TextView tvEdNum;
    @BindView(R.id.tv_advisory_type)
    TextView tvAdvisoryType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.v1)
    View v1;

    //    public static void start(Context context, String ReplyID) {
//        Bundle bundle = new Bundle();
//        bundle.putString(KEY_REPLY_ID,ReplyID);
//        Intent starter = new Intent(context, EditCommonWordsActivity.class);
//        starter.putExtras(bundle);
//        context.startActivity(starter);
//    }
    public static void startActivityForresult(Fragment fragment, int requestCode, String ReplyID,String content) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_REPLY_ID, ReplyID);
        bundle.putString(KEY_CONTENT, content);
        Intent starter = new Intent(fragment.getContext(), EditCommonWordsActivity.class);
        starter.putExtras(bundle);
        fragment.startActivityForResult(starter, requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_common_words, menu);
        //设置文本颜色
        MenuItem item = mToolbar.getMenu().findItem(R.id.id_complete);
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
        int itemId = item.getItemId();
        if (itemId == R.id.id_complete) {
            if (TextUtils.isEmpty(edCommonWords.getText().toString())) {
                showCenterToast("请输入常用语");
            } else {
                //TODO 调用添加接口
                String ReplyID = getBundleString(KEY_REPLY_ID, "");
                String content = edCommonWords.getText().toString();
                getPresenter().getEditCommonWords(ReplyID, content);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void initData() {
        super.initData();
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, view -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "编辑常用语");
        llType.setVisibility(View.GONE);
        v1.setVisibility(View.GONE);
        edCommonWords.setText(getBundleString(KEY_CONTENT,""));
        edCommonWords.requestFocus();
        edCommonWords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable text = edCommonWords.getText();
                int length = text.length();
                tvEdNum.setText(length + "" + "/50");
                if (length > 50) {
                    ToastUtil.showCenterToast("超出字数限制");
                    int selEndIndex = Selection.getSelectionEnd(text);
                    String str = text.toString();
                    //截取新字符串
                    String newStr = str.substring(0, 50);
                    edCommonWords.setText(newStr);
                    text = edCommonWords.getText();
                    //新字符串的长度
                    int newLen = text.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = text.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(text, selEndIndex);
                }
            }
        });
    }

    @Override
    public void onEditCommonWordsSuccess(EditDeleteCommonWordsBean deleteCommonWordsBean) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onEditCommonWordsFiled(String errorMsg) {

    }

}