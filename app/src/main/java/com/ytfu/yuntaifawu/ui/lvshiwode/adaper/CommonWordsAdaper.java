package com.ytfu.yuntaifawu.ui.lvshiwode.adaper;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;

import org.jetbrains.annotations.NotNull;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/15
*
*  @Des
*
*/
public class CommonWordsAdaper extends QuickAdapter<CommonWordsListBean.DataBean> {

    public CommonWordsAdaper() {
        super(R.layout.common_words_list_item);
        addChildClickViewIds(R.id.tv_edit);
        addChildClickViewIds(R.id.tv_del);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CommonWordsListBean.DataBean dataBean) {
        super.convert(baseViewHolder, dataBean);
        baseViewHolder.setText(R.id.tv_common_words_content,dataBean.getContent());
    }
}