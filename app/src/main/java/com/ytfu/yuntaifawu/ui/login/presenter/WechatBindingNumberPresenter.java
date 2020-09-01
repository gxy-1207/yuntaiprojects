package com.ytfu.yuntaifawu.ui.login.presenter;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.login.bean.CodeBean;
import com.ytfu.yuntaifawu.ui.login.bean.WechatBindingnumBean;
import com.ytfu.yuntaifawu.ui.login.view.WechatBindingNumberView;

import io.reactivex.Observable;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/8
*
*  @Des 微信登录绑定手机号
*
*/
public class WechatBindingNumberPresenter extends BasePresenter<WechatBindingNumberView> {
    //获取验证码
    public void getWechatBindingCode(String uid,String mobile){
        Observable<CodeBean> observable = createService(ApiService.class).setWechatBingingCode(uid, mobile);
        requestRemote(observable, new BaseRxObserver<CodeBean>() {
            @Override
            public void onNextImpl(CodeBean data) {
                if(data.getStatus() == 0){
                    getView().showToast("获取验证码失败，请稍后再试");
                    return;
                }
                getView().showToast("验证码发送成功");
                getView().onBindingSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getWechatBindingCode","========"+errorMessage);
            }
        });
    }

    //微信登录绑定手机号
    public void getWechatBindingNum(String uid,String mobile,String code){
        Observable<WechatBindingnumBean> observable = createService(ApiService.class).setWechatBindingNum(uid, mobile, code);
        requestRemote(observable, new BaseRxObserver<WechatBindingnumBean>() {
            @Override
            public void onNextImpl(WechatBindingnumBean data) {
                if(data == null){
                    getView().showToast("应用程序内部出现问题，请稍后再试");
                    return;
                }
                getView().onBindingMobileSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
            Logger.e("getWechatBindingNum","======"+errorMessage);
            }
        });
    }
} 