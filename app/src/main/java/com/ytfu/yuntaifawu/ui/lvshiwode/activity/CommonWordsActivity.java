package com.ytfu.yuntaifawu.ui.lvshiwode.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.AddCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.presenter.CommonWordsPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.CommonWordsView;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/** @Auther gxy @Date 2020/7/14 @Des 常用语 */
@InjectLayout(
        value = R.layout.activity_common_words,
        toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(CommonWordsPresenter.class)
public class CommonWordsActivity extends BaseActivity<CommonWordsView, CommonWordsPresenter>
        implements CommonWordsView {

    @BindView(R.id.ed_common_words)
    EditText edCommonWords;

    @BindView(R.id.tv_ed_num)
    TextView tvEdNum;

    @BindView(R.id.tv_advisory_type)
    TextView tvAdvisoryType;

    private List<ClassificationOfCommonWordsBean.ListBean> types = new ArrayList<>();
    private ClassificationOfCommonWordsBean.ListBean currentSelectType = null;

    public static void start(Context context) {
        Intent starter = new Intent(context, CommonWordsActivity.class);
        //        starter.putExtra();
        context.startActivity(starter);
    }

    public static void startActovityForResult(Fragment fragment, int requestCode) {
        Intent starter = new Intent(fragment.getContext(), CommonWordsActivity.class);
        //        starter.putExtra();
        fragment.startActivityForResult(starter, requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_common_words, menu);
        // 设置文本颜色
        MenuItem item = mToolbar.getMenu().findItem(R.id.id_complete);
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
        int itemId = item.getItemId();
        if (itemId == R.id.id_complete) {
            if (TextUtils.isEmpty(edCommonWords.getText().toString())) {
                showCenterToast("请输入常用语");
                return super.onOptionsItemSelected(item);
            }
            if (currentSelectType == null) {
                showCenterToast("请选择常用语分类");
                return super.onOptionsItemSelected(item);
            }
            // TODO 调用添加接口
            String UserId = SpUtil.getString(mContext, AppConstant.UID, "");
            String content = edCommonWords.getText().toString();
            String cid = currentSelectType.getId();
            getPresenter().getAddCommonWords(UserId, content, cid);
            //            }
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
        setToolbarText(R.id.tv_global_title, "添加常用语");
        getPresenter().getClassificationOfCommonWords();
        edCommonWords.requestFocus();
        edCommonWords.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        Editable text = edCommonWords.getText();
                        int length = text.length();
                        tvEdNum.setText(length + "" + "/50");
                        if (length > 50) {
                            ToastUtil.showCenterToast("超出字数限制");
                            int selEndIndex = Selection.getSelectionEnd(text);
                            String str = text.toString();
                            // 截取新字符串
                            String newStr = str.substring(0, 50);
                            edCommonWords.setText(newStr);
                            text = edCommonWords.getText();

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
        tvAdvisoryType.setOnClickListener(
                view -> {
                    showDialog();
                });
    }

    private void showDialog() {
        // 1、使用Dialog、设置style
        Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
        // 2、设置布局
        View view = View.inflate(this, R.layout.dialog_type_item, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        // 设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        // 设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        // 设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setOnDismissListener(
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //                lighton();
                    }
                });
        WheelView wheelView = dialog.findViewById(R.id.wheel_view);
        wheelView.setCurrentItem(0);
        wheelView.setTextSize(20);
        wheelView.setLineSpacingMultiplier(2f);
        wheelView.setCyclic(false);
        // wheelView.setDividerWidth(6);
        wheelView.setDividerType(WheelView.DividerType.FILL);
        wheelView.setAdapter(
                new ArrayWheelAdapter<ClassificationOfCommonWordsBean.ListBean>(types) {
                    @Override
                    public Object getItem(int index) {
                        return types.get(index).getContent();
                    }
                });
        wheelView.setOnItemSelectedListener(
                index -> {
                    ClassificationOfCommonWordsBean.ListBean select = types.get(index);
                    currentSelectType = select;
                    tvAdvisoryType.setText(select.getContent());
                    tvAdvisoryType.invalidate();
                });
        dialog.findViewById(R.id.tv_quxiao)
                .setOnClickListener(
                        v -> {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        });
        dialog.findViewById(R.id.tv_queding)
                .setOnClickListener(
                        v -> {
                            int selectIndex = wheelView.getCurrentItem();
                            currentSelectType = types.get(selectIndex);
                            tvAdvisoryType.setText(currentSelectType.getContent());
                            tvAdvisoryType.invalidate();
                            dialog.dismiss();
                        });
    }

    @Override
    public void onAddCommonWordsSuccess(AddCommonWordsBean addCommonWordsBean) {
        //        showCenterToast(addCommonWordsBean.getMsg());
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onAddCommonWordsFiled(String errorMsg) {
        showCenterToast(errorMsg);
    }

    @Override
    public void onClassificationOfCommonWordsSuccess(
            ClassificationOfCommonWordsBean classificationOfCommonWordsBean) {
        types.clear();
        types.addAll(classificationOfCommonWordsBean.getList());
    }
}
