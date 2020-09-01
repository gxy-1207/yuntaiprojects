package com.ytfu.yuntaifawu.ui.complaint.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectLayout;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.ui.complaint.act.ComplaintListActivity;
import com.ytfu.yuntaifawu.ui.complaint.adaper.ComplainAdaper;
import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintClassificationBean;
import com.ytfu.yuntaifawu.ui.complaint.p.ComplaintHomePresenter;
import com.ytfu.yuntaifawu.ui.qisuzhuang.activity.ActivityQszXinagqing;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.ItemDecoration;
import com.ytfu.yuntaifawu.utils.LoginHelper;

import qiu.niorgai.StatusBarCompat;

/**
 * @Auther gxy @Date 2020/8/11 @Des 起诉状
 */
@InjectPresenter(ComplaintHomePresenter.class)
@InjectLayout(
        value = R.layout.fragment_base_recycler,
        toolbarLayoutId = R.layout.layout_toolbar_center_title_right_text)
public class ComplaintFragment
        extends BaseRecyclerViewFragment<
        ComplaintClassificationBean.ListBean,
        BaseRefreshView<ComplaintClassificationBean.ListBean>,
        ComplaintHomePresenter> {
    private static final String KEY_SHOW_BACK_BUTTON = "SHOW_BACK_BUTTON";

    private static final String KEY_OWNER = "OWNER";
    private static final String KEY_LAWYER = "LAWYER";

    public static ComplaintFragment newInstance(
            boolean showBackButton, String ownerId, String lawyerId) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_SHOW_BACK_BUTTON, showBackButton);
        args.putString(KEY_OWNER, ownerId);
        args.putString(KEY_LAWYER, lawyerId);
        ComplaintFragment fragment = newInstance();
        fragment.setArguments(args);
        return fragment;
    }

    public static ComplaintFragment newInstance() {
        return new ComplaintFragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            FragmentActivity activity = getActivity();
            if (null != activity) {
                StatusBarCompat.setStatusBarColor(activity, Color.WHITE);
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        setToolbarText(R.id.tv_global_title, "起诉状");
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#ff333333"));

        setToolbarText(R.id.tv_right, "我的起诉状");
        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#666666"));
        setToolbarViewClickListener(
                R.id.tv_right,
                view -> {
                    String owner =
                            getArgumentString(
                                    KEY_OWNER, LoginHelper.getInstance().getLoginUserId());
                    String lawyerId = getArgumentString(KEY_LAWYER, "");
                    ComplaintListActivity.Companion.starter(mContext, owner, lawyerId);
                });
        mToolbar.setBackgroundColor(CommonUtil.getColor(R.color.transparent_half));

        //        mToolbar.inflateMenu(R.menu.complaint_menu);
        if (getArgumentValue(KEY_SHOW_BACK_BUTTON, false)) {
            // back
            mToolbar.setNavigationIcon(R.drawable.fanhui_hui);
            mToolbar.setNavigationOnClickListener(
                    view -> {
                        FragmentActivity activity = getActivity();
                        if (null != activity) {
                            activity.onBackPressed();
                        }
                    });
        }

        getRecycleView().setBackgroundColor(getResources().getColor(R.color.textcolor_f2));
        getRecycleView().setPadding(0, 10, 0, 0);

        View header = new View(mContext);
        header.setBackgroundColor(getResources().getColor(R.color.textcolor_f2));
        RecyclerView.LayoutParams paramsH =
                new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT, XPopupUtils.dp2px(mContext, 10F));
        header.setLayoutParams(paramsH);
        addHeaderView(header);

        int color = Color.parseColor("#f5f5f5");
        int height = XPopupUtils.dp2px(getContext(), 0.5f);
        int pading = XPopupUtils.dp2px(getContext(), 15f);
        addItemDecoration(ItemDecoration.createVertical(color, height, pading));
        enableLoadMore(false);
        enableRefresh(true);
        getAdapter()
                .setOnItemClickListener(
                        (adapter, view, position) -> {
                            ActivityQszXinagqing.start(
                                    getActivity(),
                                    mAdapter.getData().get(position).getHref(),
                                    mAdapter.getData().get(position).getId(),
                                    getArgumentString(KEY_LAWYER, ""),
                                    mAdapter.getData().get(position).getLabel())
                            ;
                        });
    }

    //    @Override
    //    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    //        super.onCreateOptionsMenu(menu, inflater);
    //        inflater.inflate(R.menu.complaint_menu, menu);
    //        // 设置文本颜色
    //        MenuItem item = mToolbar.getMenu().findItem(R.id.id_complaint);
    //        if (null != item) {
    //            CharSequence title = item.getTitle();
    //            SpannableStringBuilder ssb = new SpannableStringBuilder(title);
    //            // 设置文字颜色。
    //            ssb.setSpan(
    //                    new ForegroundColorSpan(getResources().getColor(R.color.textColor_66)),
    //                    0,
    //                    title.length(),
    //                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    //            item.setTitle(ssb);
    //        }
    //    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //        int itemId = item.getItemId();
    //        if (itemId == R.id.id_complaint) {
    //            // 进入我的起诉状
    //            ComplaintListActivity.Companion.starter(mContext);
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }

    @Override
    protected BaseQuickAdapter<ComplaintClassificationBean.ListBean, BaseViewHolder>
    createAdapter() {
        return new ComplainAdaper();
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        getPresenter().setComplaint();
    }
}
