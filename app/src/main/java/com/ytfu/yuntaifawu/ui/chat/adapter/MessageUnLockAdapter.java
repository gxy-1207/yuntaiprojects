package com.ytfu.yuntaifawu.ui.chat.adapter;

import android.graphics.BlurMaskFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.MaskFilterSpan;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.chat.bean.UnLockMessage;
import com.ytfu.yuntaifawu.utils.GlideManager;

import org.jetbrains.annotations.NotNull;

public class MessageUnLockAdapter extends QuickAdapter<UnLockMessage.XiangqinBean.XiaoxiArrBean> {

    public MessageUnLockAdapter() {
        super(R.layout.item_message_unlock);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UnLockMessage.XiangqinBean.XiaoxiArrBean item) {
        super.convert(baseViewHolder, item);

        ImageView iv_header = baseViewHolder.getView(R.id.iv_header);
        GlideManager.loadCircleImage(getContext(), item.getAvatar(), iv_header);

        baseViewHolder.setText(R.id.tv_name, item.getUser_login());
        baseViewHolder.setText(R.id.tv_time, item.getCon_date());

        //View从API Level 11才加入setLayerType方法 //设置View以软件渲染模式绘图


        baseViewHolder.getView(R.id.tv_connect).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        SpannableString sb = new SpannableString(item.getContent());
        sb.setSpan(new MaskFilterSpan(new BlurMaskFilter(12f, BlurMaskFilter.Blur.NORMAL)),
                0, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        baseViewHolder.setText(R.id.tv_connect, sb);
    }
}
