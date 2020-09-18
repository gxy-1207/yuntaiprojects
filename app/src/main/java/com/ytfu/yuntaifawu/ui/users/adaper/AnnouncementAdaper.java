package com.ytfu.yuntaifawu.ui.users.adaper;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.users.bean.AnnouncementBean;

import org.jetbrains.annotations.NotNull;

/** @Auther gxy @Date 2020/6/10 @Des 公告Adaper */
public class AnnouncementAdaper extends QuickAdapter<AnnouncementBean.DataBean> {
  public AnnouncementAdaper() {
    super(R.layout.announcement_recycleview_item);
  }

  @Override
  protected void convert(
      @NotNull BaseViewHolder baseViewHolder, AnnouncementBean.DataBean dataBean) {
    super.convert(baseViewHolder, dataBean);
    baseViewHolder.setText(R.id.tv_title, dataBean.getTitle());
    baseViewHolder.setText(R.id.tv_time, dataBean.getAddtime());
    baseViewHolder.setGone(R.id.tv_top, !"1".equals(dataBean.getIstop()));
    baseViewHolder.setText(R.id.tv_connect, dataBean.getContent());
    baseViewHolder.setGone(R.id.red_dot, 1 != dataBean.getRand_type());
  }
}
