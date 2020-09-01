package com.ytfu.yuntaifawu.ui.lvshiwode.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.CommonWordsApi;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.ManagementCommonWordsView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Auther gxy
 * @Date 2020/7/15
 * @Des 管理常用语p
 */
public class ManagementCommonWordsPresenter extends BaseRefreshPresenter<ManagementCommonWordsView> {


    public void getCommonWordsList(String UserID,String id) {
        Observable<CommonWordsListBean> observable = createService(CommonWordsApi.class).setCommonWordList(UserID,id);
        requestRemote(observable, new BaseRxObserver<CommonWordsListBean>() {
            @Override
            public void onNextImpl(CommonWordsListBean data) {
                getView().stopRefresh();
                if (data == null) {
                    getView().showToast(data.getMsg());
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if (data.getCode() != 200) {
                    getView().showToast(data.getMsg());
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if(data.getData() == null || data.getData().isEmpty()){
                    getView().showToast(data.getMsg());
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                getView().setNewData(data.getData());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getCommonWordsList", errorMessage);
            }
        });
    }

    //删除
    public void getDelCommonWords(CommonWordsListBean.DataBean dataBean, String Type) {
        Observable<EditDeleteCommonWordsBean> observable = createService(CommonWordsApi.class).setdelCommonWords(dataBean.getId(), Type);
        requestRemote(observable, new BaseRxObserver<EditDeleteCommonWordsBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(EditDeleteCommonWordsBean data) {
                getView().hideProgress();
                if (data == null) {
                    getView().showToast("应用程序内部出现问题,请稍后再试");
                    return;
                }
                if (data.getCode() != 200) {
                    getView().onfiledError(data.getMsg());
                    return;
                }
                getView().getAdapter().remove(dataBean);
//                getView().onDelSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getDelCommonWords", errorMessage);
            }
        });
    }
} 