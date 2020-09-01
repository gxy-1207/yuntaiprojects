package com.ytfu.yuntaifawu.ui.qisuzhuang.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintBean;
import com.ytfu.yuntaifawu.ui.qisuzhuang.activity.ActivityZjqdXq;
import com.ytfu.yuntaifawu.ui.qisuzhuang.activity.ActivityZjqdXqNew;
import com.ytfu.yuntaifawu.ui.qisuzhuang.bean.AddZjqdBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** @Auther gxy @Date 2019/12/18 @Des 添加证据清单Adaper */
public class AddZjqdAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AddZjqdBean.ListBean> mList;
    private ComplaintBean complaintBean;

    public AddZjqdAdaper(Context mContext, ComplaintBean complaintBean) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        this.complaintBean = complaintBean;
    }

    public void setmList(List<AddZjqdBean.ListBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_zjqd, parent, false);
        return new AddZjqdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddZjqdViewHolder viewHolder = (AddZjqdViewHolder) holder;
        AddZjqdBean.ListBean listBean = mList.get(position);
        viewHolder.tvZjqdName.setText(listBean.getOrder_date() + "填写");
        viewHolder.itemView.setOnClickListener(
                v -> {
                    if (null == complaintBean) {
                        ActivityZjqdXqNew.start(mContext, listBean.getId());
                    } else {
                        ActivityZjqdXq.start(mContext, listBean.getId(), complaintBean);
                    }

                    //                    Intent intent = new Intent(mContext,
                    // ActivityZjqdXq.class);
                    //                    intent.putExtra("zhengjuid", listBean.getId());
                    //                    intent.putExtra("complaintId", complaintBeanId);
                    //                    intent.putExtra("types", 2);
                    //                    mContext.startActivity(intent);
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class AddZjqdViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_zjqd_name)
        TextView tvZjqdName;

        @BindView(R.id.iv_gd)
        ImageView ivGd;

        public AddZjqdViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
