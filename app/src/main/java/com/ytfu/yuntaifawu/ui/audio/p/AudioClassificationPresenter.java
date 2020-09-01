package com.ytfu.yuntaifawu.ui.audio.p;

import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.audio.v.AudioClassificationView;
import com.ytfu.yuntaifawu.ui.home.bean.AudioNavBean;

import java.util.List;

import io.reactivex.Observable;

public class AudioClassificationPresenter extends BasePresenter<AudioClassificationView> {


    public void getAudioClassify() {
        Observable<AudioNavBean> ob = createService(ApiService.class)
                .navTitle();
        requestRemote(ob, new BaseRxObserver<AudioNavBean>() {
            @Override
            public void onNextImpl(AudioNavBean data) {
                if (null == data) {
                    getView().showError();
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showError();
                    return;
                }
                List<AudioNavBean.ListBean> list = data.getList();
                if (null == list || list.isEmpty()) {
                    getView().showEmpty();
                    return;
                }
                getView().onGetAudioClassify(list);
                getView().hideLoading();
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().showError();
            }
        });
    }
}
