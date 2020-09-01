package com.ytfu.yuntaifawu.ui.home.presenter;

import com.ytfu.yuntaifawu.apis.AdvisoryApi;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.apis.LawyerService;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.consult.bean.ZiXunNavBean;
import com.ytfu.yuntaifawu.ui.home.bean.AdvisoryPostBean;
import com.ytfu.yuntaifawu.ui.home.bean.RandomLawyerResponse;
import com.ytfu.yuntaifawu.ui.home.view.AdvisoryView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class AdvisoryPresenter extends BasePresenter<AdvisoryView> {
    public void requestSendMessage(String uid, Runnable call) {
        //        HttpUtil.getInstance().getService(ChatService.class)
        //                .test(uid)
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .as(bindLifecycle())
        //                .subscribe(new BaseRxObserver<Object>() {
        //                    @Override
        //                    public void onNextImpl(Object lawyerListBean) {
        //                        call.run();
        //                    }
        //
        //                    @Override
        //                    public void onErrorImpl(String errorMessage) {
        //                        call.run();
        //                        Logger.e("============");
        //                    }
        //                });
    }

    /**
     * 发布界面获取必要数据信息
     */
    public void getAdvisoryInfo() {
        Observable<ZiXunNavBean> ob = createService(ApiService.class)
                .getZiXunNav();
        requestRemote(ob, new BaseRxObserver<ZiXunNavBean>() {
            @Override
            public void onNextImpl(ZiXunNavBean data) {
                if (null == data) {
                    getView().showError();
                    return;
                }
                if (AppConstant.CODE_SUCCESS == data.getStatus()) {
                    getView().onGetAdvisoryInfo(data);
                    getView().hideLoading();
                } else {
                    getView().showError();
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().showError();
            }
        });

        //        @Override
        //        public void onNextImpl(ZiXunNavBean entity) {
        //            if (AppConstant.CODE_SUCCESS == entity.getStatus()) {
        //                getView().onZiXunNavSuccess(entity);
        //            } else {
        //                getView().onZiXunNavSuccess(null);
        //            }
        //
        //        }
        //
        //        @Override
        //        public void onErrorImpl(String errorMessage) {
        //            Logger.e("getNavTitle" + errorMessage);
        //            //                        ToastUtil.showToast("网络开小差了");
        //            getView().showTimeout();
        //            getView().onZiXunNavFiled();
        //        }

    }

    /**
     * 提交发布
     *
     * @param userId         用户id
     * @param consultType    分类
     * @param consultContent 咨询内容
     * @param money          悬赏金额
     */
    public void commit(String userId, String consultType, String consultContent, double money) {
        boolean isUseMoney;
        if (money == 0) {
            isUseMoney = false;
        } else {
            isUseMoney = true;
        }

        Observable<AdvisoryPostBean> ob = createService(AdvisoryApi.class)
                .postConsultation(userId, consultType, consultContent, isUseMoney ? 1 : 0);

        requestRemote(ob, new BaseRxObserver<AdvisoryPostBean>() {

            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(AdvisoryPostBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("请求数据出现错误，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 200) {
                    getView().showToast(data.getMsg());
                    return;
                }
                //清空用户输入
                getView().cleanUserInput();
                //判断是否需要跳转到付费页面
                if (isUseMoney) {
                    //获取付费页面信息
                    getView().onGotoPayActivity(data.getZixun_id(), money);
                } else {
                    //进入我的咨询列表
                    getView().onGotoPostSuccessActivity(data.getZixun_id());
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
                getView().showToast(errorMessage);
            }
        });

    }

    public void getRandomLawyers() {
        Observable<RandomLawyerResponse> ob = createService(LawyerService.class)
                .getRandomLawyers();

        requestRemote(ob, new BaseRxObserver<RandomLawyerResponse>() {
            @Override
            public void onNextImpl(RandomLawyerResponse data) {
                if (null == data) {
                    getView().onGetRandomLawyersFail();
                    return;
                }
                if (data.getCode() != 200) {
                    getView().onGetRandomLawyersFail();
                    return;
                }
                RandomLawyerResponse.D d = data.getData();
                if (null == d || null == d.getResult() || d.getResult().isEmpty()) {
                    getView().onGetRandomLawyersFail();
                    return;
                }
                getView().onGetRandomLawyers(d);

            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().onGetRandomLawyersFail();
            }
        });
    }

}
