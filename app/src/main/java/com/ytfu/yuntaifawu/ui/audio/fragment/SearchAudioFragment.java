package com.ytfu.yuntaifawu.ui.audio.fragment;

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
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.audio.act.AudioDetailActivity;
import com.ytfu.yuntaifawu.ui.audio.adapter.AudioListAdapter;
import com.ytfu.yuntaifawu.ui.audio.p.AudioListPresenter;
import com.ytfu.yuntaifawu.ui.audio.p.AudioSearchListPresenter;
import com.ytfu.yuntaifawu.ui.home.bean.AudioListBean;
import com.ytfu.yuntaifawu.utils.CommonUtil;

@InjectPresenter(AudioSearchListPresenter.class)
public class SearchAudioFragment extends BaseRecyclerViewFragment<AudioListBean.ListBean,
        BaseRefreshView<AudioListBean.ListBean>, AudioSearchListPresenter> {

    private EditText ed_search_contract;

    public static SearchAudioFragment newInstance() {

        Bundle args = new Bundle();

        SearchAudioFragment fragment = new SearchAudioFragment();
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
        getAdapter().setOnItemClickListener((adapter, view, position) -> {
            AudioDetailActivity.Companion.start(getActivity(),getAdapter().getData().get(position).getId());
        });
    }

    @Override
    protected BaseQuickAdapter<AudioListBean.ListBean, BaseViewHolder> createAdapter() {
        return new AudioListAdapter();
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
        getPresenter().getAudioSearchList(isLoadMore, currentPage, input);
    }
}