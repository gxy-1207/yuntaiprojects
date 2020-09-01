package com.ytfu.yuntaifawu.ui.users.p;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.LawyerService;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.home.bean.HomeBannerBean;
import com.ytfu.yuntaifawu.ui.users.bean.LawyerHomeListBean;
import com.ytfu.yuntaifawu.ui.users.v.IUserHomeView;
import com.ytfu.yuntaifawu.utils.ToastUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Auther gxy
 * @Date 2020/6/9
 * @Des* 用户端首页presenter
 */
public class UserHomePresenter extends BaseRefreshPresenter<IUserHomeView> {
    //首页轮播
    public void getHomeBannerList() {
        HttpUtil.getInstance().getApiService().homeBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new BaseRxObserver<HomeBannerBean>() {
                    @Override
                    public void onNextImpl(HomeBannerBean entity) {
                        if (AppConstant.CODE_SUCCESS == entity.getStatus()) {
                            getView().userHomeBannerList(entity);
                        } else {
                            getView().userHomeBannerList(null);
                        }

                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        Logger.e("getBannerList" + errorMessage);
                        ToastUtil.showToast("网络开小差了");
                        getView().userHomeFiled(errorMessage);
                    }
                });
    }

    //首页律师接口
    public void getLawyerList(boolean isLoadMore, String lawyername, String expertplace, String page) {
        LawyerService service = HttpUtil.getInstance().getService(LawyerService.class);
        Observable<LawyerHomeListBean> observable = service.setLawyerList(lawyername, expertplace, page);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<LawyerHomeListBean>() {
            @Override
            public void onNextImpl(LawyerHomeListBean data) {
//                getView().userLawyerList(data);
                if (isLoadMore) {
                    if (null == data) {
                        getView().resetCurrentPage(isLoadMore, 1);
                        getView().loadMoreFail();
                        return;
                    }
                    if (data.getData() == null || data.getData().isEmpty()) {
                        getView().loadMoreEnd(false);
                    } else {
                        getView().loadMoreComplete();
                    }
                    getView().addData(data.getData());
                } else {
                    getView().stopRefresh();
                    getView().setNewData(data.getData());
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().loadMoreComplete();
                getView().stopRefresh();
                getView().resetCurrentPage(isLoadMore,1);
            }
        });
    }
}
