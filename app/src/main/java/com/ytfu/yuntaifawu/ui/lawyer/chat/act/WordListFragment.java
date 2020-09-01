package com.ytfu.yuntaifawu.ui.lawyer.chat.act;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.lawyer.chat.p.WordListPresenter;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.CommonWordsListBean;
import com.ytfu.yuntaifawu.utils.ItemDecoration;
import com.ytfu.yuntaifawu.utils.LoginHelper;

import org.jetbrains.annotations.NotNull;

@InjectPresenter(WordListPresenter.class)
public class WordListFragment extends
        BaseRecyclerViewFragment<CommonWordsListBean.DataBean,
                BaseRefreshView<CommonWordsListBean.DataBean>,
                WordListPresenter> {

    public interface OnWordClickListener {
        void onWordCLick(CommonWordsListBean.DataBean item);
    }

    private OnWordClickListener listener;

    public void setListener(OnWordClickListener listener) {
        this.listener = listener;
    }

    private static final String KEY_CID = "CID";

    public static WordListFragment newInstance(String cid) {
        Bundle args = new Bundle();
        args.putString(KEY_CID, cid);
        WordListFragment fragment = new WordListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseQuickAdapter<CommonWordsListBean.DataBean, BaseViewHolder> createAdapter() {
        return new QuickAdapter<CommonWordsListBean.DataBean>(R.layout.item_chat_word_item) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, CommonWordsListBean.DataBean dataBean) {
                baseViewHolder.setText(R.id.tv_text1, dataBean.getContent());
            }
        };
    }


    //===Desc:================================================================================


    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        getAdapter().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (null != listener) {
                    listener.onWordCLick(getAdapter().getData().get(position));
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        enableLoadMore(false);
        enableRefresh(false);

        addItemDecoration(ItemDecoration.createVertical(
                Color.parseColor("#E5E5E5"),
                XPopupUtils.dp2px(mContext, 0.5F),
                XPopupUtils.dp2px(mContext, 15)
        ));

    }


    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        String userId = LoginHelper.getInstance().getLoginUserId();
        String cid = getArgumentString(KEY_CID, "0");
        getPresenter().getReplyContent(userId, cid);
    }
}
