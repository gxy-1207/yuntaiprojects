package com.ytfu.yuntaifawu.ui.lawyer.question.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.lawyer.question.bean.AreaResponseBean;
import com.ytfu.yuntaifawu.ui.lawyer.question.p.AreaPresenter;
import com.ytfu.yuntaifawu.ui.lawyer.question.v.AreaView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import qiu.niorgai.StatusBarCompat;

/*设置擅长领域的界面*/
@InjectLayout(value = R.layout.activity_setting_area, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(AreaPresenter.class)
public class SettingAreaActivity extends BaseActivity<AreaView, AreaPresenter> implements AreaView {


    private static final String KEY_AREA = "KEY_AREA";
    public static final String KEY_SELECT = "KEY_SELECT";

    public static void startActivityForResult(Activity activity, int requestCode, String areas) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_AREA, areas);
        Intent starter = new Intent(activity, SettingAreaActivity.class);
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
            if (null == list) {
                return true;
            }
            TagFlowLayout fl_area_recommend = findViewById(R.id.fl_area_recommend);

            StringBuilder sb = new StringBuilder();
            Set<Integer> selectedList = fl_area_recommend.getSelectedList();
            Iterator<Integer> iterator = selectedList.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                sb.append(list.get(next).getDomain());
                if (i != selectedList.size() - 1) {
                    sb.append(",");
                }
                i++;
            }

            Bundle bundle = new Bundle();
            bundle.putString(KEY_SELECT, sb.toString());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setViewListener() {
        super.setViewListener();
        CheckBox cb_area_all = findViewById(R.id.cb_area_all);
        cb_area_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = cb_area_all.isChecked();
                TagFlowLayout fl_area_recommend = findViewById(R.id.fl_area_recommend);
                TagAdapter<?> adapter = fl_area_recommend.getAdapter();
                if (checked) {
                    int[] selectIndex = new int[adapter.getCount()];
                    for (int i = 0; i < adapter.getCount(); i++) {
                        selectIndex[i] = i;
                    }
                    adapter.setSelectedList(selectIndex);
                } else {
                    fl_area_recommend.getSelectedList().clear();
                    adapter.setSelectedList();
                    adapter.notifyDataChanged();
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent_4c));
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "擅长领域");

        getPresenter().getAllArea();


    }

    //===Desc:================================================================================
    List<AreaResponseBean.DataBean> list;

    @Override
    public void onGetAllAreaSuccess(AreaResponseBean data) {
        if (null == data) {
            showToast("应用程序出现未知错误,请稍后重试");
            finish();
            return;
        }
        int code = data.getCode();
        if (code != 200) {
            showToast("应用程序出现未知错误,请稍后重试");
            finish();
            return;
        }
        list = data.getData();

        String areasStr = getBundleString(KEY_AREA, "");
        String[] areas = areasStr.split(",");

        TagFlowLayout fl_area_recommend = findViewById(R.id.fl_area_recommend);
        TagAdapter<AreaResponseBean.DataBean> adapter = new TagAdapter<AreaResponseBean.DataBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, AreaResponseBean.DataBean s) {
                TextView tv = new TextView(mContext);
                tv.setTextColor(Color.parseColor("#43A1F7"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                int paddingTop = XPopupUtils.dp2px(mContext, 7F);
                int paddingLeft = XPopupUtils.dp2px(mContext, 14F);
                tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
                tv.setBackgroundResource(R.drawable.selector_serarch_recommend);
                tv.setText(s.getDomain());
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.WHITE);
                }

                Logger.e("选中:" + fl_area_recommend.getSelectedList().size() + ",总共 : " + list.size());

                if (fl_area_recommend.getSelectedList().size() == list.size()-1) {
                    CheckBox cb_area_all = findViewById(R.id.cb_area_all);
                    cb_area_all.setChecked(true);
                }
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                CheckBox cb_area_all = findViewById(R.id.cb_area_all);
                cb_area_all.setChecked(false);
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.parseColor("#43A1F7"));
                }
            }
        };
        fl_area_recommend.setAdapter(adapter);
        ArrayList<Integer> selectIndex = new ArrayList<>();
        List<String> selfAreas = Arrays.asList(areas);
        for (int i = 0; i < list.size(); i++) {
            AreaResponseBean.DataBean item = list.get(i);
            if (selfAreas.contains(item.getDomain())) {
                //                selectIndex[i] = i;
                selectIndex.add(i);
            }
        }
        int[] arr = new int[selectIndex.size()];
        for (int i = 0; i < selectIndex.size(); i++) {
            arr[i] = selectIndex.get(i);
        }
        adapter.setSelectedList(arr);
    }
}
