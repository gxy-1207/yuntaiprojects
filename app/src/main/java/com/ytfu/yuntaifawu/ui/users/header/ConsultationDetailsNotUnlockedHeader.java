package com.ytfu.yuntaifawu.ui.users.header;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.domain.EventBusMessage;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;
import com.ytfu.yuntaifawu.utils.GlideManager;
import com.ytfu.yuntaifawu.utils.header.base.BaseHeaderController;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther gxy
 * @Date 2020/6/15
 * @Des 咨询详情未解锁header
 */
public class ConsultationDetailsNotUnlockedHeader extends BaseHeaderController<ConsultationDetailsBean.ContentBean> {


    private static class AnimHandle extends Handler {

        private WeakReference<ConsultationDetailsNotUnlockedHeader> wr;

        public AnimHandle(ConsultationDetailsNotUnlockedHeader header) {
            this.wr = new WeakReference<>(header);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            ConsultationDetailsNotUnlockedHeader header = wr.get();
            if (null == header) {
                return;
            }
            if (msg.what == 0) {
                int current = msg.arg1;
                List<String> list = (List<String>) msg.obj;
                int total = list.size();
                header.avatarView(list.get(current), total, current);
                removeMessages(0);
                if (current + 1 >= total) {
                    removeMessages(0);
                } else if (current >= 8) {
                    removeMessages(0);
                } else {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = list;
                    message.arg1 = current + 1;
                    sendMessageDelayed(message, 1000);
                }

            }
        }
    }


    private AnimHandle handle = new AnimHandle(this);

    public ConsultationDetailsNotUnlockedHeader(Context mContext) {
        super(mContext);
    }

    @Override
    protected View onCreateHeaderView() {
        return inflateView(R.layout.consultation_details_not_nulked_header);
    }

    public void onDestroy() {
        handle.removeCallbacksAndMessages(null);
    }

    public void avatarView(String url, int count, int current) {
        LinearLayout linearLayout = findHeaderViewById(R.id.ll_ivs);
        if (linearLayout.getChildCount() > 9) {
            return;
        }
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(mContext);
        imageView.setElevation(count - current);
        //设置图片宽高
        int size = XPopupUtils.dp2px(mContext, 30);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        if (current != 0) {
            layoutParams.leftMargin = -XPopupUtils.dp2px(mContext, 10);
        }

        if (current == 8) {
            imageView.setImageResource(R.drawable.tupian_shenglue);
        } else {
            GlideManager.loadCircleImage(mContext, url, imageView);
        }
        setVIewText(R.id.tv_total, String.format("律师回复(%d)", current + 1));
        setVIewText(R.id.tv_sum, String.valueOf(current + 1));
        linearLayout.addView(imageView, layoutParams);
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                imageView.setAlpha(value);
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    @Override
    public void render(ConsultationDetailsBean.ContentBean data) {
        super.render(data);

        EventBusMessage eventBusMessage = new EventBusMessage();
        eventBusMessage.what = 100;
        eventBusMessage.obj = data.getCount();
        //        EventME
        EventBus.getDefault().postSticky(eventBusMessage);


        setVIewText(R.id.tv_sum, data.getCount());
        setVIewText(R.id.id_tv_unlock, data.getJiesuo_anniu());
        setVIewText(R.id.tv_one, data.getJiesuo_chakan());
        setVIewText(R.id.tv_view_now, data.getJiesuo_anniu());
        List<String> shuzu = data.getShuzu();
        if (shuzu == null) {
            shuzu = new ArrayList<>();
        }
        int count = 0;
        if (shuzu.size() > 9) {
            count = 9;
        } else {
            count = shuzu.size();
        }

        LinearLayout linearLayout2 = findHeaderViewById(R.id.ll_ivs);
        linearLayout2.removeAllViews();

        if (data.getIs_first() == 0) {
            //动画
            Message message = Message.obtain();
            message.what = 0;
            message.obj = shuzu;
            message.arg1 = 0;
            handle.sendMessage(message);
        } else {
            for (int i = 0; i < count; i++) {
                avatarView(shuzu.get(i), shuzu.size(), i);
            }
            setVIewText(R.id.tv_total, String.format("律师回复(%s)", data.getCount()));
            setVIewText(R.id.tv_sum, String.valueOf(data.getCount()));
        }


        //立即查看，开始私聊
        findHeaderViewById(R.id.tv_view_now).setOnClickListener(v -> {
            if (unlockClickListener != null) {
                unlockClickListener.unlockClick(data);
            }
        });
    }

    private UnlockClickListener unlockClickListener;

    public void getUnlockClickListener(UnlockClickListener unlockClickListener) {
        this.unlockClickListener = unlockClickListener;
    }

    public interface UnlockClickListener {
        void unlockClick(ConsultationDetailsBean.ContentBean contentBean);
    }
}
