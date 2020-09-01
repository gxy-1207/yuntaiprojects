package com.ytfu.yuntaifawu.utils;

import android.app.Activity;
import android.util.Log;

public class JavaScripClass {

  private Activity activity;

  public JavaScripClass(Activity activity) {
    this.activity = activity;
  }

  @android.webkit.JavascriptInterface
  public void jumpActivity(String id) {

    Log.e("sid", "-------" + id);
    //            Intent intent = new Intent(ActivityQszXinagqing.this,
    // ActivityQszXqClassify.class);
    //            intent.putExtra("id", id);
    //            startActivity(intent);
    activity.finish();
  }
}
