package com.ytfu.yuntaifawu.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ytfu.yuntaifawu.R;

public class RatioViewGroup extends RelativeLayout {
    private float ration = 0.0F;

    public RatioViewGroup(Context context) {
        this(context, null);
    }

    public RatioViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioViewGroup);
        ration = typedArray.getFloat(R.styleable.RatioViewGroup_rvg_ratio, 0.0F);
        typedArray.recycle();
    }

    //===Desc:=================================================================


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && ration != 0.0F) {
            float height = width / ration;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public float getRation() {
        return ration;
    }

    public void setRation(float ration) {
        this.ration = ration;
    }
}
