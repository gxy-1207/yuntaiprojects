package com.ytfu.yuntaifawu.ui.chat.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.util.DateUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.im.EmChatManager;
import com.ytfu.yuntaifawu.ui.chat.bean.LawyerListBean;
import com.ytfu.yuntaifawu.utils.GlideManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LawyerListAdapter
        extends BaseQuickAdapter<LawyerListBean.LawyerItemBean, BaseViewHolder>
        implements LoadMoreModule {
    public LawyerListAdapter() {
        super(R.layout.item_lawyer_list);
    }

    private boolean isEdit = false;
    private boolean animIsRunning = false;

    public boolean isAnimIsRunning() {
        return animIsRunning;
    }

    private List<LawyerListBean.LawyerItemBean> selected = new ArrayList<>();

    @Override
    protected void convert(BaseViewHolder helper, LawyerListBean.LawyerItemBean item) {
        ImageView ivHead = helper.getView(R.id.iv_lawyer_head);
        GlideManager.loadRadiusImage(getContext(), item.getHeaderImage(), ivHead, 4);
        helper.setText(R.id.tv_lawyer_name, item.getNickName());
        String time = item.getTime();
        long t;
        try {
            t = Long.parseLong(time);
        } catch (Exception e) {
            t = 0L;
        }
        String timeStr = "";
        try {
            timeStr = DateUtils.getTimestampString(new Date(t * 1000));
        } catch (Exception e) {
            timeStr = "";
        }
        if (TextUtils.isEmpty(timeStr)) {
            helper.setVisible(R.id.tv_lawyer_time, false);
        } else {
            helper.setText(R.id.tv_lawyer_time, timeStr);
            helper.setVisible(R.id.tv_lawyer_time, true);
        }


        int count;
        EMConversation conversation = EmChatManager.getInstance().getConversationById(item.getConversationId());
        if (null != conversation) {
            count = conversation.getUnreadMsgCount();
        } else {
            count = item.getMessagesCount();
        }
        Logger.e("未读消息数量:" + count);

        if (count > 0) {
            helper.setVisible(R.id.tv_lawyer_unread, true);
            helper.setText(R.id.tv_lawyer_unread, String.valueOf(count));
        } else {
            helper.setVisible(R.id.tv_lawyer_unread, false);
        }

        String text = item.getText();
        if (TextUtils.isEmpty(text)) {
            helper.setVisible(R.id.tv_lawyer_last, false);
        } else {
            helper.setText(R.id.tv_lawyer_last, text);
            helper.setVisible(R.id.tv_lawyer_last, true);
        }

        CheckBox cb = helper.getView(R.id.cb_lawyer_select);

        cb.measure(0, 0);
        int width = cb.getMeasuredWidth();
        if (isEdit) {
            if (cb.getVisibility() != View.VISIBLE) {
                cb.setVisibility(View.VISIBLE);
                ValueAnimator anim = ValueAnimator.ofInt(0, width);
                anim.addUpdateListener(valueAnimator -> {
                    int value = (int) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams params = cb.getLayoutParams();
                    params.width = value;
                    cb.requestLayout();
                });
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        animIsRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animIsRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                anim.setDuration(150);
                anim.start();
            }

            cb.setChecked(selected.contains(item));
        } else {
            if (cb.getVisibility() != View.GONE) {
                ValueAnimator anim = ValueAnimator.ofInt(width, 0);
                anim.addUpdateListener(valueAnimator -> {
                    int value = (int) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams params = cb.getLayoutParams();
                    params.width = value;
                    cb.requestLayout();
                });
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        animIsRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        cb.setVisibility(View.GONE);
                        animIsRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                anim.setDuration(150);
                anim.start();
            }


        }
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
        if (!isEdit) {
            selected.clear();
        }
    }

    public boolean isEdit() {
        return isEdit;
    }


    public void addOrRemoveSelect(LawyerListBean.LawyerItemBean bean) {
        if (bean.getType() == 1) {
            return;
        }
        if (selected.contains(bean)) {
            selected.remove(bean);
        } else {
            selected.add(bean);
        }
        notifyItemChanged(getData().indexOf(bean) + getHeaderLayoutCount());
    }

    public void selectAll() {
        selected.clear();

        for (LawyerListBean.LawyerItemBean bean : getData()) {
            if (bean.getType() == 1) {
                continue;
            } else {
                selected.add(bean);
            }
        }
        notifyDataSetChanged();
    }

    public void removeSelectAll() {
        selected.clear();
        notifyDataSetChanged();
    }


    public void resetSelect() {
        setEdit(false);
        selected.clear();
    }

    public List<LawyerListBean.LawyerItemBean> getSelected() {
        return selected;
    }

    public String getSelect() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selected.size(); i++) {
            LawyerListBean.LawyerItemBean item = selected.get(i);
            sb.append(item.getConversationId());
            if (i != selected.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
