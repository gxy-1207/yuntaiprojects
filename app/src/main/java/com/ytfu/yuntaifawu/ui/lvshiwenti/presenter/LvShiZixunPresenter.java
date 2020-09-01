package com.ytfu.yuntaifawu.ui.lvshiwenti.presenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.ApiService;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.base.BasePresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.LawyerConsultingBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.LvShiZiXunListBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.QuickAnswerBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.view.IlvshiZiXunListView;
import com.ytfu.yuntaifawu.ui.mine.bean.MineZiXunBean;
import com.ytfu.yuntaifawu.ui.users.bean.MineConsulitatioBean;
import com.ytfu.yuntaifawu.utils.CommonUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
*
*  @Auther  gxy
*
*  @Date    2020/5/25
*
*  @Des 律师端 问题列表p
*
*/
public class LvShiZixunPresenter extends BaseRefreshPresenter<BaseRefreshView<LawyerConsultingBean.ListBean>> {

    public void getLvShiZiXunList(boolean isLoadMore,int page,String uid){
        HttpUtil.getInstance().getApiService().setLawyerZiXunList(page,uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new BaseRxObserver<LawyerConsultingBean>() {
                    @Override
                    public void onNextImpl(LawyerConsultingBean lvShiZiXunListBean) {

                        if(lvShiZiXunListBean == null){
                            if(!isLoadMore){
                                getView().setEmptyView(R.layout.layout_empty);
                                getView().stopRefresh();
                            }else{
                                getView().loadMoreComplete();
                            }
                            getView().resetCurrentPage(isLoadMore,1);
                            return;
                        }
                        int status = lvShiZiXunListBean.getStatus();
                        if(status == 1){
                            List<LawyerConsultingBean.ListBean> list = lvShiZiXunListBean.getList();
                            if(isLoadMore){
                                if(list == null || list.isEmpty()){
                                    getView().resetCurrentPage(true,1);
                                    getView().loadMoreEnd(false);
                                }else{
                                    getView().addData(list);
                                    getView().loadMoreComplete();
                                }
                            }else{
                                if(list == null || list.isEmpty()){
                                    getView().resetCurrentPage(false,1);
                                    getView().loadMoreEnd(false);
                                    getView().setEmptyView(R.layout.layout_empty);
                                }else{
                                    getView().getAdapter().getData().clear();
                                    getView().getAdapter().notifyDataSetChanged();
                                    Logger.e("===============================");
                                    getView().setNewData(list);
                                    getView().getRecycleView().scrollToPosition(0);
                                }
                                getView().stopRefresh();
                            }
                        }else{
                            getView().setEmptyView(R.layout.layout_empty);
                            getView().resetCurrentPage(isLoadMore, 1);
                            getView().loadMoreEnd(false);
                        }
                    }

                    @Override
                    public void onErrorImpl(String errorMessage) {
                        Logger.e("getMineZiXunList" + errorMessage);
                        getView().stopRefresh();
                        getView().loadMoreComplete();
                        if(!isLoadMore){
                            getView().setEmptyView(R.layout.layout_empty);
                        }
                    }
                });
    }

    //点击快速抢答
    public void getQuickAnswer(int position,String uid,String lsid,String consult_id){
        Observable<QuickAnswerBean> observable = createService(ApiService.class).setQuickAnswer(uid, lsid, consult_id);
        requestRemote(observable, new BaseRxObserver<QuickAnswerBean>() {
            @Override
            public void onSubscribeImpl(Disposable d) {
                super.onSubscribeImpl(d);
                getView().showProgress();
            }

            @Override
            public void onNextImpl(QuickAnswerBean data) {
                getView().hideProgress();
                if(data == null){
                    getView().showToast(CommonUtil.getString(R.string.text_error));
                    return;
                }
                if(data.getStatus()!=200){
                    getView().showToast(data.getMsg());
                    return;
                }
                getView().getAdapter().notifyItemChanged(position);
                getView().getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                Logger.e("getQuickAnswer",errorMessage);
            }
        });
    }
}
