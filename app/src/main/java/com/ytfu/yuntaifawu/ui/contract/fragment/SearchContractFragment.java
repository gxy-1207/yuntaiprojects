package com.ytfu.yuntaifawu.ui.contract.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.contract.adaper.SearchContractAdaper;
import com.ytfu.yuntaifawu.ui.contract.p.SearchContractPresenter;
import com.ytfu.yuntaifawu.ui.lvshihetong.activity.ActivityContractDetails;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractDatalistBean;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.ItemDecoration;

@InjectPresenter(SearchContractPresenter.class)
public class SearchContractFragment extends BaseRecyclerViewFragment<ContractDatalistBean.ListBean,
        BaseRefreshView<ContractDatalistBean.ListBean>, SearchContractPresenter> {

    private EditText ed_search_contract;

    public static SearchContractFragment newInstance() {

        Bundle args = new Bundle();

        SearchContractFragment fragment = new SearchContractFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        super.init();
        autoRefresh = false;
    }

    @Override
    protected void initData() {
        super.initData();
        View viewBottom = LayoutInflater.from(getContext()).inflate(R.layout.search_contract_activity_top, null, false);
        viewBottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        ed_search_contract = viewBottom.findViewById(R.id.ed_search_contract);
        addTopSuspensionView(viewBottom);
        ed_search_contract.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String input = ed_search_contract.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    getPresenter().refresh();
                    CommonUtil.hideSoftInput(getActivity());
                }
                return true;
            }
            return false;
        });
        getRecycleView().setBackgroundColor(Color.WHITE);
        int color = Color.parseColor("#F2F2F2");
        int lineHeight = XPopupUtils.dp2px(mContext, 0.5F);
        addItemDecoration(ItemDecoration.createVertical(color, lineHeight, 0));
        getAdapter().setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ActivityContractDetails.class);
            intent.putExtra("id",getAdapter().getData().get(position).getId());
            startActivity(intent);
        });
    }

    @Override
    protected BaseQuickAdapter<ContractDatalistBean.ListBean, BaseViewHolder> createAdapter() {
        return new SearchContractAdaper();
    }


    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        String input = ed_search_contract.getText().toString();
        if (TextUtils.isEmpty(input)) {
            showToast("请输入搜索内容");
            return;
        }
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        getPresenter().setSearchContractList(isLoadMore,input,currentPage);
    }
}