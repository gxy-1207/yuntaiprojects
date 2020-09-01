package com.ytfu.yuntaifawu.utils.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.ui.chat.bean.UnLockMessage;
import com.ytfu.yuntaifawu.utils.GlideManager;

import java.util.ArrayList;
import java.util.List;

public class UnLockMessageClickDialog extends CenterPopupView {

    public UnLockMessageClickDialog(@NonNull Context context) {
        super(context);
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_un_lock_message;
    }

    private Handler handler = new Handler();

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void render(UnLockMessage.XiangqinBean data) {
        TextView tv_sum = findViewById(R.id.tv_sum);
        tv_sum.setText(String.valueOf(data.getCount()));

        TextView tv_one = findViewById(R.id.tv_one);
        tv_one.setText(data.getJiesuo_chakan());

        TextView tv_view_now = findViewById(R.id.tv_view_now);
        tv_view_now.setText(data.getJiesuo_anniu());

        //===Desc:================================================================================

        RelativeLayout linearLayout = findViewById(R.id.ll_iv);
        List<String> shuzu = data.getShuzu();
        if (shuzu == null) {
            shuzu = new ArrayList<>();
        }
        int count = 0;
        if (shuzu.size() > 8) {
            count = 8;
        } else {
            count = shuzu.size();
        }
        linearLayout.removeAllViews();

        int finalCount = count;
        List<String> finalShuzu = shuzu;
        for (int i = finalCount - 1; i >= 0; i--) {
            ImageView imageView = new ImageView(getContext());
            //设置图片宽高
            int size = XPopupUtils.dp2px(getContext(), 30);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(size, size);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            layoutParams.rightMargin = XPopupUtils.dp2px(getContext(), 20) * (finalCount - (i + 1));
            imageView.setLayoutParams(layoutParams);
            int finalI = i;
            if (isShow()) {
                handler.post(() -> {
                    if (finalI == 7) {
                        imageView.setImageResource(R.drawable.tupian_shenglue);
                    } else {
                        GlideManager.loadCircleImage(getContext(), finalShuzu.get(finalI), imageView);
                    }
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
                });
            }
        }

        //立即查看，开始私聊
        findViewById(R.id.tv_view_now).setOnClickListener(v -> {
            if (unlockClickListener != null) {
                unlockClickListener.unlockClick(data);
            }
        });
    }


    private UnlockMessageClickListener unlockClickListener;

    public void setUnlockClickListener(UnlockMessageClickListener unlockClickListener) {
        this.unlockClickListener = unlockClickListener;
    }

    public interface UnlockMessageClickListener {
        void unlockClick(UnLockMessage.XiangqinBean contentBean);
    }

}
