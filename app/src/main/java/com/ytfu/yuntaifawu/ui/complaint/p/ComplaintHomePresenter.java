package com.ytfu.yuntaifawu.ui.complaint.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.apis.ComplaintService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintClassificationBean;
import com.ytfu.yuntaifawu.ui.qisuzhuang.bean.QszFenLeiBean;

import io.reactivex.Observable;

public class ComplaintHomePresenter
        extends BaseRefreshPresenter<BaseRefreshView<ComplaintClassificationBean.ListBean>> {

    public void setComplaint() {
        Observable<ComplaintClassificationBean> observable = createService(ComplaintService.class).getComplaintClassification();
        requestRemote(observable, new BaseRxObserver<ComplaintClassificationBean>() {
            @Override
            public void onCompleteImpl() {
                super.onCompleteImpl();
                getView().stopRefresh();
            }

            @Override
            public void onNextImpl(ComplaintClassificationBean data) {
                if (data == null) {
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if (data.getStatus() != 200) {
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                if (data.getList() == null || data.getList().isEmpty()) {
                    getView().setEmptyView(R.layout.layout_empty);
                    return;
                }
                getView().setNewData(data.getList());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("setComplaint", errorMessage);
            }
        });
    }
}
