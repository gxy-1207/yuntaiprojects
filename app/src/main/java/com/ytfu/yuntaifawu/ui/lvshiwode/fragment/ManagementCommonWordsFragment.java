package com.ytfu.yuntaifawu.ui.lvshiwode.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.app.AppConstant;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.ui.lvshiwode.activity.CommonWordsActivity;
import com.ytfu.yuntaifawu.ui.lvshiwode.activity.EditCommonWordsActivity;
import com.ytfu.yuntaifawu.ui.lvshiwode.adaper.CommonWordsAdaper;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.EditDeleteCommonWordsBean;
import com.ytfu.yuntaifawu.ui.lvshiwode.presenter.ManagementCommonWordsPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwode.view.ManagementCommonWordsView;
import com.ytfu.yuntaifawu.utils.CommonUtil;
import com.ytfu.yuntaifawu.utils.ItemDecoration;
import com.ytfu.yuntaifawu.utils.SpUtil;

/** @Auther gxy @Date 2020/7/14 @Des 管理常用语 */
@InjectPresenter(ManagementCommonWordsPresenter.class)
public class ManagementCommonWordsFragment
        extends BaseRecyclerViewFragment<
                CommonWordsListBean.DataBean,
                ManagementCommonWordsView,
                ManagementCommonWordsPresenter>
        implements ManagementCommonWordsView {
    private static final int KEY_REQUECT_CODE = 0;
    private static final String KEY_CID = "CID";

    public static ManagementCommonWordsFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY_CID, id);
        ManagementCommonWordsFragment fragment = new ManagementCommonWordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseQuickAdapter<CommonWordsListBean.DataBean, BaseViewHolder> createAdapter() {
        return new CommonWordsAdaper();
    }

    @Override
    protected void initData() {
        super.initData();
        enableLoadMore(false);
        int padding = XPopupUtils.dp2px(mContext, 15);
        int height = XPopupUtils.dp2px(mContext, 0.5f);
        View viewBottom =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.add_common_words_bottom, null, false);
        viewBottom.setLayoutParams(
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
        addBottomSuspensionView(viewBottom);
        viewBottom
                .findViewById(R.id.tv_add_common_words)
                .setOnClickListener(
                        view -> {
                            CommonWordsActivity.startActovityForResult(this, KEY_REQUECT_CODE);
                        });
        addItemDecoration(
                ItemDecoration.createVertical(
                        CommonUtil.getColor(R.color.textColor_E5E5E5), height, padding));
        getAdapter()
                .setOnItemChildClickListener(
                        (adapter, view, position) -> {
                            int id = view.getId();
                            switch (id) {
                                case R.id.tv_edit:
                                    EditCommonWordsActivity.startActivityForresult(
                                            this,
                                            KEY_REQUECT_CODE,
                                            getAdapter().getData().get(position).getId(),
                                            getAdapter().getData().get(position).getContent());
                                    break;
                                case R.id.tv_del:
                                    getPresenter()
                                            .getDelCommonWords(
                                                    getAdapter().getData().get(position),
                                                    String.valueOf(1));
                                    break;
                            }
                        });
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        String UserId = SpUtil.getString(mContext, AppConstant.UID, "");
        String cid = getArgumentString(KEY_CID, "");
        getPresenter().getCommonWordsList(UserId, cid);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == KEY_REQUECT_CODE) {
                getPresenter().refresh();
            }
        }
    }

    @Override
    public void onDelSuccess(EditDeleteCommonWordsBean deleteCommonWordsBean) {}

    @Override
    public void onfiledError(String error) {
        showToast(error);
    }
}
