package com.ytfu.yuntaifawu.ui.users.p;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.AnnouncementService;
import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.base.BaseRefreshPresenter;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.helper.BaseRxObserver;
import com.ytfu.yuntaifawu.ui.users.bean.AnnouncementBean;
import com.ytfu.yuntaifawu.ui.users.v.AnnouncementView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Auther gxy
 * @Date 2020/6/10
 * @Des 公告presenter
 */
public class AnnouncementPresenter extends BaseRefreshPresenter<BaseRefreshView<AnnouncementBean.DataBean>> {

    //获取公告列表
    public void getAnnouncementList(boolean isLoadMore,int page) {
        AnnouncementService service = HttpUtil.getInstance().getService(AnnouncementService.class);
        Observable<AnnouncementBean> observable = service.setAnnouncementList(page);
        HttpUtil.getInstance().enqueue(observable, bindLifecycle(), new BaseRxObserver<AnnouncementBean>() {
            @Override
            public void onNextImpl(AnnouncementBean data) {
                getView().stopRefresh();
                if(data == null ){
                    if(!isLoadMore){
                        getView().setEmptyView(R.layout.layout_empty);
                    }else {
                        getView().loadMoreComplete();
                    }
//                    getView().showToast("获取数据失败");
                    getView().resetCurrentPage(isLoadMore,1);
                    return;
                }
                int code = data.getCode();
                if(code == 200){
                    List<AnnouncementBean.DataBean> dataBeans = data.getData();
                    if(isLoadMore){
                        if(dataBeans == null || dataBeans.isEmpty()){
                            getView().resetCurrentPage(true,1);
                            getView().loadMoreEnd(false);
                        }else{
                            getView().addData(dataBeans);
                            getView().loadMoreComplete();
                        }
                    }else{
                        if(dataBeans == null || dataBeans.isEmpty()){
                            getView().resetCurrentPage(false,1);
                            getView().loadMoreEnd(false);
                            getView().setEmptyView(R.layout.layout_empty);
                        }else{
                            getView().setNewData(dataBeans);
                        }
                    }
                }else{
//                    getView().showToast("获取数据失败");
                    getView().setEmptyView(R.layout.layout_search_empte);
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
