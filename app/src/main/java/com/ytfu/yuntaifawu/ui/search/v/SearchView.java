package com.ytfu.yuntaifawu.ui.search.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.search.bean.HistoryWord;
import com.ytfu.yuntaifawu.ui.search.bean.RecommendWordBean;

import java.util.List;

public interface SearchView extends BaseView {

    void onRecommendWordsSuccess(List<RecommendWordBean.DataBean> data);

    void onRecommendWordsFail();

    void onSearchHistorySuccess(List<HistoryWord> data);
}
