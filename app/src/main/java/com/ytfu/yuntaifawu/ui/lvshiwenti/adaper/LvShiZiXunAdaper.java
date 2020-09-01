package com.ytfu.yuntaifawu.ui.lvshiwenti.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;
import com.ytfu.yuntaifawu.ui.lvshiwenti.activity.WenTiXiangQIngAtivity;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.LawyerConsultingBean;
import com.ytfu.yuntaifawu.ui.lvshiwenti.bean.LvShiZiXunListBean;
import com.ytfu.yuntaifawu.ui.mine.bean.MineZiXunBean;
import com.ytfu.yuntaifawu.utils.GlideManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther gxy
 * @Date 2020/5/25
 * @Des 律师端问题列表adaper
 */
public class LvShiZiXunAdaper extends QuickAdapter<LawyerConsultingBean.ListBean> {

    public LvShiZiXunAdaper() {
        super(R.layout.lvshi_zixun_recycleview_item);
        addChildClickViewIds(R.id.tv_quick_answer);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LawyerConsultingBean.ListBean listBean) {
        super.convert(baseViewHolder, listBean);
        GlideManager.loadImageByUrl(getContext(),listBean.getAvatar(),baseViewHolder.getView(R.id.iv_zixun_icon));
        baseViewHolder.setText(R.id.tv_zixun_name,listBean.getUser_login());
        baseViewHolder.setText(R.id.tv_zixun_time,listBean.getConsult_date());
        baseViewHolder.setText(R.id.tv_zixun_content,listBean.getConsult_content());
        baseViewHolder.setText(R.id.tv_zixun_type,listBean.getConsult_type());
        baseViewHolder.setText(R.id.tv_zixun_num,"已回复(" + listBean.getSum() + ")");
        int qiangda = listBean.getQiangda();
        baseViewHolder.setEnabled(R.id.tv_quick_answer,qiangda == 0);
        baseViewHolder.setVisible(R.id.iv_choice,listBean.getPrice_type() == 1);
    }
//    private Context mContext;
//    private List<LvShiZiXunListBean.ListBean> beanList;
//
//    public LvShiZiXunAdaper() {
//
//    }
//
//    public void setListBeans(List<LvShiZiXunListBean.ListBean> listBeans) {
//        this.beanList = listBeans;
//        notifyDataSetChanged();
//    }
//
//    public void addListBeans(List<LvShiZiXunListBean.ListBean> listBeans) {
//        if (!listBeans.isEmpty()) {
//            this.beanList.addAll(listBeans);
//            notifyDataSetChanged();
//        }
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.lvshi_zixun_recycleview_item, parent, false);
//        return new LvShiZiXunListViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        LvShiZiXunListViewHolder viewHolder = (LvShiZiXunListViewHolder) holder;
//        LvShiZiXunListBean.ListBean listBean = beanList.get(position);
//        GlideManager.loadImageByUrl(mContext, listBean.getAvatar(), viewHolder.ivZixunIcon);
//        viewHolder.tvZixunName.setText(listBean.getUser_login());
//        viewHolder.tvZixunTime.setText(listBean.getConsult_date());
//        viewHolder.tvZixunContent.setText(listBean.getConsult_content());
//        viewHolder.tvZixunType.setText(listBean.getConsult_type());
//        viewHolder.tvZixunNum.setText("已回复(" + listBean.getSum() + ")");
//        if (listBean.getType() == 1) {
//            viewHolder.tvZhifuleixing.setVisibility(View.GONE);
//        } else if(listBean.getType() == 2){
//            viewHolder.tvZhifuleixing.setVisibility(View.VISIBLE);
//            viewHolder.tvZhifuleixing.setText("悬赏咨询");
//        }
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, WenTiXiangQIngAtivity.class);
//                intent.putExtra("id",listBean.getId());
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return beanList.size();
//    }
//
//    public class LvShiZiXunListViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.iv_zixun_icon)
//        ImageView ivZixunIcon;
//        @BindView(R.id.tv_zixun_name)
//        TextView tvZixunName;
//        @BindView(R.id.tv_zixun_time)
//        TextView tvZixunTime;
//        @BindView(R.id.tv_zhifuleixing)
//        TextView tvZhifuleixing;
//        @BindView(R.id.tv_zixun_content)
//        TextView tvZixunContent;
//        @BindView(R.id.tv_zixun_type)
//        TextView tvZixunType;
//        @BindView(R.id.tv_zixun_num)
//        TextView tvZixunNum;
//
//        public LvShiZiXunListViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
}
