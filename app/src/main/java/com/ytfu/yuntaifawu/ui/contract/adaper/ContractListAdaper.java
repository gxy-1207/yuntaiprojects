package com.ytfu.yuntaifawu.ui.contract.adaper;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractDatalistBean;
import com.ytfu.yuntaifawu.utils.GlideManager;

import org.jetbrains.annotations.NotNull;

public class ContractListAdaper extends QuickAdapter<ContractDatalistBean.ListBean> {
    public ContractListAdaper() {
        super(R.layout.item_relevant_contract);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ContractDatalistBean.ListBean listBean) {
        super.convert(baseViewHolder, listBean);
            baseViewHolder.setText(R.id.text_title,listBean.getTitle());
            baseViewHolder.setText(R.id.text_count,"已有"+listBean.getDownload_count()+"购买");
        GlideManager.loadImageByUrl(getContext(),listBean.getImg(),baseViewHolder.getView(R.id.contract_icon));
    }
}