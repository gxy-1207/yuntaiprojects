package com.ytfu.yuntaifawu.ui.lawyer.chat.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ytfu.yuntaifawu.ui.lawyer.chat.custom.SmoothImageView;
import com.ytfu.yuntaifawu.utils.GlideManager;

// @InjectLayout(value = R.layout.activity_chat_big_image)
public class ChatBigImageActivity extends AppCompatActivity {

    private static final String KEY_IMAGEURL = "IMAGEURL";
    private static final String KEY_LOCATION_X = "LOCATION_X";
    private static final String KEY_LOCATION_Y = "LOCATION_Y";
    private static final String KEY_WIDTH = "WIDTH";
    private static final String KEY_HEIGHT = "HEIGHT";
    private static Bundle bundle;

    public static void start(
            Context context, String imageUrl, int locationX, int locationY, int width, int height) {
        bundle = new Bundle();
        bundle.putString(KEY_IMAGEURL, imageUrl);
        bundle.putInt(KEY_LOCATION_X, locationX);
        bundle.putInt(KEY_LOCATION_Y, locationY);
        bundle.putInt(KEY_WIDTH, width);
        bundle.putInt(KEY_HEIGHT, height);
        Intent starter = new Intent(context, ChatBigImageActivity.class);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imageUrl = bundle.getString(KEY_IMAGEURL);
        int locationx = bundle.getInt(KEY_LOCATION_X, 0);
        int locationy = bundle.getInt(KEY_LOCATION_Y, 0);
        int width = bundle.getInt(KEY_WIDTH, 0);
        int height = bundle.getInt(KEY_HEIGHT, 0);
        SmoothImageView imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(width, height, locationx, locationy);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);
        GlideManager.loadImageByUrl(this, imageUrl, imageView);
    }
}
