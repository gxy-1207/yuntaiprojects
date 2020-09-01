package com.ytfu.yuntaifawu.ui.lawyer.question.p;

import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lawyer.question.v.QuestionSettingView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class QuestionSettingPresenter extends BasePresenter<QuestionSettingView> {

    public void saveInfo(String userId, boolean isOpen, String answer, String ares) {
        Observable<Object> ob = createService(ApiService.class)
                .saveAnswerInfo(userId, isOpen ? 1 : 0, answer, ares);
        requestRemote(ob, new BaseRxObserver<Object>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(Object data) {
                getView().hideProgress();
                getView().showToast("设置成功");
                getView().onSaveInfoSuccess();
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
                getView().showToast("保存信息失败,请稍后重试");
            }
        });
    }
}