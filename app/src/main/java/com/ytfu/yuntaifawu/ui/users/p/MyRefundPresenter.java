package com.ytfu.yuntaifawu.ui.users.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.RefundService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.users.bean.MyRefundBean;

import io.reactivex.Observable;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/23
*
*  @Des 我的退款p
*
*/
public class MyRefundPresenter extends BaseRefreshPresenter<BaseRefreshView<MyRefundBean.DataBean>> {

    public void getMyRefundList(String UserID){
        Observable<MyRefundBean> observable = createService(RefundService.class).setMyRefundList(UserID);
        requestRemote(observable, new BaseRxObserver<MyRefundBean>() {
            @Override
            public void onNextImpl(MyRefundBean data) {
                getView().stopRefresh();
                if(data == null){
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if(data.getCode() !=200){
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if(data.getData() == null || data.getData().isEmpty()){
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                getView().setNewData(data.getData());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getMyRefundList",errorMessage);
            }
        });
    }
} 