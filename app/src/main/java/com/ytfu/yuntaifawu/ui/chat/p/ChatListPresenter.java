package com.ytfu.yuntaifawu.ui.chat.p;

import android.text.TextUtils;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatRoomManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.apis.ChatService;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.TopPaymentApi;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.im.EmChatManager;
import com.ytfu.yuntaifawu.ui.chat.bean.DeleteResponseBean;
import com.ytfu.yuntaifawu.ui.chat.bean.LawyerListBean;
import com.ytfu.yuntaifawu.ui.chat.bean.UnLockMessage;
import com.ytfu.yuntaifawu.ui.chat.v.IChatListView;
import com.ytfu.yuntaifawu.ui.pay.PayHelper;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.LogUtils;

public class ChatListPresenter extends BasePresenter<IChatListView> implements EMMessageListener {
    /**
     * 获取律师列表
     */
    public void loadLawyerList(String uid) {
        HttpUtil.getInstance().getService(ChatService.class)
                .getLawyerChatList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new BaseRxObserver<LawyerListBean>() {
                    @Override
                    public void onNextImpl(LawyerListBean lawyerListBean) {
                        if (null == lawyerListBean) {
                            getView().onLawyerListFail("加载失败");
                        } else {
                            if ("200".equals(lawyerListBean.getCode()) || "202".equals(lawyerListBean.getCode())) {
                                getView().onLawyerListSuccess(lawyerListBean.getData());

                                //                                EventBusMessage eventBusMessage = new EventBusMessage();
                                //                                eventBusMessage.what = 100;
                                //                                eventBusMessage.obj = 0;
                                //                                EventBus.getDefault().postSticky(eventBusMessage);
                            } else {
                                getView().onLawyerListFail("错误码:" + lawyerListBean.getCode() + "  " + lawyerListBean.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        getView().onLawyerListFail(errorMessage);
                    }
                });
    }

    public void loadLawyerList2(String uid, UnLockMessage unLockMessage) {
        HttpUtil.getInstance().getService(ChatService.class)
                .getLawyerChatList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new BaseRxObserver<LawyerListBean>() {
                    @Override
                    public void onNextImpl(LawyerListBean lawyerListBean) {
                        if (null == lawyerListBean) {
                            getView().onLawyerListFail("加载失败");
                        } else {
                            if ("200".equals(lawyerListBean.getCode()) || "202".equals(lawyerListBean.getCode())) {
                                getView().onLawyerListSuccess2(lawyerListBean.getData(), unLockMessage);
                            } else {
                                getView().onLawyerListFail("错误码:" + lawyerListBean.getCode() + "  " + lawyerListBean.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        getView().onLawyerListFail(errorMessage);
                    }
                });
    }


    public void getConsultType(String userId, Runnable isOkRun) {
        Observable<UnLockMessage> ob = createService(ChatService.class)
                .getConsultType(userId);
        requestRemote(ob, new BaseRxObserver<UnLockMessage>() {
            @Override
            public void onNextImpl(UnLockMessage data) {
                //con_type	int	状态 1是显示消息列表2是未解锁，需要去解锁
                if (null == data) {
                    getView().showToast("应用程序出现位置错误,请稍后重试");
                    return;
                }
                int con_type = data.getCon_type();
                if (con_type == 1) {
                    isOkRun.run();

                } else {
                    getView().showPayDialog(data);
                }
                //                getView().onShowUnLockMessage(data);

            }

            @Override
            public void onErrorImpl(String errorMessage) {

            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    public void registerMessageListener() {
        EmChatManager.getInstance().registerMessageListener(this);
    }

    public void unRegisterMessageListener() {
        EmChatManager.getInstance().unRegisterMessageListener(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 环信接收消息的监听
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //        for (EMMessage item : list) {
        //            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(item.conversationId());
        //            conversation.getLastMessage()
        //            LogUtils.e("--------------->>>>>>>>" + conversation.getLastMessage());
        //        }

        //getView().onMessageReceived(list);
        getView().reLoadList();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    //===Desc:================================================================================

    /**
     * 微信支付
     */
    public void payWatch(String uid, String id, int type, int xitong, String consult_jiaji, String price) {
        Observable<WxPayBean> observable = createService(TopPaymentApi.class).setPayWatch(uid, id, type, xitong, consult_jiaji, price);
        requestRemote(observable, new BaseRxObserver<WxPayBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(WxPayBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast("请求数据出现错误，请稍后重试");
                    return;
                }
                WxPayBean.SignBean sign = data.getSign();
                if (sign == null) {
                    getView().showToast("数据解析失败，请稍后重试");
                    return;
                }
                //唤起微信
                PayHelper.getInstance().payByWechat(sign);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch", errorMessage);
            }
        });
    }

    /**
     * 支付宝支付
     */
    public void payAli(String uid, String id, int type, int xitong, String consult_jiaji, String price) {
        Observable<PayBean> observable = createService(TopPaymentApi.class).stePayAli(uid, id, type, xitong, consult_jiaji, price);
        requestRemote(observable, new BaseRxObserver<PayBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(PayBean data) {
                getView().hideProgress();
                if (data == null) {
                    getView().showToast("获取数据失败,请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast(data.getState());
                    return;
                }
                if (TextUtils.isEmpty(data.getSign())) {
                    getView().showToast("数据异常,请稍后重试");
                    return;
                }
                //唤起支付宝
                getView().onAwakeAli(data.getSign());
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch", errorMessage);
            }
        });
    }

    /**
     * 余额
     */
    public void payOverage(String uid, String id, int type, int xitong, String consult_jiaji, String price) {
        Observable<AccountPayResponseBean> observable = createService(TopPaymentApi.class).setPayOverage(uid, id, type, xitong, consult_jiaji, price);
        requestRemote(observable, new BaseRxObserver<AccountPayResponseBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(AccountPayResponseBean data) {
                getView().hideProgress();
                if (data == null) {
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                int status = data.getStatus();
                if (status != 1) {
                    getView().showToast("获取数据失败，请稍后重试");
                    return;
                }
                getView().onPayByAccountSuccess(data);
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("payWatch", errorMessage);
            }
        });
    }


    public void deleteConversation(String userId, String conversationIds) {
        Observable<DeleteResponseBean> ob = createService(ChatService.class)
                .deleteConversation(userId, conversationIds);

        requestRemote(ob, new BaseRxObserver<DeleteResponseBean>() {

            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(DeleteResponseBean data) {
                getView().hideProgress();
                if (null == data) {
                    getView().showToast("应用程序错误,请稍后重试");
                    return;
                }
                if (data.getStatus() != 200) {
                    getView().showToast(data.getMsg());
                    return;
                }
                getView().onDeleteSuccess();
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().showToast("应用程序错误,请稍后重试");
                getView().hideProgress();
            }
        });
    }
}
