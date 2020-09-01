package com.ytfu.yuntaifawu.ui.users.adaper;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.users.bean.MyRefundBean;

import org.jetbrains.annotations.NotNull;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/23
*
*  @Des 我的退款Adaper
*
*/
public class MyRefundAdaper extends QuickAdapter<MyRefundBean.DataBean> {

    public MyRefundAdaper() {
        super(R.layout.my_refund_list_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyRefundBean.DataBean dataBean) {
        super.convert(baseViewHolder, dataBean);
        baseViewHolder.setText(R.id.tv_time,dataBean.getTime());
        baseViewHolder.setText(R.id.tv_review_type,dataBean.getReason());
        baseViewHolder.setText(R.id.tv_price,dataBean.getPrice());
        String status = dataBean.getStatus();
        //  status     1 审核中 2 审核通过 3 审核拒绝
        switch (status){
            case "1":
                baseViewHolder.setImageResource(R.id.iv_review_type,R.mipmap.icon_under_review);
                baseViewHolder.setTextColor(R.id.tv_review_type,getContext().getResources().getColor(R.color.textColoe_ec980d));
                break;
            case "2":
                baseViewHolder.setImageResource(R.id.iv_review_type,R.mipmap.icon_examination_passed);
                baseViewHolder.setTextColor(R.id.tv_review_type,getContext().getResources().getColor(R.color.textColoe_50C023));
                break;
            case "3":
                baseViewHolder.setImageResource(R.id.iv_review_type,R.mipmap.icon_audit_failure);
                baseViewHolder.setTextColor(R.id.tv_review_type,getContext().getResources().getColor(R.color.textColoe_e13b38));
                break;
        }
    }
}