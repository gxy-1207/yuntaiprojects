package com.ytfu.yuntaifawu.ui.home.adaper;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.base.adapter.QuickAdapter;

import org.jetbrains.annotations.NotNull;

public class RewardAdapter extends QuickAdapter<Integer> {

    private Integer selected = null;

    public RewardAdapter() {
        super(R.layout.item_pay_money);
        setAnimationEnable(false);
    }
    //===Desc:================================================================================

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Integer integer) {
        super.convert(baseViewHolder, integer);
        TextView tv_item_money = baseViewHolder.getView(R.id.tv_item_money);
        int index = getData().indexOf(integer);
        if (index % 2 == 0) {
            tv_item_money.setBackgroundResource(R.drawable.tv_consulting_one_bg);
            tv_item_money.setTextColor(Color.parseColor("#EF6A6A"));
        } else {
            tv_item_money.setBackgroundResource(R.drawable.tv_consulting_two_bg);
            tv_item_money.setTextColor(Color.WHITE);
        }
        baseViewHolder.setText(R.id.tv_item_money, "悬赏" + integer + "元");
        baseViewHolder.setVisible(R.id.iv_item_icon, integer.equals(selected));
    }

    public void setSelected(Integer selected) {
        if (selected.equals(this.selected)) {
            return;
        }

        int oldSelectIndex = getData().indexOf(this.selected);
        this.selected = selected;
        int selectIndex = getData().indexOf(selected);
        notifyItemChanged(oldSelectIndex);
        notifyItemChanged(selectIndex);
    }

    public Integer getSelected() {
        return selected;
    }

    public void cleanSelected() {
        int selectIndex = getData().indexOf(selected);
        selected = null;
        notifyItemChanged(selectIndex);

    }

}
