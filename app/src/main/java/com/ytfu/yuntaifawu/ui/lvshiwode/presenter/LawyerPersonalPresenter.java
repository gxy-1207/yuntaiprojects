package com.ytfu.yuntaifawu.ui.lvshiwode.presenter;

import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.LawyerService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.QuestionResponseBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.LawyerPersonalView;
import com.ytfu.yuntaifawu.ui.mine.bean.MineBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 律师我的界面的P层
 */
public class LawyerPersonalPresenter extends BasePresenter<LawyerPersonalView> {

    /**
     * 律师获取个人数据
     */
    public void requestPersonal(String uid) {
        HttpUtil.getInstance().getService(LawyerService.class)
                .getPersonalData(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new BaseRxObserver<MineBean>() {


                    @Override
                    public void onNextImpl(MineBean mineBean) {
                        if (null == mineBean) {
                            getView().onGetPersonalFail("获取数据失败");
                            return;
                        }
                        int status = mineBean.getStatus();
                        if (status != 200) {
                            getView().onGetPersonalFail("获取数据失败");
                            return;
                        }
                        if (null == mineBean.getFind()) {
                            getView().onGetPersonalFail("解析数据失败");
                            return;
                        }
                        getView().onGetPersonalSuccess(mineBean);
                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        getView().onGetPersonalFail(errorMessage);
                    }
                });

    }


    /**
     * 获取律师设置抢答信息
     */
    public void getQuestionInfo(String lawyerId) {
        Observable<QuestionResponseBean> ob = createService(LawyerService.class)
                .getQuestionInfo(lawyerId);
        requestRemote(ob, new BaseRxObserver<QuestionResponseBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(QuestionResponseBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }
                int code = data.getCode();
                if (code != 200) {
                    getView().showToast(data.getMsg());
                    return;
                }
                QuestionResponseBean.DataBean dataBean = data.getData();
                if (null == dataBean) {
                    getView().showToast("数据解析失败,请稍后重试");
                    return;
                }
                getView().onGetQuestionInfoSuccess(dataBean);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().hideProgress();
                getView().showToast("网络出现异常，请稍后重试");

            }
        });


    }
}
