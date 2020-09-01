package com.ytfu.yuntaifawu.ui.home.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseFragment;
import com.ytfu.yuntaifawu.ui.consult.bean.ZiXunNavBean;
import com.ytfu.yuntaifawu.ui.home.adaper.RewardAdapter;
import com.ytfu.yuntaifawu.ui.home.bean.RandomLawyerResponse;
import com.ytfu.yuntaifawu.ui.home.presenter.AdvisoryPresenter;
import com.ytfu.yuntaifawu.ui.home.view.AdvisoryView;
import com.ytfu.yuntaifawu.ui.pay.act.PayActivity;
import com.ytfu.yuntaifawu.ui.pay.p.PayPresenter;
import com.ytfu.yuntaifawu.ui.users.act.MineConsultationListActivity;
import com.ytfu.yuntaifawu.utils.GlideManager;
import com.ytfu.yuntaifawu.utils.SpUtil;
import com.ytfu.yuntaifawu.utils.dialog.BottomWheelDialog;
import com.ytfu.yuntaifawu.utils.dialog.CenterDialog;
import com.ytfu.yuntaifawu.utils.view.NumberView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.jessyan.autosize.utils.LogUtils;
import qiu.niorgai.StatusBarCompat;

/** 首页咨询界面 */
@InjectLayout(R.layout.fragment_consulting)
@InjectPresenter(AdvisoryPresenter.class)
public class AdvisoryFragment extends BaseFragment<AdvisoryView, AdvisoryPresenter>
        implements AdvisoryView {
    private static final int MAX_INPUT = 300;

    private BottomWheelDialog<ZiXunNavBean.ListBean> dialog;
    private CenterDialog centerDialog;

    @BindView(R.id.tv_personal_title)
    TextView tv_personal_title;

    @BindView(R.id.sv_scan)
    ScrollView sv_scan;

    @BindView(R.id.ll_top)
    View ll_top;

    @BindView(R.id.sv_content)
    ScrollView sv_content;

    @BindView(R.id.ll_head)
    LinearLayout ll_head;

    @BindView(R.id.tv_num)
    TextView tv_num;

    @BindView(R.id.tv_num1)
    NumberView tv_num1;

    @BindView(R.id.iv_gif)
    ImageView iv_gif;

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    @BindView(R.id.et_advisory_input)
    EditText et_advisory_input;

    @BindView(R.id.iv_advisory_money_free_icon)
    View iv_advisory_money_free_icon;

    @BindView(R.id.tv_advisory_tip)
    TextView tv_advisory_tip;

    @BindView(R.id.tv_advisory_tip2)
    TextView tv_advisory_tip2;

    @BindView(R.id.tv_advisory_input_count)
    TextView tv_advisory_input_count;

    @BindView(R.id.tv_advisory_type)
    TextView tv_advisory_type;

    @BindView(R.id.et_advisory_money)
    EditText et_advisory_money;

    @BindView(R.id.iv_advisory_moneys)
    RecyclerView iv_advisory_moneys;

    private List<ZiXunNavBean.ListBean> types = new ArrayList<>();
    private ZiXunNavBean.ListBean currentSelectType = null;

    private RewardAdapter adapter = new RewardAdapter();

    public static AdvisoryFragment newInstance() {
        return new AdvisoryFragment();
    }

    // ===Desc:================================================================================
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.e("onHiddenChanged ------====");
        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
            countDownTimer = null;
            ll_head.removeAllViews();
        }
        if (!hidden) {
            FragmentActivity activity = getActivity();
            if (null != activity) {
                StatusBarCompat.setStatusBarColor(
                        activity, getResources().getColor(R.color.transparent_4c));
            }
            if (getPresenter() != null) {
                getPresenter().getRandomLawyers();
            }
        } else {
            if (null != timer) {
                timer.onFinish();
                timer.cancel();
                timer = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onHiddenChanged(false);
        Logger.e("onResume========");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("setUserVisibleHint====================");

        if (isVisibleToUser) {
            StatusBarCompat.setStatusBarColor(
                    getActivity(), getResources().getColor(R.color.transparent_4c));
        }

        LogUtils.e("isVisibleToUser =========== " + isVisibleToUser);
    }

    @Override
    public void init() {
        super.init();
        centerDialog = new CenterDialog(mContext);
    }

    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        tv_advisory_type.setOnClickListener(
                v -> {
                    showDialog();
                    //                new XPopup.Builder(getContext())
                    //                        .setPopupCallback(new XPopupCallback() {
                    //                            @Override
                    //                            public void onCreated() {
                    //
                    //                            }
                    //
                    //                            @Override
                    //                            public void beforeShow() {
                    //                            }
                    //
                    //                            @Override
                    //                            public void onShow() {
                    //                                dialog.test();
                    //                            }
                    //
                    //                            @Override
                    //                            public void onDismiss() {
                    //
                    //                            }
                    //
                    //                            @Override
                    //                            public boolean onBackPressed() {
                    //                                return true;
                    //                            }
                    //                        })
                    //                        .asCustom(dialog)
                    //                        .show();
                });
        //
        rootView.findViewById(R.id.rl_advisory_money_free)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean shown =
                                        rootView.findViewById(R.id.iv_advisory_money_free_icon)
                                                .isShown();
                                if (shown) {
                                    return;
                                }
                                new XPopup.Builder(mContext)
                                        .dismissOnBackPressed(false)
                                        .dismissOnTouchOutside(false)
                                        .setPopupCallback(
                                                new XPopupCallback() {
                                                    @Override
                                                    public void onCreated() {
                                                        centerDialog.prepare();
                                                        centerDialog.setFreeClick(
                                                                v -> {
                                                                    centerDialog.dismiss();

                                                                    View
                                                                            iv_advisory_money_free_icon =
                                                                                    rootView
                                                                                            .findViewById(
                                                                                                    R.id.iv_advisory_money_free_icon);
                                                                    iv_advisory_money_free_icon
                                                                            .setVisibility(
                                                                                    View.VISIBLE);
                                                                    adapter.cleanSelected();
                                                                    et_advisory_money.setText("");
                                                                });
                                                        centerDialog.setMoneyClick(
                                                                v -> {
                                                                    centerDialog.dismiss();
                                                                    rootView.findViewById(
                                                                                    R.id.iv_advisory_money_free_icon)
                                                                            .setVisibility(
                                                                                    View.GONE);
                                                                    adapter.setSelected(
                                                                            adapter.getData()
                                                                                    .get(0));
                                                                    et_advisory_money.setText(
                                                                            String.valueOf(
                                                                                    adapter.getData()
                                                                                            .get(
                                                                                                    0)));
                                                                });
                                                    }

                                                    @Override
                                                    public void beforeShow() {}

                                                    @Override
                                                    public void onShow() {}

                                                    @Override
                                                    public void onDismiss() {}

                                                    @Override
                                                    public boolean onBackPressed() {
                                                        return false;
                                                    }
                                                })
                                        .asCustom(centerDialog)
                                        .show();
                            }
                        });

        et_advisory_input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        String input = et_advisory_input.getText().toString().trim();
                        if (input.length() > MAX_INPUT) {
                            String newStr = input.substring(0, MAX_INPUT);
                            et_advisory_input.setText(newStr);
                        }
                        tv_advisory_input_count.setText(input.length() + "/" + MAX_INPUT);
                    }
                });

        rootView.findViewById(R.id.tv_advisory_commit)
                .setOnClickListener(
                        v -> {
                            if (null == currentSelectType) {
                                showToast("请选择问题类型");
                                return;
                            }
                            String userId = SpUtil.getString(mContext, AppConstant.UID, "");
                            String consultType = currentSelectType.getName();
                            String consultContent = et_advisory_input.getText().toString().trim();
                            if (TextUtils.isEmpty(consultContent)) {
                                showToast("请输入咨询内容");
                                return;
                            }
                            if (consultContent.length() < 15) {
                                showToast("请输入至少15个字的问题描述");
                                return;
                            }
                            // 悬赏金额
                            double money = 0.0;
                            Integer selected = adapter.getSelected();
                            if (null == selected) {
                                money = 0.0;
                            } else {
                                money = adapter.getSelected() * 1.0;
                            }
                            String inputMoney = et_advisory_money.getText().toString().trim();
                            if (!TextUtils.isEmpty(inputMoney)) {
                                try {
                                    money = Double.parseDouble(inputMoney);
                                    if (money < PayPresenter.getDefaultMinMoney()) {
                                        showToast(
                                                "最低输入金额不能低于"
                                                        + PayPresenter.getDefaultMinMoney()
                                                        + "元");
                                        return;
                                    }
                                } catch (NumberFormatException e) {
                                    showToast("请输入有效的金额");
                                    return;
                                }
                            }
                            getPresenter().commit(userId, consultType, consultContent, money);
                        });

        adapter.setOnItemClickListener(
                (a, view, position) -> {
                    adapter.setSelected(adapter.getData().get(position));
                    View iv_advisory_money_free_icon =
                            rootView.findViewById(R.id.iv_advisory_money_free_icon);
                    iv_advisory_money_free_icon.setVisibility(View.GONE);
                    et_advisory_money.setText(String.valueOf(adapter.getData().get(position)));
                });
        et_advisory_money.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            double inputMoney =
                                    Double.parseDouble(
                                            et_advisory_money.getText().toString().trim());
                            Integer selected = adapter.getSelected();
                            if (null == selected) {
                                adapter.cleanSelected();
                            } else {
                                if (inputMoney != selected.doubleValue()) {
                                    adapter.cleanSelected();
                                }
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        tv_personal_title.setText("咨询");
        sv_scan.setVisibility(View.GONE);
        sv_content.setVisibility(View.VISIBLE);

        iv_advisory_moneys.setLayoutManager(new GridLayoutManager(mContext, 3));
        iv_advisory_moneys.setAdapter(adapter);
        List<Integer> reward6 = PayPresenter.getReward6();
        adapter.setNewInstance(reward6);
        adapter.setSelected(reward6.get(0));
        et_advisory_money.setText(String.valueOf(reward6.get(0)));

        getPresenter().getAdvisoryInfo();
    }

    @Override
    protected void onMyReload(View v) {
        super.onMyReload(v);

        getPresenter().getAdvisoryInfo();
    }

    // ===Desc:================================================================================

    private void showDialog() {
        // 1、使用Dialog、设置style
        Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
        // 2、设置布局
        View view = View.inflate(getContext(), R.layout.dialog_type_item, null);
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
                new ArrayWheelAdapter<ZiXunNavBean.ListBean>(types) {
                    @Override
                    public Object getItem(int index) {
                        return types.get(index).getName();
                    }
                });
        wheelView.setOnItemSelectedListener(
                index -> {
                    ZiXunNavBean.ListBean select = types.get(index);
                    currentSelectType = select;
                    tv_advisory_type.setText(select.getName());
                    tv_advisory_type.invalidate();
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
                            tv_advisory_type.setText(currentSelectType.getName());
                            tv_advisory_type.invalidate();
                            dialog.dismiss();
                        });
    }

    // ===Desc:================================================================================
    @Override
    public void onGetAdvisoryInfo(ZiXunNavBean data) {
        // 设置滚轮数据
        dialog = new BottomWheelDialog<>(mContext);
        dialog.setData(data.getList());
        types.clear();
        types.addAll(data.getList());
        // 设置免费弹窗数据
        centerDialog.setLawyerCount(data.getLvshi());
        centerDialog.setSum(data.getSum());

        et_advisory_input.setHint(data.getZixun_descript());
        tv_advisory_tip.setText(data.getXuanshang_descript());
        tv_advisory_tip2.setText(data.getNew_zixun_xiangqing());
    }

    @Override
    public void onGotoPayActivity(String advisoryId, double money) {
        Logger.e("进入支付界面");
        PayActivity.start(mContext, advisoryId, money);
    }

    @Override
    public void onGotoPostSuccessActivity(String advisoryId) {
        //        getPresenter().requestSendMessage(LoginHelper.getInstance().getLoginUserId(), new
        // Runnable() {
        //            @Override
        //            public void run() {
        //                MainActivity.start(mContext, 1);
        //            }
        //        });
        //         MineConsultationListActivity.start(mContext);

        //        MainActivity.start(mContext, 1);
        //        getPresenter().requestSendMessage(LoginHelper.getInstance().getLoginUserId(), new
        // Runnable() {
        //            @Override
        //            public void run() {
        //                MainActivity.start(mContext, 1);
        //            }
        //        });
        MineConsultationListActivity.start(mContext);
    }

    // ===Desc:================================================================================

    /** 清空用户输入 */
    @Override
    public void cleanUserInput() {
        currentSelectType = null;
        tv_advisory_type.setText("请选择问题类型");
        et_advisory_input.setText("");

        adapter.setSelected(adapter.getData().get(0));
        iv_advisory_money_free_icon.setVisibility(View.GONE);
        et_advisory_money.setText(String.valueOf(adapter.getData().get(0)));
    }

    private CountDownTimer timer;

    private CountDownTimer countDownTimer;
    int index = 0;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onGetRandomLawyers(RandomLawyerResponse.D data) {
        sv_scan.setVisibility(View.GONE);
        ll_top.setVisibility(View.VISIBLE);
        sv_content.setVisibility(View.VISIBLE);

        // 添加头像
        ll_head.removeAllViews();

        List<RandomLawyerResponse.DataBean> list = new ArrayList<>();
        if (data.getResult().size() < 3) {
            list.addAll(data.getResult());
        } else {
            list.addAll(data.getResult().subList(0, 3));
        }
        if (null == countDownTimer) {
            countDownTimer =
                    new CountDownTimer(4100, 1000) {
                        @Override
                        public void onTick(long l) {
                            int size = XPopupUtils.dp2px(mContext, 30);
                            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(size, size);
                            if (index != 0) {
                                p.leftMargin = -XPopupUtils.dp2px(mContext, 13);
                            }

                            ImageView imageView = new ImageView(mContext);
                            imageView.setZ(list.size() - index);
                            if (index == 3) {
                                GlideManager.loadCircleImage(
                                        mContext, R.drawable.tupian_shenglue, imageView);
                                ll_head.addView(imageView, p);
                                countDownTimer.onFinish();
                                return;
                            }
                            RandomLawyerResponse.DataBean dataBean = list.get(index);
                            GlideManager.loadCircleImage(
                                    mContext, dataBean.getIconurl(), imageView);
                            ll_head.addView(imageView, p);
                            index++;
                        }

                        @Override
                        public void onFinish() {
                            index = 0;
                            cancel();
                            countDownTimer = null;
                        }
                    }.start();
        }

        //        for (int i = 0; i < list.size(); i++) {
        //            if (i >= 3) {
        //                break;
        //            }
        //            RandomLawyerResponse.DataBean dataBean = list.get(i);
        //            int size = XPopupUtils.dp2px(mContext, 30);
        //            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(size, size);
        //            if (i != 0) {
        //                p.leftMargin = -XPopupUtils.dp2px(mContext, 13);
        //            }
        //            ImageView imageView = new ImageView(mContext);
        //            GlideManager.loadCircleImage(mContext, dataBean.getIconurl(), imageView);
        //            ll_head.addView(imageView, p);
        //        }
        //        String s1 = String.format(Locale.CHINA, "已为您成功匹配 <font color='#44A2F7'>%s</font>
        // 位律师", data.getNum());
        //        mTvCountNum1.showNumberWithAnimation(3201.23f, CountNumberView.FLOATREGEX);
        //        mTvCountNum2.showNumberWithAnimation(65535f, CountNumberView.INTREGEX);
        tv_num1.setDuration(4 * 1000L);
        tv_num1.showNumberWithAnimation(data.getNum(), NumberView.INTREGEX);
        String s = String.format(Locale.CHINA, "有%d位律师在等待匹配", data.getNum());
        tv_num.setText(s);
    }

    @Override
    public void onGetRandomLawyersFail() {
        sv_scan.setVisibility(View.GONE);
        ll_top.setVisibility(View.GONE);
        sv_content.setVisibility(View.VISIBLE);
    }
}
