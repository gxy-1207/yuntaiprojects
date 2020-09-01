package com.ytfu.yuntaifawu.ui.zixun.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.ReplyDetailsApi;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.zixun.bean.AdoptionBean;
import com.ytfu.yuntaifawu.ui.zixun.bean.ReplyDetailBean;
import com.ytfu.yuntaifawu.ui.zixun.v.AdoptionAndReplyDetailView;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import io.reactivex.Observable;

/**
 * @Auther gxy
 * @Date 2020/6/16
 * @Des 评价回复详情和采纳presenter
 */
public class AdoptionAndReplyDetailPresenter extends BasePresenter<AdoptionAndReplyDetailView> {
    //评价回复详情
    public void getReplyDetails(String id) {
        ReplyDetailsApi service = HttpUtil.getInstance().getService(ReplyDetailsApi.class);
        Observable<ReplyDetailBean> observable = service.setReplyDetail(id);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<ReplyDetailBean>() {
            @Override
            public void onNextImpl(ReplyDetailBean data) {
                if(data == null){
                    getView().showEmpty();
                    return;
                }
                if (data.getStatus() != 200) {
                    getView().showEmpty();
                    return;
                }
                if(data.getFind() == null){
                    getView().showEmpty();
                    return;
                }
                getView().onReplyDetailsSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getReplyDetails" + errorMessage);
            }
        });
    }

    //采纳
    public void getAdoption(String uid, String pingjia_uid, String pingjia_id, String consult_id) {
        ReplyDetailsApi service = HttpUtil.getInstance().getService(ReplyDetailsApi.class);
        Observable<AdoptionBean> observable = service.setAdoption(uid, pingjia_uid, pingjia_id, consult_id);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<AdoptionBean>() {
            @Override
            public void onNextImpl(AdoptionBean data) {
                if(data.getStatus() == 200){
                    getView().onAdoptionSuccess(data);
                }else{
                    ToastUtil.showCenterToast(data.getMsg());
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {

            }
        });
    }
}
