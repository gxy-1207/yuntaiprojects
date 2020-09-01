package com.ytfu.yuntaifawu.utils.header;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.ui.audio.act.AudioClassificationActivity;
import com.ytfu.yuntaifawu.ui.contract.act.ContractClassificationActivity;
import com.ytfu.yuntaifawu.ui.contract.act.JoinMembershipActivity;
import com.ytfu.yuntaifawu.utils.header.base.BaseHeaderController;

public class HomeLawyerContractHeaderController extends BaseHeaderController<Void> {

    public HomeLawyerContractHeaderController(Context mContext) {
        super(mContext);
    }

    @Override
    protected View onCreateHeaderView() {
        return inflateView(R.layout.header_lawyer_contract);
    }

    @Override
    protected void onInitSetListener() {
        super.onInitSetListener();
        //音频
        findHeaderViewById(R.id.rv_header_audio).setOnClickListener(view ->
                AudioClassificationActivity.start(mContext));
        //合同
        findHeaderViewById(R.id.rv_header_contract).setOnClickListener(view ->
                ContractClassificationActivity.start(mContext));
        //会员
//        findHeaderViewById(R.id.rl_member).setOnClickListener(view ->
//                JoinMembershipActivity.start(mContext));
    }

    @Override
    protected void onInitSetData() {
        super.onInitSetData();
        View arcView = findHeaderViewById(R.id.ll_header_arc);
        arcView.measure(0, 0);
        float arc = arcView.getMeasuredHeight() / 2.0F;
        int color = Color.parseColor("#FDEFE6");
        GradientDrawable drawable = createRectangleDrawable(color, arc, 0, arc, 0);
        arcView.setBackgroundDrawable(drawable);
//        TextView tv_contract_money = findHeaderViewById(R.id.tv_contract_money);
//        tv_contract_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }

    //===Desc:================================================================================
    public static GradientDrawable createRectangleDrawable(int rgb, float topLeftRadius, float topRightRadius,
                                                           float bottomLeftRadius, float bottomRightRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置矩形
        //创建圆角
        float[] radius = {
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius,
                bottomLeftRadius, bottomLeftRadius};
        drawable.setCornerRadii(radius);
        drawable.setColor(rgb);
        return drawable;
    }
}
