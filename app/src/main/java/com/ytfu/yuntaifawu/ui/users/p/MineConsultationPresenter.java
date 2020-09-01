package com.ytfu.yuntaifawu.ui.users.p;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.apis.MineConsultationService;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.users.bean.MineConsulitatioBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Auther gxy
 * @Date 2020/6/12
 * @Des 我的咨询presenter
 */
public class MineConsultationPresenter extends BaseRefreshPresenter<BaseRefreshView<MineConsulitatioBean.ListBean>> {
    //我这咨询列表
    public void getMineConsultationList(boolean isLoadMore, int page, String uid) {
        MineConsultationService service = HttpUtil.getInstance().getService(MineConsultationService.class);
        Observable<MineConsulitatioBean> observable = service.setMineConsulitationList(page, uid);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<MineConsulitatioBean>() {
            @Override
            public void onNextImpl(MineConsulitatioBean data) {
                getView().stopRefresh();
                if(data == null){
                    if(!isLoadMore){
                        getView().setEmptyView(R.layout.layout_empty);
                    }else{
                        getView().loadMoreComplete();
                    }
                    getView().resetCurrentPage(isLoadMore,1);
                    return;
                }
                int status = data.getStatus();
                if(status == 1){
                    List<MineConsulitatioBean.ListBean> list = data.getList();
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
                            getView().setNewData(list);
                        }
                    }
                }else{
                    getView().setEmptyView(R.layout.layout_empty);
                    getView().resetCurrentPage(isLoadMore, 1);
                    getView().loadMoreEnd(false);
                }
            }

            @Override
            public void onErrorImpl(String errorMessage) {
                getView().stopRefresh();
                getView().loadMoreComplete();
                if(!isLoadMore){
                    getView().setEmptyView(R.layout.layout_empty);
                }
            }
        });
    }
}
