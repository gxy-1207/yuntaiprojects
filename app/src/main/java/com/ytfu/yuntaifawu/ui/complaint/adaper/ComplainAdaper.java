package com.ytfu.yuntaifawu.ui.complaint.adaper;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintClassificationBean;
import com.ytfu.yuntaifawu.ui.qisuzhuang.bean.QszFenLeiBean;
import com.ytfu.yuntaifawu.utils.GlideManager;

import org.jetbrains.annotations.NotNull;

/** @Auther gxy @Date 2020/8/11 @Des 起诉状Adaper */
public class ComplainAdaper extends QuickAdapter<ComplaintClassificationBean.ListBean> {
    public ComplainAdaper() {
        super(R.layout.complain_item_content);
    }

    @Override
    protected void convert(
            @NotNull BaseViewHolder baseViewHolder, ComplaintClassificationBean.ListBean listBean) {
        super.convert(baseViewHolder, listBean);
        baseViewHolder.setText(R.id.tv_complaint_title, listBean.getLabel());
        baseViewHolder.setText(R.id.tv_complaint_description, listBean.getMiaoshu());
        GlideManager.loadImageByUrl(
                getContext(), listBean.getImgx(), baseViewHolder.getView(R.id.iv_complaint));
    }
}
