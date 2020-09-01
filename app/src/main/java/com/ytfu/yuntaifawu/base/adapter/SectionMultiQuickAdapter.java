package com.ytfu.yuntaifawu.base.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组多条目布局
 */
public abstract class SectionMultiQuickAdapter<T extends SectionEntity>
        extends BaseSectionQuickAdapter<T, BaseViewHolder> implements LoadMoreModule {

    //    public SectionQuickAdapter(int sectionHeadResId, @Nullable List<T> data) {
    //        super(sectionHeadResId, data);
    //    }
    //
    //    public SectionQuickAdapter(int sectionHeadResId) {
    //        super(sectionHeadResId);
    //    }

    public SectionMultiQuickAdapter(int sectionHeadResId) {
//        super(sectionHeadResId, layoutResId, data);
        super(sectionHeadResId, new ArrayList<>());
        // （只有这里有变化）
//        addItemType(类型, 你的布局id_1)
//        addItemType(类型2, 你的布局id_2)
    }

    //===Desc:================================================================================



}
