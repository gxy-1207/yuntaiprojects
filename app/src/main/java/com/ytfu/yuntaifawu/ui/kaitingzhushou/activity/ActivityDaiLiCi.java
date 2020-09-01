package com.ytfu.yuntaifawu.ui.kaitingzhushou.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.adaper.DlcTileAdaper;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.bean.DlcBean;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.bean.DlcSendEmailBean;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.bean.GenerateProxyWordsBean;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.presenter.DlcPresenter;
import com.ytfu.yuntaifawu.ui.kaitingzhushou.view.IDlCiView;
import com.ytfu.yuntaifawu.ui.mine.bean.BdEmailBean;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.LoginHelper;
import com.ytfu.yuntaifawu.utils.SnackbarUtils;
import com.ytfu.yuntaifawu.utils.SpUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/** @Auther gxy @Date 2019/11/21 @Des 代理词Activity */
public class ActivityDaiLiCi extends BaseActivity<IDlCiView, DlcPresenter> implements IDlCiView {
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;

    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;

    @BindView(R.id.tv_dlc_wenhou)
    TextView tvDlcWenhou;

    @BindView(R.id.btn_dailici_buy)
    Button btnDailiciBuy;

    @BindView(R.id.btn_dailici_fasong)
    Button btnDailiciFasong;
    //    @BindView(R.id.tv_dlc_title)
    //    TextView tvDlcTitle;
    //    @BindView(R.id.tv_dlc_nierong)
    //    TextView tvDlcNierong;
    //    @BindView(R.id.ll_bufen)
    //    LinearLayout llBufen;
    @BindView(R.id.recycle_dlc)
    RecyclerView recycleDlc;

    @BindView(R.id.snackbar_ll)
    RelativeLayout snackbarLl;

    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;

