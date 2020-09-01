package com.ytfu.yuntaifawu.ui.audio.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lee.annotation.InjectPresenter;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.BaseRecyclerViewFragment;
import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.audio.act.AudioDetailActivity;
import com.ytfu.yuntaifawu.ui.audio.p.AudioDetailAboutPresenter;
import com.ytfu.yuntaifawu.ui.home.bean.AudioDetailsBean;
import com.ytfu.yuntaifawu.utils.DensityUtil;
import com.ytfu.yuntaifawu.utils.view.RatioImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@InjectPresenter(AudioDetailAboutPresenter.class)
public class AudioDetailAboutFragment extends
        BaseRecyclerViewFragment<AudioDetailsBean.AudioListBean, BaseRefreshView<AudioDetailsBean.AudioListBean>, AudioDetailAboutPresenter> {


    private static final String KEY_ABOUT_AUDIO_LIST = "ABOUT_AUDIO_LIST";

    public static AudioDetailAboutFragment newInstance(List<AudioDetailsBean.AudioListBean> list) {
        Bundle args = new Bundle();
        ArrayList<AudioDetailsBean.AudioListBean> data = new ArrayList<>(list);
        args.putParcelableArrayList(KEY_ABOUT_AUDIO_LIST, data);
        AudioDetailAboutFragment fragment = new AudioDetailAboutFragment();
        fragment.setArguments(args);
        return fragment;
    }


    //===Desc:================================================================================


    @Override
    protected BaseQuickAdapter<AudioDetailsBean.AudioListBean, BaseViewHolder> createAdapter() {
        return new QuickAdapter<AudioDetailsBean.AudioListBean>(R.layout.item_audio_list) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, AudioDetailsBean.AudioListBean item) {
                RoundedCorners rc = new RoundedCorners(DensityUtil.dip2px(5F));

                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.icon_seat)
                        .error(R.drawable.icon_seat)
                        .transform(new CenterCrop(), rc)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH);
                RatioImageView iv = baseViewHolder.getView(R.id.rv_audio_img);
                Glide.with(getContext()).load(item.getPost_img())
                        .apply(options)
                        .into(iv);

                baseViewHolder.setText(R.id.tv_audio_title, item.getPost_title());
                baseViewHolder.setText(R.id.tv_audio_desc, item.getPost_excerpt());

                baseViewHolder.setText(R.id.tv_audio_count,  String.format("已有%s人浏览",item.getOrder_count()));
            }
        };
    }

    @Override
    protected void onLoadMoreOrRefresh(boolean isLoadMore) {
        if (null != getArguments()) {
            ArrayList<AudioDetailsBean.AudioListBean> list = getArguments().getParcelableArrayList(KEY_ABOUT_AUDIO_LIST);
            setNewData(list);
        } else {
            setEmptyView(R.layout.layout_empty);
        }
    }


    @Override
    protected void setViewListener(View rootView) {
        super.setViewListener(rootView);
        getAdapter().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AudioDetailActivity.Companion.start(mContext, getAdapter().getData().get(position).getId());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        enableRefresh(false);
        enableLoadMore(false);
    }
}
