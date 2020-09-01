package com.ytfu.yuntaifawu.ui.contract.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.ui.contract.act.ContractListActivity;
import com.ytfu.yuntaifawu.ui.contract.adaper.ContractClassificationAdaper;
import com.ytfu.yuntaifawu.ui.contract.p.ContractClassificationPresenter;
import com.ytfu.yuntaifawu.ui.contract.v.ContractClassificationView;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractListSecondBean;
import com.ytfu.yuntaifawu.utils.ItemDecoration;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/30
*
*  @Des 合同分类fragment
*
*/
@InjectPresenter(ContractClassificationPresenter.class)
public class ContractClassificationFragment extends BaseRecyclerViewFragment<ContractListSecondBean.ListBean,
        ContractClassificationView, ContractClassificationPresenter> implements ContractClassificationView{
    private static final String KEY_ID = "ID";
    public static ContractClassificationFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(KEY_ID,id);
        ContractClassificationFragment fragment = new ContractClassificationFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected BaseQuickAdapter<ContractListSecondBean.ListBean, BaseViewHolder> createAdapter() {
        return new ContractClassificationAdaper();
    }

    @Override
    protected void initData() {
        super.initData();
        enableLoadMore(false);
        enableRefresh(false);
        int color = Color.parseColor("#F2F2F2");
        int lineHeight = XPopupUtils.dp2px(mContext, 0.5F);
        addItemDecoration(ItemDecoration.createVertical(color, lineHeight, 0));
        getRecycleView().setBackgroundColor(getResources().getColor(R.color.textcolor_f2));
        getAdapter().setOnItemClickListener((adapter, view, position) -> {
            ContractListActivity.start(getActivity(),getAdapter().getData().get(position).getId()
                    ,getAdapter().getData().get(position).getLabel());
        });
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        String id = getArgumentString(KEY_ID, "");
        getPresenter().contractSecondList(id);
    }

    @Override
    public void onContractSuccess(ContractListSecondBean listSecondBean) {

    }

    @Override
    public void onContractFiled() {

    }
}