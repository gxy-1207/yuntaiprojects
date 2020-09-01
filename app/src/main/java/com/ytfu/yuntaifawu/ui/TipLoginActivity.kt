package com.ytfu.yuntaifawu.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import com.core.ui.activity.BaseEmptyActivity
import com.github.lee.annotation.InjectLayout
import com.github.lee.core.utils.normalIntent
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.ui.login.activity.LoginCodeActivity
import com.ytfu.yuntaifawu.ui.login.activity.LoginPhonePwdActivity
import kotlinx.android.synthetic.main.activity_tip_login.*

@InjectLayout(R.layout.activity_tip_login)
class TipLoginActivity : BaseEmptyActivity() {

    companion object {
        var isOpenLogin = false

        private val whitelistActivity = mutableListOf(LoginCodeActivity::class.java.name, LoginPhonePwdActivity::class.java.name)

        fun startWithFlag(context: Context) {
            try {
                val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val cn = am.getRunningTasks(1)[0].topActivity
                if (whitelistActivity.contains(cn?.className ?: "")) {
                    return
                }
            } catch (e: Exception) {

            }

            //            ActivityManager am =(ActivityManager) context . getSystemService (context.ACTIVITY_SERVICE);
            //            ComponentName cn = am . getRunningTasks (1).get(0).topActivity;
            //            Log.d("测试", "pkg:" + cn.getPackageName()); //包名
            //            Log.d("测试", "cls:" + cn.getClassName()); //包名加类名
            //            return cn.getClassName();

            val intent = normalIntent(context, TipLoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }


    override fun onSetViewListener() {
        super.onSetViewListener()
        cv_dialog_login.setOnClickListener {
            LoginCodeActivity.startNewTask(mContext)
            finish()
        }
    }


    //禁用返回键
    override fun onBackPressed() {
        // super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        isOpenLogin = true
    }

    override fun onDestroy() {
        super.onDestroy()
        isOpenLogin = false
    }

}