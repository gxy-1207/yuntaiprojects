package com.ytfu.yuntaifawu.ui.users.header;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
import com.ytfu.yuntaifawu.utils.GlideManager;
import com.ytfu.yuntaifawu.utils.header.base.BaseHeaderController;

/**
 * @Auther gxy
 * @Date 2020/6/15
 * @Des 咨询详情悬赏header
 */
public class ConsultationDetailsRewardHeader extends BaseHeaderController<ConsultationDetailsBean.ContentBean> {

    public ConsultationDetailsRewardHeader(Context mContext) {
        super(mContext);
    }

    @Override
    protected View onCreateHeaderView() {
        return inflateView(R.layout.consultation_details_reward_header);
    }

    @Override
    public void render(ConsultationDetailsBean.ContentBean data) {
        super.render(data);
        ImageView imageView = findHeaderViewById(R.id.iv_header);
        GlideManager.loadImageByUrl(mContext, data.getAvatar(), imageView);
        setVIewText(R.id.tv_name, data.getUser_login());
        setVIewText(R.id.tv_type, data.getConsult_type());
        setVIewText(R.id.tv_time, data.getConsult_date());
        setVIewText(R.id.tv_price, "悬赏" + data.getReward_price() + "元");
        setVIewText(R.id.tv_connect, data.getConsult_content());
        setVIewText(R.id.tv_sum, "律师回复 (" + data.getCount() + ")");
        String con_type = data.getCon_type();
        if ("0".equals(con_type)) {
            setViewGone(R.id.iv_status_solve);
            setViewGone(R.id.iv_status_closed);
        } else {
            if ("1".equals(con_type)) {
                //已解决
                setViewVisible(R.id.iv_status_solve);
                setViewGone(R.id.iv_status_closed);
            } else if ("3".equals(con_type)) {
                //已关闭
                setViewGone(R.id.iv_status_solve);
                setViewVisible(R.id.iv_status_closed);
            }
        }
        Log.e("render", "render:  " + data.getUrgent());
        if ("0".equals(data.getUrgent())) {
            setViewGone(R.id.iv_urgent);
        } else {
            setViewVisible(R.id.iv_urgent);
        }
        Log.e("render", "render:  " + data.getXuanshang_pay());
        int xuanshang_pay = data.getXuanshang_pay();
        if (xuanshang_pay == 0) {
            setViewGone(R.id.tv_price);
        } else {
            setViewVisible(R.id.tv_price);
        }

        if ("0".equals(data.getUnlock_type())) {
            setViewGone(R.id.tv_sum);
        }else{
            setViewVisible(R.id.tv_sum);
        }

    }
}
