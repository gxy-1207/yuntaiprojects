package com.ytfu.yuntaifawu.ui.redpackage.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lee.annotation.InjectLayout;
import com.hyphenate.util.DateUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseActivity;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.lawyer.chat.bean.HistoryChatExtBean;

import java.util.Date;
import java.util.zip.DataFormatException;

import qiu.niorgai.StatusBarCompat;

/**
 * 用户红包详情界面
 */
@InjectLayout(value = R.layout.activity_user_red_package,
        toolbarLayoutId = R.layout.layout_toolbar_center_title_transparent)
public class UserRedPackageActivity extends BaseActivity<BaseView, BasePresenter<BaseView>> {

    private static final String KEY_EXT_BEAN = "EXT_BEAN";
    private static final String KEY_LAWYER_NAME = "KEY_LAWYER_NAME";
    private static final String KEY_LAWYER_AVATAR = "KEY_LAWYER_AVATAR";
    private static final String KEY_TIME = "KEY_TIME";

    public static void start(Context context, HistoryChatExtBean data, String lawyerName, String lawyerAvatar, long time) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_EXT_BEAN, data);
        bundle.putString(KEY_LAWYER_NAME, lawyerName);
        bundle.putString(KEY_LAWYER_AVATAR, lawyerAvatar);
        bundle.putLong(KEY_TIME, time);
        Intent starter = new Intent(context, UserRedPackageActivity.class);
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

        //律师信息
        ImageView iv_detail_lawyer_avatar = findViewById(R.id.iv_detail_lawyer_avatar);
        Glide.with(mContext).load(getBundleString(KEY_LAWYER_AVATAR, ""))
                .apply(options)
                .into(iv_detail_lawyer_avatar);

        TextView tv_detail_lawyer_name = findViewById(R.id.tv_detail_lawyer_name);
        tv_detail_lawyer_name.setText(getBundleString(KEY_LAWYER_NAME, ""));
        TextView tv_detail_lawyer_money = findViewById(R.id.tv_detail_lawyer_money);
        tv_detail_lawyer_money.setText(bean.getPrice() + "元");
        long time = getBundleLong(KEY_TIME, System.currentTimeMillis() / 1000);
        TextView tv_detail_lawyer_time = findViewById(R.id.tv_detail_lawyer_time);
        String timestampString = DateUtils.getTimestampString(new Date(time * 1000));
        tv_detail_lawyer_time.setText(timestampString);
    }
}
