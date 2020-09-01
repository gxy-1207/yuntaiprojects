package com.ytfu.yuntaifawu.ui.mseeage.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.App;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.custom.MyRadioGroup;
import com.ytfu.yuntaifawu.ui.mseeage.adaper.UserEvaluateAdaper;
import com.ytfu.yuntaifawu.ui.mseeage.bean.UserEvaluateBean;
import com.ytfu.yuntaifawu.ui.mseeage.bean.UserEvaluateClassBean;
import com.ytfu.yuntaifawu.ui.mseeage.presenter.UserEvaluatePresenter;
import com.ytfu.yuntaifawu.ui.mseeage.view.IUserEvaluateView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.Eyes;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/** @Auther gxy @Date 2020/4/20 @Des 用户评价 */
public class UserEvaluateActivity extends BaseActivity<IUserEvaluateView, UserEvaluatePresenter>
        implements IUserEvaluateView {
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;

    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;

    @BindView(R.id.flowlayout)
    MyRadioGroup flowlayout;

    @BindView(R.id.rl_pingjia_content)
    RecyclerView rlPingjiaContent;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.ll_empey)
    LinearLayout ll_empey;

    private String lid;
    private ViewGroup.MarginLayoutParams layoutParams;
    private UserEvaluateAdaper evaluateAdaper;
    private TextView view;
    Bitmap a = null;
    private int j = 0;
    private int page = 1;
    private int pingJiId;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_evaluate;
    }

    @Override
    protected UserEvaluatePresenter createPresenter() {
        return new UserEvaluatePresenter(this);
    }

    //    @Override
    //    protected View provideLoadServiceRootView() {
    //        return refreshLayout;
    //    }

    @Override
    public void init() {
        super.init();
        Eyes.setStatusBarColor(this, CommonUtil.getColor(R.color.transparent_4c));
    }

    private static final String KEY_LID = "LID";
    private static final String KEY_TITLE = "TITLE";

    public static void start(Context context, String lid, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LID, lid);
        bundle.putString(KEY_TITLE, title);
        Intent starter = new Intent(context, UserEvaluateActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        //        hideLoading();
        //        lid = getIntent().getStringExtra("lid");
        String title = getBundleString(KEY_TITLE, "");
        tvTopTitle.setText(title);
        layoutParams =
                new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 20;
        layoutParams.rightMargin = 20;
        layoutParams.topMargin = 15;
        layoutParams.bottomMargin = 10;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlPingjiaContent.setLayoutManager(layoutManager);
        evaluateAdaper = new UserEvaluateAdaper(this);
        rlPingjiaContent.setAdapter(evaluateAdaper);
        evaluateAdaper.setiUserEvaluateClientListener(
                new UserEvaluateAdaper.IUserEvaluateClientListener() {
                    @Override
                    public void userEvaluateListener(String id) {
                        String lid = getBundleString(KEY_LID, "");
                        Intent intent =
                                new Intent(
                                        UserEvaluateActivity.this, EvaluateDetailsActivity.class);
                        intent.putExtra("lid", lid);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setRefreshHeader(
                new MaterialHeader(App.getContext())
                        .setColorSchemeResources(
                                R.color.primaryColor, R.color.blue_3C, R.color.green_59));
        refreshLayout.setRefreshFooter(
                new BallPulseFooter(App.getContext())
                        .setAnimatingColor(CommonUtil.getColor(R.color.primaryColor)));
    }

    @Override
    protected void initData() {
        //        HashMap<String, String> map = new HashMap<>();
        //        map.put("lid", lid);
        //        getPresenter().getUserEvaluateClass(map);
        getListData();
        refreshLayout.setOnRefreshListener(
                new OnRefreshListener() {
                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        getListData();
                        refreshLayout.finishRefresh();
                    }
                });

        refreshLayout.setOnLoadMoreListener(
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        addListDatas();
                        refreshLayout.finishLoadMore();
                    }
                });
    }

    private void getListData() {
        page = 1;
        String lid = getBundleString(KEY_LID, "");
        HashMap<String, String> map = new HashMap<>();
        map.put("lid", lid);
        if (!TextUtils.isEmpty(String.valueOf(pingJiId))) {
            map.put("pingjia", String.valueOf(pingJiId));
        }
        map.put("p", String.valueOf(page));
        getPresenter().getUserEvaluate(map);
    }

    private void addListDatas() {
        page++;
        String lid = getBundleString(KEY_LID, "");
        HashMap<String, String> map = new HashMap<>();
        map.put("lid", lid);
        if (!TextUtils.isEmpty(String.valueOf(pingJiId))) {
            map.put("pingjia", String.valueOf(pingJiId));
        }
        map.put("p", String.valueOf(page));
        getPresenter().getUserEvaluate(map);
    }

    @OnClick(R.id.iv_fanhui)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onUserEvaluateSuccess(UserEvaluateBean userEvaluateBean) {
        hideLoading();

        if (userEvaluateBean != null && userEvaluateBean.getCategory() != null) {
            ll_empey.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
            List<UserEvaluateBean.CategoryBean> category = userEvaluateBean.getCategory();
            if (flowlayout.getChildCount() == 0) {
                for (int i = 0; i < category.size(); i++) {
                    //                    view = new TextView(this);
                    RadioButton button = new RadioButton(this);
                    button.setText(
                            category.get(i).getName() + " (" + category.get(i).getNum() + ")");
                    button.setButtonDrawable(new BitmapDrawable(a));
                    button.setBackgroundResource(R.drawable.textview_color_selector);
                    ColorStateList csl =
                            getResources().getColorStateList(R.color.selector_radio_check);
                    button.setTextColor(csl);

                    int finalI = i;
                    button.setOnCheckedChangeListener(
                            new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(
                                        CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        showWaitingDialog("加载中...", true);
                                        pingJiId = category.get(finalI).getId();
                                        //                                HashMap<String, String>
                                        // map = new HashMap<>();
                                        //                                map.put("lid", lid);
                                        //                                map.put("pingjia",
                                        // String.valueOf(pingJiId));
                                        //
                                        // getPresenter().getUserEvaluate(map);
                                        getListData();
                                    }
                                }
                            });
                    flowlayout.addView(button, layoutParams);
                }
                //                RadioButton radio = (RadioButton) flowlayout.getChildAt(0);
                //                radio.setChecked(true);
            }
            if (userEvaluateBean.getShuju().size() != 0) {
                hideWaitingDialog();
                ll_empey.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                if (page == 1) {
                    evaluateAdaper.setBeanList(userEvaluateBean.getShuju());
                } else {
                    evaluateAdaper.addBeanList(userEvaluateBean.getShuju());
                }
            } else {
                hideWaitingDialog();
                evaluateAdaper.clear();
                if (page == 1) {
                    ll_empey.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
                //
                // RefreshLayoutHelper.getInstance().setLoadedState(userEvaluateBean.getShuju(),
                // page, refreshLayout, this);
            }
        } else {
            hideWaitingDialog();
            if (page == 1) {
                ll_empey.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onUserEvaluateClassSuccess(UserEvaluateClassBean userEvaluateClassBean) {
        //        if (userEvaluateClassBean != null && userEvaluateClassBean.getType() != null) {
        //            List<UserEvaluateClassBean.TypeBean> type = userEvaluateClassBean.getType();
        //            if (flowlayout.getChildCount() == 0) {
        //                for (int i = 0; i < type.size(); i++) {
        //                    RadioButton button = new RadioButton(this);
        //                    button.setText(type.get(i).getName() + " (" + type.get(i).getNum() +
        // ")");
        //                    button.setButtonDrawable(new BitmapDrawable(a));
        //                    button.setBackgroundResource(R.drawable.textview_color_selector);
        //                    ColorStateList csl =
        // getResources().getColorStateList(R.color.selector_radio_check);
        //                    button.setTextColor(csl);
        //                    int finalI = i;
        //                    button.setOnCheckedChangeListener(new
        // CompoundButton.OnCheckedChangeListener() {
        //                        @Override
        //                        public void onCheckedChanged(CompoundButton buttonView, boolean
        // isChecked) {
        //                            if (isChecked) {
        //                                int id = type.get(finalI).getId();
        ////                                ToastUtil.showCenterToast(id+"");
        //                                HashMap<String, String> map = new HashMap<>();
        //                                map.put("lid", lid);
        //                                map.put("pingjia", id + "");
        //                                getPresenter().getUserEvaluate(map);
        //                            }
        //                        }
        //                    });
        //                    flowlayout.addView(button, layoutParams);
        //                }
        //                RadioButton radio = (RadioButton) flowlayout.getChildAt(0);
        //                radio.setChecked(true);
        //            }
        //        }
    }

    @Override
    public void onUserEvaluateFiled() {}
}
