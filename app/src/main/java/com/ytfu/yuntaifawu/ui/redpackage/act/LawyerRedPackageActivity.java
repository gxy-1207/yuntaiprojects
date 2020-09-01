package com.ytfu.yuntaifawu.ui.redpackage.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lee.annotation.InjectLayout;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatExtBean;

import qiu.niorgai.StatusBarCompat;

/**
 * 律师红包详情界面
 */

@InjectLayout(value = R.layout.activity_lawyer_red_package,
        toolbarLayoutId = R.layout.layout_toolbar_center_title_transparent)
public class LawyerRedPackageActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> {
    private static final String KEY_EXT_BEAN = "EXT_BEAN";

    public static void start(Context context, HistoryChatExtBean data) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_EXT_BEAN, data);
        Intent starter = new Intent(context, LawyerRedPackageActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    //===Desc:================================================================================


    @Override
    public void init() {
        super.init();
        toolbarOverlap = true;
    }

    @Override
    protected void initData() {
        super.initData();
        hideLoading();
        StatusBarCompat.translucentStatusBar(this,false);
        changeStatusBarTextColor(false);
        setToolbarLeftImage(R.drawable.fanhui_bai, v -> onBackPressed());
        setToolbarText(R.id.tv_global_title, "红包");


        HistoryChatExtBean bean = getBundleParcelable(KEY_EXT_BEAN);
        if (null == bean) {
            showToast("应用程序出现未知错误,请稍后重试");
            finish();
            return;
        }
        //设置数据显示
        //头像
        ImageView iv_detail_avatar = findViewById(R.id.iv_detail_avatar);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.touxiang)//图片加载出来前，显示的图片
                .fallback(R.drawable.touxiang) //url为空的时候,显示的图片
                .error(R.drawable.touxiang);//图片加载失败后，显示的图片
        Glide.with(mContext).load(bean.getIconurl())
                .apply(options)
                .into(iv_detail_avatar);
        //姓名
        TextView tv_detail_name = findViewById(R.id.tv_detail_name);
        tv_detail_name.setText(bean.getNickname());
        //说明
        TextView tv_detail_content = findViewById(R.id.tv_detail_content);
        tv_detail_content.setText(bean.getRpcontent());

        TextView tv_detail_money = findViewById(R.id.tv_detail_money);
        tv_detail_money.setText(bean.getPrice());


    }
}
