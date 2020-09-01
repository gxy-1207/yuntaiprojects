package com.ytfu.yuntaifawu.ui.audio.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter
import com.ytfu.yuntaifawu.ui.home.bean.AudioListBean
import com.ytfu.yuntaifawu.utils.DensityUtil

class AudioListAdapter : QuickAdapter<AudioListBean.ListBean>(R.layout.item_audio_list) {
    override fun convert(holder: BaseViewHolder, item: AudioListBean.ListBean?) {
        if (null == item) {
            return
        }
        val roundedCorners = RoundedCorners(DensityUtil.dip2px(5F))
        val options = RequestOptions().placeholder(R.drawable.icon_seat).error(R.drawable.icon_seat).transform(CenterCrop(), roundedCorners).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH)
        Glide.with(context).load(item.post_img).apply(options).into(holder.getView(R.id.rv_audio_img))

        holder.setText(R.id.tv_audio_title, item.post_title)
        holder.setText(R.id.tv_audio_desc, item.post_excerpt)
        holder.setText(R.id.tv_audio_count, "已有%s人浏览".format(item.order_count))

    }
}
