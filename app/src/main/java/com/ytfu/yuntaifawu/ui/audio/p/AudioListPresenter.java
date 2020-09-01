package com.ytfu.yuntaifawu.ui.audio.p;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.home.bean.AudioListBean;

import java.util.List;

import io.reactivex.Observable;

public class AudioListPresenter extends BaseRefreshPresenter<BaseRefreshView<AudioListBean.ListBean>> {


    public void getAudioList(boolean isLoadMore, int page, String classId, String keyword) {
        Observable<AudioListBean> ob = createService(ApiService.class)
                .navAudioList(classId, page, keyword);
        requestRemote(ob, new BaseRxObserver<AudioListBean>() {
            @Override
            public void onNextImpl(AudioListBean data) {
                if (null == data) {
                    if (isLoadMore) {
                        getView().loadMoreComplete();
                    } else {
                        getView().setEmptyView(R.layout.layout_empty);
                        getView().stopRefresh();
                    }
                    getView().resetCurrentPage(isLoadMore, 1);
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    if (isLoadMore) {
                        getView().loadMoreEnd(false);
                    } else {
                        getView().setEmptyView(R.layout.layout_empty);
                        getView().stopRefresh();
                    }
                    getView().resetCurrentPage(isLoadMore, 1);
                    return;
                }
                List<AudioListBean.ListBean> list = data.getList();
                if (null == list || list.isEmpty()) {
                    if (isLoadMore) {
                        getView().loadMoreEnd(false);
                    } else {
                        getView().setEmptyView(R.layout.layout_empty);
                        getView().stopRefresh();
                    }
                    getView().resetCurrentPage(isLoadMore, 1);
                    return;
                }
                if (isLoadMore) {
                    getView().loadMoreComplete();
                    getView().addData(list);
                } else {
                    getView().setNewData(list);
                    getView().stopRefresh();
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                if (isLoadMore) {
                    getView().loadMoreComplete();
                } else {
                    getView().setEmptyView(R.layout.layout_empty);
                    getView().stopRefresh();
                }
                getView().resetCurrentPage(isLoadMore, 1);
            }
        });
    }
}
