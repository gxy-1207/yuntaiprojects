package com.ytfu.yuntaifawu.ui.lvshihetong.adaper;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.util.XPopupUtils;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.SectionMultiQuickAdapter;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.ContractGroupHeaderBean;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.HomeContractBean;
import com.ytfu.yuntaifawu.ui.lvshihetong.bean.VipPageInfo;
import com.ytfu.yuntaifawu.utils.DensityUtil;
import com.ytfu.yuntaifawu.utils.GlideManager;

import org.jetbrains.annotations.NotNull;

public class HomeLawyerContractAdapter extends SectionMultiQuickAdapter<HomeContractBean> {

    public HomeLawyerContractAdapter() {
        super(R.layout.header_contract_item);
        addItemType(1, R.layout.item_vip_page_audio);
        addItemType(2, R.layout.item_vip_page_contract);
    }

    @Override
    protected void convert(
            @NotNull BaseViewHolder baseViewHolder, HomeContractBean homeContractBean) {
        boolean audio = homeContractBean.isAudio();
        if (audio) {
            VipPageInfo.AudioBean audioBean = homeContractBean.getAudioBean();
            ImageView iv_info_audio_img = baseViewHolder.getView(R.id.iv_info_audio_img);


            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(DensityUtil.dip2px(5));

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.icon_seat)
                    .error(R.drawable.icon_seat)
                    .transform( new CenterCrop(),roundedCorners)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(getContext())
                    .load(audioBean.getPost_img())
                    .apply(options)
                    .into(iv_info_audio_img);

            baseViewHolder.setText(R.id.tv_info_audio_title, audioBean.getPost_title());
            baseViewHolder.setText(R.id.tv_info_audio_label, audioBean.getLabel());
            String showText = String.format("%s播放", audioBean.getOrder_count());
            baseViewHolder.setText(R.id.tv_info_audio_count, showText);

        } else {
            VipPageInfo.ContractBean contractBean = homeContractBean.getContractBean();
            TextView tv_info_title = baseViewHolder.getView(R.id.tv_info_title);
            int i = getData().indexOf(homeContractBean) - getHeaderLayoutCount();
            int imgRes;
            switch (i) {
                case 0:
                    imgRes = R.mipmap.icon_num0;
                    break;
                case 1:
                    imgRes = R.mipmap.icon_num1;
                    break;
                case 2:
                    imgRes = R.mipmap.icon_num2;
                    break;
                case 3:
                    imgRes = R.mipmap.icon_num3;
                    break;
                case 4:
                    imgRes = R.mipmap.icon_num4;
                    break;
                default:
                    imgRes = -1;
                    break;
            }
            if (imgRes != -1) {
                Drawable drawable = getContext().getResources().getDrawable(imgRes);
                drawable.setBounds(
                        0,
                        0,
                        XPopupUtils.dp2px(getContext(), 10f),
                        XPopupUtils.dp2px(getContext(), 12f));
                tv_info_title.setCompoundDrawables(drawable, null, null, null);
            }

            baseViewHolder.setText(R.id.tv_info_title, contractBean.getTitle());
            String showText = String.format("%s人下载", contractBean.getDownload_count());
            baseViewHolder.setText(R.id.tv_info_count, showText);
        }
    }

    @Override
    protected void convertHeader(
            @NotNull BaseViewHolder baseViewHolder, @NotNull HomeContractBean homeContractBean) {
        ContractGroupHeaderBean headerBean = homeContractBean.getGroupHeaderBean();
        baseViewHolder.setText(R.id.tv_header_contract_title, headerBean.getShowText());
        ImageView iv_header_contract_icon = baseViewHolder.getView(R.id.iv_header_contract_icon);
        if (headerBean.getIcon() == -1) {
            iv_header_contract_icon.setVisibility(View.GONE);
        } else {
            iv_header_contract_icon.setVisibility(View.VISIBLE);
            iv_header_contract_icon.setImageResource(headerBean.getIcon());
        }
    }
}
