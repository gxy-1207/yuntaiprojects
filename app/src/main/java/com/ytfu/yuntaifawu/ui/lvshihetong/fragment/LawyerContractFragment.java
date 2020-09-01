package com.ytfu.yuntaifawu.ui.lvshihetong.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.ui.audio.act.AudioClassificationActivity;
import com.ytfu.yuntaifawu.ui.audio.act.AudioDetailActivity;
import com.ytfu.yuntaifawu.ui.contract.act.ContractClassificationActivity;
import com.ytfu.yuntaifawu.ui.lvshihetong.activity.ActivityContractDetails;
import com.ytfu.yuntaifawu.ui.lvshihetong.adaper.HomeLawyerContractAdapter;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractGroupHeaderBean;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.HomeContractBean;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.VipPageInfo;
import com.ytfu.yuntaifawu.ui.lvshihetong.presenter.LawyerContractPresenter;
import com.ytfu.yuntaifawu.ui.lvshihetong.view.LawyerContractView;
import com.ytfu.yuntaifawu.utils.header.HomeLawyerContractHeaderController;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

@InjectPresenter(LawyerContractPresenter.class)
public class LawyerContractFragment
        extends BaseRecyclerViewFragment<
                HomeContractBean, LawyerContractView, LawyerContractPresenter>
        implements LawyerContractView {

    private HomeLawyerContractHeaderController headerController;

    public static LawyerContractFragment newInstance() {
        return new LawyerContractFragment();
    }
    // ===Desc:================================================================================

    @Override
    public void init() {
        super.init();
        headerController = new HomeLawyerContractHeaderController(mContext);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager lm = new GridLayoutManager(mContext, 2);
        GridLayoutManager.SpanSizeLookup lookup =
                new GridLayoutManager.SpanSizeLookup() {

                    @Override
                    public int getSpanSize(int position) {
                        if (getAdapter().getItemViewType(position)
                                == BaseQuickAdapter.HEADER_VIEW) {
                            return 0;
                        }
                        HomeContractBean bean =
                                getAdapter()
                                        .getData()
                                        .get(position - getAdapter().getHeaderLayoutCount());
                        boolean header = bean.isHeader();
                        if (header) {
                            return 0;
                        }
                        return bean.getItemType();
                    }
                };
        lm.setSpanSizeLookup(lookup);
        return lm;
    }

    @Override
    protected BaseQuickAdapter<HomeContractBean, BaseViewHolder> createAdapter() {
        return new HomeLawyerContractAdapter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (null != getActivity()) {
                StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    int visibility;
                    visibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

                    getActivity().getWindow().getDecorView().setSystemUiVisibility(visibility);
                }
            }
        }
    }

    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        getAdapter()
                .setOnItemClickListener(
                        (adapter, view, position) -> {
                            HomeContractBean homeContractBean =
                                    getAdapter().getData().get(position);
                            if (homeContractBean.isHeader()) {
                                homeContractBean.getGroupHeaderBean().getClickRun().run();
                                return;
                            }
                            if (homeContractBean.isAudio()) {
                                VipPageInfo.AudioBean audioBean = homeContractBean.getAudioBean();
                                AudioDetailActivity.Companion.start(mContext, audioBean.getId());
                            } else {
                                VipPageInfo.ContractBean contractBean =
                                        homeContractBean.getContractBean();
                                ActivityContractDetails.start(mContext, contractBean.getId());
                            }
                        });
    }

    @Override
    protected void initData() {
        super.initData();
        hideLoading();
        Toolbar toolbar = inflateToolbar(R.layout.layout_toolbar_center_title);
        toolbar.setBackgroundColor(Color.WHITE);
        TextView tv_global_title = toolbar.findViewById(R.id.tv_global_title);
        tv_global_title.setTextColor(Color.parseColor("#303030"));
        tv_global_title.setText("合同");
        addTopSuspensionView(toolbar);

        addHeaderView(headerController.getHeaderView());

        View footer = new View(mContext);
        footer.setBackgroundColor(Color.WHITE);
        RecyclerView.LayoutParams params =
                new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT, XPopupUtils.dp2px(mContext, 15F));
        footer.setLayoutParams(params);
        addFooterView(footer);

        enableRefresh(true);
        enableLoadMore(false);
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        getPresenter().getVipPageInfo();
    }

    @Override
    public void onGetVipPageInfo(VipPageInfo data) {
        List<HomeContractBean> list = new ArrayList<>();
        List<VipPageInfo.ContractBean> contract = data.getContract();
        if (null != contract && !contract.isEmpty()) {
            ContractGroupHeaderBean contractHeaderBean =
                    new ContractGroupHeaderBean(
                            "合同文书",
                            -1,
                            () -> {
                                ContractClassificationActivity.start(mContext);
                            });
            list.add(new HomeContractBean(contractHeaderBean));
            for (int i = 0; i < contract.size(); i++) {
                list.add(new HomeContractBean(contract.get(i)));
            }
        }
        // 音频
        List<VipPageInfo.AudioBean> audio = data.getAudio();
        if (null != audio && !audio.isEmpty()) {
            ContractGroupHeaderBean audioHeaderBean =
                    new ContractGroupHeaderBean(
                            "音频",
                            R.mipmap.icon_audio_vip,
                            new Runnable() {
                                @Override
                                public void run() {
                                    AudioClassificationActivity.start(mContext);
                                }
                            });
            list.add(new HomeContractBean(audioHeaderBean));
            for (int i = 0; i < audio.size(); i++) {
                list.add(new HomeContractBean(audio.get(i)));
            }
        }
        setNewData(list);
    }
}