    @BindView(R.id.ll_connect)
    LinearLayout ll_connect;

    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.tv_dlc_qy)
    TextView tvDlcQy;

    @BindView(R.id.iv_weifufei)
    ImageView ivWeifufei;

    private DlcTileAdaper tileAdaper;
    private static final String KEY_SZID = "SZID";
    private static final String KEY_LAWYERID = "LAWYERID";
    private static final String KEY_USERID = "USERID";
    private static final String KEY_SHENHE_TYPE = "SHENHE_TYPE";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_dai_li_ci;
    }

    @Override
    protected DlcPresenter createPresenter() {
        return new DlcPresenter(this);
    }

    public static void start(Context context, String szId, String lawyerId, String userId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SZID, szId);
        bundle.putString(KEY_LAWYERID, lawyerId);
        bundle.putString(KEY_USERID, userId);
        Intent starter = new Intent(context, ActivityDaiLiCi.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public void init() {
        super.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            //      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent_half));
        }
    }

    @Override
    protected void initView() {
        //        hideLoading();
        tvTopTitle.setText("代理词");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycleDlc.setLayoutManager(layoutManager);
        tileAdaper = new DlcTileAdaper(this);
        recycleDlc.setAdapter(tileAdaper);
        scrollView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
        if (LoginHelper.getInstance().isUserLogin()) {
            // 用户显示发送邮箱
            btnDailiciFasong.setVisibility(View.VISIBLE);
            btnDailiciBuy.setVisibility(View.GONE);
        } else {
            // 律师显示生成证据清单
            btnDailiciFasong.setVisibility(View.GONE);
            btnDailiciBuy.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        String uid = SpUtil.getString(mContext, AppConstant.UID, "");
        String id = getIntent().getStringExtra("id");
        String szId = getBundleString(KEY_SZID, "");
        HashMap<String, String> map = new HashMap<>();
        map.put("lsid", uid);
        map.put("zhushouid", szId);
        getPresenter().setDlc(map);
    }

    @OnClick({R.id.iv_fanhui, R.id.btn_dailici_buy, R.id.btn_dailici_fasong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            case R.id.btn_dailici_buy:
                // 生成代理词
                String ssz_id = getBundleString(KEY_SZID, "");
                String lsId = getBundleString(KEY_LAWYERID, "");
                String userId = getBundleString(KEY_USERID, "");
                getPresenter().setGeneratesProxyWords(ssz_id, lsId, userId);
                break;
            case R.id.btn_dailici_fasong:
                // 发送邮箱
                showWaitingDialog("发送中...", true);
                String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                String szId = getBundleString(KEY_SZID, "");
                String id = getIntent().getStringExtra("id");
                HashMap<String, String> map = new HashMap<>();
                map.put("uid", uid);
                map.put("zhushouid", szId);
                getPresenter().setDlcSendEmail(map);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDlcSuccess(DlcBean dlcBean) {
        hideLoading();
        if (dlcBean == null) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_connect.setVisibility(View.GONE);
            return;
        }
        if (dlcBean.getArr() == null || dlcBean.getArr().isEmpty()) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_connect.setVisibility(View.GONE);
            return;
        }
        ll_empty.setVisibility(View.GONE);
        ll_connect.setVisibility(View.VISIBLE);
        tvDlcQy.setText(dlcBean.getQianyan());
        tvDlcWenhou.setText(dlcBean.getMoban());
        tileAdaper.setmList(dlcBean.getArr());
        if (dlcBean.getShencheng_type() == 0) {
            // 未生成代理词
            btnDailiciBuy.setEnabled(true);
        } else {
            // 已生成代理词
            btnDailiciBuy.setText("已生成代理词");
            btnDailiciBuy.setEnabled(false);
        }
    }

    @Override
    public void onDlicFiled() {
        hideWaitingDialog();
    }

    @Override
    public void onDlcSendEmailSuccess(DlcSendEmailBean sendEmailBean) {
        hideWaitingDialog();
        if (sendEmailBean != null) {
            int status = sendEmailBean.getStatus();
            switch (status) {
                case 200:
                case 202:
                    SnackbarUtils.showLongSnackbar(
                            snackbarLl,
                            sendEmailBean.getMsg(),
                            CommonUtil.getColor(R.color.textcolo_299),
                            CommonUtil.getColor(R.color.textcolor_26),
                            "确定",
                            CommonUtil.getColor(R.color.textcolo_299),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SnackbarUtils.dismissSnackbar();
                                }
                            });
                    break;
                case 201:
                    SnackbarUtils.showIndefiniteSnackbar(
                            snackbarLl,
                            sendEmailBean.getMsg(),
                            CommonUtil.getColor(R.color.textcolo_299),
                            CommonUtil.getColor(R.color.textcolor_26),
                            "确定",
                            CommonUtil.getColor(R.color.textcolo_299),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlertDialog();
                                }
                            });
                    break;
                default:
                    break;
            }
        }
    }
    // 绑定邮箱弹框
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        View view = View.inflate(this, R.layout.view_alert_dialog_confirm_email, null);
        TextView tvMsg = view.findViewById(R.id.tv_message_dialog);
        TextView tvCancel = view.findViewById(R.id.tv_cancel_dialog);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm_dialog);
        TextView tv_tishi = view.findViewById(R.id.tv_tishi);
        EditText ed_email = view.findViewById(R.id.ed_email);
        tvCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
        tvConfirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = ed_email.getText().toString().trim();
                        if (TextUtils.isEmpty(email)) {
                            tv_tishi.setVisibility(View.VISIBLE);
                            tv_tishi.setText("邮箱输入为空");
                        } else {
                            tv_tishi.setVisibility(View.GONE);
                            boolean contains = email.contains("@");
                            if (contains == true) {
                                showWaitingDialog("请稍后...", true);
                                String uid = SpUtil.getString(mContext, AppConstant.UID, "");
                                HashMap<String, String> map = new HashMap<>();
                                map.put("uid", uid);
                                map.put("mail", email);
                                getPresenter().setDlcBdEmail(map);
                                alertDialog.dismiss();
                            } else {
                                tv_tishi.setVisibility(View.VISIBLE);
                                tv_tishi.setText("邮箱格式不正确");
                            }
                        }
                    }
                });
        // 只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Window window = alertDialog.getWindow();
        window.setContentView(view);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams attributes = window.getAttributes();
        // 设置宽度
        attributes.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        attributes.gravity = Gravity.CENTER; // 设置位置
        window.setAttributes(attributes);
    }

    @Override
    public void onDlcPaySuccess(PayBean payBean) {
        if (payBean != null) {
            String sign = payBean.getSign();
        }
    }

    @Override
    public void onDlcPayWxSuccess(WxPayBean wxPayBean) {
        if (wxPayBean != null || wxPayBean.getSign() != null) {
            WxPayBean.SignBean sign = wxPayBean.getSign();
            //            startWxPay(sign);
            PayHelper.getInstance().payByWechat(sign);
        }
    }

    @Override
    public void onDlcBdEmail(BdEmailBean bdEmailBean) {
        hideWaitingDialog();
        if (bdEmailBean != null) {
            int status = bdEmailBean.getStatus();
            switch (status) {
                case 200:
                case 201:
                case 202:
                    showToast(bdEmailBean.getMsg());
                    break;
                default:
                    break;
            }
        }
    }
    // 生成代理词
    @Override
    public void GeneratesProxyWords(GenerateProxyWordsBean generateProxyWordsBean) {
        btnDailiciBuy.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
