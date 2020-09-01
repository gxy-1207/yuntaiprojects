package com.ytfu.yuntaifawu.ui.contract.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.contract.adaper.ContractListAdaper;
import com.ytfu.yuntaifawu.ui.contract.p.ContractListPresenter;
import com.ytfu.yuntaifawu.ui.lvshihetong.activity.ActivityContractDetails;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractDatalistBean;
import com.ytfu.yuntaifawu.utils.ItemDecoration;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/30
*
*  @Des 合同列表f
*
*/
@InjectPresenter(ContractListPresenter.class)
public class ContractListFragment extends BaseRecyclerViewFragment<ContractDatalistBean.ListBean,
        BaseRefreshView<ContractDatalistBean.ListBean>, ContractListPresenter> {
    private static final String KEY_CLASSIFICATION_ID = "CLASSIFICATION_ID";
    public static ContractListFragment newInstance(String classificationId) {

        Bundle args = new Bundle();
        args.putString(KEY_CLASSIFICATION_ID,classificationId);
        ContractListFragment fragment = new ContractListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        int color = Color.parseColor("#F2F2F2");
        int lineHeight = XPopupUtils.dp2px(mContext, 0.5F);
        addItemDecoration(ItemDecoration.createVertical(color, lineHeight, 0));
        getRecycleView().setBackgroundColor(Color.WHITE);
        getAdapter().setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ActivityContractDetails.class);
            intent.putExtra("id",getAdapter().getData().get(position).getId());
            startActivity(intent);
        });
    }

    @Override
    protected BaseQuickAdapter<ContractDatalistBean.ListBean, BaseViewHolder> createAdapter() {
        return new ContractListAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        if(isLoadMore){
            currentPage++;
        }else{
            currentPage=1;
        }
        String classificationId = getArgumentString(KEY_CLASSIFICATION_ID, "");
        getPresenter().contractListData(isLoadMore,classificationId,currentPage);
    }
}