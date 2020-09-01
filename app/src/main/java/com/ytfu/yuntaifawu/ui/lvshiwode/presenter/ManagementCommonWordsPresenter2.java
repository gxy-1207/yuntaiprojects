package com.ytfu.yuntaifawu.ui.lvshiwode.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.CommonWordsApi;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.ManagementCommonWordsView;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.ManagementCommonWordsView2;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Auther gxy
 * @Date 2020/7/15
 * @Des 管理常用语p
 */
public class ManagementCommonWordsPresenter2 extends BasePresenter<ManagementCommonWordsView2> {
    public void getClassificationOfCommonWords(){
        Observable<ClassificationOfCommonWordsBean> observable = createService(CommonWordsApi.class).setClassificationOfCommonWords();
        requestRemote(observable, new BaseRxObserver<ClassificationOfCommonWordsBean>() {
            @Override
            public void onNextImpl(ClassificationOfCommonWordsBean data) {
                if(data == null){
                    return;
                }
                if(data.getStatus() !=1){
                    return;
                }
                if(data.getList() == null || data.getList().isEmpty()){
                    return;
                }
                getView().onCategorySuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getClassificationOfCommonWords",errorMessage);
            }
        });
    }
} 