package com.ytfu.yuntaifawu.utils.dialog;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.orhanobut.logger.Logger;
import com.ytfu.yuntaifawu.R;

import java.util.List;

/**
 * 底部滚轮dialog
 */
public class BottomWheelDialog<T> extends BottomPopupView {
    private TextView tv_dialog_cancel;
    private TextView tv_dialog_ok;
    private WheelView wv_dialog_content;
    private List<T> data;

    public BottomWheelDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_bottom_wheel;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_dialog_cancel = findViewById(R.id.tv_dialog_cancel);
        tv_dialog_ok = findViewById(R.id.tv_dialog_ok);
        wv_dialog_content = findViewById(R.id.wv_dialog_content);

        tv_dialog_cancel.setOnClickListener(v -> dismiss());
        tv_dialog_ok.setOnClickListener(v -> {

        });


    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * 0.55);
    }

    @Override
    public int getMinimumHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * 0.55);
    }


    public void setData(List<T> list) {
        this.data = list;
    }

    public void test() {
        wv_dialog_content.setBackgroundColor(Color.RED);
        wv_dialog_content = findViewById(R.id.wv_dialog_content);
        wv_dialog_content.setTextSize(20);
        wv_dialog_content.setLineSpacingMultiplier(2f);
        wv_dialog_content.setDividerType(WheelView.DividerType.FILL);

        wv_dialog_content.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Logger.e("select index = " + index);
            }
        });

        ArrayWheelAdapter<T> adapter = new ArrayWheelAdapter<>(data);
        wv_dialog_content.setAdapter(adapter);
    }

}
