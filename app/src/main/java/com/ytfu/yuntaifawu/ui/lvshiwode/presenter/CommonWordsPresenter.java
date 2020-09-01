package com.ytfu.yuntaifawu.ui.lvshiwode.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.CommonWordsApi;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.AddCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.ClassificationOfCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.CommonWordsView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/14
*
*  @Des 常用语p
*
*/
public class CommonWordsPresenter extends BasePresenter<CommonWordsView> {

    public void getAddCommonWords(String UserID,String Content,String cid){
        Observable<AddCommonWordsBean> observable = createService(CommonWordsApi.class).setAddCommonWords(UserID, Content,cid);
        requestRemote(observable, new BaseRxObserver<AddCommonWordsBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(AddCommonWordsBean data) {
                getView().hideProgress();
                if(data == null){
                    getView().showToast("应用程序内部问题，请稍后再试");
                    return;
                }
                if(data.getCode()!=200){
                    getView().onAddCommonWordsFiled(data.getMsg());
                    return;
                }
                getView().onAddCommonWordsSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getAddCommonWords",errorMessage);
            }
        });
    }


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
                getView().onClassificationOfCommonWordsSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getClassificationOfCommonWords",errorMessage);
            }
        });
    }
} 