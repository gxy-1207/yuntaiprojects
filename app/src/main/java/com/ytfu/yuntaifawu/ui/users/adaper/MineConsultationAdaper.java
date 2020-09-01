package com.ytfu.yuntaifawu.ui.users.adaper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.users.bean.MineConsulitatioBean;
import com.ytfu.yuntaifawu.utils.SpUtil;

import org.jetbrains.annotations.NotNull;

/** @Auther gxy @Date 2020/6/12 @Des 我的咨询Adaper */
public class MineConsultationAdaper extends QuickAdapter<MineConsulitatioBean.ListBean> {
    public MineConsultationAdaper() {
        super(R.layout.mine_zixun_recycleview_item);
    }

    @Override
    protected void convert(
            @NotNull BaseViewHolder baseViewHolder, MineConsulitatioBean.ListBean listBean) {
        super.convert(baseViewHolder, listBean);
        ImageView iv = baseViewHolder.getView(R.id.iv_zixun_icon);
        RequestOptions options =
                new RequestOptions()
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.drawable.logo) // 图片加载出来前，显示的图片
                        .fallback(R.drawable.logo) // url为空的时候,显示的图片
                        .error(R.drawable.logo); // 图片加载失败后，显示的图片
        Glide.with(getContext()).load(listBean.getAvatar()).apply(options).into(iv);
        baseViewHolder.setText(R.id.tv_zixun_name, listBean.getUser_login());
        baseViewHolder.setText(R.id.tv_zixun_time, listBean.getConsult_date());
        baseViewHolder.setText(R.id.tv_zixun_content, listBean.getConsult_content());
        baseViewHolder.setText(R.id.tv_zixun_type, listBean.getConsult_type());
        baseViewHolder.setText(R.id.tv_zixun_num, "已回复(" + listBean.getSum() + ")");
        String identityType = SpUtil.getString(getContext(), AppConstant.SHENFEN, "1");
        if (identityType.equals("1")) {
            baseViewHolder.setGone(R.id.iv_urgent, "0".equals(listBean.getUrgent()));
            baseViewHolder.setGone(R.id.tv_zhifuleixing, 1 == listBean.getType());
            baseViewHolder.setText(R.id.tv_zhifuleixing, "悬赏" + listBean.getReward_price() + "元");
            baseViewHolder.setGone(R.id.iv_choice, true);
        } else {
            baseViewHolder.setGone(R.id.iv_urgent, true);
            baseViewHolder.setGone(R.id.tv_zhifuleixing, true);
            baseViewHolder.setVisible(R.id.iv_choice, listBean.getPrice_type() == 1);
        }
        addChildClickViewIds(R.id.iv_urgent);
    }
}
